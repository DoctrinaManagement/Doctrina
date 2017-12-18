package zu.b5.doctrina.controller.requests;

import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;
import zu.b5.doctrina.model.export.ReUsable;

/** 
 * @author Pandi
 */

public class CourseLoad  extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req,
			HttpServletResponse response) throws ServletException, IOException {
			    
		String url = req.getRequestURI();
    	HttpSession session = req.getSession();
    	ReUsable get = new ReUsable(session.getAttribute("connection"));
    	PrintWriter writer = response.getWriter();
    	String parse = url.substring(1);
    	if(parse.indexOf('/') != -1) {
    	    parse = parse.substring(0,parse.indexOf('/')).toLowerCase();
    	    parse = "/"+parse;
        	if(parse.equals("/course")) {
        		String values = req.getRequestURI().substring("/course/".length());
        		
        		
        		try{
        		    Connection conn = (Connection) session.getAttribute("connection");
        		    Statement stmt = conn.createStatement();
        		    String courseName = "";
        		    if(values.indexOf('/') == -1 ){
        		        courseName = values.substring(0,1).toUpperCase() + values.substring(1).toLowerCase();
        		    }
        		    else{
        		         courseName = values.substring(0,values.indexOf('/'));
                         courseName = courseName.substring(0,1).toUpperCase() + courseName.substring(1).toLowerCase();
        		    }
        		    ResultSet rs = stmt.executeQuery("select courses_name from courses where course_name = '"+courseName+"';");
        		    ArrayList<String> course_name = get.resultSetToUserID(rs);
        		    if(course_name.indexOf(course_name) == -1) {
        		        writer.write("404");
        		    } else {
        		        
        		    }
        		} catch (Exception e) {
        		    System.out.println("CourseLoadAuth - "+ e.getMessage());
        		}
    		
        	}
        	else {
        	    writer.write("404");
        	}
    	} else {
    	    parse = "/"+parse.toLowerCase();
    	    if(parse.equals("/myclassroom")) {
    	        response.sendRedirect("/myclassroom");
    	    }
    	    writer.write("404");
    	}
	}
}