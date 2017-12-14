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
    
    public void setValuesPost (String user_id, String class_id, String message) {
        try {
            stmt = conn.prepareStatement("insert into posts (message, class_id, user_id) values(?, ?, ?) ");
            stmt.setString(1, message);
            stmt.setString(2, class_id);
            stmt.setString(3, user_id);
            stmt.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("insertFeedsProcess - setValuesPost - " + e.getMessage());
        }
    }
    
    public void setValuesComments(String commender, String post_id, String message){
        try {
            stmt = conn.prepareStatement("insert into comments (post_id, commenter, message) values(?, ?, ?) ");
            stmt.setInt(1, Integer.parseInt(post_id));
            stmt.setString(2, commender);
            stmt.setString(3, message);
            stmt.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("insertFeedsProcess - setValuesComments - " + e.getMessage());
        }
    
    }
    
    public void setValuesreply(String replyer, String comment_id, String message){
        try {
            stmt = conn.prepareStatement("insert into replies (message, replyer, comment_id) values(?, ?, ?) ");
            stmt.setString(1, message);
            stmt.setString(2, replyer);
            stmt.setInt(3, Integer.parseInt(comment_id));
            
            
            stmt.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("insertFeedsProcess - setValuesreply - " + e.getMessage());
        }
    
    }
    
}








