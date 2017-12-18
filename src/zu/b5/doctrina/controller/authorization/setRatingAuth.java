package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import zu.b5.doctrina.model.export.*;

/**
 * @author Basheer
 */

public class setRatingAuth implements Filter {

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
        		try {
        			CheckValidDetails checkDetails = new CheckValidDetails(
        					session.getAttribute("connection"));
        			String user_id = request.getParameter("user_id");
        			String student_id = request.getParameter("student_id");
        			float rating = Float.parseFloat(request.getParameter("rating"));
        			String class_id = request.getParameter("class_id");
        			if (checkDetails.userIdCheck(user_id)
        					&& checkDetails.userIdCheck(student_id)
        					&& checkDetails.classIdCheck(class_id)) {
        				if (checkDetails.checkClassCreater(user_id, class_id)
        						&& checkDetails.checkClassroomPermission(student_id,
        								class_id)) {
        					if (rating <= 5 && rating >= 0) {
        						chain.doFilter(request, response);
        					} else {
        						writer.write("400");
        					}
        				} else {
        					writer.write("401");
        				}
        			} else {
        			writer.write("404");
        				// throw new Exception();
        			}
        		} catch (Exception e) {
        			System.out.println("setRating - " + e.getMessage());
        		}
    		} else {
    		    res.sendRedirect("/landingpage");
    		} 
		}
		catch(Exception e){
		    System.out.println("setratingAuth - "+e.getMessage());
		}
	}

	public void destroy() {
	}
}