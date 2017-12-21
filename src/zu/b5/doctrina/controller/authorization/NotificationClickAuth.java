package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
/** 
 * @author Pandi
 */
public class NotificationClickAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Statement stmt = null;
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
        		    if ((request.getParameter("user_id")+"") != "" && (request.getParameter("user_id")+"") != null) {
            			stmt = ((Connection) session.getAttribute("connection")).createStatement();
            			String msg = "'" + request.getParameter("message") + "'";
            			String user = "'" + request.getParameter("user_id") + "'";
            			String Query = "select * from notification where message = "
            							+ msg+"AND user_id="+user+";";
            			ResultSet rs = stmt
            					.executeQuery(Query);
            
            			HashMap<String, String> details = get.resultSetToHashMap(rs);
            
            			if (details.size() != 0 && details.get("user_id").equals((request.getParameter("user_id")+"")) && details.get("message").equals((request.getParameter("message")+""))) {
            				chain.doFilter(request, response);
            			} else {
            				throw new Exception();
            			}
        		    }
        		} catch (Exception e) {
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
		    System.out.println("NotificationClickAuth - "+e.getMessage());
		}
	}

	public void destroy() {
	}

}