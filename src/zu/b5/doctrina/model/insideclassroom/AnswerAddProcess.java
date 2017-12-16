package zu.b5.doctrina.model.insideclassroom;

import com.google.gson.*;
import java.sql.*;
import zu.b5.doctrina.model.export.*;
import java.util.*;

//author Basheer 

public class AnswerAddProcess{
    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    
    public AnswerAddProcess(Object connection) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
    }
    
    public void addAnswers (JsonObject object, String class_id, String tableName, String user_id) {
        try {
            stmt = conn.prepareStatement("delete from "+tableName+" where id ="+Integer.parseInt(object.get("id").getAsString())+";");
           System.out.println("delete from "+tableName+" where id ="+Integer.parseInt(object.get("id").getAsString())+";");
            stmt.executeUpdate();
            stmt = conn.prepareStatement("insert into "+tableName+" values(?, ?, ?, ?);");
            stmt.setInt(1, Integer.parseInt(class_id));
            stmt.setInt(2, Integer.parseInt(object.get("id").getAsString()));
            stmt.setString(3, object.get("answer").getAsString());
            stmt.setString(4, user_id);
    
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("AnswerAddProcess - AnswerAddprocess"+ e.getMessage());
        }
    }
    
    public HashMap<String,String> quizmark (String table, int title_id, String user_id) {
        int mark = 0;
        HashMap<String, String> total = new	HashMap<String, String> ();
        
        try {
			stmt = conn.prepareStatement("select question_id from "
					+ table + " where id ="
					+ title_id + ";");

			ResultSet rs = stmt.executeQuery();
			ArrayList<String> questionID = get.resultSetToUserID(rs);
			String answerTable = table.substring(0, table.length() - 1)
					+ "answer";

			for (String ids : questionID) {
				stmt = conn.prepareStatement("select answer from "
						+ table + " where question_id ="
						+ Integer.parseInt(ids) + ";");
				rs = stmt.executeQuery();
				ArrayList<String> answer = get.resultSetToUserID(rs);

				stmt = conn.prepareStatement("select answer from "
						+ answerTable + " where id ="
						+ Integer.parseInt(ids) + " and user_id = '"
						+ user_id + "';");
				rs = stmt.executeQuery();

				ArrayList<String> useranswer = get
						.resultSetToUserID(rs);

				if (answer.get(0).equals(useranswer.get(0))) {
					mark += 1;
				}
			}
			
		    total = get.resultSetToHashMap(rs);
			total.put("total",questionID.size()+"");
			total.put("mark", mark + "");
			
			stmt = conn.prepareStatement("delete from quizmark values where user_id = '"+user_id+"' and title_id = "+title_id+";");
			stmt.executeUpdate();
		    stmt = conn.prepareStatement("insert into quizmark values(?, ?, ?, ?);");
			stmt.setString(1, user_id);
			stmt.setInt(2, title_id);
			stmt.setInt(3, mark);
			stmt.setInt(4, questionID.size());
			stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("AnswerAddProcess - "+e.getMessage());
        }
		return total;
    }
}