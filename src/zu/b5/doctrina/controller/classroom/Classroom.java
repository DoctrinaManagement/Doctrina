package zu.b5.doctrina.controller.classroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;

/**
 * @author Pandi
 */
public class Classroom extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
		
		String user_id = request.getParameter("user_id");
		String class_id = request.getParameter("class_id");
		
		if ( checkDetails.userIdCheck(user_id) && checkDetails.classIdCheck(class_id)) {
		    if(checkDetails.checkClassroomPermission(user_id, class_id)) {
		        session.setAttribute("class_id",class_id);
		        Connection conn = (Connection) session.getAttribute("connection");
		        try{
		            Statement stmt = conn.createStatement();
		            ResultSet rs  = stmt.executeQuery("select classroom_name,class_creater from classroom where classroom_id ='"+class_id+"';");
		            ReUsable get = new ReUsable(conn);
		            HashMap<String,String> details = get.resultSetToHashMap(rs);
		            session.setAttribute("class_name", details.get("classroom_name"));
		            session.setAttribute("class_role", "Student");
		            if(details.get("class_creater").equals(user_id)) {
		                session.setAttribute("class_role", "Teacher");
		            }
		            writer.write("200");
		        } catch (Exception e) {
		            System.out.println("classroom.java");
		        }
		         
		    } else {
	                writer.write("401");	    
		      }
		} else {
		    writer.write("404");
		  }
	}
}