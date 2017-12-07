package zu.b5.doctrina.model.classroom;

import java.sql.*;
import zu.b5.doctrina.model.export.CheckValidDetails;
import zu.b5.doctrina.model.export.ReUsable;
import java.util.*;

public class GetMyclassroomProcess   {
    
    Connection conn;
    Statement stmt;
    
    public GetMyclassroomProcess(Object connection) {
        conn = (Connection) connection;
        try{
            stmt = conn.createStatement();
        } catch(Exception e) {
            System.out.println("GetMyclassroomProcess - constructor"+e);
        }
    }
    
    public ArrayList<HashMap<String,String>>  getMyClassroom(String userId) {
        ArrayList<HashMap<String,String>> array = new ArrayList<HashMap<String,String>>();
        
        try {
            ResultSet rs = stmt.executeQuery("select classroom_id from members where member = '"+userId+"';");
            ReUsable get = new ReUsable(conn);
            
            ArrayList<String> classId = get.resultSetToUserID(rs);
            array = new ArrayList<HashMap<String,String>>();
            int tap_index = 0;
            for(String class_id : classId) {
                
                class_id  = "'"+class_id+"'";
                rs = stmt.executeQuery("select * from classroom where classroom_id="+class_id+";");
                HashMap<String,String> temp = get.resultSetToHashMap(rs);
                if(temp.get("class_creater").equals(userId)) {
                    temp.put("name", "You");
                }
                else {
                    String Query = "select name from userdetails where user_id = '"+temp.get("class_creater")+"';";
                    rs = stmt.executeQuery(Query);
                    ArrayList<String> name = get.resultSetToUserID(rs);
                    temp.put("name", name.get(0));
                }
                rs = stmt.executeQuery("select course_name from courses where course_id='"+temp.get("course")+"';");
                HashMap<String,String> course_name = get.resultSetToHashMap(rs);
                temp.put("course_name", course_name.get("course_name"));
                tap_index++;
                
                temp.put("tabIndex", (tap_index+""));
                array.add(temp);
                
            }
        } catch(SQLException e) {
            System.out.println("GetMyclassroomProcess - getMyClassroom"+ e.getMessage());
        }
        
        return array;
        
    }
}