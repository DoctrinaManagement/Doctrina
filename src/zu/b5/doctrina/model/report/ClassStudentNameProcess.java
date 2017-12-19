package zu.b5.doctrina.model.report;

import com.google.gson.Gson;
import java.util.*;
import java.sql.*;
import zu.b5.doctrina.model.export.ReUsable;

public class ClassStudentNameProcess {

	Connection conn;
	Statement stmt;
	String user_id;
	String class_id;
	ReUsable get ;

	public ClassStudentNameProcess(Object connection, String userId, String classId) {
		conn = (Connection) connection;
		class_id = classId;
		user_id = userId;
		get = new ReUsable(conn);
		try{
		    stmt = conn.createStatement();
		    
		} catch (SQLException e) {
		    System.out.println("classStudentNameProcess - " +e.getMessage());
		}
	}

	public String getDetails() {
        
        String Query = "select member from members where classroom_id = '"+class_id+"' and member != '"+user_id+"';";
        try {
            
            ResultSet rs = stmt.executeQuery(Query);
            ArrayList<String> user_ids = get.resultSetToUserID(rs);
            ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
            for(String user : user_ids) {
                Query = "select * from userdetails where user_id ='"+user+"';";
                rs = stmt.executeQuery(Query);
                HashMap<String,String> userdetails = get.resultSetToHashMap(rs);
                result.add(userdetails);
            }
            HashMap<String,ArrayList<HashMap<String,String>>> out = new HashMap<String,ArrayList<HashMap<String,String>>>();
            out.put("Name", result);
            return new Gson().toJson(out);
            
        } catch(Exception e) {
            System.out.println("classStudentNameProcess - " +e.getMessage());
        }
        
        return "";
        
    }
}