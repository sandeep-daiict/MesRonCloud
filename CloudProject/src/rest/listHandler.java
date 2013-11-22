package rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.Run;

public class listHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request request, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException, ServletException {
		
		httpResponse.setContentType("text/html;charset=utf-8");
		request.setHandled(true);
		//System.out.println("callback:"+);
		if(request.getUri().toString().contains("callback"))
		{
			httpResponse.getWriter().write("JSON_CALLBACK_IMAGES("+Run.manager.List_Images()+")");
		}
		else
		{
			httpResponse.getWriter().write(Run.manager.List_Images());
		}
		
	}

}
