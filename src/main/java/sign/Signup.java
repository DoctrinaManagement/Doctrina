package sign;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnection;

public class Signup extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		DatabaseConnection data = new DatabaseConnection();
		if(data == null) {
			writer.write("bye");
		}
		else {
			Connection connection = data.connect();
			writer.write("hi");
		}
		
		
	}
}
