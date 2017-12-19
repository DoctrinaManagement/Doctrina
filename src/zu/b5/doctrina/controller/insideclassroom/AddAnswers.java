package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.*;
import java.sql.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;
import zu.b5.doctrina.model.export.*;

/**
 * @author Basheer
 */

public class AddAnswers extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		AnswerAddProcess insert = new AnswerAddProcess(
				session.getAttribute("connection"));
		PreparedStatement stmt;
		Connection conn = (Connection) session.getAttribute("connection");
		ReUsable get = new ReUsable(conn);

		JsonArray answersArray = (JsonArray) new JsonParser().parse(request
				.getParameter("answers"));

		String class_id = session.getAttribute("class_id") + "";
		String user_id = session.getAttribute("user_id") + "";
		String tableName = request.getParameter("type");
		String id = "";
		int count = 0;
		for (JsonElement answers : answersArray) {

			JsonObject obj = (JsonObject) answers;
			id = obj.get("id").getAsString();
			if (!obj.get("answer").getAsString().equals("")) {
				insert.addAnswers(obj, class_id, tableName, user_id);
			} else {
				count++;
			}
		}

		if (count == answersArray.size()) {
			writer.write("400");
		} else {
			try {
				String table = tableName.substring(0, tableName.length() - 6)
						+ "s";
				stmt = conn.prepareStatement("select id from " + table
						+ " where question_id = " + id + ";");
				ResultSet rs = stmt.executeQuery();
				ArrayList<String> title_id = get.resultSetToUserID(rs);

				String status = table.substring(0, table.length() - 1)
						+ "status";
				stmt = conn.prepareStatement("delete from quizstatus where user_id ='"+user_id+"' and title_id = "+Integer.parseInt(title_id.get(0))+";");
				stmt.executeUpdate();
				stmt = conn.prepareStatement("insert into " + status
						+ " values(?, ?, ?);");

				stmt.setString(1, user_id);
				stmt.setInt(2, Integer.parseInt(class_id));
				stmt.setInt(3, Integer.parseInt(title_id.get(0)));
				stmt.executeUpdate();

				if (table.equals("quizs")) {
				    HashMap<String,String> total = insert.quizmark(table, Integer.parseInt(title_id.get(0)), user_id);
					String json = new Gson().toJson(total);
					writer.write(json);
				}
				else { 
				    writer.write("200");    
				}
			} catch (SQLException e) {
				System.out.println("AddAnswers - " + e.getMessage());
			}
		}
	}
}