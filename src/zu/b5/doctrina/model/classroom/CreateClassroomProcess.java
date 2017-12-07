package zu.b5.doctrina.model.classroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.ReUsable;

public class CreateClassroomProcess{
    
    Connection conn;
    Statement stmt = null;
    
    public CreateClassroomProcess(Object connection) {
        conn = (Connection) connection;
        try{
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("CreateClassroomProcess - constructor");
        }
    }
    
    public void createClassroom(HashMap<String,String> details) {
        try {
            String Query = "insert into classroom(classroom_name, classroom_description, course, class_creater) values('"+
		    			details.get("classroom_name") +"','"+	details.get("classroom_description") + "','" + details.get("course_id") + "','" + details.get("user_id") +"');";
		    stmt.executeUpdate(Query);
		    
		    String message = "'A new classroom <b>"+details.get("classroom_name")+"</b> has been created.'";
		    ResultSet rs = stmt.executeQuery("select user_id from settings where course_id ='"+details.get("course_id")+"';");
		    ReUsable get = new ReUsable(conn);
		    
		    ArrayList<String> user_id = get.resultSetToUserID(rs);
		    for(String id : user_id) {
		    	id = "'"+id+"'";
		    	stmt.executeUpdate("insert into notification(user_id,message,status,sender) values("+id+","+message+",'true','"+details.get("user_id")+"');");
		    }
		    rs = stmt.executeQuery("select classroom_id from classroom");
		    ArrayList<String> classroom_ids = get.resultSetToUserID(rs);
		    int clasroom_id = Integer.parseInt(classroom_ids.get(classroom_ids.size() -1));
		    stmt.executeUpdate("insert into members values("+clasroom_id+",'"+details.get("user_id")+"','Teacher');");
        } catch(SQLException e) {
            
        }
        
    }
}