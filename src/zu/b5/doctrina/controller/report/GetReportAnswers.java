package zu.b5.doctrina.controller.report;

import com.google.gson.Gson;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import zu.b5.doctrina.model.report.*;

/**
 * @author Basheer
 */
 
public class GetReportAnswers extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		GetValues values = new GetValues(session.getAttribute("connection"));
	
	    try {	
            String user = request.getParameter("user_id");
    		String class_id = request.getParameter("class_id");
    		String type = request.getParameter("type");
    		String id = request.getParameter("id");
    		
    	    HashMap<String,ArrayList<HashMap<String, String>>> titlesObj = new HashMap<String,ArrayList<HashMap<String, String>>>();
        	ArrayList<HashMap<String,String>> questionAnswerObj = values.getQuestionAnswers(class_id, id, type, user);
        	
        	titlesObj.put("questionanswers", questionAnswerObj);
        	
        	String json = new Gson().toJson(titlesObj);
        	writer.write(json);
	    }
	    catch (Exception e) {
	        System.out.println("GetReportAnswers - "+e.getMessage());
	    }
    }
}