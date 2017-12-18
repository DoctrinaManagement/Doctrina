package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import java.sql.*;
import javax.servlet.http.*;

/**
 * @author Pandi
 */
public class SigninAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		DatabaseConnection data = new DatabaseConnection();
		Connection connection = data.connect();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		try {
    		session.setAttribute("connection", connection);
    		CheckValidDetails checkDetails = new CheckValidDetails(
    				session.getAttribute("connection"));
    
    		if (request.getParameter("user_id").matches("^[0-9]{5,50}$")
    				&& ( request.getParameter("login").equals("google"))
    				|| request.getParameter("login").equals("fb") ) {
    
    			if (checkDetails.userIdCheck(request.getParameter("user_id"))) {
    				chain.doFilter(request, response);
    			} else {
    				writer.write("Please Sign-up into DOCTRINA");
    			}
    		} else {
    			writer.write("400");
    		}
		}
		catch(Exception e) {
		    System.out.println("SigninAuth - "+e.getMessage());
		}
	}

	public void destroy() {
	}

}