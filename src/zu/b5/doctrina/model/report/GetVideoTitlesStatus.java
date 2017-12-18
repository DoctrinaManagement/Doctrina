package zu.b5.doctrina.model.report;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.ReUsable;

public class GetVideoTitlesStatus{

Connection conn;
Statement stmt = null;
ReUsable get;

    public GetVideoTitlesStatus(Connection connection) {
        conn = connection;
        get = new ReUsable(connection);
    }
    
    public HashMap<String, String> titlesStatus(String class_id, String user_id) {
        HashMap<String, String> titles = new HashMap<String, String> ();
        try {
        	    stmt = conn.createStatement();
        	    ResultSet rs = stmt.executeQuery("select count(title) as count from videos where class_id = "+class_id+";");
        	    titles = get.resultSetToHashMap(rs);
        	    
        	    rs = stmt.executeQuery("select count(status) from videostatus where user_id = '"+user_id+"' and class_id = "+class_id+" and status = 'true';");
        	    ArrayList<String> finish = get.resultSetToUserID(rs);
        	    titles.put("finish", finish.get(0)+"");
        	    
        } catch (SQLException e) {
            System.out.println("getvideotitlesstatus - "+e.getMessage());
        }
        return titles;
    }
}