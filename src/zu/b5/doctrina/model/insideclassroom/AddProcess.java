package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;

public class AddProcess{
    
    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    String tableName;
    
    public AddProcess(Object connection, String tableName) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
        tableName = tableName;
    }
    
    public void setValues(String class_id, String title_id, ArrayList<String> questions) {
        String Query = "insert into "+tableName+"values(?,?,?)";
        try {
            for (String question : questions ) {
                stmt = conn.prepareStatement(Query);
                stmt.setInt(1, Integer.parseInt(class_id));
                stmt.setInt(2, Integer.parseInt(title_id));
                stmt.setString(3, question);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("AddProcess - setValues" + e.getMessage());
        }
    }
    
    public String getID(String class_id, String title) {
        String title_id = "";
        if(isTitle(class_id, title)) {
            title_id = get_ID(class_id, title);
            
        } else {
            try {
                String Query = "insert into "+tableName+"(ass_title, class_id) values(?,?);";
                stmt = conn.prepareStatement(Query);
                stmt.setString(1, title);
                stmt.setInt(2, Integer.parseInt(class_id));
                stmt.executeUpdate();
                title_id = get_ID(class_id, title);
            } catch (SQLException e) {
                System.out.println("AddProcess - getID" + e.getMessage());
            }
            
        }
        return title_id;
    }
    
    private String get_ID(String class_id, String title) {
        String Id = "";
        try{
            String Query = "select ass_id from "+tableName+"where class_id = ? and ass_title = ?;";
            stmt = conn.prepareStatement(Query);
            stmt.setInt(1, Integer.parseInt(class_id));
            stmt.setString(2, title);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> ass_id = get.resultSetToUserID(rs);
            Id = ass_id.get(0);
        } catch(SQLException e) {
            System.out.println("AddProcess - get_ID" + e.getMessage());
        }
        return Id;
    }
    public boolean isTitle(String class_id, String title) {
        try {
            
            String Query = "select * from "+tableName+"where class_id = ? and ass_title = ?;";
            stmt = conn.prepareStatement(Query);
            stmt.setInt(1, Integer.parseInt(class_id));
            stmt.setString(2, title);
            ResultSet rs = stmt.executeQuery();
            HashMap<String,String> titles = get.resultSetToHashMap(rs);
            if(titles.size() != 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("AddProcess - isTitle" + e.getMessage());
        }
        return false;
    }
    
}