package zu.b5.doctrina.controller.report;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.report.ClassStudentNameProcess;
import javax.servlet.*;

/**
 * @author Basheer
 */
public class ClassStudentName extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		ClassStudentNameProcess value = new ClassStudentNameProcess(
				session.getAttribute("connection"),
				(session.getAttribute("user_id") + ""),
				(session.getAttribute("class_id") + ""));
        writer.write(value.getDetails());
	}
}
