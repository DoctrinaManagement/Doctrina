package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;

/**
 * @author Pandi
 */
public class ReplyJoinrequestAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		System.out.println(request.getParameter("requester") + " "
				+ request.getParameter("class_id"));
		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		try {
    		CheckValidDetails checkDetails = new CheckValidDetails(
    				session.getAttribute("connection"));
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
        		if (checkDetails.userIdCheck(request.getParameter("requester") + "")
        				&& checkDetails.classIdCheck(request.getParameter("class_id")
        						+ "")) {
                    if ((request.getParameter("status")+"").equals("Accept") || (request.getParameter("status")+"").equals("Decline")) {
                        
                        if (checkDetails.isAvailableJoinRequest(
        					request.getParameter("requester"),
        					request.getParameter("class_id"))) {
        					    
            				chain.doFilter(request, response);
            			} else {
            				writer.write("something went wrong");
            			}
                    }
                    else {
                        writer.write("404");
                    }
        			
        		} else {
        			writer.write("404");
        		}
    		} else {
    		    res.sendRedirect("/landingpage");
    		}
		}
		catch(Exception e) {
		    System.out.println("ReplyJoinRequestAuth - "+e.getMessage());
		}
	}

	public void destroy() {
	}

}