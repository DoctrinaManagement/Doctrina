package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;
import java.text.SimpleDateFormat;
/**
 * @author pandi
 */
 
public class InsertFeeds extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		
		String class_id = request.getParameter("class_id");
		String user_id=request.getParameter("user_id");
		String postMessage = request.getParameter("message");
		InsertFeedsProcess insert = new InsertFeedsProcess(session.getAttribute("connection"));
		if(request.getParameter("type").equals("posts")) {
		    insert.setValuesPost(user_id, class_id, postMessage);
		    GetFeedsDetailsProcess value = new GetFeedsDetailsProcess(session.getAttribute("connection"));
            HashMap<String, ArrayList<HashMap<String,String>>> result = new HashMap<String, ArrayList<HashMap<String,String>>>();
            SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeStamp.setTimeZone(TimeZone.getTimeZone("IST"));
            Date date = new Date();
            result.put("posts", value.getObject((session.getAttribute("class_id")+""), timeStamp.format(date), "yet"));
            String json = new Gson().toJson(result);
            writer.write(json);
		}
		else if(request.getParameter("type").equals("comments")) {
		    insert.setValuesComments(request.getParameter("user_id"), request.getParameter("post_id"), request.getParameter("message"));
		}
		else if(request.getParameter("type").equals("replies")) {
		    insert.setValuesreply(request.getParameter("user_id"), request.getParameter("comment_id"), request.getParameter("message"));
		}
		writer.write("ok");
		
	}
}