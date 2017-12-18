package zu.b5.doctrina.controller.report;

import com.google.gson.Gson;
import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import java.sql.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import zu.b5.doctrina.model.report.GetVideoTitlesStatus;
/**
 * @author Basheer
 */
 
public class getVideoReports extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("connection");
        ReUsable get = new ReUsable(conn);
        Statement stmt = null;
        GetVideoTitlesStatus videoTable = new GetVideoTitlesStatus(conn);
        
	    String user = request.getParameter("user_id");
		String class_id = request.getParameter("class_id");
		
		try {
		    stmt = conn.createStatement();
		    HashMap<String, Object> videoReport= new HashMap<String, Object>();
		    
		    ResultSet rs = stmt.executeQuery("select title,id from videos where class_id = "+class_id+";");
			ArrayList<HashMap<String, String>> titles = get.resultSetToArrayList(rs);
			videoReport.put("titles", titles);
		
			HashMap<String, String> count = videoTable.titlesStatus(class_id, user);
    		videoReport.put("values", count);
    		
    		rs = stmt.executeQuery("select id from videostatus where user_id = '"+user+"' and class_id = "+class_id+" and status = 'true';");
        	ArrayList<String> finish = get.resultSetToUserID(rs);
    		videoReport.put("finished", finish);
    		
    		rs = stmt.executeQuery("select id, time, total_time from videostatus where user_id = '"+user+"' and class_id = "+class_id+";");
    	
    		ArrayList<HashMap<String, String>> status = get.resultSetToArrayList(rs);
            videoReport.put("timeStatus", status);
            System.out.println(videoReport);
    		String json = new Gson().toJson(videoReport);
			writer.write(json);
		}
		catch(SQLException e) {
		    System.out.println("getVideoReports - "+e.getMessage());
		}
		
	}
}