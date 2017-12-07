package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import zu.b5.doctrina.model.export.*;

/** 
 * @author Basheer
 */
 
public class ClassroomJoinRequestAuth implements Filter {
    
	public void init(FilterConfig arg0) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	
		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));

        if (checkDetails.userIdCheck(request.getParameter("requester")) && checkDetails.classIdCheck(request.getParameter("class_id"))) {
		       
	       if (!(checkDetails.isAvailableJoinRequest(request.getParameter("requester"), request.getParameter("class_id")))) {
 	            chain.doFilter(request, response);
	       }
	       else {
	           writer.write("Your Request is already in Progress");
	       }
	   }
	   else {
	       writer.write("404");
	   }
	}
	
	public void destroy() {}
}