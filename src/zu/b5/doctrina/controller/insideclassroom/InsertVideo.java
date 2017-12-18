package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;


/**
 * @author pandi
 */
 
 public class InsertVideo extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		VideoProcess process = new VideoProcess(session.getAttribute("connection"));
		
		writer.write(process.insertVideo(request.getParameter("video_id"), request.getParameter("class_id"), request.getParameter("title")));
		
		
		
	}
}