package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.*;
import java.sql.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;

/**
 * @author pandi
 */

public class SaveUpdate extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		VideoProcess insert = new VideoProcess(
				session.getAttribute("connection"));
		
	
		insert.insertUpdate((session.getAttribute("user_id") + ""),
				request.getParameter("id"),
				request.getParameter("current_duration"),
				request.getParameter("total_duration"),
				(session.getAttribute("class_id") + ""));
				
		writer.write("200");
				

	}
}