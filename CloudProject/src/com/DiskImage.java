package com;

import java.util.ArrayList;

public class DiskImage 
{
	public int status;
	public String name;
	public int size;
	public int vmid;
	
	public DiskImage(int st, String storagename , int space , int id)
	{
		status = st;
		name= storagename;
		size=space;
		vmid = id;
	}
}
