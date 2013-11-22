package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Scanner;

import rest.restServer;

 
public class Run 
{	
	
	public static SetEnvironment environment;
	public static DomainManager manager;
	public static StorageManager storagemanager;
	public static void main(String[] args) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader (new File(args[3])));
		String str = new String(); 
		 
		String monIP = new String();
		String hostName = new String();
		while((str=reader.readLine())!= null) 
		{ 
				if(str.contains("mon initial members") ) 
				{
					hostName= str.split("=")[1].trim();
				} 
		
				if(str.contains("mon host") ) 
				{ 
					monIP= str.split("=")[1].trim();  
				} 
			 
			}
		reader.close();
		System.out.println("Monitor IP:"+monIP+"HostName:"+hostName);
		
		System.setProperty("jna.library.path", "/usr/lib/");
		
		
		
		System.out.println("Trying to connect CEPH server......");
		
		SshConnection ssh = new SshConnection( monIP, "ceph", "");
		
		System.out.println("Instantiating.......");
		
		environment = new SetEnvironment(args[0],args[1],args[2]);
		environment.MonIP=monIP;
		environment.MonHostName=hostName;
		System.out.println("Setting Environment sucessful....:");
		
		storagemanager = new StorageManager(ssh,environment);
		System.out.println("Storage Manager sucessfully Created....");
		
		manager = new DomainManager(environment,args[2],storagemanager);
		
		System.out.println("Domain Manager sucessfully Created....");
		
		
		
		
		//SshConnection ssh = new SshConnection( monIP, "root", "");
		//ssh.Execute("sudo qemu-img create -f rbd rbd:rbd/img2 2G");
		
		/*System.out.println("Instantiating..");
		environment = new SetEnvironment(args[0],args[1],args[2]);
		System.out.println("Setting Environment sucessful.... vcpus:"+environment.machines.get(0).getCpus()+"ram:"+environment.machines.get(0).getMemory());
		manager = new DomainManager(environment,args[2]);
		System.out.println("Domain Manager sucessfully Created....");
		storagemanager = new StorageManager(ssh,environment);
		System.out.println("Storage Manager sucessfully Created....");
		
		/*
		
		System.out.println("Welcome to MesRonCLOUD" +
				"----*-----");
		Scanner scanIn = new Scanner(System.in);
		String str;
		while(!(str= scanIn.nextLine()).equals("6")) 
		{
			if(str.equals("1")) 
			{
				System.out.println("Enter name:size");
				Scanner scan = new Scanner(System.in);
				String[] s = scan.nextLine().split(":");
				System.out.println(storagemanager.Volume_Creation(s[0],Integer.parseInt(s[1])));	
			}
			else if(str.equals("2"))
			{
				System.out.println("Enter ID");
				Scanner scan = new Scanner(System.in);
				String s = scan.nextLine();
				System.out.println(storagemanager.Volume_Query(Integer.parseInt(s)));
			}
			else if(str.equals("3"))
			{
				System.out.println("Create VM");
				Scanner scan = new Scanner(System.in);
				String s = scan.nextLine();
				System.out.println(manager.VM_Creation(s, 1, 1));
			}
			else if(str.equals("4"))
			{
				System.out.println("Attach Storage to VM");
				Scanner scan = new Scanner(System.in);
				String[] s = scan.nextLine().split(":");
				System.out.println(storagemanager.Volume_Attach(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
			}	
			System.out.println("Choices");
		}*/
		
		//args[2]="Vm_types";
		/*environment = new SetEnvironment(args[0],args[1],args[2]);*/
		//System.out.println("size:"+environment.machines.size());
		/*manager = new DomainManager(environment,args[2]);*/
		restServer.startServer();
		ssh.session.disconnect();
		//System.out.println(manager.List_Images());
		/*Scanner s = new Scanner(System.in);
		System.out.println("Welcome to MesRonCLOUD" +
				"----*-----");
		
		System.out.println("id:"+manager.VM_Type());
		
		System.out.println("----*-----");
		
		System.out.println("id:"+manager.VM_Creation("Sandeep1", 1, 1));
		
		System.out.println("----*-----");
		
		System.out.println("id:"+manager.VM_Creation("Sandeep2", 2, 1));
		
		System.out.println("----*-----");
		
		System.out.println("id:"+manager.VM_Creation("Sandeep3", 3, 1));
		
		System.out.println("----*-----");
		
		int i = s.nextInt();
		
		i = s.nextInt();
		
		System.out.println("id:"+manager.VM_Query(i));
		
		System.out.println("----*-----");
		
		i = s.nextInt();
		
		System.out.println("id:"+manager.VM_Query(i));
		
		System.out.println("----*-----");
		
		i = s.nextInt();
		
		System.out.println("id:"+manager.VM_Query(i));
		
		System.out.println("----*-----");
		//System.out.println("id:"+manager.VM_Destroy(i));
		
		//i = s.nextInt();
		//System.out.println("id:"+manager.VM_Destroy(i));
		
		System.out.println("id:"+manager.VM_Creation("Sandeep3", 1, 1));
		System.out.println("id:"+manager.VM_Creation("Sandeep4", 3, 1));
		System.out.println("id:"+manager.VM_Creation("Sandeep5", 2, 1));
		System.out.println("id:"+manager.VM_Creation("Sandeep6", 1, 1));
		i = s.nextInt();
		
		System.out.println("id:"+manager.VM_Query(i));
		
		System.out.println("----*-----");
		
		i = s.nextInt();
		
		System.out.println("id:"+manager.VM_Query(i));
		
		System.out.println("----*-----");
		
		while((i=s.nextInt())!=0)
		{
			System.out.println("id:"+manager.VM_Destroy(i));
			System.out.println("----*-----");
		}
		
		/*System.out.println("id:"+manager.VM_Creation("Sandeep7", 3, 1));
		System.out.println("id:"+manager.VM_Creation("Sandeep8", 2, 1));*/
		//i = s.nextInt();
		//System.out.println("id:"+manager.VM_Destroy(i));
		
		//i = s.nextInt();
		//System.out.println("id:"+manager.VM_Destroy(i));
		
		//System.out.println("id:"+manager.VM_Destroy(1));
		//new Discover(args[0]);
		//for(int i=0;i<machines.size();i++)
		//{
		//	System.out.println("name:"+machines.get(i).num_domain);
		//}
		
		//int request_cpus=2;
		//int request_memory=512345;
		//int machine_index=SelectMachine(request_cpus,request_memory);
		
		//Create_Domain(machine_index,request_cpus,request_memory);
	/*		  
      ConnectAuth ConnAuth = new ConnectAuthDefault();
      Connect conn = new Connect("qemu:///system", ConnAuth, 0);
     
      NodeInfo ni = conn.nodeInfo();
      
      System.out.println("model: " + ni.model + " mem(kb):" + ni.memory);
      
      int numOfVMs = conn.numOfDomains();
 
      for(int i=1; i < numOfVMs + 1; i++)
      {
        Domain vm = conn.domainLookupByID(i);
        vm.reboot(i);
        
      }
      String cap = conn.getCapabilities();
      System.out.println("cap: " + cap);
      conn.close();
    }
	catch(LibvirtException le)
	{
		le.printStackTrace();
	}*/
   }

	
}