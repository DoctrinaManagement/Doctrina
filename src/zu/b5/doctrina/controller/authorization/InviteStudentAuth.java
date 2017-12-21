package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;

/**
 * @author Basheer
 */

public class InviteStudentAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		try {
    		CheckValidDetails checkDetails = new CheckValidDetails(
    				session.getAttribute("connection"));
    
    		String teacher = request.getParameter("user_id");
    		String class_id = request.getParameter("class_id");
    		String student_id = request.getParameter("student_id");
            String cookieValue = "";
    		Cookie[] cookies = req.getCookies();
    		for(Cookie cookie : cookies) {
    		   if ( cookie.getName().equals("Name") ) {
    		       cookieValue = cookie.getValue();
    		   }
    		}
    		ReUsable get = new ReUsable(session.getAttribute("connection")); 
    		String cookieUser_id = get.getUserId(cookieValue);
    		
    		if(session.getAttribute("user_id") != null && cookieValue != "" &&  cookieUser_id != "") {
        		if (checkDetails.userIdCheck(teacher)
        				&& checkDetails.userIdCheck(student_id)
        				&& checkDetails.classIdCheck(class_id)) {
        
        			if (checkDetails.checkClassCreater(teacher, class_id)) {
        				    chain.doFilter(request, response);
        			} else {
        				writer.write("401");
        			}
        
        		} else {
        			writer.write("404");
        		}
    		} else {
    		    for(Cookie cookie : cookies) {
        		   cookie.setMaxAge(0);
        		   res.addCookie(cookie);
        		}
    		    res.sendRedirect("/landingpae");
    		}
		}
		catch (Exception e) {
		    System.out.println("InviteStudentsAuth - "+e.getMessage());
		}
    }

	public void destroy() {
	}
}