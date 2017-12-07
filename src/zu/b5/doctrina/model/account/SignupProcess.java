package zu.b5.doctrina.model.account;


import java.sql.*;
import java.util.*;

public class SignupProcess {
	Connection conn;
    Statement stmt = null;
	public SignupProcess(Object connection) {
		conn = (Connection) connection;
		try {
		    stmt = conn.createStatement();
		} catch (Exception e) {
		    System.out.println("SignupProcess -- constructor");
		}
	}

	public String userdetailsAdd(HashMap<String, String> details) {

		

		try {
			
			String Userdetails_Query = "insert into userdetails values('"
					+ details.get("user_id") + "','" + details.get("name")
					+ "','" + details.get("email_id") + "','"
					+ details.get("image") + "','" + details.get("role")
					+ "');";

			stmt.executeUpdate(Userdetails_Query);
            return "done";
		} catch (SQLException e) {
			System.out.println("SignupProcess -- userdetailsAdd"
					+ e.getMessage());
			return e.getMessage();
		}
	}

	public String notificationAdd(HashMap<String, String> details) {
	    
	    try {
	        
	        String Notification_Query = "insert into notification(user_id,message,status,sender) values('" + details.get("userId")+ "','" + details.get("notification") + "','" + details.get("status") +"','"+details.get("sender") +"');";
            stmt.executeUpdate(Notification_Query);
            return "done";
	    } catch (SQLException e) {
	        System.out.println("SignupProcess -- notificationAdd"
					+ e.getMessage());
			return e.getMessage();
	    }
	}
	
	public String cookieAdd(String cookie, String userId) {

	    try {
	        stmt.executeUpdate("insert into cookie values('"+cookie+"','"+userId+"');");
	        return "done";
	    } catch (SQLException e) {
	        System.out.println("SignupProcess -- cookieAdd"
					+ e.getMessage());
			return e.getMessage();
	    }
	}
}