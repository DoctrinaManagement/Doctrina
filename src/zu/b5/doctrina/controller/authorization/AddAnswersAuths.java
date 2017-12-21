package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import zu.b5.doctrina.model.export.*;
import com.google.gson.*;

/**
 * @author Basheer
 */

public class AddAnswersAuths implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		try {
    		CheckValidDetails checkDetails = new CheckValidDetails(
    				session.getAttribute("connection"));
            
    		String type = request.getParameter("type");
    		int count = 0;
    		Cookie[] cookies = req.getCookies();
    		String cookieValue = "";
    		for(Cookie cookie : cookies) {
    		   if ( cookie.getName().equals("Name") ) {
    		       cookieValue = cookie.getValue();
    		   }
    		}
    		ReUsable get = new ReUsable(session.getAttribute("connection")); 
    		String cookieUser_id = get.getUserId(cookieValue);
    		
    		if(session.getAttribute("user_id") != null && cookieValue != "" &&  cookieUser_id != "") {
        		if (checkDetails.classIdCheck(session.getAttribute("class_id") + "")) {
        			// Check user permission for his classroom
        			if (checkDetails.checkClassroomPermission(session.getAttribute("user_id")
        					+ "", session.getAttribute("class_id") + "")) {
        				if (type.equals("assignmentanswer")
        						|| type.equals("testanswer")
        						|| type.equals("quizanswer")) {
        					JsonArray answersArray = (JsonArray) new JsonParser()
        							.parse(request.getParameter("answers"));

        					for (JsonElement answers : answersArray) {
        					    
        						JsonObject obj = (JsonObject) answers;
        						if (checkDetails.questionIdCheck(obj.get("id").getAsString(), type)) {
        						    
        							count++;
        						} 
        						else {
        							break;
        						}
        					}

        					if (count == answersArray.size()) {
        						chain.doFilter(request, response);
        					} else {
        						writer.write("400");
        					}
        				} else {
        					writer.write("400");
        				}
        			} 
        			else {
        			    writer.write("400");
        			}
        		} else {
        			writer.write("404");
        		}
    		} else {
    		    for(Cookie cookie : cookies) {
        		   cookie.setMaxAge(0);
        		   res.addCookie(cookie);
        		}
    		    res.sendRedirect("/landingpage");
    		}
    	}
    	catch(Exception e){
    	    System.out.println("AddAnswersasdfg - "+ e.getMessage());
    	}
	}

	public void destroy() {
	}
}