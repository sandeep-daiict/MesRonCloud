package com;
import org.libvirt.Connect;
public class Nodes 
{
	public int cpus;
	public long memory;
	public int getCpus() {
		return cpus;
	}
	public void setCpus(int cpus) {
		this.cpus = cpus;
	}
	public long getMemory() {
		return memory;
	}
	public void setMemory(long memory) {
		this.memory = memory;
	}
	public int getDisk() {
		return disk;
	}
	public void setDisk(int disk) {
		this.disk = disk;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPm_id() {
		return pm_id;
	}
	public void setPm_id(int pm_id) {
		this.pm_id = pm_id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getNum_domain() {
		return num_domain;
	}
	public void setNum_domain(int num_domain) {
		this.num_domain = num_domain;
	}
	public Connect getConn() {
		return conn;
	}
	public void setConn(Connect conn) {
		this.conn = conn;
	}
	public Capabilities getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(Capabilities capabilities) {
		this.capabilities = capabilities;
	}
	public int disk;
	public String id;
	int pm_id;
	public String model;
	public int num_domain;
	public Connect conn;
	public Capabilities capabilities;
}
