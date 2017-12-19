package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;
/** 
 * @author Basheer
 */
public class GetTitlesAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		try {
        	CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
        	
        	String user = request.getParameter("user_id");
        	String class_id = request.getParameter("class_id");
        	String type = request.getParameter("type");
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
        		if(checkDetails.userIdCheck(user) && checkDetails.classIdCheck(class_id)) {
            		   
        	        if (type.equals("assignmenttitles") || type.equals("quiztitles") || type.equals("testtitles") || type.equals("videos")) {
        		        
        		        if (checkDetails.checkClassroomPermission(user, class_id)) {
        		            System.out.println("student id - "+user);
        		            chain.doFilter(request, response);
        		            
        		        } else {
            			   writer.write("permission 401");
            		   }
            		   
        	        } else {
        	            writer.write("400");
        	        }
        	   } else {
        	       writer.write("404");
        	   }
        	} else {
        	    res.sendRedirect("/landingpage");
        	}
		}
		catch(Exception e) {
		    System.out.println("GetTitles - "+e.getMessage());
		}
	}
	
	public void destroy() {}
}