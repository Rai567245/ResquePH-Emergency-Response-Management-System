package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/rqph";
    private static final String USER = "root";
    private static final String PASS = "";
    
    private static final int maxAttempts = 3;
    private static final int lockAccountMins = 10;
  

    public static LoginResult validateLogin(String usernameInput, String passwordInput) {
        String query = "SELECT * FROM users WHERE BINARY username = ?";

        try (Connection connect = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement statement = connect.prepareStatement(query)) {

            statement.setString(1, usernameInput);
            //statement.setString(2, passwordInput);
            
            ResultSet queryResult = statement.executeQuery();
			if(!queryResult.next()) {
			    return LoginResult.invalid_credentials;        	
			}
            int failedAttempts = queryResult.getInt("failed_attempts");
            Timestamp lockoutTime = queryResult.getTimestamp("lock_time");
            String storedPass = queryResult.getString("password_hash");
            
            if(lockoutTime != null) {
            	LocalDateTime unlockTime = lockoutTime.toLocalDateTime().plusMinutes(lockAccountMins);
            	if(LocalDateTime.now().isBefore(unlockTime)) {
            		return LoginResult.account_locked;
            	} else {
            		resetAttempts(usernameInput, connect);
            		failedAttempts = 0;
            	}
            }
            
            if(!storedPass.equals(passwordInput)) {
            	return increaseAttempts(usernameInput, failedAttempts,connect);
            }
            resetAttempts(usernameInput, connect);
            return LoginResult.success;

        } catch (SQLException e) {
            System.out.println("Database Connection Failed or Query Error:");
            e.printStackTrace();
            return LoginResult.error;
        }
    }
    private static LoginResult increaseAttempts(String username, int currentAttempts, Connection conn) throws SQLException{
    	if(currentAttempts + 1  >= maxAttempts) {
    		String lockQuery = "UPDATE users SET failed_attempts = ?, lock_time = NOW() where username = ?";
    		PreparedStatement stmt = conn.prepareStatement(lockQuery);
    		stmt.setInt(1, maxAttempts);
        	stmt.setString(2, username);
        	stmt.executeUpdate();
    		return LoginResult.max_attempts;
    		
    	} else {
    		String updateQuery = "UPDATE users SET failed_attempts = failed_attempts + 1 WHERE username = ?";
    		PreparedStatement stmt = conn.prepareStatement(updateQuery);
    		stmt.setString(1, username);
        	stmt.executeUpdate();
    		return LoginResult.invalid_credentials;
    	}
    }
    private static void resetAttempts(String username, Connection conn) throws SQLException {
    	String resetQuery = "UPDATE users SET failed_attempts = 0, lock_time = NULL WHERE username = ?";
    	PreparedStatement stmt = conn.prepareStatement(resetQuery);
    	stmt.setString(1, username);
    	stmt.executeUpdate();
    }
    
    public enum LoginResult{
    	success, invalid_credentials, max_attempts, account_locked, error
    }
}