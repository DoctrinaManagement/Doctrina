package zu.b5.doctrina.controller.classroom;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import com.google.gson.Gson;
import zu.b5.doctrina.model.classroom.GetMyclassroomProcess;
import zu.b5.doctrina.model.export.*;
import java.util.*; // import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;

/**
 * @author Pandi
 */
public class GetVideos extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession();
		//System.out.println(session.getAttribute("connection"));
		GetMyclassroomProcess process = new GetMyclassroomProcess(session.getAttribute("connection"));
        CheckValidDetails temp = new CheckValidDetails(session.getAttribute("connection"));
        
        

	}
}