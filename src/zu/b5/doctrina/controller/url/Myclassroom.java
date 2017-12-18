package zu.b5.doctrina.controller.url;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;

public class Myclassroom extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("load", "myclassroom");
        response.sendRedirect("/doctrina.index.do");
	}
}