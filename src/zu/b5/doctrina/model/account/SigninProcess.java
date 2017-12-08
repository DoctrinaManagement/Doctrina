package zu.b5.doctrina.model.account;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;

public class SigninProcess{
    Connection conn;
    PreparedStatement stmt;
    ReUsable get;
    public SigninProcess(Object connection) {
        conn = (Connection) connection;
        get = new ReUsable(conn);
    }
    
    public HashMap<String, String> userdetailsCheck(HashMap<String,String> details) {
        HashMap<String, String> user_details = new HashMap<String,String>();
        try {
            stmt = conn.prepareStatement("select * from userdetails where user_id = ?;");
            stmt.setString(1, details.get("user_id"));
            ResultSet rs = stmt.executeQuery();
    		
    		user_details = get.resultSetToHashMap(rs);
    		if(user_details.get("image").equals(details.get("image")) == false || user_details.get("name").equals(details.get("name")) == false) {
				
				
				stmt = conn.prepareStatement("update userdetails set name = ?, email_id = ?, image = ? where user_id = ?;");
				stmt.setString(1, details.get("name"));
				stmt.setString(2, details.get("email_id"));
				stmt.setString(3, details.get("image"));
				stmt.setString(4, details.get("user_id"));
				stmt.executeUpdate();
				
				stmt = conn.prepareStatement("select * from userdetails where user_id = ?;");
				stmt.setString(1, details.get("user_id"));
				rs = stmt
						.executeQuery();
				
				user_details = get.resultSetToHashMap(rs);
			}
        } catch (SQLException e) {
            System.out.println("SigninProcess -- userdetailsCheck"+e.getMessage());
        }
        
        return user_details;
    }
    
    
}