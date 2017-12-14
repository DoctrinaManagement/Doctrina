package zu.b5.doctrina.controller.insideclassroom;

import com.google.gson.Gson;
import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.servlet.*;
import zu.b5.doctrina.model.insideclassroom.*;
import java.text.SimpleDateFormat;
/**
 * @author pandi
 */
 
public class GetFeedsDetails extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
        GetFeedsDetailsProcess value = new GetFeedsDetailsProcess(session.getAttribute("connection"));
        HashMap<String, ArrayList<HashMap<String,String>>> result = new HashMap<String, ArrayList<HashMap<String,String>>>();
        ArrayList<HashMap<String,String>> temp = new ArrayList<HashMap<String,String>>();
        
        if(request.getParameter("type").equals("initial")) {
            SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeStamp.setTimeZone(TimeZone.getTimeZone("IST"));
            Date date = new Date();
            temp = value.getObject((session.getAttribute("class_id")+""), timeStamp.format(date), "get");
            result.put("posts", temp);
        }
        else {
            temp = value.getObject((session.getAttribute("class_id")+""), (session.getAttribute("date")+""), "get");
            result.put("posts", temp);
        }
        String date = temp.get(9).get("date");
        session.setAttribute("date", date);
        String json = new Gson().toJson(result);
        writer.write(json);
	}
}