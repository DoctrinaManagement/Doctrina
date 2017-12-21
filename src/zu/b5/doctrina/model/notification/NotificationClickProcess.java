package zu.b5.doctrina.model.notification;

import java.sql.*;

public class NotificationClickProcess{
    Connection conn;
    Statement stmt = null;
    
    public NotificationClickProcess(Object connection) {
        conn = (Connection) connection;
        try{ 
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("NotificationClickProcess - constructor");
        }
    }
    
    public void clickAction(String user_id, String date) {
        try {
            String Query = "update notification set status='"+false+"'where user_id='"+user_id+"'AND date ='"+date+"';";
			stmt.executeUpdate(Query);
        } catch (SQLException e) {
            System.out.println("NotificationClickProcess - clickAction"+ e.getMessage());
        }
    }
}