package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
/**
 * @author Pandi
 */
public class CourseLoadAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        
    	HttpServletRequest req = (HttpServletRequest) request;
		String values = req.getRequestURI().substring("/course/".length());
		
		HttpSession session = req.getSession();
		ReUsable get = new ReUsable(session.getAttribute("connection"));
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
		  //  if() {
		        
		  //  }
		} catch (Exception e) {
		    System.out.println("CourseLoadAuth - "+ e.getMessage());
		}
		
        
	}

	public void destroy() {
	}

}