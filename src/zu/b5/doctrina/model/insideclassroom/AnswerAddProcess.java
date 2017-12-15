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
}