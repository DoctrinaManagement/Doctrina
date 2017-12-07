package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;
import java.sql.*;
/** 
 * @author Basheer
 */
public class SignupAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		DatabaseConnection data = new DatabaseConnection();
		Connection connection = data.connect();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		session.setAttribute("connection", connection);
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
		
		if ( !( checkDetails.userIdCheck(request.getParameter("user_id")) ) ) {
			if (request.getParameter("role").equals("Teacher")
					|| request.getParameter("role").equals("Student")) {
					    
				chain.doFilter(request, response);
			} 
			else {
				writer.write("Wrong role");
			}
		} 
		else {
			writer.write("Your Account is already exists in DOCTRINA");
		}
	
	
	}

	public void destroy() {
	}

}