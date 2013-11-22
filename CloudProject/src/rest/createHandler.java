package rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.Run;


public class createHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request request, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException, ServletException {
		
		httpResponse.setContentType("text/html;charset=utf-8");
		request.setHandled(true);
		String name = request.getParameter("name");
		String instance  = request.getParameter("instance_type");
		String imageid=request.getParameter("image_id");
		String desc=request.getParameter("desc");
		System.out.println("desc"+desc);
		System.out.println("name:"+name+"instance:"+instance+"imageid"+imageid);
		if(request.getUri().toString().contains("callback"))
		{
			
			httpResponse.getWriter().write("JSON_CALLBACK_CREATE("+Run.manager.VM_Creation(name,instance,imageid,desc)+")");
		}
		else
		{
			httpResponse.getWriter().write(Run.manager.VM_Creation(name,instance,imageid,desc));
		}
	}

}
