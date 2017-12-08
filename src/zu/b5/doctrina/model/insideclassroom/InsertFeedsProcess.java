package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;

public class InsertFeedsProcess{

    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    
    public InsertFeedsProcess(Object connection) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
    }
    
    public void setValues (String user_id, String class_id, String message, String tableName) {
        try {
            stmt = conn.prepareStatement("insert into "+tableName+"(message, class_id, user_id) values(?, ?, ?) ");
            stmt.setString(1, message);
            stmt.setString(2, class_id);
            stmt.setString(3, user_id);
            stmt.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("insertFeedsProcess - setValues - " + e.getMessage());
        }
    }
}








