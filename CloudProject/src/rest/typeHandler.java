package rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.Run;
public class typeHandler extends AbstractHandler
{

	@Override
	public void handle(String target, Request request, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException, ServletException {
			
			
			httpResponse.setContentType("text/html;charset=utf-8");
			request.setHandled(true);
			/*FilterChain fc =FilterChain 
			CrossOriginFilter cr = new CrossOriginFilter();
			cr.doFilter(request, httpResponse, fc);*/
			//httpResponse.addHeader("Access-Control-Allow-Origin", "*");
			//httpResponse.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
			//System.out.println("callback:"+);
			if(request.getUri().toString().contains("callback"))
			{
				System.out.println("In JSONp");
				httpResponse.getWriter().write("JSON_CALLBACK("+Run.manager.VM_Type()+")");
			}
			else
			{
				System.out.println("out");
				httpResponse.getWriter().write(Run.manager.VM_Type());
			}
		
	}

}
