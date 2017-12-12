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
		HttpSession session = req.getSession();
		Statement stmt = null;
		try {
		    if ((request.getParameter("user_id")+"") != "" && (request.getParameter("user_id")+"") != null) {
    			stmt = ((Connection) session.getAttribute("connection")).createStatement();
    			String msg = "'" + request.getParameter("message") + "'";
    			String user = "'" + request.getParameter("user_id") + "'";
    			String Query = "select * from notification where message = "
    							+ msg+"AND user_id="+user+";";
    			ResultSet rs = stmt
    					.executeQuery(Query);
    
    			ReUsable get = new ReUsable(session.getAttribute("connection"));
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
	}

	public void destroy() {
	}

}