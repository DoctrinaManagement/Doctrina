package zu.b5.doctrina.model.insideclassroom;

import java.sql.*;
import java.util.*;
import zu.b5.doctrina.model.export.*;
import com.google.gson.*;

public class AddProcess {

	Connection conn;
	PreparedStatement stmt;
	ReUsable get;

	public AddProcess(Object connection) {
		conn = (Connection) connection;
		get = new ReUsable(conn);
	}

	public void setValues(String class_id, String title_id,
			ArrayList<String> questions, String tableName) {
		String Query = "insert into " + tableName
				+ " (class_id, id, question) values(?,?,?)";
		try {
			for (String question : questions) {
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
		if (isTitle(class_id, title, tableName)) {
			title_id = get_ID(class_id, title, tableName);

		} else {
			try {
				String Query = "insert into " + tableName
						+ " (title, class_id) values(?,?);";
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
		try {
			String Query = "select id from " + tableName
					+ " where class_id = ? and title = ?;";
			stmt = conn.prepareStatement(Query);
			stmt.setInt(1, Integer.parseInt(class_id));
			stmt.setString(2, title);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> ass_id = get.resultSetToUserID(rs);
			Id = ass_id.get(0);
		} catch (SQLException e) {
			System.out.println("AddProcess - get_ID" + e.getMessage());
		}
		return Id;
	}

	public boolean isTitle(String class_id, String title, String tableName) {
		try {

			String Query = "select * from " + tableName
					+ " where class_id = ? and title = ?;";
			stmt = conn.prepareStatement(Query);
			stmt.setInt(1, Integer.parseInt(class_id));
			stmt.setString(2, title);
			ResultSet rs = stmt.executeQuery();
			HashMap<String, String> titles = get.resultSetToHashMap(rs);
			if (titles.size() != 0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("AddProcess - isTitle" + e.getMessage());
		}
		return false;
	}

	public void addquizQuestions(JsonObject object, String class_id, String title) {
		
		String title_id = getID(class_id, title, "quiztitles");
		ArrayList<String> answer = new ArrayList<String>();
		answer.add("a");
		answer.add("b");
		answer.add("c");
		answer.add("d");
		String s = object.get("answer").getAsString();
		int idx = answer.indexOf(s) + 1;
		try {
			stmt = conn
					.prepareStatement("insert into quizs (class_id, id, question, option1, option2, option3, option4, answer) values (?, ?, ?, ?, ?, ?, ?, ?);");
			
			stmt.setInt(1, Integer.parseInt(class_id));
			stmt.setInt(2, Integer.parseInt(title_id));
			stmt.setString(3, object.get("question").getAsString()
					.toLowerCase());
			stmt.setString(4, object.get("option1").getAsString().toLowerCase());
			stmt.setString(5, object.get("option2").getAsString().toLowerCase());
			stmt.setString(6, object.get("option3").getAsString().toLowerCase());
			stmt.setString(7, object.get("option4").getAsString().toLowerCase());
			stmt.setString(8, object.get("option" + idx).getAsString()
					.toLowerCase());
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("addquizQuestions -" + e);
		}
	}
}