package zu.b5.doctrina.controller.classroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;
import java.sql.*;

/**
 * @author Pandi
 */
public class LeaveClassroom extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		//System.out.println(session.getAttribute("connection"));
		Connection conn = (Connection) session.getAttribute("connection");
		try{
		    Statement stmt = conn.createStatement();
		    stmt.executeUpdate("delete from members where member = '"+session.getAttribute("user_id")+"' and classroom_id = '"+session.getAttribute("class_id")+"';");
		    writer.write("ok");
		} catch(Exception e) {
		    System.out.println("LeaveClassroom - "+e.getMessage());
		}
		

	}
}