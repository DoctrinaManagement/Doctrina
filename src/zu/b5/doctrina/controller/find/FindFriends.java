package zu.b5.doctrina.controller.find;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.Gson;
import java.sql.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;
import zu.b5.doctrina.model.export.ReUsable;

/**
 * @author Pandi
 */
public class FindFriends extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		Connection conn = (Connection) session.getAttribute("connection");
        ReUsable get = new ReUsable(conn);
        String s = request.getParameter("content");
        String Query = "(name like '%%' ";
        // if(s != "") {
        //     Query ="(name like '%"+s.charAt(0)+"%' or name like '%"+(s.charAt(0)+"").toUpperCase()+"%'";
        // }
            
        
        // for(int i = 1; i < s.length(); i++) {
        //   String x = "and name like '%";
        //   Query = Query+x+s.charAt(i)+"%' or name like '%"+(s.charAt(i)+"").toUpperCase()+"%' ";
        // }
        Query = "select * from userdetails where (name like '%"+s.toLowerCase()+"%' or name like '%"+s.toUpperCase()+"%') and user_id != '105457747522331469494' ";
        System.out.println(Query);
        //Query = "select * from userdetails where "+Query+")and user_id != '105457747522331469494' ";
        String QueryString = "select member from members where classroom_id = '"+session.getAttribute("class_id")+"';";
        System.out.println(QueryString);
        ArrayList<String> member_id = new ArrayList<String>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QueryString);
            member_id = get.resultSetToUserID(rs);
            
        } catch (Exception e) {
            System.out.println("FindFriends - member");
        }
        String ss = "";
        for(String member : member_id) {
            ss = ss+" and user_id != '"+member+"' ";
        }
        Query = Query + ss+";";
        System.out.println(Query);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            HashMap<String,ArrayList<HashMap<String,String>> > result = new HashMap<String,ArrayList<HashMap<String,String>> >();
            result.put("finders", get.resultSetToArrayList(rs));
            String json = new Gson().toJson(result);
            writer.write(json);
        } catch (Exception e) {
            System.out.println("FindFriends ");
        }
            

	}
}