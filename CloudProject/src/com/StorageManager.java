package com;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.libvirt.LibvirtException;

public class StorageManager 
{
	SshConnection ssh;
	int id;
	SetEnvironment environment;

	public StorageManager(SshConnection sshobj,SetEnvironment env)
	{	
		ssh=sshobj;
		id=0;
		environment = env;
	}
	
	///Code to create volume in storage pool
	public String Volume_Creation(String name, int size)
	{
		id++;
		if( ssh.Execute("sudo rbd create "+id +" --size "+size*1024) !=0 )
		{
			
			System.out.println("Fail to create DiskImage check ceph........");
			return "{\"status\" : 0}";
		}
		else
		{
			DiskImage di = new DiskImage(0,name,size,-1);
			environment.storagelist.put(id,di);
			System.out.println("Volume Sucessfully Created.....");
			return "{\"volumeid\" :" + id +"}";
		}
	}
	
	///Code to Query already created Volume in pool
	public String Volume_Query(int id)
	{
		if(!environment.storagelist.containsKey(id))
		{
			System.out.println("Cannot find volume with id" + id +"......");
			return "{\"error \": \"volumeid : "+ id +" does not exist\"}";
			
		}
		else
		{
			DiskImage di = environment.storagelist.get(id);
			if(di.status==0)
			{
				
				return "{\"volumeid : "+ id +",\"name\":\"" + di.name + "\"" + ",\"size\":" + di.size + ",\"status\":\"available\" " +"\"vmid\":" + di.vmid + "}";
			}
			else
			{
				return "{\"volumeid : "+ id +",\"name\":\"" + di.name + "\"" + ",\"size\":" + di.size + ",\"status\":\"attached\" " +"\"vmid\":" + di.vmid + "}";
			}
		}
	}
	
	///Function to destroy already created volume in storage pool
	public String Volume_Destroy(int volumeid)
	{
		if(!environment.storagelist.containsKey(volumeid))
		{
			System.out.println("Cannot find volume with id" + volumeid +"......");
			return "{\"status\" : 0}";
		}
		if(environment.storagelist.get(volumeid).status!=0)
		{
			System.out.println("Volume already attached to vm " + environment.storagelist.get(volumeid).vmid +"......");
			return "{\"status\" : 0}";
		}
		if(ssh.Execute("sudo rbd -p rbd rm "+volumeid)==0)
		{
			System.out.println("Destroyed Sucessfully.....");
			environment.storagelist.remove(volumeid);
			return "{\"status\" : 1}";
		}
		else
		{
			System.out.println("Failed to destroy....");
			return "{\"status\" : 0}";
		}
				
		
	}
	
	///Code to detach volume from virtual machine
	
	public String Volume_Detach(int volumeid)
	{
		
		if(!environment.storagelist.containsKey(volumeid))
		{
			System.out.println("Cannot find volume with id" + id +"......");
			return "{\"status\" : 0}";
		}
		if(environment.storagelist.get(volumeid).status==0)
		{
			System.out.println("volume already detached : " + id +"......");
			return "{\"status\" : 0}";
		}
		//System.out.println("Reached Here");
		//for(int i=0;i<environment.domainlist.size();i++)
		//{
			int vm=environment.storagelist.get(volumeid).vmid;
			System.out.println("In Detach");
			if(environment.domainlist.get(vm).vol.containsKey(volumeid))
			{
				System.out.println("In if");
				DomainInfo DIs = environment.domainlist.get(vm);
				String deviceXML = "<disk type='network' device='disk'>" + "<source protocol='rbd' name='rbd/"+volumeid+"'>" + "<host name='"+environment.MonIP+"' port='6789'/>"+ "</source>"+ "<auth username='admin'>" + "<secret type='ceph' usage='client.admin secret'/>"+ "</auth>"+ "<target dev='sd"+DIs.vol.get(volumeid)+"' bus='scsi'/>"+ "</disk>";
				try 
				{
					DIs.getDomain().detachDevice(deviceXML);
					DIs.vol.remove(volumeid);
					
					environment.storagelist.get(volumeid).status=0;
					environment.storagelist.get(volumeid).vmid=-1;
					return "{\"status\" : 1}";
				} 
				catch (LibvirtException e)
				{
					
					e.printStackTrace();
					return "{\"status\" : 0}";
				}
			}
			
			
			return "{\"status\" : 0}";
				
				
				/*
				SshConnection sshhost = new SshConnection(ip,"ceph","");
				if(sshhost.Execute("sudo virsh detach-disk " + DIs.getUsername() +  " --target sd" + num )==0)
				{
					environment.storagelist.get(volumeid).status=0;
					System.out.println("Detached Sucessful....");
					if(sshhost.Execute("sudo rbd unmap "+"/dev/rbd/rbd/"+volumeid)==0)
					{
						
						System.out.println("Unmapped Sucessful....");
						sshhost.session.disconnect();
						return "{\"status\" : 1}";
					}
					else
					{
						System.out.println("fail unmap");
						sshhost.session.disconnect();
						return "{\"status\" : 0}";
					}
					
					
				}
				else
				{
					System.out.println("fail Detach");
					sshhost.session.disconnect();
					return "{\"status\" : 0}";
				}
			//}*/
			
		
		
		
	}
	public String Volume_Attach(int vmid,int volumeid)
	{
				
		if(!environment.storagelist.containsKey(volumeid))
		{
			System.out.println("Cannot find volume with id" + volumeid +"......");
			return "{\"status\" : 0}";
		}
		if(!environment.domainlist.containsKey(vmid))
		{
			System.out.println("Cannot find vm with id" + volumeid +"......");
			return "{\"status\" : 0}";
		}
		if(environment.storagelist.get(volumeid).status!=0)
		{
			System.out.println("Volume already attached to vm " + environment.storagelist.get(volumeid).vmid +"......");
			return "{\"status\" : 0}";
		}
		//System.out.println("Reached Here");
		DomainInfo DI = environment.domainlist.get(vmid);
		
		
			
		
		try {
			/*for(int m=0;m<DI.getDomain().getConnect().listSecrets().length;m++)
				System.out.println("UUID:"+DI.getDomain().getConnect().listSecrets()[m]);*/
			String deviceXML = "<disk type='network' device='disk'>" + "<source protocol='rbd' name='rbd/"+volumeid+"'>" + "<host name='"+environment.MonIP+"' port='6789'/>"+ "</source>"+ "<auth username='admin'>" + "<secret type='ceph' usage='client.admin secret'/>"+ "</auth>"+ "<target dev='sd"+DI.num_disk+"' bus='scsi'/>"+ "</disk>";
			//System.out.println("XML:  "+deviceXML);
			DI.getDomain().attachDevice(deviceXML);
			
			
			DI.vol.put(volumeid, DI.num_disk);
			
			environment.storagelist.get(volumeid).status=1;
			environment.storagelist.get(volumeid).vmid=vmid;
			DI.num_disk++;
			return "{\"status\" : 1}";
		} 
		catch (LibvirtException e) 
		{
			
			e.printStackTrace();
			DI.num_disk++;
			return "{\"status\" : 0}";
		}
		/*
		SshConnection sshhost = new SshConnection(ip,"ceph","");		
		if(sshhost.Execute("modprobe rbd")!=0)
			System.out.println("ModProbe command Failed .....");		 
		String Mapcommand = "sudo rbd map "+ id +" --pool rbd --name client.admin";
		if(sshhost.Execute(Mapcommand)!=0)
		{
			System.out.println("Cannot run Mapcommand.....");
			return "{\"status\" : 0}";
		}
		
		System.out.println("Mapping Image Sucessful......");
		
		
		if(sshhost.Execute("sudo virsh attach-disk "+DI.getUsername()+" /dev/rbd/rbd/"+volumeid+" sd"+DI.num_disk+" --persistent")!=0)
		{
			System.out.println("Cannot run Attach Disk...");
			return "{\"status\" : 0}";
		}
		
		System.out.println("Attaching Image Sucessful......");
		
		DI.vol.put(volumeid, DI.num_disk);
		//System.out.println(environment.storagelist.get(volumeid).name);
		environment.storagelist.get(volumeid).status=1;
		environment.storagelist.get(volumeid).vmid=vmid;
		DI.num_disk++;
		sshhost.session.disconnect();
			return "{\"status\" : 1}";
		
		
		*/
	}

	public String Volumes() 
	{
		String result = new String();
		
		Iterator<Entry<Integer, DiskImage>> it = environment.storagelist.entrySet().iterator();
		int i=0;
		result=result+"{\"volumes\":[";
		while(it.hasNext())
		{
			i++;
			Map.Entry<Integer,DiskImage> pairs = it.next();
			DiskImage di = (DiskImage) pairs.getValue();
			result=result+"{ \"id\":"+ pairs.getKey() + ",\"name\":\""+di.name +  "\",\"size\":\""+di.size+ "\",\"status\":\""+di.status+ "\",\"vmid\":"+di.vmid+"}";
			if(i!=environment.storagelist.size())
				result=result+",";
		}
		result=result+"]}";
		return result;
	}
}
