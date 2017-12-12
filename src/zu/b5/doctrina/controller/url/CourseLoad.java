package zu.b5.doctrina.controller.url;

import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;
import zu.b5.doctrina.model.export.ReUsable;

/** 
 * @author Pandi
 */
public class CourseLoad extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			    
		HttpSession session = request.getSession();
		String values = request.getRequestURI().substring("/course/".length());
		String courseName = values.substring(0,1).toUpperCase() + values.substring(1).toLowerCase();
        Connection conn = (Connection) session.getAttribute("connection");
        ReUsable get = new ReUsable(conn);
        try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select course_id from courses where course_name = '"+courseName+"';");
			ArrayList<String> course_Name = get.resultSetToUserID(rs);
			values = course_Name.get(0);
		} catch (SQLException e) {
		    System.out.println("sample"+ e.getMessage());
		}
        session.setAttribute("function", "getClassroom("+values+","+courseName+")");
        session.setAttribute("course", courseName);
        response.sendRedirect("/doctrina.index.do");
	}
}