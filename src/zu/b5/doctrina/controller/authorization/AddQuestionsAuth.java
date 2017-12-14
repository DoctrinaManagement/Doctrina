package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import zu.b5.doctrina.model.export.*;
import com.google.gson.*;

/**
 * @author Basheer
 */

public class AddQuestionsAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(
				session.getAttribute("connection"));
				
		if (checkDetails.classIdCheck(request.getParameter("class_id"))) {
		    // Check user permission for hihs classroom
			if (checkDetails.checkAddPermission(session.getAttribute("user_id") + "",
					request.getParameter("class_id"))) {
					    
                    chain.doFilter(request, response);
			} 
			else {
				writer.write("404");
			  }
		}
		else {
			writer.write("404");
		}
	}
	public void destroy() {
	}
}