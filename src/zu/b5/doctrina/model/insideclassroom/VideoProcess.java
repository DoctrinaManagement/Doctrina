package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;

public class VideoProcess{
    
    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    
    public VideoProcess(Object connection ) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
    }
    
    public String insertVideo(String video_id, String class_id, String title) {
        
        try{
            String Query = "insert into videos (video_id,class_id,title) values(?,?,?)";
            stmt = conn.prepareStatement(Query);
            stmt.setString(1, video_id);
            stmt.setInt(2, Integer.parseInt(class_id));
            stmt.setString(3, title);
            stmt.executeUpdate();
            
            return "200";
        } catch( Exception e) {
            System.out.println("VideoProcess - insertVideo " + e);
        }
        
        return "not ok";
    }
    
    
    public ArrayList<HashMap<String,String>> getVideo(String class_id) {
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
        
        try{
            String Query = "select id from videos where class_id = ? order by date";
            stmt = conn.prepareStatement(Query);
            stmt.setInt(1, Integer.parseInt(class_id));
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> tempArray = get.resultSetToUserID(rs);
            Query = "select title from videos where class_id = ? order by date";
            stmt = conn.prepareStatement(Query);
            stmt.setInt(1, Integer.parseInt(class_id));
            rs = stmt.executeQuery();
            ArrayList<String> title = get.resultSetToUserID(rs);
            
            for(int i = 0; i < tempArray.size(); i++) {
                HashMap<String,String> tempMap = new HashMap<String,String>();
                Query = "select status from videostatus where id = '"+tempArray.get(i)+"' order by date desc;";
                tempMap.put("video_id", tempArray.get(i));
                tempMap.put("title", title.get(i));
                tempMap.put("status", "false");
                stmt = conn.prepareStatement(Query);
                rs = stmt.executeQuery();
                ArrayList<String> status = get.resultSetToUserID(rs);
                if(status.size() != 0) {
                    tempMap.put("status", status.get(0));
                }
                result.add(tempMap);
            }
            
        } catch (Exception e) {
            System.out.println("Videoprocess - getVideo"+e);
        }
        
        return result;
    }
    
    public HashMap<String, String> getVideoId(String id) {
        
        String Query = "select video_id from videos where id = '"+id+"' order by date;";
        HashMap<String,String> result = new HashMap<String,String>();
        
        try{
            stmt = conn.prepareStatement(Query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> temp = get.resultSetToUserID(rs);
            result.put("video_id", temp.get(0));
            result.put("time", "0");
            Query = "select time from videostatus where id = '"+id+"';";
            stmt = conn.prepareStatement(Query);
            rs = stmt.executeQuery();
            temp = get.resultSetToUserID(rs);
            if(temp.size() != 0) {
                result.put("time", temp.get(0));
            }
            
        } catch(Exception e) {
            System.out.println("VideoProcess - getVideoId" + e );
        }
        
        return result;
        
    }
    
    public ArrayList<String> videoFinishedId(String class_id, String user_id) {
        ArrayList<String> result = new ArrayList<String>();
        String Query = "select id from videos where class_id = '"+class_id+"' order by date ;";
        try{
            
            stmt = conn.prepareStatement(Query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> videoIds = get.resultSetToUserID(rs);
            for(String video_id : videoIds) {
                result.add(video_id);
                Query = "select status from videostatus where id = '"+video_id+"' and user_id = '"+user_id+"';";
                stmt = conn.prepareStatement(Query);
                rs = stmt.executeQuery();
                ArrayList<String> out = get.resultSetToUserID(rs);
                if(out.size() == 0 || out.get(0).equals("false")) {
                    return result;
                }
            }
            
        } catch(Exception e) {
            System.out.println("VideoProcess - videoFinishedId " + e);
        }
        return result;
    }
    
    public void insertUpdate(String user_id, String id, String currentTime, String totalTime, String class_id) {
        String Query = "insert into videostatus (user_id, id, time, status, class_id, total_time) values(?,?,?,?::\"enum_status\",?,?);";
        try {
            
            String status = "false";
            int current = Integer.parseInt(currentTime);
            int end = Integer.parseInt(totalTime);
            
            if ((end - current) < 10) {
                status = "true";
            }
            stmt = conn.prepareStatement("delete from videostatus where user_id = '"+user_id+"' and id = '"+id+"';");
            stmt.executeUpdate();
            
            stmt = conn.prepareStatement(Query);
            stmt.setString(1, user_id);
            stmt.setInt(2, Integer.parseInt(id));
            stmt.setString(3, currentTime);
            stmt.setString(4, status);
            stmt.setInt(5, Integer.parseInt(class_id));
            stmt.setString(6, totalTime);
            stmt.executeUpdate();
            
            
        } catch(Exception e) {
            System.out.println("VideoProcess - insertUpdate "+ e);
        }
    }

}