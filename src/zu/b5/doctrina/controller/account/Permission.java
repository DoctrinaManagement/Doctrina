package zu.b5.doctrina.controller.account;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import java.sql.*;

public class Permission extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Connection conn = (Connection) session.getAttribute("connection");
	    if(session.getAttribute("user_id") == null) {
	        System.out.println("hi");
	        response.getWriter().write("ok");
	    }
		
		
		
	}
}