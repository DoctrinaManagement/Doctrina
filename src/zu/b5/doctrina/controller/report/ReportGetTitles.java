package zu.b5.doctrina.controller.report;

import com.google.gson.Gson;
import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import java.sql.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.export.ReUsable;
import zu.b5.doctrina.model.insideclassroom.*;
/**
 * @author Basheer
 */
 
public class ReportGetTitles extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("connection");
        ReUsable get = new ReUsable(conn);
        Statement stmt = null;
    
	    String user = request.getParameter("user_id");
		String class_id = request.getParameter("class_id");
		String type = request.getParameter("type");
		
		System.out.println(user +" - "+ class_id +" - "+ type);
		try {
    		stmt = conn.createStatement();
    		HashMap<String,ArrayList<HashMap<String, String>>> titlesObj = new HashMap<String,ArrayList<HashMap<String, String>>>();
    		
    		ResultSet rs = stmt.executeQuery("select title,id from "+type+" where class_id = "+class_id+";");
    		
			ArrayList<HashMap<String, String>> titles = get.resultSetToArrayList(rs);
			
			//question total 
    		rs = stmt.executeQuery("select count(title) as count from "+type+" where class_id = "+class_id+";");
    		HashMap<String, String> count = get.resultSetToHashMap(rs);
    	    
    	    // questionAnswers total
    	    String typesubstring = type.substring(0, type.length()-6)+"status";
    	    rs = stmt.executeQuery("select count(title_id) as finish from "+typesubstring+" where class_id = "+class_id+" group by title_id;");
    		ArrayList<String> finish = get.resultSetToUserID(rs);
    	    count.put("finish", finish.size()+"");
    	    ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
    		values.add(count);
    	    
    	    titlesObj.put("titles", titles);
    	    titlesObj.put("values", values);
    		String json = new Gson().toJson(titlesObj);
	        writer.write(json);
		}
		catch (SQLException e) {
		    System.out.println(e.getMessage());
		}
	}
}