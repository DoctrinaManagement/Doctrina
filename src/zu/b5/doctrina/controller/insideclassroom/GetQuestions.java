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
 
public class GetQuestions extends HttpServlet {
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
		String id = request.getParameter("id");
		
		try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select * from "+type+" where id = "+id+" and class_id = "+class_id+";");
    		
    		ArrayList<HashMap<String,String>> questions = get.resultSetToArrayList(rs);
    		
    		HashMap<String, ArrayList<HashMap<String,String>>> questionsObj = new HashMap<String, ArrayList<HashMap<String,String>>>();
    		questionsObj.put("questions",questions);
    		String json = new Gson().toJson(questionsObj);
	        writer.write(json);
		}
		catch (SQLException e) {
		    System.out.println("GetTitles - "+ e.getMessage());
		}
	}
}