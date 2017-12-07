package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;

/**
 * @author Pandi
 */
public class ReplyJoinrequestAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		System.out.println(request.getParameter("requester") + " "
				+ request.getParameter("class_id"));
		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(
				session.getAttribute("connection"));

		if (checkDetails.userIdCheck(request.getParameter("requester") + "")
				&& checkDetails.classIdCheck(request.getParameter("class_id")
						+ "")) {
            if ((request.getParameter("status")+"").equals("Accept") || (request.getParameter("status")+"").equals("Decline")) {
                
                if (checkDetails.isAvailableJoinRequest(
					request.getParameter("requester"),
					request.getParameter("class_id"))) {
					    
    				chain.doFilter(request, response);
    			} else {
    				writer.write("something went wrong");
    			}
            }
            else {
                writer.write("404");
            }
			
		} else {
			writer.write("404");
		}
	}

	public void destroy() {
	}

}