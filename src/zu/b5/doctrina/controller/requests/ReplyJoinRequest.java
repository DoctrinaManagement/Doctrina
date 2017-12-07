package zu.b5.doctrina.controller.requests;

import java.io.*;
import javax.servlet.http.*; // import javax.servlet.ServletException;
import zu.b5.doctrina.model.export.*;
import zu.b5.doctrina.model.request.ReplyJoinRequestProcess;
import javax.servlet.*;
import java.sql.*;

/** 
 * @author Basheer
 */
 
public class ReplyJoinRequest extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		    
		    PrintWriter writer = response.getWriter();
    		HttpSession session = request.getSession();
    		ReplyJoinRequestProcess process = new ReplyJoinRequestProcess (session.getAttribute("connection"));
    
    			process.deleteJoinRequest(request.getParameter("requester"), request.getParameter("class_id"));
    			String message;
    			if ((request.getParameter("status")+"").equals("Accept")) {
    			   message = "Your request was Accepted";
    			   process.MemberAdd(request.getParameter("requester"), request.getParameter("class_id"));
    			}
    			else {
    			    message = "Your request was Declined";
    			}
    			process.NotificationSend(message, request.getParameter("requester"), request.getParameter("class_id"));
			    writer.write("ok");
    }
}