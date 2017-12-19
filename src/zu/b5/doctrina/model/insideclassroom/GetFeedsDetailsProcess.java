package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.ReUsable;

public class GetFeedsDetailsProcess{
    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    ArrayList<HashMap<String,Object>> Obj ;
    
    public GetFeedsDetailsProcess(Object connection) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
        Obj = new ArrayList<HashMap<String,Object>>();
        
    }
    
    public ArrayList<HashMap<String,Object>> getObject(String class_id, String date, String type) {
        String Query = "";
        if(type.equals("get")) {
            Query = "select id from posts where class_id = ? and date < '"+date+"' order by date desc limit 10;";
        } else {
            Query ="select id from posts where class_id = ? and date < '"+date+"' order by date desc limit 1;";
        }
        
        try {
            stmt = conn.prepareStatement(Query);
            stmt.setInt(1, Integer.parseInt(class_id));
            ResultSet rs = stmt.executeQuery();
            
            ArrayList<String> post_ids = get.resultSetToUserID(rs);
            for(String post_id : post_ids) {
                
                HashMap<String,Object> post = (HashMap) getValue(post_id,"posts");
                stmt = conn.prepareStatement("select id from comments where post_id = ?;");
                stmt.setInt(1, Integer.parseInt(post_id));
                rs = stmt.executeQuery();
                ArrayList<String> comment_ids = get.resultSetToUserID(rs);
                ArrayList<HashMap<String,Object>> comments = new ArrayList<HashMap<String,Object>>();
                
                for(String comment_id : comment_ids) {
                    HashMap<String,Object> comment = (HashMap) getValue(comment_id ,"comments");
                    stmt = conn.prepareStatement("select id from replies where comment_id = ?;");
                    stmt.setInt(1, Integer.parseInt(comment_id));
                    rs = stmt.executeQuery();
                    ArrayList<String> reply_ids = get.resultSetToUserID(rs);
                    ArrayList<HashMap<String,Object>> replies = new ArrayList<HashMap<String,Object>>();
                    for(String reply_id : reply_ids) {
                        replies.add((HashMap) getValue(reply_id, "replies"));
                    }
                    comment.put("replies", replies);
                    comments.add(comment);
                }
                
                post.put("comments", comments);
                Obj.add(post);
                
            }
        } catch (SQLException e) {
            System.out.println(" GetFeedsDetailsProcess - " +e);
        }
        
        return Obj;
        
    }
    
    public HashMap<String,Object> getValue(String id, String tableName) {
        String Query = "select * from "+tableName+" where id = ?";
        HashMap<String,Object> details = new HashMap<String,Object>();
        try{
            stmt = conn.prepareStatement(Query);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            details =(HashMap) get.resultSetToHashMap(rs);
            stmt = conn.prepareStatement("select name,image from userdetails where user_id = ?;");
            String use = "";
            if(tableName.equals("posts")) {
                tableName = "postlike";
                use = (String) details.get("user_id");
            } 
            else if(tableName.equals("comments")) {
                tableName = "commentlike";
                use = (String) details.get("commenter");
            } 
            else if(tableName.equals("replies")) {
                tableName = "replylike";
                use = (String) details.get("replyer");
            }
            stmt.setString(1, use);
            rs = stmt.executeQuery();
            HashMap<String,String> temp = get.resultSetToHashMap(rs);
            details.put("name", temp.get("name"));
            details.put("image", temp.get("image"));
            
            details.put("likers", getLikersName((String) details.get("id"), tableName));
            
        } catch (SQLException e) {
            System.out.println("GetFeedsDetailsProcess - getValue"+ e.getMessage());
        }
        return details;
        
    }
    
    
    public ArrayList<String> getLikersName(String ID, String tableName) {
        ArrayList<String> name = new ArrayList<String>();
        try {
            String Query = "select user_id from "+tableName+" where id = ? ;";
            stmt = conn.prepareStatement(Query);
            stmt.setInt(1, Integer.parseInt(ID));
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> temp = get.resultSetToUserID(rs);
            for (String ids : temp) {
                name.add(getName(ids));
            }
            
        } catch (Exception e) {
            System.out.println("GetFeedsDetailsProcess - getLikersName "+ e.getMessage());
        }
        return name;
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
            System.out.println("GetFeedsDetailsProcess - getName "+ e.getMessage());
        }
        return Name;
    }
    

}