package com;
import java.util.HashMap;
import java.util.UUID;

import org.libvirt.Domain;
public class DomainInfo 
{
	private String username;
	private UUID uuid;
	private int PMID;
	public char num_disk;
	public HashMap<Integer,Character> vol;
	public String desc;
	public DomainInfo() 
	{
		vol = new HashMap<Integer,Character>();
		num_disk='f';
		// TODO Auto-generated constructor stub
	}
	public int getPMID() 
	{
		return PMID;
	}
	public void setPMID(int pMID) {
		PMID = pMID;
	}
	private long VMType;
	private Domain domain;
		
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
		
	public long getVMType() {
		return VMType;
	}
	public void setVMType(long vMType) {
		VMType = vMType;
	}
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public String getDesc() {
		
		return desc;
	}
	public void setDesc(String desc) 
	{
		this.desc=desc;
		
	}
	
	
}
