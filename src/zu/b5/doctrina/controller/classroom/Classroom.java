package zu.b5.doctrina.controller.classroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.Gson;
import zu.b5.doctrina.model.classroom.GetMyclassroomProcess;
import zu.b5.doctrina.model.export.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import java.sql.*;

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
		        writer.write("This user can access this classroom");
		    } else {
	                writer.write("This user can't access this classroom");	    
		      }
		} else {
		    writer.write("404");
		  }
	}
}