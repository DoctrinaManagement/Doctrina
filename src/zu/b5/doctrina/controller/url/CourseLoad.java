package zu.b5.doctrina.controller.url;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
/**
 * @author Pandi
 */

public class CourseLoad extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		
		int length = "/course/".length();
    	String url = request.getRequestURI().substring(length);
    	HttpSession session = request.getSession();
    	String courseName = url.substring(0,1).toUpperCase();
    	Connection conn = (Connection) session.getAttribute("connection");
    	PrintWriter writer = response.getWriter();
    	
    	if(url.indexOf('/') != -1) {
    	    courseName = courseName + url.substring(1, url.indexOf('/')).toLowerCase();
    	} else {
    	    courseName = courseName + url.substring(1).toLowerCase();
    	}
    	System.out.println(courseName);
    	String Query = "select course_id from courses where course_name = '"+courseName+"';";
    	
    	try {
    	    Statement stmt = conn.createStatement();
    	    ResultSet rs = stmt.executeQuery(Query);
    	    ReUsable get = new ReUsable(conn);
    	    ArrayList <String> courseNames = get.resultSetToUserID(rs);
    	    
    	    if(courseNames.size() != 0 ) {
    	        session.setAttribute("course_id", courseNames.get(0));
    	        session.setAttribute("courseName", courseName);
    	        session.setAttribute("load", "course");
    	        response.sendRedirect("/doctrina.index.do");
    	    }
    	    else {
    	        writer.write("404");
    	    }
    	} catch(Exception e) {
    	    System.out.println("CourseLoad "+e);
    	}
    	
		    
    }
}



        