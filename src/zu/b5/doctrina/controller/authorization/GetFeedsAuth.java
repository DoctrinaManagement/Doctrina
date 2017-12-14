package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;

public class GetFeedsAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
		
        if(checkDetails.checkClassroomPermission((session.getAttribute("user_id")+""), (session.getAttribute("class_id")+""))) {
            if(request.getParameter("type").equals("initial") || request.getParameter("type").equals("next")) {
                chain.doFilter(request, response);
            } else {
                 response.getWriter().write("400");
             }
        } else {
            response.getWriter().write("404");
        }
	}

	public void destroy() {
	}

}