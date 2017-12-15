package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;

import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;

/**
 * @author Basheer
 */
public class CreateClassroomAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(
				session.getAttribute("connection"));

		if (checkDetails.courseIdCheck(request.getParameter("course_id"))
				&& checkDetails.userIdCheck(request.getParameter("user_id"))) {

			if (request.getParameter("classroom_name").matches("^[a-zA-Z0-9]{3,25}$")
					&& request.getParameter("classroom_description").matches(
							"^.{5,50}$")) {
				if (checkDetails.isTeacher(request.getParameter("user_id"))) {
        				chain.doFilter(request, response);
				}
				else {
				    writer.write("403");
				}
			} else {
				writer.write("400");
			}

		} else {
			writer.write("404");
		}
	}

	public void destroy() {
	}

}