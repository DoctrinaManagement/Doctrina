package zu.b5.doctrina.controller.report;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.CheckValidDetails;
/**
 * @author Basheer
 */
 
public class GetReportDetails extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(session.getAttribute("connection"));
		
		if(checkDetails.userIdCheck(request.getParameter("student_id"))) {
    		   
		   if(checkDetails.checkClassroomPermission( request.getParameter("student_id"), session.getAttribute("class_id")+"" )){

			   session.setAttribute("student_id", request.getParameter("student_id"));
			   writer.write("200");
		   }
		   else {
			   writer.write("getReportDetails 401");
		   }
	   }
	   else {
	       writer.write("getReportDetails 404");
	   }
	}
}