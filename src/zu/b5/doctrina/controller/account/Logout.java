package zu.b5.doctrina.controller.account;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import java.sql.*;

public class Logout extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Connection conn = (Connection) session.getAttribute("connection");
		Cookie[] cookies = request.getCookies();
		String cookieValue = "";
		for(Cookie cookie : cookies) {
		   cookie.setMaxAge(0);
		   response.addCookie(cookie);
		}
// 		String[] sessionAttributes = {"user_id","image","name","role","load","class_id","class_name","class_role","student_id"}; 
// 		for(String Attr : sessionAttributes) {
// 		    session.removeAttribute(Attr);
// 		}
		
		try{
		    String Query = "delete from cookie where user_id = '"+session.getAttribute("user_id")+"';";
		    Statement stmt = conn.createStatement();
		    stmt.executeUpdate(Query);
		} catch(Exception e) {
		    System.out.println("Logout "+ e);
		}
		
		
	}
}