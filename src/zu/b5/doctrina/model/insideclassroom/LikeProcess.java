package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;

public class LikeProcess{

    Connection conn;
    Statement stmt;
    
    public LikeProcess(Object connection) {
        conn = (Connection) connection;
        try {
            stmt = conn.createStatement();
        } catch(Exception e) {
            System.out.println("LikeProcess - constructor" + e);
        }
    }
    
    public String insertLike(String id, String user_id, String tableName) {
        try{
            String Query = "insert into " + tableName+ " values("+id+",'"+ user_id + "');";
            stmt.executeUpdate(Query);
            return "ok";
        } catch(Exception e) {
            System.out.println("LikeProcess - insertLike" + e);
        }
        return "not ok";
    }
    
    public String deleteLike(String id, String user_id, String tableName) {
        
        try{
            String Query = "delete from " + tableName+ " where id = "+id+" and user_id = '"+user_id+"';";
            stmt.executeUpdate(Query);
            return "ok";
        } catch(Exception e) {
            System.out.println("LikeProcess - deleteLike " + e);
        }
        
        return "not ok";
    }
    
    public void addNotification() {
        
    }
}