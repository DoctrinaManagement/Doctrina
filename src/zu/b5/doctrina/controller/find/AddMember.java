package zu.b5.doctrina.controller.find;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.classroom.*;
import zu.b5.doctrina.model.export.CheckValidDetails;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import java.sql.*;


public class AddMember extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		CheckValidDetails check = new CheckValidDetails(session.getAttribute("connection"));
		if(check.userIdCheck(request.getParameter("requester"))) {
		    if( !(check.checkAddPermission(request.getParameter("requester"), (session.getAttribute("class_id")+""))) ) {
		        String Query = "insert into members values(?,?,?::\"member\");";
		        Connection conn = (Connection) session.getAttribute("connection");
		        
		        try{
		            PreparedStatement stmt = conn.prepareStatement(Query);
		            stmt.setInt(1, Integer.parseInt((session.getAttribute("class_id")+"")) );
		            stmt.setString(2, request.getParameter("requester"));
		            stmt.setString(3, "Student");
		            stmt.executeUpdate();
		            String message = "You have just now added in "+session.getAttribute("class_name")+" classroom";
		            stmt = conn.prepareStatement("insert into notification(user_id,message,status,sender) values(?, ?, ?::\"enum_status\", ?) ");
                    stmt.setString(1, request.getParameter("requester"));
                    stmt.setString(2, message);
                    stmt.setString(3, "true");
                    stmt.setString(4, session.getAttribute("user_id")+"");
                    stmt.executeUpdate();
		            writer.write("ok");
		        } catch (Exception e) {
		            System.out.println("AddMember - " + e.getMessage());
		        }
		        
		    } else {
		        writer.write("400");
		    }
		} else{
		    writer.write("404");
		}
	}
}
