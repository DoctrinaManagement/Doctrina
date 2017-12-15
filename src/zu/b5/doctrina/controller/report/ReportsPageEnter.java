package zu.b5.doctrina.controller.report;

import com.google.gson.Gson;
import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import java.sql.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
/**
 * @author Basheer
 */
 
public class ReportsPageEnter extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		Connection conn = (Connection) session.getAttribute("connection");
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
        ReUsable get = new ReUsable(conn);
		Statement stmt = null;
		
		try {
    		if(checkDetails.userIdCheck(request.getParameter("student_id"))) {
        		   
    		   if(checkDetails.checkClassroomPermission( request.getParameter("student_id"), request.getParameter("class_id")+"" )){
                 
                 String Query = "select name,image from userdetails where user_id = '"+request.getParameter("student_id")+"';";
    			 stmt = conn.createStatement();
    			 ResultSet rs = stmt.executeQuery(Query);
    			 HashMap<String,String> obj = get.resultSetToHashMap(rs);
    			 String json = new Gson().toJson(obj);
    			 writer.write(json);
    		   }
    		   else {
    			   writer.write("ReportsPageEnter 401");
    		   }
    	   }
    	   else {
    	       writer.write("ReportsPageEnter 404");
    	   }
		}
		catch (Exception e) {
		    System.out.println("ReportsPageEnter" + e);
		}
	}
}