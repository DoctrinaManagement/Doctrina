package zu.b5.doctrina.controller.insideclassroom;
import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;

/**
 * @author Basheer
 */
 
public class InsertFeeds extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		
		String class_id = request.getParameter("class_id");
		String user_id=request.getParameter("user_id");
		String postMessage = request.getParameter("postmessage");
		InsertFeedsProcess insert = new InsertFeedsProcess(session.getAttribute("connection"));
		
		insert.setValues(user_id, class_id, postMessage, "posts");
		
        writer.write("ok");
	}
}