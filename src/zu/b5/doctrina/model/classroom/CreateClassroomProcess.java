package zu.b5.doctrina.model.classroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.ReUsable;

public class CreateClassroomProcess {

	Connection conn;
	PreparedStatement stmt;

	public CreateClassroomProcess(Object connection) {
		conn = (Connection) connection;
	}

	public String createClassroom(HashMap<String, String> details) {
		try {
			// String Query =
			// "insert into classroom(classroom_name, classroom_description, course, class_creater) values('"+
			// details.get("classroom_name") +"','"+
			// details.get("classroom_description") + "','" +
			// details.get("course_id") + "','" + details.get("user_id") +"');";
			stmt = conn
					.prepareStatement("insert into classroom (classroom_name, classroom_description, course, class_creater) values(?, ?, ?, ?)");
			stmt.setString(1, details.get("classroom_name"));
			stmt.setString(2, details.get("classroom_description"));
			stmt.setInt(3, Integer.parseInt(details.get("course_id")));
			stmt.setString(4, details.get("user_id"));
			stmt.executeUpdate();

			String message = "A new classroom <b>"
					+ details.get("classroom_name") + "</b> has been created.";
					
			stmt = conn.prepareStatement("select user_id from settings where course_id =?;");
			stmt.setInt(1, Integer.parseInt(details.get("course_id")));
			ResultSet rs = stmt.executeQuery();
			ReUsable get = new ReUsable(conn);

			ArrayList<String> user_id = get.resultSetToUserID(rs);
			for (String id : user_id) {
				
				stmt = conn
					.prepareStatement("insert into notification (user_id, message, status, sender) values(?, ?, ?::\"enum_status\", ?)");
    			
    			stmt.setString(1, id);
    			stmt.setString(2, message);
    			stmt.setString(3, "true");
    			stmt.setString(4, details.get("user_id"));
    			stmt.executeUpdate();
				
				// stmt.executeUpdate("insert into notification(user_id,message,status,sender) values("
				// 		+ id
				// 		+ ","
				// 		+ message
				// 		+ ",'true','"
				// 		+ details.get("user_id") + "');");
			}
			System.out.println("hi");
			stmt = conn.prepareStatement("select classroom_id from classroom");
			rs = stmt.executeQuery();
			ArrayList<String> classroom_ids = get.resultSetToUserID(rs);
			int classroom_id = Integer.parseInt(classroom_ids.get(classroom_ids
					.size() - 1));
					
			stmt = conn.prepareStatement("insert into members values(?, ?, ?::\"member\")");
			stmt.setInt(1, classroom_id);
			stmt.setString(2, details.get("user_id"));
			stmt.setString(3, "Teacher");
			stmt.executeUpdate();
			return "OK";
// 			stmt.executeUpdate("insert into members values(" + classroom_id
// 					+ ",'" + details.get("user_id") + "','Teacher');");
		} catch (SQLException e) {
            System.out.println(e.getMessage());
		}
        return "not ok";
	}
}