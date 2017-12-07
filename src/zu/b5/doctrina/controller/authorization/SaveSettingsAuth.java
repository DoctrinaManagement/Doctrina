package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;

/** 
 * @author Basheer
 */
 
public class SaveSettingsAuth implements Filter {
    
	public void init(FilterConfig arg0) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	
		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
		if (checkDetails.userIdCheck(request.getParameter("user_id"))) {
		       
            int total_length = (request.getParameter("courses")+"").length();
	        String[] course_id = (request.getParameter("courses")+"").split("A");
	        if ((total_length/2) == course_id.length || (request.getParameter("courses")+"").equals("")) {
			    int count = 0;
			    for (String courseId : course_id) {
			        if(courseId.equals("")) {
			            break;
			        }
			        else if (Integer.parseInt(courseId) <= 15) {
			            count += 1;
			        }
			        else {
			            break;
			        }
			    }
			    if (course_id.length == count || course_id.length == 1) {
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
   else {
       writer.write("404");
   }

		
	}
	
	public void destroy() {}
}