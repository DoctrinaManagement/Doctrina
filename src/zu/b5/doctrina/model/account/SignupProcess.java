package zu.b5.doctrina.model.account;


import java.sql.*;
import java.util.*;

public class SignupProcess {
	Connection conn;
    PreparedStatement stmt;
    // Statement stmt;
	public SignupProcess(Object connection) {
		conn = (Connection) connection;
		
	}

	public String userdetailsAdd(HashMap<String, String> details) {

		try {
			
// 			String Userdetails_Query = "insert into userdetails values('"
// 					+ details.get("user_id") + "','" + details.get("name")
// 					+ "','" + details.get("email_id") + "','"
// 					+ details.get("image") + "','" + details.get("role")
// 					+ "');";
            String role = details.get("role");
            stmt = conn.prepareStatement("insert into userdetails values (?, ?, ?, ?, ?::\"enum_role\")");
            stmt.setString(1, details.get("user_id"));
            stmt.setString(2, details.get("name"));
            stmt.setString(3, details.get("email_id"));
            stmt.setString(4, details.get("image"));
            stmt.setString(5, role);
			stmt.executeUpdate();
            return "done";
		} catch (SQLException e) {
			System.out.println("SignupProcess -- userdetailsAdd"
					+ e.getMessage());
			return e.getMessage();
		}
	}

	public String notificationAdd(HashMap<String, String> details) {
	    
	    try {
	        
	       // String Notification_Query = "insert into notification(user_id,message,status,sender) values('" + details.get("userId")+ "','" + details.get("notification") + "','" + details.get("status") +"','"+details.get("sender") +"');";
            stmt = conn.prepareStatement("insert into notification(user_id,message,status,sender) values(?, ?, ?::\"enum_status\", ?) ");
            stmt.setString(1, details.get("user_id"));
            stmt.setString(2, details.get("notification"));
            stmt.setString(3, details.get("status"));
            stmt.setString(4, details.get("sender"));
            stmt.executeUpdate();
            return "done";
	    } catch (SQLException e) {
	        System.out.println("SignupProcess -- notificationAdd"
					+ e.getMessage());
			return e.getMessage();
	    }
	}
	
	public String cookieAdd(String cookie, String userId) {

	    try {
	        stmt = conn.prepareStatement("insert into cookie values (?, ?)");
	        stmt.setString(1, cookie);
	        stmt.setString(2, userId);
	        stmt.executeUpdate();
	       // stmt.executeUpdate("insert into cookie values('"+cookie+"','"+userId+"');");
	        return "done";
	    } catch (SQLException e) {
	        System.out.println("SignupProcess -- cookieAdd"
					+ e.getMessage());
			return e.getMessage();
	    }
	}
}