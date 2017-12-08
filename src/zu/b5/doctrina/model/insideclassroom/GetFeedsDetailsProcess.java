package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.ReUsable;

public class GetFeedsDetailsProcess{
    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    ArrayList<HashMap<String,String>> Obj ;
    
    public GetFeedsDetailsProcess(Object connection, String tableName) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
        Obj = new ArrayList<HashMap<String,String>>();
        
    }
    
    public void getObject(String class_id) {

        String Query = "select id from posts where class_id = ?;";
        try {
            stmt = conn.prepareStatement(Query);
            stmt.setString(1, class_id);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> post_ids = get.resultSetToUserID(rs);
            
            for(String post_id : post_ids) {
                
                HashMap<String,String> post = getValue(post_id,"posts");
                stmt = conn.prepareStatement("select id from comments where post_id = ?;");
                stmt.setString(1, post_id);
                rs = stmt.executeQuery();
                ArrayList<String> comment_ids = get.resultSetToUserID(rs);
                ArrayList<HashMap<String,String>> comments = new ArrayList<HashMap<String,String>>();
                
                for(String comment_id : comment_ids) {
                    HashMap<String,String> comment = getValue(post_id,"comments");
                    stmt = conn.prepareStatement("select id from replies where comment_id = ?;");
                    stmt.setString(1, comment_id);
                    rs = stmt.executeQuery();
                    ArrayList<String> reply_ids = get.resultSetToUserID(rs);
                    ArrayList<HashMap<String,String>> replies = new ArrayList<HashMap<String,String>>();
                    for(String reply_id : reply_ids) {
                        replies.add(getValue(reply_id, "replies"));
                    }
                    comment.put("replies", (replies+""));
                    comments.add(comment);
                }
                
                post.put("comments", (comments+""));
                Obj.add(post);
            }
        } catch (SQLException e) {
            
        }
        
    }
    
    public HashMap<String,String> getValue(String id, String tableName) {
        String Query = "select * from "+tableName+" where id = ?";
        HashMap<String,String> details = new HashMap<String,String>();
        try{
            stmt = conn.prepareStatement(Query);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            details = get.resultSetToHashMap(rs);
            stmt = conn.prepareStatement("select name,image from userdetails where user_id = ?;");
            stmt.setString(1, details.get("user_id"));
            rs = stmt.executeQuery();
            HashMap<String,String> temp = get.resultSetToHashMap(rs);
            details.put("name", temp.get("name"));
            details.put("image", temp.get("image"));
            details.put("likers", getLikersName(details.get("id"), tableName));
            
        } catch (SQLException e) {
            System.out.println("GetFeedsDetailsProcess - getValue"+ e.getMessage());
        }
        return details;
        
    }
    
    
    public String getLikersName(String ID, String tableName) {
        ArrayList<String> name = new ArrayList<String>();
        try {
            String Query = "select user_id from "+tableName+"where id = ? ;";
            stmt = conn.prepareStatement(Query);
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> temp = get.resultSetToUserID(rs);
            for (String ids : temp) {
                name.add(getName(ids));
            }
            
        } catch (SQLException e) {
            System.out.println("GetFeedsDetailsProcess - getLikersName"+ e.getMessage());
        }
        return (name+"");
    }
    
    
    public String getName(String user_id) {
        String Query = "select Name from userdetails where user_id = ?;";
        String Name = "";
        try {
            stmt =  conn.prepareStatement(Query);
            stmt.setString(1, user_id);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> temp = get.resultSetToUserID(rs);
            Name = temp.get(0);
        } catch (SQLException e) {
            System.out.println("GetFeedsDetailsProcess - getName"+ e.getMessage());
        }
        return Name;
    }
    
}