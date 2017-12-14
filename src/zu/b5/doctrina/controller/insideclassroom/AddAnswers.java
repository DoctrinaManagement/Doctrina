package zu.b5.doctrina.controller.insideclassroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;
import zu.b5.doctrina.model.export.*;

/**
 * @author pandi
 */
 
public class AddAnswers extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		AnswerAddProcess insert = new AnswerAddProcess(session.getAttribute("connection"));
		JsonArray answersArray = (JsonArray) new JsonParser().parse(request.getParameter("answers"));
        
        String class_id = session.getAttribute("class_id")+"";
        String tableName = request.getParameter("type");
        
        int count = 0; 
        for (JsonElement answers : answersArray ){
            
            JsonObject obj = (JsonObject) answers;
            if(!obj.get("answer").getAsString().equals("")) {
                    insert.addAnswers(obj, class_id, tableName);
            }
            else {
                count++;
            }
        }
        if(count == answersArray.size()) {
            writer.write("400");
        }else{
            writer.write("200");
        }
	}
}