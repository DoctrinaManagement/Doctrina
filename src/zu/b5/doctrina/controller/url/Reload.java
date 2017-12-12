package zu.b5.doctrina.controller.url;

import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import javax.servlet.*;
import zu.b5.doctrina.model.export.ReUsable;

public class Reload extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			    
		HttpSession session = request.getSession();
		String values = request.getRequestURI();
		String temp1 = values.substring(1, values.indexOf('/'));
		if (temp1.equals("course")) {
		    String cls = values.substring("/course/".length());
		    String courseName = "";
		    if(cls.indexOf('/') == -1) {
		        courseName = cls.substring(0,1).toUpperCase() + cls.substring(1).toLowerCase();
		        
		    } else {
		        cls = cls.substring(0,cls.indexOf('/'));
		        courseName = cls.substring(0,1).toUpperCase() + cls.substring(1).toLowerCase();
		    }
		    
		    response.sendRedirect("/course/"+cls);
		}
		else if(temp1.equals("myclassroom")) {
		    
		}
	}
}