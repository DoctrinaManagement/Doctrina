package zu.b5.doctrina.controller.notification;

import com.google.gson.*;
import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.notification.SaveProcess;
import javax.servlet.*;
import java.util.*;


/** 
 * @author Basheer
 */
public class SaveSettings extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		
		 JsonArray courseArray = (JsonArray) new JsonParser().parse(request.getParameter("courses"));
        
            ArrayList<String> course_id = new ArrayList<String>();
            
            for (JsonElement course_ids : courseArray ){
                course_id.add(course_ids.getAsString());
            }
	        
		
		SaveProcess process = new SaveProcess(session.getAttribute("connection"));
		process.updateSetting(request.getParameter("user_id"), course_id);
		writer.write("save successfully");
	}
}
