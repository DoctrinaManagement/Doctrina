package zu.b5.doctrina.controller.notification;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.websocket.*;
import javax.websocket.server.*;
import java.sql.*;
import com.google.gson.Gson;
import javax.websocket.server.ServerEndpoint;
import zu.b5.doctrina.model.export.*;

//@ApplicationScoped
@ServerEndpoint("/websocket/{id}")
public class Notification {
    
    	private static HashMap<String, Session> clientId = new HashMap<String, Session>();
        
        
    	@OnOpen
    	public void onOpen(@PathParam("id") String id, Session session) {
    	   // System.out.println(ses.getAttribute("user_id") + "notification");
    	     System.out.println("ClientId _ socket");
    	   
    		clientId.put(id, session);
    		 System.out.println(clientId);
    	}
    
    	@OnMessage
    	public void onMessage(String id, Session session) throws IOException {
    		DatabaseConnection data = new DatabaseConnection();
    		Connection connection = data.connect();
    		Statement stmt = null;
    		ReUsable get = new ReUsable(connection);
    
    		if (id.equals("all")) {
    			for (String users : clientId.keySet()) {
    				try {
    					try {
    						stmt = connection.createStatement();
    						String Query = "select * from notification where user_id = "
    								+ "'" + users + "' order by date desc";
    						get.setUserId(users);
    						ResultSet rs = stmt.executeQuery(Query);
    						HashMap<String, ArrayList<HashMap<String, String>>> jsonObj = new HashMap<String, ArrayList<HashMap<String, String>>>();
    						jsonObj.put("notifications",
    								get.resultSetToArrayList(rs));
    						Query = "select classroom_id from classroom where class_creater='"
            						+ users + "';";
            				rs = stmt.executeQuery(Query);
            				jsonObj.put("requests", get.resultSetToRequest(rs));
            				String json = new Gson().toJson(jsonObj);
    						clientId.get(users).getBasicRemote().sendText(json);
    					} catch (SQLException e) {
    						System.out.println(e);
    					}
    				} catch (IOException ex) {
    					ex.printStackTrace();
    				}
    
    			}
    		} else {
    			try {
    				get.setUserId(id);
    				stmt = connection.createStatement();
    				String Query = "select * from notification where user_id = "
    						+ "'" + id + "' order by date desc";
    				ResultSet rs = stmt.executeQuery(Query);
    				HashMap<String, ArrayList<HashMap<String, String>>> jsonObj = new HashMap<String, ArrayList<HashMap<String, String>>>();
    				jsonObj.put("notifications", get.resultSetToArrayList(rs));
    				Query = "select classroom_id from classroom where class_creater='"
    						+ id + "';";
    				rs = stmt.executeQuery(Query);
    				jsonObj.put("requests", get.resultSetToRequest(rs));
    				String json = new Gson().toJson(jsonObj);
    				clientId.get(id).getBasicRemote().sendText(json);
    
    			} catch (SQLException e) {
    				System.out.println(e.getMessage());
    			} catch (IOException e) {
    				System.out.println(e.getMessage());
    			}
    		}
    	}
    
    	@OnClose
    	public void onClose(Session session, @PathParam("id") String id) {
    		clientId.remove(id);
    	}
    	
	
}
