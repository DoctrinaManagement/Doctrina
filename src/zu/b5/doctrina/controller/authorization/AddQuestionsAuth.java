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
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		try {
    		CheckValidDetails checkDetails = new CheckValidDetails(
    				session.getAttribute("connection"));
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
    		} else {
    		    for(Cookie cookie : cookies) {
        		   cookie.setMaxAge(0);
        		   res.addCookie(cookie);
        		}
    		    res.sendRedirect("/landingpage");
    		}
		}
		catch (Exception e ){
		    System.out.println("AddQuestionsAuth - "+e.getMessage());
		}
	}
	public void destroy() {
	}
}