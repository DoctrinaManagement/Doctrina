package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;

public class AddProcess{
    
    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    
    public AddProcess(Object connection) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
    }
    
    public void setValues(String class_id, String title_id, ArrayList<String> questions, String tableName) {
        String Query = "insert into "+tableName+" values(?,?,?)";
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
    
    public String getID(String class_id, String title, String tableName) {
        String title_id = "";
        if(isTitle(class_id, title, tableName)) {
            title_id = get_ID(class_id, title, tableName);
            
        } else {
            try {
                String Query = "insert into "+tableName+" (title, class_id) values(?,?);";
                stmt = conn.prepareStatement(Query);
                stmt.setString(1, title);
                stmt.setInt(2, Integer.parseInt(class_id));
                stmt.executeUpdate();
                title_id = get_ID(class_id, title, tableName);
            } catch (SQLException e) {
                System.out.println("AddProcess - getID" + e.getMessage());
            }
            
        }
        return title_id;
    }
    
    private String get_ID(String class_id, String title, String tableName) {
        String Id = "";
        try{
            String Query = "select id from "+tableName+" where class_id = ? and title = ?;";
            System.out.println("get_id    "+Query);
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
    public boolean isTitle(String class_id, String title, String tableName) {
        try {
            
            String Query = "select * from "+tableName+" where class_id = ? and title = ?;";
            System.out.println("isTittle    "+Query);
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