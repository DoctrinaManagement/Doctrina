package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import zu.b5.doctrina.model.export.*;

/**
 * @author Basheer
 */

public class getRatingAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String cookieValue = "";
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies) {
		   if ( cookie.getName().equals("Name") ) {
		       cookieValue = cookie.getValue();
		   }
		}
		try {
    		ReUsable get = new ReUsable(session.getAttribute("connection")); 
    		String cookieUser_id = get.getUserId(cookieValue);
    		
    		if(session.getAttribute("user_id") != null && cookieValue != "" &&  cookieUser_id != "") {
        		
        			CheckValidDetails checkDetails = new CheckValidDetails(
        					session.getAttribute("connection"));
        			String student_id = request.getParameter("student_id");
        			String class_id = request.getParameter("class_id");
        			
        			if (checkDetails.userIdCheck(student_id)
        					&& checkDetails.classIdCheck(class_id)) {
        			    if (checkDetails.checkClassroomPermission(student_id, class_id)) {
        						chain.doFilter(request, response);
        			    }
        			    else {
        			        writer.write("400");
        			    }
        			} else {
        			writer.write("404");
        				// throw new Exception();
        			}
        		
    		} else {
    		    res.sendRedirect("/landingpage");
    		} 
		} catch (Exception e) {
    			System.out.println("getRating - " + e.getMessage());
    	}
	
	}

	public void destroy() {
	}
}