package zu.b5.doctrina.controller.notification;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.Gson;
import zu.b5.doctrina.model.notification.NotificationSettingsProcess;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
/** 
 * @author Basheer
 */
public class NotificationSettings extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		NotificationSettingsProcess process = new NotificationSettingsProcess(session.getAttribute("connection"));
		ArrayList<String> courses = process.getSettings(request.getParameter("user_id"));
		String json = new Gson().toJson(courses);
		writer.write(json);
	}
}
