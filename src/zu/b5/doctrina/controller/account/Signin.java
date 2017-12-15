package zu.b5.doctrina.controller.account;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.account.*;

/** 
 * @author Pandi
 */
public class Signin extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		ReUsable get = new ReUsable(session.getAttribute("connection"));
		
		SignupProcess process1 = new SignupProcess(session.getAttribute("connection"));
		SigninProcess process = new SigninProcess(session.getAttribute("connection"));
		
		session.setAttribute("course", "Maths");
		
		String[] keys = {"user_id", "name", "email_id", "image", "role"};
	    HashMap<String,String> user_details = new HashMap<String,String>();
	    
		for (String key : keys) {
		    user_details.put(key, request.getParameter(key) );
		}
		
		
		HashMap<String, String> details = process.userdetailsCheck(user_details);
		// Session setup

		for (String values : details.keySet()) {
			session.setAttribute(values, details.get(values));
			
		}
//     	String cookie = get.CookieCreate();
// 		Cookie cookies=new Cookie("Name", cookie);//creating cookie object  
//         response.addCookie(cookies);//adding cookie in the response  
//         process1.cookieAdd(cookie, request.getParameter("user_id"));
	    writer.write("200");
		
	}
}