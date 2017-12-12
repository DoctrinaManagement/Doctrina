package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;

/**
 * @author Basheer
 */

public class InviteStudentAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(
				session.getAttribute("connection"));

		String teacher = request.getParameter("user_id");
		String class_id = request.getParameter("class_id");
		String student_id = request.getParameter("student_id");

		if (checkDetails.userIdCheck(teacher)
				&& checkDetails.userIdCheck(student_id)
				&& checkDetails.classIdCheck(class_id)) {

			if (checkDetails.checkClassCreater(teacher, class_id)) {
				    chain.doFilter(request, response);
			} else {
				writer.write("401");
			}

		} else {
			writer.write("404");
		}
	}

	public void destroy() {
	}
}