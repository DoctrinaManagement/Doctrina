package zu.b5.doctrina.model.notification;

import java.sql.*;
import zu.b5.doctrina.model.export.ReUsable;
import java.util.*;

public class NotificationSettingsProcess{
    
    Connection conn;
    Statement stmt = null;
    public NotificationSettingsProcess(Object connection) {
        conn = (Connection) connection;
        try {
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("NotificationSettingsProcess - constructor");
        }
    }
    
    
    public ArrayList<String> getSettings(String userId) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            String Query = "select course_id from settings where user_id ='"+userId+"';";
			ResultSet rs = stmt.executeQuery(Query);
            ReUsable get = new ReUsable(conn);
            result = get.resultSetToUserID(rs);
        } catch (SQLException e) {
            System.out.println("NotificationSettingsProcess - getSettings"+e.getMessage());
        }
        return result;
    }
}