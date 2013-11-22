package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.libvirt.Domain;
import org.libvirt.LibvirtException;

public class DomainManager 
{
	public int DomainID;
	public SetEnvironment environment;
	public String vmtypefile;
	public StorageManager storagemanager;
	public DomainManager(SetEnvironment env, String args, StorageManager storagemanager1)
	{
		DomainID=0;
		environment = env;
		vmtypefile=args;
		storagemanager = storagemanager1;
	}
	public String VM_Creation(String name, String  type1, String image_id1, String desc)  
	{
		int type = Integer.parseInt(type1);
		if(!environment.ImageList.containsKey(Integer.parseInt(image_id1)))
		{
			System.out.println("Can Not found Image list");
			return "{\"status\" : 0}";
		}
		ResorceRequested rreq=environment.VMType.get((long)type);
		
		Nodes node=environment.getNodeSelected(rreq.cpu,rreq.ram,rreq.disk);
		if(node==null)
		{
			System.out.println("Sorry Machine not available....");
			return "{\"status\" : 0}";
		}
		//System.out.println("node:"+node.id);
		UUID domainid = UUID.randomUUID();
		//String image_path=environment.ImageList.get(image_id);
		/*
		 * 			TODODODODODOD
		 * 			Image Add
		 */
		
		String image_path = environment.ImageList.get(Integer.parseInt(image_id1));
		System.out.println("image:"+image_path);
		String xml;
		Domain dom = null;
		try {
			xml = environment.domainXML(domainid, node, name, (long)type, image_path);
			try {
				dom=node.conn.domainDefineXML(xml);
				dom.create();
				node.cpus -= rreq.cpu;
				node.memory -= rreq.ram*1024;
				DomainInfo DI = new DomainInfo();
				DomainID++;
				DI.setDomain(dom);
				DI.setUsername(name);
				DI.setUuid(domainid);
				DI.setVMType(type);
				DI.setPMID(node.pm_id);
				DI.setDesc(desc);
				//DI.num_disk=3;
				environment.domainlist.put(DomainID, DI);
				System.out.println("cpu:"+node.cpus + "ram:"+node.memory);
				//System.out.println("size:"+environment.domainlist.size());
				return "{\"vmid\" :" + DomainID+"}";
			} 
			catch (LibvirtException e) 
			{
				
				e.printStackTrace();
				return "{\"status\" : 0}";
			}
			
		} catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"status\" : 0}";
		} catch (TransformerException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"status\" : 0}";
		}
		
		
		
	}
	public String VM_Destroy(String ii)
	{
		int i= Integer.parseInt(ii);
		System.out.println("size:"+environment.domainlist.size());
		if(!environment.domainlist.containsKey(i))
		{
			
			return "{\"status\" : 0}";
		}
		if(environment.storagelist!=null)
		{	
			Iterator<Entry<Integer, DiskImage>> it = environment.storagelist.entrySet().iterator();
			while(it.hasNext())
			{
				Entry<Integer, DiskImage> entry = it.next();
				if(entry.getValue().status==1 && entry.getValue().vmid==i)
				{
					storagemanager.Volume_Detach(entry.getKey());
					entry.getValue().vmid=-1;
					entry.getValue().status=0;
				}
			}
		}
		DomainInfo DI = environment.domainlist.get(i);
		Domain dom = DI.getDomain();
		
		
		try {
			int k=0;
			while(dom.isActive()!=1)
			{
				k++;
				System.out.println("Not Active");
				if(k==1)
					dom.create();
			}
			dom.destroy();
			dom.undefine();
			Nodes host=new Nodes();
			for(int j=0;j<environment.machines.size();j++)
			{
				if( environment.machines.get(j).pm_id==DI.getPMID())
					host = environment.machines.get(j);
			}
			
			host.cpus += environment.VMType.get(DI.getVMType()).cpu;
			host.memory += environment.VMType.get(DI.getVMType()).ram*1024;
			System.out.println("cpu:"+host.cpus + "ram:"+host.memory);
			environment.domainlist.remove(i);
			return "{\"status\" : 1}";
		}
		catch (LibvirtException e) 
		{
						
			e.printStackTrace();
			return "{\"status\" : 0}";
		}
		
	}
	public String VM_Query(String ii) 
	{
		int i = Integer.parseInt(ii);
		if(!environment.domainlist.containsKey(i))
		{
			
			return "{\"status\" : 0}";
		}
		DomainInfo DI = environment.domainlist.get(i);
		
		return "{\"vmid\":"+i +" ,\"name\":\""+DI.getUsername() + "\" ,\"instance_type\":" + DI.getVMType() +" ,\"pmid\":"+DI.getPMID()+"}";
	}
	public String VM_Type()
	{
		System.out.println("VM Types Request");
		String str = new String();
		try 
		{
			
			FileReader fr = new FileReader(vmtypefile);
			BufferedReader br = new BufferedReader(fr);
			String line=new String();
			
			while((line=br.readLine())!=null)
					str=str+line;
			br.close();
		}
		catch (IOException e) 
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return str;
	}	
	
	public String List_Images()
	{
		String str= new String();
		str=str+"{\"images\":[";
		HashMap<Integer, String> map = environment.ImageList;
		int i=0;
		for (Entry<Integer, String> entry : map.entrySet()) 
		{
			i++;
			str=str+"{\"id\":" + entry.getKey() + " ,\"name\":\"" +entry.getValue() + "\"}";
			if(i!=map.size())
				str=str+",";
		}
		str=str+"]}";

		return str;
	}
	public String VM_Instances() 
	{
		
		String result = new String();
		
		Iterator<Entry<Integer, DomainInfo>> it = environment.domainlist.entrySet().iterator();
		int i=0;
		result=result+"{\"instances\":[";
		while(it.hasNext())
		{
			i++;
			Map.Entry<Integer,DomainInfo> pairs = (Entry<Integer, DomainInfo>)it.next();
			DomainInfo di = (DomainInfo) pairs.getValue();
			result=result+"{ \"id\":"+ pairs.getKey() + ",\"name\":\""+di.getUsername() +  "\",\"instance\":\""+di.getVMType()+ "\",\"disk\":\""+di.vol.size()+ "\",\"desc\":\""+di.getDesc()+"\"}";
			if(i!=environment.domainlist.size())
				result=result+",";
		}
		result=result+"]}";
		return result;
	}
}
