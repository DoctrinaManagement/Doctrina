package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;
/** 
 * @author Basheer
 */
public class GetClassroomAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
		
		if(checkDetails.userIdCheck(request.getParameter("user_id"))) {
    		   
		   if(checkDetails.courseIdCheck(request.getParameter("course_id"))) {

			   chain.doFilter(request, response);
		   }
		   else {
			   writer.write("course 404");
		   }
	   }
	   else {
	       writer.write(" user 404");
	   }

	}

	public void destroy() {}
	
}