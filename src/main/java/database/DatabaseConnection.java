package database;

import java.sql.*;

//import com.google.cloud.sql.jdbc.Connection;

public class DatabaseConnection {
	private final String url = "jdbc:postgresql://35.188.220.205:5432/doctrina";
	//private final String url = "jdbc:postgresql://localhost:5432/doctrina";
    private final String user = "postgres";
    private final String password = "postgres";
    
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            //log.info(url);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage()+"   SQLException ");
        }
        return conn;
    }
}
