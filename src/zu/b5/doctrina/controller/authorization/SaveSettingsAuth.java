package zu.b5.doctrina.controller.authorization;

import com.google.gson.*;
import java.io.*;
import java.util.*;
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
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		try {
    		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
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
        		if (checkDetails.userIdCheck(request.getParameter("user_id"))) {
        	       
    	       JsonArray courseArray = (JsonArray) new JsonParser().parse(request.getParameter("courses"));
            
                    ArrayList<String> course_id = new ArrayList<String>();
                    
                    for (JsonElement course_ids : courseArray ){
                        course_id.add(course_ids.getAsString());
                    }
    	        
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
        	   		
        	   		if (course_id.size() == count || course_id.size() == 1) {
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
		catch(Exception e) {
		    System.out.println("SaveSettingsAuth - "+e.getMessage());
		}
	}
	
	public void destroy() {}
}