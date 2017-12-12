package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.*;
import com.mysql.jdbc.Connection;
import java.sql.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;

/**
 * @author Basheer
 */
 
public class InviteStudent extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		Connection conn = (Connection)session.getAttribute("connection");
		PreparedStatement stmt = null;
		
		String class_id = request.getParameter("class_id");
		String student_id=request.getParameter("student_id");
		
		try{
		    stmt = conn.prepareStatement("insert into members values(?, ?, ?::\"member\")");
		    stmt.setInt(1, Integer.parseInt(student_id));
		    stmt.setString(2, class_id);
		    stmt.setString(3, "Student");
		}
		catch (SQLException e) {
		    System.out.println("InviteStudents - "+e.getMessage());
		}
	}
}