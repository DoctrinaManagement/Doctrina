package zu.b5.doctrina.model.request;

import java.sql.*;
import zu.b5.doctrina.model.export.ReUsable;
import java.util.*;

public class ReplyJoinRequestProcess{


    Connection conn;
    Statement stmt;
    
    public ReplyJoinRequestProcess(Object connection) {
        conn = (Connection) connection;
        try {
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("ReplyJoinRequestProcess - constructor");        
            
        }
    }
    
    
    public void deleteJoinRequest(String user, String class_id) {
        try {
            String deleteQuery = "delete from joinrequest where requester = '"+user+"' and class_id = '"+class_id +"';";
            stmt.executeUpdate(deleteQuery);
        } catch(SQLException e) {
            System.out.println("ReplyJoinRequestProcess - deleteJoinRequest"+e.getMessage());
        }
    }
    
    
    
    public void NotificationSend(String message, String user_id, String class_id) {
        String sender_id = getSender(class_id);
        String notificationQuery = "insert into notification(user_id,message,status,sender) values('"+user_id+"','"+message+"','true','"+sender_id+"');";
        try {
            stmt.executeUpdate(notificationQuery);
        } catch (SQLException e) {
            System.out.println("ReplyJoinRequestProcess - NotificationSend"+e.getMessage());
        }
    }
    
    public String getSender(String classroom_id) {
        
        String sender = "";
        String senderQuery = "Select class_creater from classroom where classroom_id = '"+classroom_id+"';";
        try {
            ResultSet rs = stmt.executeQuery(senderQuery);
            ReUsable get = new ReUsable(conn);
            ArrayList<String> array = get.resultSetToUserID(rs);
            sender = array.get(0);
        } catch (SQLException e) {
            System.out.println("ReplyJoinRequestProcess - getSender"+e.getMessage());
        }
        return sender;
        
    }
    
    public void MemberAdd(String member, String classroom_id) {
        try{
            String memberAddQuery = "insert into members values('"+classroom_id+"','"+member+"','Student');";
            stmt.executeUpdate(memberAddQuery);
        } catch(SQLException e) {
            System.out.println("ReplyJoinRequestProcess - MemeberAdd"+e.getMessage());
        }
    }
}