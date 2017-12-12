package zu.b5.doctrina.model.export;

import java.sql.*;
import java.util.*;

public class CheckValidDetails {
	Connection conn;
	Statement stmt = null;
	

	public CheckValidDetails(Object connection) {
		conn = (Connection) connection;
		try {
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.out.println("CheckValidDetails - constructor"+ e);
		}
	}

	public boolean userIdCheck(String userId) {
		try {
			String userId_Query = "select * from userdetails where user_id = '"
					+ userId + "';";
			ResultSet rs = stmt.executeQuery(userId_Query);
            ReUsable get = new ReUsable(conn);
			HashMap<String, String> details = get.resultSetToHashMap(rs);
			if (details.size() != 0) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("CheckValidDetails - userIdCheck"
					+ e.getMessage());
		}
		return false;
	}

	public boolean classIdCheck(String classId) {
		try {
			ResultSet rs = stmt
					.executeQuery("select classroom_id from classroom where classroom_id ='"
							+ classId + "';");
			ReUsable get = new ReUsable(conn);
			ArrayList<String> Classdetails = get.resultSetToUserID(rs);
			if(Classdetails.size() != 0) {
			    return true;
			}
		} catch (SQLException e) {
			System.out.println("CheckValidDetails - classIdCheck"
					+ e.getMessage());
		}
		return false;
	}

	public boolean courseIdCheck(String courseId) {
		try {
			int course_id = Integer.parseInt(courseId);
			ResultSet rs = stmt
					.executeQuery("select * from courses where course_id = "
							+ course_id + ";");
			ReUsable get = new ReUsable(conn);
			HashMap<String, String> details = get.resultSetToHashMap(rs);
			if (details.size() != 0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("CheckValidDetails - courseIdCheck"
					+ e.getMessage());
		}
		return false;
	}

	public boolean isTeacher(String userId) {
		try {
			ResultSet rs = stmt
					.executeQuery("select role from userdetails where user_id ='"
							+ userId + "';");
			ReUsable get = new ReUsable(conn);
			HashMap<String, String> details = get.resultSetToHashMap(rs);
			if (details.get("role").equals("Teacher")) {
				return true;
			}

		} catch (SQLException e) {
			System.out
					.println("CheckValidDetails - isTeacher" + e.getMessage());
		}
		return false;
	}
	
	public boolean isAvailableJoinRequest(String userId, String classId) {
	    try {
	        ResultSet rs = stmt
					.executeQuery("select requester from joinrequest where requester= '"
							+ userId + "' and class_id = '" + classId
							+ "';");
			ReUsable get = new ReUsable(conn);
			ArrayList<String> joinrequestDetails = get.resultSetToUserID(rs);
            
            if(joinrequestDetails.size() != 0) {
                return true;
            }
	        
	    } catch (SQLException e) {
	        System.out
					.println("CheckValidDetails - isAvailableJoinRequest" + e.getMessage());
	    }
	    return false;
	}
	
	public boolean checkAddPermission (String user_id, String class_id) {
	    try {
	    ResultSet rs = stmt
					.executeQuery("select * from classroom where class_creater= '"
							+ user_id + "' and classroom_id = '" + class_id
							+ "';");
    	    ReUsable get = new ReUsable(conn);
    		ArrayList<String> joinrequestDetails = get.resultSetToUserID(rs);
            
            if(joinrequestDetails.size() != 0) {
                return true;
            }
	    }
	    catch (SQLException e) {
	        System.out.println("CheckValidDetails - checkpermission" + e.getMessage());
	    }
	    return false;
	}
	
	public boolean checkClassroomPermission (String user_id, String class_id) {
	    try {
	    ResultSet rs = stmt
					.executeQuery("select * from members where member= '"
							+ user_id + "' and classroom_id = '" + class_id
							+ "';");
    	    ReUsable get = new ReUsable(conn);
    		ArrayList<String> memberDetails = get.resultSetToUserID(rs);
            
            if(memberDetails.size() != 0) {
                return true;
            }
	    }
	    catch (SQLException e) {
	        System.out.println("CheckValidDetails - checkpermission" + e.getMessage());
	    }
	    return false;
	}
	
	public boolean checkID (String id, String tableName) {
	    try {
	        ResultSet rs = stmt.executeQuery("select id from "+tableName+" where id = "+id+";");
	        
	        ReUsable get = new ReUsable(conn);
    		ArrayList<String> idValues = get.resultSetToUserID(rs);
            
            if(idValues.size() != 0) {
                return true;
            }
	    }
	    catch (SQLException e) {
	        System.out.println("CheckValidDetails - checkID" + e.getMessage());
	    }
	    return false;
	}
	
	public boolean checkClassCreater (String user_id, String class_id) {
	    try {
	        ResultSet rs = stmt.executeQuery("select class_creater from classroom where classroom_id ='"+class_id+"' and class_creater ='" +user_id+ "';");
	        
	        ReUsable get = new ReUsable(conn);
    		ArrayList<String> classCreater = get.resultSetToUserID(rs);
            
            if(classCreater.size() != 0) {
                return true;
            }
	    }
	    catch (SQLException e) {
	        System.out.println("CheckValidDetails - checkClassCreater" + e.getMessage());
	    }
	    return false;
	}
}