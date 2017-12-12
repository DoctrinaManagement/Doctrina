package zu.b5.doctrina.controller.classroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.classroom.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;

public class CreateClassroom extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession(); 
		
		CreateClassroomProcess process = new CreateClassroomProcess(session.getAttribute("connection"));
		String[] keys = {"user_id", "classroom_name", "classroom_description", "course_id"};
		HashMap<String,String> details = new HashMap<String,String>();
		for(String key : keys) {
		    details.put(key, request.getParameter(key));
		}
		
		if (process.createClassroom(details).equals("OK")){
		
		    writer.write("classroom has been created.");
		}
	}
}