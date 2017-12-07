package zu.b5.doctrina.controller.requests;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.*;
import javax.servlet.*;
import java.sql.*;

/** 
 * @author Basheer
 */
 
public class ClassroomJoinRequest extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		    
		    PrintWriter writer = response.getWriter();
    		HttpSession session = request.getSession();
            Statement stmt = null;
    		
    		try {
    			stmt = ((Connection) session.getAttribute("connection")).createStatement();
    			String user = request.getParameter("requester");
    			String class_id = request.getParameter("class_id");
    			String Query = "insert into joinrequest(requester, class_id) values ('"+user+"','"+class_id+"');";
    			stmt.executeUpdate(Query);
			    writer.write("ok");
    		}
    		catch (SQLException e) {
    		    System.out.println("JoinclassroomJAVA - " + e);
    		}
    }
}
