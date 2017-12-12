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
public class AddAssignment extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
        AddProcess process = new AddProcess(session.getAttribute("connection"));
        
        String class_id = request.getParameter("class_id");
        String title = request.getParameter("title");
        JsonArray questionArray = (JsonArray) new JsonParser().parse(request.getParameter("questions"));
        
            ArrayList<String> questionsList = new ArrayList<String>();
            
            for (JsonElement questions : questionArray ){
                questionsList.add(questions.getAsString());
            }
        // Get title id from Process method 
        String title_id = process.getID(class_id, title,"assignmenttitles");
        // Set values into table ...
        process.setValues(class_id, title_id, questionsList, "assignments");
        
        writer.write("200");
	}
}