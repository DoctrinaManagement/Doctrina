package zu.b5.doctrina.controller.authorization;


import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
/**
 * @author Pandi
 */
 
public class SaveUpdateAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        
        PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		try {
    		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
    		int total_duration = 0;
    		int current_duration = 0;
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
        		try {
            		total_duration = Integer.parseInt(request.getParameter("total_duration"));
            		current_duration = Integer.parseInt(request.getParameter("current_duration"));
        		} catch(Exception e) {
        		    writer.write("something went wrong");
        		}
        		
        		if(total_duration > current_duration) {
        		    
        		    if(checkDetails.checkVideoPrimaryId((session.getAttribute("class_id")+""), request.getParameter("id"))) {
        		        
        		        chain.doFilter(request, response);
        		        
        		    } else{
        		        writer.write("401");
        		    }
        		    
        		} else {
        		    writer.write("401");
    		    }
    		} else {
    		    for(Cookie cookie : cookies) {
        		   cookie.setMaxAge(0);
        		   res.addCookie(cookie);
        		}
    		    res.sendRedirect("/landingpage");
    		}
		}
		catch(Exception e){
		    System.out.println("SaveUpdateAuth - "+e.getMessage());
		}
	}

	public void destroy() {
	}

}