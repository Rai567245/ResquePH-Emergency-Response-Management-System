package application;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBconn {
	private static final String URL = "jdbc:mysql://localhost:3306/rqph";
	private static final String USER = "root";
	private static final String PASS = "";
	
	private static Connection conn;
	
	public static Connection getConnection() {
		try {
			if(conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(URL, USER, PASS);
				System.out.println("Database Connected");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}