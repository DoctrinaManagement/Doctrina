package zu.b5.doctrina.model.notification;

import java.sql.*;
import java.util.Arrays.*;

public class SaveProcess{
    Connection conn;
    Statement stmt = null;
    
    public SaveProcess(Object connection) {
        conn = (Connection) connection;
        try {
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("SaveProcess - constructor");
        }
    }
    
    
    public void updateSetting(String userId, java.util.ArrayList<String> course_id) {
        try {
            String Query = "delete from settings where user_id ='"+userId+"';";
			stmt.executeUpdate(Query);
			for(String course_idValues : course_id) {
			    if (course_idValues.equals("")) {
			        break;
			    }
				Query = "insert into settings values ('"+userId+"','"+course_idValues+"');";
				stmt.executeUpdate(Query);
			}
        } catch(SQLException e) {
            System.out.println("SaveProcess - updateSetting");
        }
    }
    
}