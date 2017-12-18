package zu.b5.doctrina.controller.account;


import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.account.*;


/** 
 * @author Basheer
 */
 
public class Signup extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		SignupProcess process = new SignupProcess(session.getAttribute("connection"));
		HashMap<String,String> user_details = new  HashMap<String,String>();
		ReUsable get = new ReUsable(session.getAttribute("connection"));
		session.setAttribute("load", "null");
		String[] keys = {"user_id", "name", "email_id", "image", "role"};
		for (String key : keys) {
		    user_details.put(key, request.getParameter(key) );
		}
		
		String user_status = process.userdetailsAdd(user_details);
		// Session setUP ...
		for (String values : keys) {
			session.setAttribute(values, user_details.get(values));
		}

        HashMap<String,String> notification_details = new HashMap<String,String>();
        notification_details.put("notification", "Welcome to Doctrina. Learn Anything easy and better ...");
        notification_details.put("status", "true");
        notification_details.put("sender", "105457747522331469494");
        notification_details.put("user_id", request.getParameter("user_id"));
        String noti_status = process.notificationAdd(notification_details);
        
        String cookie = get.CookieCreate();
		Cookie cookies=new Cookie("Name", cookie);//creating cookie object  
        response.addCookie(cookies);//adding cookie in the response  
        String cookie_status = process.cookieAdd(cookie, request.getParameter("user_id"));
        writer.write("200");
	}
}
