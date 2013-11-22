package com;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;



public class SetEnvironment 
{
	public HashMap<Long, ResorceRequested> VMType = new HashMap<Long, ResorceRequested>();
	public HashMap<Integer,DomainInfo> domainlist = new HashMap<Integer, DomainInfo>();
	public HashMap<Integer,String> ImageList = new HashMap<Integer, String>();
	public HashMap<Integer,DiskImage> storagelist = new HashMap<Integer,DiskImage>();
	public String MonIP = new String();
	public String MonHostName = new String();
	public ArrayList<Nodes> machines = new ArrayList<Nodes>();
	public int SchedulerType;
	
	public SetEnvironment(String string, String args2,String args) throws FileNotFoundException, IOException, ParseException, ParserConfigurationException, SAXException 
	{
		setImage(args2);
		setVMType(args);
		setSchedulerType();
		new Discover(machines,string);
	}
	private void setImage(String args2) throws IOException 
	{
		System.out.println("Creating Image List...");
		
		FileReader fr = new FileReader(args2);
		BufferedReader br = new BufferedReader(fr);
		String line=new String();
		int id=100;
		while((line=br.readLine())!=null)
		{
			id++;
			String[] tokens = line.split("/"); 
			ImageList.put(id,tokens[tokens.length-1]);
		}
		br.close();
	}
	private void setSchedulerType() 
	{
		System.out.println("Finding Scheduler Type......");
		SchedulerType=1;
		
	}
	public Nodes getNodeSelected(long requestedCpus,long requestedMemory,long requestedDisk)
	{
		//System.out.println("Hello");
		Nodes NodeSelected = new Nodes();
		
		if(SchedulerType==1)
		{
			 NodeSelected= SelectMachine(requestedCpus, requestedMemory,requestedDisk);
		}
		else 
		{
			 NodeSelected= SelectMachineFCFS(requestedCpus, requestedMemory,requestedDisk);
		}
			
		return NodeSelected;
	}
	
	public int getSchedulerType()
	{
		return SchedulerType;
	}
	private void setVMType(String string) throws FileNotFoundException, IOException, ParseException
	{
		
		System.out.println("Creating VM Type List....");
		JSONParser Jparser = new JSONParser();
		JSONObject jsonObject =(JSONObject) Jparser.parse(new FileReader(string));
		JSONArray vmtypes = (JSONArray) jsonObject.get("types");
		for(int i=0;i<vmtypes.size();i++)
		{
			ResorceRequested request = new ResorceRequested();
			JSONObject newjsonobj=(JSONObject)vmtypes.get(i);
			request.setCpu((Long)newjsonobj.get("cpu"));
			request.setRam((Long)newjsonobj.get("ram"));
			request.setDisk((Long)newjsonobj.get("disk"));
			VMType.put((Long)newjsonobj.get("tid"), request);
		}
		
	}
	private Nodes SelectMachineFCFS(long request_cpus,long request_memory,long request_disk) 
	{
		int i=0;
		for(i=0;i<machines.size();i++)
		{
			if(machines.get(i).cpus > request_cpus && machines.get(i).memory > request_memory)
				break;
		}
		if(i==machines.size())
		{
			System.out.println("OutOfMachine");
			return null;
		}
		if(i==machines.size())
		{
			System.out.println("OutOfMachine");
			return null;
		}
		
		System.out.println("i:"+i);
		return machines.get(i);
	}
	private Nodes SelectMachine(long request_cpus,long request_memory,long request_disk) 
	{
		int i=0;
		Collections.sort(machines, new Comparator<Nodes>()
		{				
							@Override
							public int compare(Nodes o1, Nodes o2) {
					
								if(o1.cpus>o2.cpus)
								{
									return -1;
								}	
								else if(o1.cpus<o2.cpus)
								{
									return 1;
								}
								else
								{
									if(o1.memory>o2.memory)
										return -1;
									else
										return 1;
								}
							}
			});
		for(i=0;i<machines.size();i++)
		{
			if(machines.get(i).cpus > request_cpus && machines.get(i).memory > request_memory)
				break;
		}
		if(i==machines.size())
		{
			
			return null;
		}
		
		System.out.println("i:"+i);
		return machines.get(i);
	}
	public String domainXML(UUID domainid,Nodes node,String username, long type, String image) throws ParserConfigurationException, TransformerException 
	{
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.newDocument();
		Element domain = doc.createElement("domain");
		doc.appendChild(domain);
		domain.setAttribute("type", "qemu");
		
		Capabilities cap = node.capabilities;
		ResorceRequested rreq = new ResorceRequested();
		rreq = VMType.get(type);
		
		
		Element uname = doc.createElement("name");
		uname.appendChild(doc.createTextNode(username));
		domain.appendChild(uname);
		
		
		
		
		Element uuid = doc.createElement("uuid");
		
		uuid.appendChild(doc.createTextNode(domainid.toString()));
		domain.appendChild(uuid);
		
		Element memory = doc.createElement("memory");
		memory.appendChild(doc.createTextNode(Long.toString(rreq.ram*1024)));
		domain.appendChild(memory);
		
		Element currentMemory = doc.createElement("currentMemory");
		currentMemory.appendChild(doc.createTextNode(Long.toString(rreq.ram*1024)));
		domain.appendChild(currentMemory);

		Element vcpu = doc.createElement("vcpu");
		vcpu.appendChild(doc.createTextNode(Long.toString(rreq.cpu)));
		domain.appendChild(vcpu);
		
		Element os = doc.createElement("os");
		domain.appendChild(os);

		Element typeofarch = doc.createElement("type");
		os.appendChild(typeofarch);
		typeofarch.setAttribute("arch",cap.getArchitecture());
		typeofarch.setAttribute("machine", cap.getMachine());
		typeofarch.appendChild(doc.createTextNode(cap.getType()));

		
		
		Element boot = doc.createElement("boot");
		os.appendChild(boot);
		
		if(image.endsWith(".img"))
			boot.setAttribute("dev", "hd");
		else
			boot.setAttribute("dev", "cdrom");
			
		Element devices = doc.createElement("devices");
		domain.appendChild(devices);
		
		Element emulator = doc.createElement("emulator");
		emulator.appendChild(doc.createTextNode(cap.getEmulator()));
		devices.appendChild(emulator);
		
		Element feature=doc.createElement("features"); Element acpi =doc.createElement("acpi"); Element apic =doc.createElement("apic"); Element pae = doc.createElement("pae"); feature.appendChild(acpi); feature.appendChild(apic); feature.appendChild(pae); 
		domain.appendChild(feature);
		
		Element disk = doc.createElement("disk");
		devices.appendChild(disk);
		disk.setAttribute("type", "file");

		if(image.endsWith(".img"))
			disk.setAttribute("device", "disk");
		else
			disk.setAttribute("device", "cdrom");
		
		if(image.contains("ubuntu"))
		{
			Element qemu = doc.createElement("driver");
			qemu.setAttribute("name", "qemu");
			qemu.setAttribute("type", "qcow2");
			disk.appendChild(qemu);
		}
		else
		{
			Element qemu = doc.createElement("driver");
			qemu.setAttribute("name", "qemu");
			qemu.setAttribute("type", "raw");
			disk.appendChild(qemu);
		}
		Element source = doc.createElement("source");
		disk.appendChild(source);
		source.setAttribute("file", "/home/cloud/" +image);
		//source.setAttribute("file",image);
		Element target = doc.createElement("target");
		disk.appendChild(target);
		target.setAttribute("dev", "hda");

		Element interfaces = doc.createElement("interface");
		devices.appendChild(interfaces);
		interfaces.setAttribute("type", "network");

		Element sources = doc.createElement("source");
		interfaces.appendChild(sources);
		sources.setAttribute("network", "default");

		Element graphics = doc.createElement("graphics");
		devices.appendChild(graphics);
		graphics.setAttribute("type", "vnc");
		graphics.setAttribute("port", "-1");

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		System.out.println("OUTPUT XML STRING is : " + output);
		return output;
	}
}
