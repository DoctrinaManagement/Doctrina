package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;


/**
 * @author pandi
 */
 
 public class InsertLike extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		LikeProcess value = new LikeProcess(session.getAttribute("connection"));
		if(request.getParameter("type").equals("posts")) {
		    String s = value.insertLike(request.getParameter("id"), request.getParameter("user_id"), "postlike");
		    writer.write(s);
		}
		else if(request.getParameter("type").equals("comments")) {
		    String s = value.insertLike(request.getParameter("id"), request.getParameter("user_id"), "commentlike");
		    writer.write(s);
		    
		}
		else if(request.getParameter("type").equals("replies")) {
		
		    String s = value.insertLike(request.getParameter("id"), request.getParameter("user_id"), "replylike");
		    writer.write(s);
		}
		
		
	}
}