package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;

/**
 * @author Basheer
 */

public class AddQuiz extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		AddProcess process = new AddProcess(session.getAttribute("connection"));

		String class_id = request.getParameter("class_id");
		String title = request.getParameter("title");
		JsonArray questionAnswerArray = (JsonArray) new JsonParser()
				.parse(request.getParameter("questionAnswers"));

		int count = 0;

		for (JsonElement questions : questionAnswerArray) {
			JsonObject obj = (JsonObject) questions;

			if (!obj.get("question").getAsString().equals("")
					|| !obj.get("option1").getAsString().equals("")
					|| !obj.get("option2").getAsString().equals("")
					|| !obj.get("option3").getAsString().equals("")
					|| !obj.get("option4").getAsString().equals("")
					|| !obj.get("answer").getAsString().equals("")) {
					    
				process.addquizQuestions(obj, class_id, title);
			} else {
				count++;
			}
		}
		// // Get title id from Process method
		// String title_id = process.getID(class_id, title,"assignmenttitles");
		// // Set values into table ...
		// process.setValues(class_id, title_id, questionsList, "assignments");
		if (count != questionAnswerArray.size()) {
			writer.write("200");
		} else {
			writer.write("400");
		}
	}
}