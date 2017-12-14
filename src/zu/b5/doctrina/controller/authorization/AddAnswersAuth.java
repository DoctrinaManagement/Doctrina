package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import zu.b5.doctrina.model.export.*;
import com.google.gson.*;

/**
 * @author Basheer
 */

public class AddAnswersAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(
				session.getAttribute("connection"));

		String type = request.getParameter("type");
		int count = 0;
		if (checkDetails.classIdCheck(session.getAttribute("class_id") + "")) {
			// Check user permission for hihs classroom
			if (checkDetails.checkAddPermission(session.getAttribute("user_id")
					+ "", session.getAttribute("class_id") + "")) {
				if (type.equals("assignmentanswer")
						|| type.equals("testanswer")
						|| type.equals("quizanswer")) {

					JsonArray answersArray = (JsonArray) new JsonParser()
							.parse(request.getParameter("answers"));
							
					for (JsonElement answers : answersArray) {
					    
						JsonObject obj = (JsonObject) answers;
						if (checkDetails.titleIdCheck(obj.get("id").getAsString(), type)) {
						    
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
			} else {
				writer.write("404");
			}
		} else {
			writer.write("404");
		}
	}

	public void destroy() {
	}
}