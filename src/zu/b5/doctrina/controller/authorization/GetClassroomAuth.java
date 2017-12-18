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
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		try {
    		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
    		
    		Cookie[] cookies = req.getCookies();
    		String cookieValue = "";
    		for(Cookie cookie : cookies) {
    		   if ( cookie.getName().equals("Name") ) {
    		       cookieValue = cookie.getValue();
    		   }
    		}
    		ReUsable get = new ReUsable(session.getAttribute("connection")); 
    		String cookieUser_id = get.getUserId(cookieValue);
    		
    		if(session.getAttribute("user_id") != null && cookieValue != "" &&  cookieUser_id != "") {
    		
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
    		} else {
    		    res.sendRedirect("/landingpage");
    		}
		}
        catch (Exception e) {
            System.out.println("GetClassroomAuth");
        }
	}

	public void destroy() {}
	
}