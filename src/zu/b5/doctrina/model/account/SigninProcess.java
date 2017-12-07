package zu.b5.doctrina.model.account;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;

public class SigninProcess{
    Connection conn;
    Statement stmt = null;
    
    public SigninProcess(Object connection) {
        conn = (Connection) connection;
        
        try {
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("SigninProcess -- constructor");
        }
    }
    
    public HashMap<String, String> userdetailsCheck(HashMap<String,String> details) {
        HashMap<String, String> user_details = new HashMap<String,String>();
        try {
            ResultSet rs = stmt
    					.executeQuery("select * from userdetails where user_id = '"
    							+ details.get("user_id")+"'");
    		ReUsable get = new ReUsable(conn);
    		user_details = get.resultSetToHashMap(rs);
    		if(user_details.get("image").equals(details.get("image")) == false || user_details.get("name").equals(details.get("name")) == false) {
				
				stmt.executeUpdate("update userdetails set name='"+details.get("name")+"',email_id='"+details.get("email_id")+"',image='"+details.get("image")+"where user_id='"+user_details.get("user_id")+"';");
				rs = stmt
						.executeQuery("select * from userdetails where user_id = '"
								+ user_details.get("user_id")+"'");
				
				user_details = get.resultSetToHashMap(rs);
			}
        } catch (SQLException e) {
            System.out.println("SigninProcess -- userdetailsCheck"+e.getMessage());
        }
        
        return user_details;
    }
    
    
}