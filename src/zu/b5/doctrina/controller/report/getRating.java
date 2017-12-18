package zu.b5.doctrina.controller.report;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.ReUsable;
import zu.b5.doctrina.model.report.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.time.*;
import com.google.gson.*;
/**
 * @author Basheer
 */
 
public class getRating extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		PreparedStatement stmt;
		
		try {
    		Connection conn = (Connection) session.getAttribute("connection");
    		HashMap<String, Object> ratingObj = new HashMap<String,Object>();
	    	ReUsable get = new ReUsable(conn);
            String student_id = request.getParameter("student_id");
            int class_id = Integer.parseInt(request.getParameter("class_id"));
            
            stmt = conn.prepareStatement("select rating, title, description from ratings where user_id = '"+student_id+"' and class_id = "+class_id+";");
            ResultSet rs = stmt.executeQuery(); 
            ArrayList<HashMap<String, String>> rating = get.resultSetToArrayList(rs);
            
            ratingObj.put("ratings", rating);
            String json = new Gson().toJson(ratingObj);
            writer.write(json);
		}
		catch(Exception e){
		    System.out.println("getratingjava - "+e.getMessage());
		}
	}
}
