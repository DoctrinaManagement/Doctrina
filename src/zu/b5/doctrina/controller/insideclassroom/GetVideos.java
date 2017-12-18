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
 
 public class GetVideos extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		VideoProcess process = new VideoProcess(session.getAttribute("connection"));
		ArrayList<HashMap<String,String>> video = process.getVideo(request.getParameter("class_id"));
		HashMap<String, Object> videoObj = new HashMap<String, Object>();
		videoObj.put("videos", video);
		videoObj.put("finished", process.videoFinishedId(request.getParameter("class_id"), request.getParameter("user_id")));
		String json = new Gson().toJson(videoObj);
		writer.write(json);

	}
}