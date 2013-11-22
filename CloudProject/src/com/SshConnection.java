package com;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshConnection 
{
	Session session=null;
	public  SshConnection (String ip_monitor,String user_monitor,String pswd_monitor) 
	{

	    JSch jsch   = new JSch();
	    String ip   = ip_monitor;
	    String user = user_monitor;
	    //String pass = pswd_monitor;
	    int port    = 22;
	    
	    
	    try 
	    {                
	    	String RestServer = "/home/kabeer/";// System.getProperty ("user.home");
	        session = jsch.getSession(user, ip, port);   
	        //session.setPassword(pass);
	        jsch.setKnownHosts ( RestServer+ "/.ssh/known_hosts");
	        jsch.addIdentity (RestServer + "/.ssh/id_rsa");
	        //session.connect();
	        Properties prop = new Properties();
	        prop.put ("StrictHostKeyChecking", "no");
			session.setConfig(prop);
			//((ChannelExec)channelCreate).setErrStream (System.err);
			session.connect ();
			
			
			
			
			//Execute("ls");
	    }
	    catch (Exception e) 
		{
			e.printStackTrace();
			
		}
	    
	}
	public int Execute(String command)
	{
		
		
		
		try 
		{
			ChannelExec channelCreate = (ChannelExec) session.openChannel("exec");
			InputStream in = channelCreate.getInputStream();
			channelCreate.setCommand (command);
			channelCreate.connect();
			
			/*int status; 
			channelCreate.setInputStream (null);
			byte [] tmp = new byte [1024];
		    while (true)
		    {
		        while (in.available() > 0)
		        {
		        	int i = in.read (tmp, 0, 1024);
		        	if (i < 0)
		        		break;
		        	System.out.print (new String (tmp, 0, i));
		        }
		        if (channelCreate.isClosed())
		        {	status = channelCreate.getExitStatus();
		        	System.out.println ("exit-status: " + channelCreate.getExitStatus());
		        	break;
		        }
		        try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }*/
			int status;
			while (true)
			{
				if (channelCreate.isClosed())
				{
					status = channelCreate.getExitStatus();
					break;
				}
			}
			 channelCreate.disconnect();
		      return status;
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
}
