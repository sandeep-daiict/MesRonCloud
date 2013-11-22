package rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.Run;


public class VolumeAttach extends AbstractHandler {

	@Override
	public void handle(String target, Request request, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException, ServletException {
		
		httpResponse.setContentType("text/html;charset=utf-8");
		request.setHandled(true);
		if(request.getUri().toString().contains("callback"))
		{
			httpResponse.getWriter().write("JSONP_CALLBACK_ATTACH_VOLUME("+Run.storagemanager.Volume_Attach(Integer.parseInt(request.getParameter("vmid")),
				Integer.parseInt(request.getParameter("volumeid")))+")");
		}
		else
			httpResponse.getWriter().write(Run.storagemanager.Volume_Attach(Integer.parseInt(request.getParameter("vmid")),
					Integer.parseInt(request.getParameter("volumeid"))));
	}

}
