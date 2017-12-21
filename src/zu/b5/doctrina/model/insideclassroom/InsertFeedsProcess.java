package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;

public class InsertFeedsProcess{

    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    
    public InsertFeedsProcess(Object connection) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
    }
    
    public String setValuesPost (String user_id, String class_id, String message) {
        try {
            stmt = conn.prepareStatement("insert into posts (message, class_id, user_id) values(?, ?, ?) ");
            stmt.setString(1, message);
            stmt.setInt(2, Integer.parseInt(class_id));
            stmt.setString(3, user_id);
            stmt.executeUpdate();
            Thread.sleep(1000);
            return "ok";
        }
        catch (Exception e) {
            System.out.println("insertFeedsProcess - setValuesPost - " + e.getMessage());
        }
        return "not ok";
    }
    
    public String setValuesComments(String commender, String post_id, String message){
        try {
            
            stmt = conn.prepareStatement("insert into comments (post_id, commenter, message) values(?, ?, ?) ");
            stmt.setInt(1, Integer.parseInt(post_id));
            stmt.setString(2, commender);
            stmt.setString(3, message);
            stmt.executeUpdate();
		    return "ok";
        }
        catch (Exception e) {
            System.out.println("insertFeedsProcess - setValuesComments - " + e);
        }
        return "not ok";
    }
    
    public String setValuesreply(String replyer, String comment_id, String message){
        try {
            stmt = conn.prepareStatement("insert into replies (message, replyer, comment_id) values(?, ?, ?) ");
            stmt.setString(1, message);
            stmt.setString(2, replyer);
            stmt.setInt(3, Integer.parseInt(comment_id));
            stmt.executeUpdate();
            return "ok";
        }
        catch (SQLException e) {
            System.out.println("insertFeedsProcess - setValuesreply - " + e.getMessage());
        }
        
        return "not ok";
    
    }
    
    public void sendNotification(String class_id, String Name, String type, String class_name, String sender_id ) {
        String selectQuery = "select member from members where classroom_id = "+class_id+" and member != '"+sender_id+"';";
        
        String message = Name +" has "+type+" in your "+class_name+" classroom";
        
        try{
            stmt = conn.prepareStatement(selectQuery);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> user_id = get.resultSetToUserID(rs);
            for(String user : user_id) {
                stmt = conn.prepareStatement("insert into notification(user_id,message,status,sender) values(?, ?, ?::\"enum_status\", ?) ");
                stmt.setString(1, user);
                stmt.setString(2, message);
                stmt.setString(3, "true");
                stmt.setString(4, sender_id);
                stmt.executeUpdate();
            }
            
        } catch(Exception e) {
            System.out.println("InsertFeedsProcess sendNotification"+e);
        }
            String Query = "insert into ";
    }
    
}








