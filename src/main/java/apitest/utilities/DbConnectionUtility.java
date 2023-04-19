package apitest.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConnectionUtility extends Helper {
	
	
	private String dbUrl = loadProperties("dbUrl");
	private String dbUsername = loadProperties("dbUsername");
	private String dbPassword = loadProperties("dbPassword");
	private String driver = loadProperties("driver");
	
	public void user(String email) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		Statement s = conn.createStatement();   
		
		s.execute("Delete from profiles where profile_id in (SELECT id from users where email ='"+email+"')");
		
	}
}
