package zu.b5.doctrina.controller.report;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.ReUsable;
import zu.b5.doctrina.model.report.*;
import javax.servlet.*;
import java.sql.*;
import java.util.*;
import java.time.*;

/**
 * @author Basheer
 */
 
public class setRating extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		PreparedStatement stmt;
		
		try {
    		Connection conn = (Connection) session.getAttribute("connection");
	    	ReUsable get = new ReUsable(conn);
            String student_id = request.getParameter("student_id");
            int class_id = Integer.parseInt(request.getParameter("class_id"));
            
            stmt = conn.prepareStatement("select date from ratings where user_id = '"+student_id+"' and class_id = "+class_id+" order by date desc limit 1;");
            ResultSet rs = stmt.executeQuery(); 
            ArrayList<String> date = get.resultSetToUserID(rs);
            int days = 0;
            if(date.size() != 0) {
                String dateTime = date.get(0);
                int year = Integer.parseInt(dateTime.substring(0, 4));
                int month = Integer.parseInt(dateTime.substring(5, 7));
                int day = Integer.parseInt(dateTime.substring(8, 10));

                LocalDate current = LocalDate.now();
                LocalDate Dblast  = LocalDate.of(year, month, day);
                Period diff = Period.between(current, Dblast);
                days = diff.getDays();

            }
            
            if ( days > 7 || date.size()  == 0) {
                stmt = conn.prepareStatement("insert into ratings (user_id, class_id, rating, title, description) values (?, ?, ?, ?, ?);");
                stmt.setString(1, student_id);
                stmt.setInt(2, class_id);
                stmt.setString(3, request.getParameter("rating"));
                stmt.setString(4, request.getParameter("title"));
                stmt.setString(5, request.getParameter("description"));
                stmt.executeUpdate();
                writer.write("200");
            }
            else {
                writer.write("400");
            }
		}
		catch(Exception e){
		    System.out.println("setratingjava - "+e.getMessage());
		}
	}
}
