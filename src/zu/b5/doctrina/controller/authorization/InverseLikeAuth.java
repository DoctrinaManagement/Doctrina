package zu.b5.doctrina.controller.authorization;

import java.io.*;
import javax.servlet.*;
import zu.b5.doctrina.model.export.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public class InverseLikeAuth implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        
    	HttpServletRequest req = (HttpServletRequest) request;
        PrintWriter writer = response.getWriter();
		HttpSession session = req.getSession();
		CheckValidDetails checkDetails = new CheckValidDetails(
				session.getAttribute("connection"));
		
		if (request.getParameter("type").equals("posts")) {

			if (checkDetails.userIdCheck(request.getParameter("user_id"))) {

				if (checkDetails.classIdCheck(request.getParameter("class_id"))
						&& checkDetails.checkClassroomPermission(
								request.getParameter("user_id"),
								request.getParameter("class_id"))
						&& checkDetails.PostIdCheck(request.getParameter("id"),
								request.getParameter("class_id")) ) {
					chain.doFilter(request, response);
				} else {
					writer.write("400");
				}

			} else {
				writer.write("404");
			}

		

		} else if (request.getParameter("type").equals("comments")) {

			
			if (checkDetails.userIdCheck(request.getParameter("user_id"))) {

				if (checkDetails.classIdCheck(request.getParameter("class_id"))
						&& checkDetails.checkClassroomPermission(
								request.getParameter("user_id"),
								request.getParameter("class_id"))
						&& checkDetails.CommentIdCheck(
								request.getParameter("class_id"),
								request.getParameter("id")) ) {
					chain.doFilter(request, response);
				} else {
					writer.write("401");
				}

			} else {
				writer.write("404");
			}	

			

		} else if (request.getParameter("type").equals("replies")) {

			if (checkDetails.userIdCheck(request.getParameter("user_id"))) {

				if (checkDetails.classIdCheck(request.getParameter("class_id"))
						&& checkDetails.checkClassroomPermission(
								request.getParameter("user_id"),
								request.getParameter("class_id"))
						&& checkDetails.ReplyIdCheck(
								request.getParameter("class_id"),
								request.getParameter("id")) ) {
					chain.doFilter(request, response);
				} else {
					writer.write("401");
				}

			} else {
				writer.write("404");
			}
		}
        
	}

	public void destroy() {
	}

}