package zu.b5.doctrina.controller.classroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.Gson;
import zu.b5.doctrina.model.classroom.GetClassroomProcess;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;

/**
 * @author Pandi
 */
public class GetClassroom extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		session.setAttribute("load", "null");
		GetClassroomProcess process = new GetClassroomProcess(session.getAttribute("connection"));
		int course_id = Integer.parseInt(request.getParameter("course_id"));
		
		HashMap<String, ArrayList<HashMap<String, String>> > classroom = new HashMap<String, ArrayList<HashMap<String, String>> >();
        classroom.put("classrooms", process.getClassroom(request.getParameter("user_id"), course_id));
        classroom.put("courseName", process.getCourseName(request.getParameter("course_id")));
		String json = new Gson().toJson(classroom);
		writer.write(json);

	}
}