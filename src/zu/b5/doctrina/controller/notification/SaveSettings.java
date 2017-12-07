package zu.b5.doctrina.controller.notification;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.notification.SaveProcess;
import javax.servlet.*;
/** 
 * @author Basheer
 */
public class SaveSettings extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		String[] course_id = (request.getParameter("courses")+"").split("A");
		SaveProcess process = new SaveProcess(session.getAttribute("connection"));
		process.updateSetting(request.getParameter("user_id"), course_id);
		writer.write("save successfully");
	}
}
