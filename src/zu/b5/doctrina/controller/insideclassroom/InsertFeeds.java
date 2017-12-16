package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;
import java.text.SimpleDateFormat;
import java.sql.*;
// import java.sql.Date;
import zu.b5.doctrina.model.export.*;
/**
 * @author pandi
 */
 
public class InsertFeeds extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		
		String class_id = request.getParameter("class_id");
		String user_id=request.getParameter("user_id");
		String postMessage = request.getParameter("message");
		InsertFeedsProcess insert = new InsertFeedsProcess(session.getAttribute("connection"));
		GetFeedsDetailsProcess value = new GetFeedsDetailsProcess(session.getAttribute("connection"));
		ReUsable get = new ReUsable(session.getAttribute("connection"));
		
		if(request.getParameter("type").equals("posts")) {
		    String s = insert.setValuesPost(user_id, class_id, postMessage);
		    if(s.equals("ok")) {
        		    
                    HashMap<String, ArrayList<HashMap<String,Object>>> result = new HashMap<String, ArrayList<HashMap<String,Object>>>();
                    SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    timeStamp.setTimeZone(TimeZone.getTimeZone("IST"));
                    Date date = new Date();
                    result.put("posts", value.getObject((session.getAttribute("class_id")+""), timeStamp.format(date), "yet"));
                    insert.sendNotification((session.getAttribute("class_id")+""), (session.getAttribute("name")+""), "posted", (session.getAttribute("class_name")+""), (session.getAttribute("user_id")+"") );                   
                    String json = new Gson().toJson(result);
                    writer.write(json);
		    }
		    else {
		        writer.write("400");
		    }
		    
		    
		}
		else if(request.getParameter("type").equals("comments")) {
		    
		    String s = insert.setValuesComments(request.getParameter("user_id"), request.getParameter("id"), request.getParameter("message"));
    		 if(s.equals("ok")) {   
    		    String Query = "select id from comments";
    		    try{
        		    Connection conn = (Connection) session.getAttribute("connection");
        		    Statement stmt = conn.createStatement();
        		    ResultSet rs = stmt.executeQuery(Query);
        		    ArrayList<String> ids = get.resultSetToUserID(rs);
        		    insert.sendNotification((session.getAttribute("class_id")+""), (session.getAttribute("name")+""), "commented", (session.getAttribute("class_name")+""), (session.getAttribute("user_id")+"") );
        		    HashMap<String,Object> comments = value.getValue(ids.get(ids.size() - 1), "comments");
    		        String json = new Gson().toJson(comments);
                    writer.write(json);
                    
    		    } catch(Exception e) {
    		        System.out.println("InsertFeeds comments " );
    		    }
    		 } else {
    		     writer.write("400");
    		 }
		}
		else if(request.getParameter("type").equals("replies")) {
		    String s = insert.setValuesreply(request.getParameter("user_id"), request.getParameter("id"), request.getParameter("message"));
		   
		    if(s.equals("ok")) {   
    		    String Query = "select id from replies";
    		    try{
        		    Connection conn = (Connection) session.getAttribute("connection");
        		    Statement stmt = conn.createStatement();
        		    ResultSet rs = stmt.executeQuery(Query);
        		    insert.sendNotification((session.getAttribute("class_id")+""), (session.getAttribute("name")+""), "replied", (session.getAttribute("class_name")+""), (session.getAttribute("user_id")+"") );
        		    ArrayList<String> ids = get.resultSetToUserID(rs);
        		    HashMap<String,Object> comments = value.getValue(ids.get(ids.size() - 1), "replies");
    		        String json = new Gson().toJson(comments);
                    writer.write(json);
                    
    		    } catch(Exception e) {
    		        System.out.println("InsertFeeds comments " );
    		    }
    		 } else {
    		     writer.write("400");
    		 }
		    
		}
		
		
	}
}