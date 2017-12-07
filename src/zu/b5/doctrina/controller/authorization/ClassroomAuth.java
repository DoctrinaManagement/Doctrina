package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.*;
import java.sql.*;
/** 
 * @author Basheer
 */
 
public class ClassroomAuth implements Filter {
    
	public void init(FilterConfig arg0) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	
		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
		
	}
	
	public void destroy() {}
}
