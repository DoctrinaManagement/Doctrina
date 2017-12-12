package zu.b5.doctrina.controller.insideclassroom;

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
 
public class GetTitles extends HttpServlet {
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
		
		try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select title,id from "+type+" where class_id = "+class_id+";");
    		
    		HashMap<String,ArrayList<HashMap<String, String>>> titlesObj = new HashMap<String,ArrayList<HashMap<String, String>>>();
    		ArrayList<HashMap<String, String>> titles = get.resultSetToArrayList(rs);
    		
    		titlesObj.put("titles", titles);
    		String json = new Gson().toJson(titlesObj);
	        writer.write(json);
		}
		catch (SQLException e) {
		    System.out.println("GetTitles - "+ e.getMessage());
		}
	}
}