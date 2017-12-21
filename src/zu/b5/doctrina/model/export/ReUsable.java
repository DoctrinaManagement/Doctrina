package zu.b5.doctrina.model.export;

import java.sql.*;
import java.util.*;


public class ReUsable {

    private String userId;
    Connection conn;
    boolean status;
    public ReUsable(Object connection) {
        conn = (Connection) connection;
        status = true;
        
    }
    public void setUserId(String user_id) {
        userId = user_id;
    }
    
    // HashMap change ...
    
	public HashMap<String, String> resultSetToHashMap(ResultSet rs)
			throws SQLException {

		ResultSetMetaData md = rs.getMetaData();
		HashMap<String, String> result = new HashMap<String, String>();
		while (rs.next()) {

			for (int i = 1; i <= md.getColumnCount(); ++i) {
				result.put(md.getColumnName(i) + "", rs.getObject(i) + "");
			}
		}
		return result;
	}

    // ArrayList change ...
    
	public ArrayList<HashMap<String, String>> resultSetToArrayList(ResultSet rs)
			throws SQLException {
        
        
        ResultSetMetaData md = rs.getMetaData();
		ArrayList<HashMap<String, String>> out = new ArrayList< HashMap<String, String> >();
		int tab_index = 0;
		while (rs.next()) {
			HashMap<String, String> result = new HashMap<String, String>();
			
			for (int i = 1; i <= md.getColumnCount(); ++i) {
				
				result.put(md.getColumnName(i) + "", rs.getObject(i) + "");
				if((md.getColumnName(i)+"").equals("sender") || (md.getColumnName(i)+"").equals("class_creater")) {
				    Statement stmt = null;
        			try {
        				stmt = conn.createStatement();
        				String Query = "select name,image from userdetails where user_id = "
        						+ "'" + rs.getObject(i) + "'";
        				// System.out.println(Query);
        				ResultSet resultset = stmt.executeQuery(Query);
        				while(resultset.next()){
        				    
        				    
            				result.put("name" , resultset.getObject(1) + "");
            				if((rs.getObject(i)+"").equals(userId)) {
        				        result.put("name" , "You");
        				    }
            				// System.out.println(resultset.getObject(2) + " : " + resultset.getObject(2).getClass() );
            				result.put("image" , resultset.getObject(2) + "");
        				}
        			}
        			catch (SQLException e) {
        			    System.out.println(e);
        			} 
        			
				}
				if((md.getColumnName(i)+"").equals("course")) {
				    Statement stmt = null;
        			try {
        				stmt = conn.createStatement();
        				String Query = "select course_name from courses where course_id = "
        						+ "'" + rs.getObject(i) + "'";
        				ResultSet rs1 = stmt.executeQuery(Query);		
        				HashMap<String,String> temp = resultSetToHashMap(rs1);
        				result.put("course_name", temp.get("course_name"));
        			} catch (SQLException e) {
        			    System.out.println(e);
        			}
				}
			}
			tab_index++;
			result.put("tabIndex", (tab_index+""));
			out.add(result);
			
			
		}
		return out;
	}

    // Cookie setUp 
    
    public String CookieCreate () {
                char[] letters={'q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m'};
				StringBuilder ans = new StringBuilder();
					for(int i = 0 ; i < 25 ; i++){
						ans.append((int)(Math.random()*30)+""+letters[(int)(Math.random()*letters.length)]);
					}
	    return ans.toString();
    }
    
    
    // all user ID 
    
    public ArrayList<String> resultSetToUserID(ResultSet rs)
			throws SQLException {

		ResultSetMetaData md = rs.getMetaData();
		ArrayList<String> result = new ArrayList<String>();
		while (rs.next()) {

			for (int i = 1; i <= md.getColumnCount(); ++i) {
				result.add(rs.getObject(i) + "");
			}
		}
		return result;
	}
	
	// Join request object making...
	public ArrayList<HashMap<String,String>> resultSetToRequest(ResultSet rs) {
	    
	    ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
	    Statement stmt = null;
	    try{
	        stmt = conn.createStatement();
	        ArrayList<String> user_classId= resultSetToUserID(rs);
	       // System.out.println(user_classId);
	        for(String class_id : user_classId) {
	            String Query = "select * from classroom where classroom_id = '"+class_id+"';";
	            rs = stmt.executeQuery(Query);
	            HashMap<String,String> temp = resultSetToHashMap(rs);
	           // System.out.println(temp +" "+ temp.get("class_creater").equals(userId)+" "+ userId);
	            if(temp.get("class_creater").equals(userId)) {
	                Query = "select requester,date from joinrequest where class_id = '"+class_id+"';";
	                rs = stmt.executeQuery(Query);
	                ArrayList<String> requester=resultSetToUserID(rs);
	                //System.out.println(requester);
	                if(requester.size() == 2) {
    	                temp.put("requester", requester.get(0));
    	                temp.put("date", requester.get(1));
    	                Query = "select name,image from userdetails where user_id ='"+requester.get(0)+ "';";
    	                rs = stmt.executeQuery(Query);
    	                requester=resultSetToUserID(rs);
    	                temp.put("name", requester.get(0));
    	                temp.put("image", requester.get(1));
    	                result.add(temp);
	                }
	            }
	        }
	        
	    } catch(SQLException e) {
	        
	    }
	    
	    return result;
	}
	
	
	public String getUserId(String cookie) {
	    
	    try {
	        String Query = "select user_id from cookie where cookie = '"+cookie+"';";
	    
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(Query);
	        ArrayList<String> user_id = resultSetToUserID(rs);
	        if(user_id.size() != 0) {
	            return user_id.get(0);
	        }
	        
	        
	    } catch (Exception e) {
	        System.out.println("ReUsable getUserId -" + e);
	    }
	    return "";
	}
}