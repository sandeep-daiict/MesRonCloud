package com;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Capabilities {
	private String architecture;
	private String machine;
	private String type;
	private String emulator;
	private String imgPath;
	private boolean is64;
	public Capabilities(String capabilities) throws IOException, ParserConfigurationException, SAXException 
	{		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(capabilities));
        Document doc=builder.parse(is);
        doc.getDocumentElement().normalize();
        
        NodeList nHost = doc.getElementsByTagName("host");
        if (nHost.getLength()>0) {
        	Node node = nHost.item(0);
        	if (node.getNodeType() == Node.ELEMENT_NODE) {
        		Element eElement = (Element) node;
        		NodeList n =eElement.getElementsByTagName("cpu");
        		if(n.item(0).getNodeType() == Node.ELEMENT_NODE) {
        			Element elem1 = (Element) n.item(0);
        			String type = elem1.getElementsByTagName("arch").item(0).getTextContent();
        			
            		if(type.endsWith("64")) {
            			setIs64(true);
            		}
        		}
        	}
        }
        
        
        NodeList nList = doc.getElementsByTagName("guest");

	
	        Node nNode = nList.item(0);
    
        	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        		Element eElement = (Element) nNode;
        		this.setType(eElement.getElementsByTagName("os_type").item(0).getTextContent());
	        		
        		NodeList list = eElement.getElementsByTagName("arch");
        		if(list.item(0).getNodeType() == Node.ELEMENT_NODE) {
        			Element elem = (Element) list.item(0);
	        			this.setArchitecture(elem.getAttribute("name"));
	            		this.setEmulator(elem.getElementsByTagName("emulator").item(0).getTextContent());
	            		this.setMachine(elem.getElementsByTagName("machine").item(0).getTextContent());        			
	            	}
        		
        	}		
	}
	
	public String getArchitecture() {
		return architecture;
	}
	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmulator() {
		return emulator;
	}
	public void setEmulator(String emulator) {
		this.emulator = emulator;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public boolean isIs64() {
		return is64;
	}

	public void setIs64(boolean is64) {
		this.is64 = is64;
	}
}
