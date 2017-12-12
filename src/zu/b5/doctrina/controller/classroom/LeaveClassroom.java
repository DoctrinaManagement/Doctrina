package zu.b5.doctrina.controller.classroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;


/**
 * @author Pandi
 */
public class LeaveClassroom extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		Connection conn = (Connection) session.getAttribute("connection");
		CheckValidDetails check = new CheckValidDetails(conn);
		ReUsable get = new ReUsable(conn);
		if(!(check.checkClassCreater((session.getAttribute("user_id")+""), (session.getAttribute("class_id")+"")))) {
    		try{
    		    Statement stmt1 = conn.createStatement();
    		    stmt1.executeUpdate("delete from members where member = '"+session.getAttribute("user_id")+"' and classroom_id = '"+session.getAttribute("class_id")+"';");
    		    
    		    String message = session.getAttribute("name")+" has left your " + session.getAttribute("class_name") + " classroom...";
    		    
    		    String Query = "select class_creater from classroom where classroom_id = '"+session.getAttribute("class_id")+"';";
    		    ResultSet rs = stmt1.executeQuery(Query);
    		    ArrayList<String> class_creater = get.resultSetToUserID(rs);
	            PreparedStatement stmt = conn.prepareStatement("insert into notification(user_id,message,status,sender) values(?, ?, ?::\"enum_status\", ?) ");
                stmt.setString(1, class_creater.get(0));
                stmt.setString(2, message);
                stmt.setString(3, "true");
                stmt.setString(4, session.getAttribute("user_id")+"");
                stmt.executeUpdate();
    		    writer.write("ok");
    		} catch(Exception e) {
    		    System.out.println("LeaveClassroom - "+e.getMessage());
    		}
		} else {
		    writer.write("you are a teacher!!!! ");
		}

	}
}