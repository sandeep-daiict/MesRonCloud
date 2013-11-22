package com;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.libvirt.Connect;
import org.libvirt.LibvirtException;
import org.libvirt.NodeInfo;
import org.xml.sax.SAXException;

public class Discover 
{
	public static Nodes NodeInfo(String str, int i) throws IOException, ParserConfigurationException, SAXException
	{
		Nodes host = new Nodes();
		Connect conn;
		try 
		{
			conn = new Connect("qemu+ssh://"+str+"/system");
			//conn = new Connect("qemu:///system");
			NodeInfo nodesinfo = conn.nodeInfo();
			host.cpus=conn.getMaxVcpus(conn.getType())*2;
			host.id=str.split("@")[1];
			host.memory=(long)((double)(nodesinfo.memory*3)/(double)2);
			host.model=nodesinfo.model;
			host.conn=conn;
			host.pm_id=i;
			host.disk=0;
			host.num_domain = conn.numOfDefinedDomains()+conn.numOfDomains();
			host.capabilities=new Capabilities(conn.getCapabilities());
		} 
		catch (LibvirtException e) 
		{
		
			e.printStackTrace();
		}
	    
		return host;
	}
	public Discover(ArrayList<Nodes> machines, String FileName) throws ParserConfigurationException, SAXException
	{
		System.out.println("Discover....");
		try {
			
			FileReader fr = new FileReader(new File(FileName));
			BufferedReader br = new BufferedReader(fr);
			String line="";
			int i=0;
			while((line=br.readLine())!=null && line.length()!=0)
			{
				
				machines.add(NodeInfo(line,i));
				i++;
			}	
			br.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
