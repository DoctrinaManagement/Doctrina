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
					+ userId + "'";
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

}