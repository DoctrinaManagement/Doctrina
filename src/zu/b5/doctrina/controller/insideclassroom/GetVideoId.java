package zu.b5.doctrina.controller.insideclassroom;

import com.google.gson.Gson;
import java.io.*;
import java.util.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;


/**
 * @author pandi
 */
 
 public class GetVideoId extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		VideoProcess process = new VideoProcess(session.getAttribute("connection"));
		HashMap<String,String> result = process.getVideoId(request.getParameter("videoId"));
		String json = new Gson().toJson(result);
		writer.write(json);

	}
}