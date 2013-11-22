package rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandler.Context;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.servlets.CrossOriginFilter;
public class restServer 
{
	

	public static void startServer() throws Exception 
	{
		//String jetty_home = System.getProperty("jetty.home","..");
		Server server = new Server(8083);
		
		//cr.doFilter(request, response, chain)
		 /*WebAppContext contextCORS = new WebAppContext();
	     contextCORS.setDescriptor("/WEB-INF/web.xml");
	     
	     contextCORS.setContextPath("/");*/
		ContextHandler contextVolumes = new ContextHandler();
		contextVolumes.setContextPath("/volume/info");
		contextVolumes.setHandler(new VolumesHandler());
	     
		ContextHandler contextInstances = new ContextHandler();
		contextInstances.setContextPath("/vm/instances");
		contextInstances.setHandler(new instanceHandler());
		
		ContextHandler contextCreate = new ContextHandler();
		contextCreate.setContextPath("/vm/create");
		contextCreate.setHandler(new createHandler());
		
		
		ContextHandler contextDestroy = new ContextHandler();
		contextDestroy.setContextPath("/vm/destroy");
		contextDestroy.setHandler(new destroyHandler());

		ContextHandler contextQuery = new ContextHandler();
		contextQuery.setContextPath("/vm/query");
		contextQuery.setHandler(new queryHandler());

		ContextHandler contextVMTypes = new ContextHandler();
		
		contextVMTypes.setContextPath("/vm/types");
		
		
		
		contextVMTypes.setHandler(new typeHandler());

		ContextHandler contextImgList = new ContextHandler();
		contextImgList.setContextPath("/image/list");
		contextImgList.setHandler(new listHandler());

		ContextHandler contextVolumeCreate = new ContextHandler();
		contextVolumeCreate.setContextPath("/volume/create");
		contextVolumeCreate.setHandler(new VolumeCreateHandler());
		
		ContextHandler contextVolumeDestroy = new ContextHandler();
		contextVolumeDestroy.setContextPath("/volume/destroy");
		contextVolumeDestroy.setHandler(new Volume_Destroy());
		
		ContextHandler contextVolumeQuery = new ContextHandler();
		contextVolumeQuery.setContextPath("/volume/query");
		contextVolumeQuery.setHandler(new Volume_Query());
		
		ContextHandler contextAttach = new ContextHandler();
		contextAttach.setContextPath("/volume/attach");
		contextAttach.setHandler(new VolumeAttach());
		
		ContextHandler contextDetach = new ContextHandler();
		contextDetach.setContextPath("/volume/detach");
		contextDetach.setHandler(new VolumeDetach());
		
		ContextHandlerCollection handlers = new ContextHandlerCollection();
		ContextHandler[] c = {contextVolumes,contextInstances,contextCreate, contextDestroy, contextQuery, contextVMTypes, contextImgList,contextDetach,contextAttach,contextVolumeQuery,contextVolumeDestroy,contextVolumeCreate};
		handlers.setHandlers(c);
		
		server.setHandler(handlers);
		server.start();
		server.join();
	}

	
	
}
