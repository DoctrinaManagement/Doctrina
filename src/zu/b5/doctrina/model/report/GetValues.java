package zu.b5.doctrina.model.report;

import java.util.*;
import java.sql.*;
import zu.b5.doctrina.model.export.*;

public class GetValues {

Connection conn;
Statement stmt = null;
ReUsable get;

    public GetValues(Object connection) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
    }
    
	public ArrayList<HashMap<String, String>> getQuestionAnswers(String class_id, String title_id, String table, String user_id) {
        ArrayList<HashMap<String, String>> questionanswer = new ArrayList<HashMap<String, String>>();
	    
	    try {
    	    stmt = conn.createStatement();
    	    String tableName = table.substring(0, table.length()-1)+"answer";
    	    
    	    String query = "select question,(select answer from "+tableName+" where id = "+table+".question_id and class_id = "+class_id+" and user_id = '"+user_id+"') as answer from "+table+" where id = "+title_id+";";
    	    
    	    ResultSet rs  = stmt.executeQuery(query);
    	    questionanswer = get.resultSetToArrayList(rs);
	    }
	    
	    catch (SQLException e) {
	        System.out.println("GetValues - Question "+e.getMessage());
	    }
        return questionanswer;
    }
}