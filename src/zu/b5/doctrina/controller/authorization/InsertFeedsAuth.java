package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;

public class InsertFeedsAuth implements Filter {

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
        		if (request.getParameter("type").equals("posts")) {
        
        			if (checkDetails.userIdCheck(request.getParameter("user_id"))) {
        
        				if (checkDetails.classIdCheck(request.getParameter("class_id"))
        						&& checkDetails.checkClassroomPermission(
        								request.getParameter("user_id"),
        								request.getParameter("class_id")) && request.getParameter("message") != "") {
        					chain.doFilter(request, response);
        				} else {
        					writer.write("400");
        				}
        
        			} else {
        				writer.write("404");
        			}
        
        		} else if (request.getParameter("type").equals("comments")) {
        
        			if (checkDetails.userIdCheck(request.getParameter("user_id"))) {
        
        				if (checkDetails.classIdCheck(request.getParameter("class_id"))
        						&& checkDetails.checkClassroomPermission(
        								request.getParameter("user_id"),
        								request.getParameter("class_id"))
        						&& checkDetails.PostIdCheck(request.getParameter("id"),
        								request.getParameter("class_id"))  && request.getParameter("message") != "") {
        					chain.doFilter(request, response);
        				} else {
        					writer.write("400");
        				}
        
        			} else {
        				writer.write("404");
        			}
        
        		} else if (request.getParameter("type").equals("replies")) {
        
        			if (checkDetails.userIdCheck(request.getParameter("user_id"))) {
        
        				if (checkDetails.classIdCheck(request.getParameter("class_id"))
        						&& checkDetails.checkClassroomPermission(
        								request.getParameter("user_id"),
        								request.getParameter("class_id"))
        						&& checkDetails.CommentIdCheck(
        								request.getParameter("class_id"),
        								request.getParameter("id"))  && request.getParameter("message") != "") {
        					chain.doFilter(request, response);
        				} else {
        					writer.write("401");
        				}
        
        			} else {
        				writer.write("404");
        			}
        		}
        	}else {
    		    res.sendRedirect("/landingpage");
    		}
		}
		catch(Exception e) {
		    System.out.println("InsertFeeds - "+e.getMessage());
		}
	}

	public void destroy() {
	}

}