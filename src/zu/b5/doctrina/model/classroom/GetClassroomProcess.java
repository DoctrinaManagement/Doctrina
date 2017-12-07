package zu.b5.doctrina.model.classroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.CheckValidDetails;
import zu.b5.doctrina.model.export.ReUsable;

public class GetClassroomProcess{
    Connection conn;
    Statement stmt;
    
    public GetClassroomProcess(Object connection) {
        conn = (Connection) connection;
        try{
            stmt = conn.createStatement();
        } catch(Exception e) {
            System.out.println("GetClassroomProcess - constructor");
        }
    }
    
    public ArrayList<HashMap<String,String>> getCourseName(String course_id) {
        ArrayList<HashMap<String,String>> course_name = new ArrayList<HashMap<String,String>>();
        try {
            String Query = "select course_name from courses where course_id = "+course_id+";";
            ResultSet rs = stmt.executeQuery(Query);
            ReUsable get = new ReUsable(conn);
            ArrayList<String> course_names = get.resultSetToUserID(rs);
            HashMap<String,String> temp = new HashMap<String,String>();
            temp.put("courseName",course_names.get(0));
            course_name.add(temp); 
        } catch (SQLException e) {
            System.out.println("GetClassroomProcess - getCourseName"+ e.getMessage());
        }
        
        return course_name;
    }
    
    public ArrayList<HashMap<String, String>> getClassroom(String user_id, int course_id) {
        
        ArrayList<HashMap<String, String>> out = new ArrayList<HashMap<String, String>> ();
        try {
            ResultSet rs = stmt.executeQuery("select * from classroom where course ="+course_id+";");
    		    		
    		ReUsable get = new ReUsable(conn);
    		out = get.resultSetToArrayList(rs);
    		
    		String Query = "select classroom_id from members where member='" +user_id+"';";
            rs = stmt.executeQuery(Query);
            ArrayList<String> classroomId = get.resultSetToUserID(rs);
            
           CheckValidDetails check = new CheckValidDetails(conn);
            for(int i = 0; i < out.size(); i++) {
                String cls_id = out.get(i).get("classroom_id");
                
                if(classroomId.indexOf(cls_id) != -1) {
                    out.get(i).put("status", "inside");
                }
                else if (check.isAvailableJoinRequest(user_id, cls_id)) {
                    out.get(i).put("status", "pending");
                }
                
                if(out.get(i).get("class_creater").equals(user_id)) {
                    out.get(i).put("name", "You");
                }
            }
        } catch (SQLException e) {
            System.out.println("GetClassroomProcess - getClassroom"+e.getMessage());
        }
        
        return out;
    }
    
}