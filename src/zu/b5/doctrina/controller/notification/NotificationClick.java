package zu.b5.doctrina.controller.notification;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.notification.NotificationClickProcess;
import javax.servlet.*;
/** 
 * @author Basheer
 */
public class NotificationClick extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		NotificationClickProcess process = new NotificationClickProcess(session.getAttribute("connection"));
		process.clickAction(request.getParameter("user_id"), request.getParameter("message"));
		writer.write("ok");
		
	}
}


