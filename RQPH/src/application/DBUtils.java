package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
	private static ResultSet rs;
	private static Connection con = DBconn.getConnection();
	private static PreparedStatement ps;
	private static int waitingCount, servedCount, L1Count, L2Count, L3Count, L4Count, L5Count, totalPatientsCount;
	
	public static int getIntValue(String command, String param) {
		int value = 0;
		
		try {
			ps = con.prepareStatement(command);
			
			if (param != null) ps.setString(1, param);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closePsRs();
		}
		return value;
	}
	
	public static List<Patient_ver> getPatients(){
		List<Patient_ver> list = new ArrayList<>();
		
		String sql = "SELECT * FROM patients";
		
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Patient_ver p = new Patient_ver(
					safe(rs.getString("queue_no")),
				    safe(rs.getString("patient_id")),
				    safe(rs.getString("patient_name")),
				    safe(rs.getString("patient_type")),
				    safe(rs.getString("triage_level")),
				    safe(rs.getString("arrival_time")),
				    safe(rs.getString("status")),
				    safe(rs.getString("service_start")),
				    safe(rs.getString("service_end")),
				    safe(rs.getString("created_at")),
				    safe(rs.getString("updated_at"))
				);

				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closePsRs();
		}
		return list;
	}
	
	public static void resetDb() {
		String resetQueue = "ALTER TABLE patients AUTO_INCREMENT = 1;";
		String truncate = "TRUNCATE TABLE patients;";
		try {
			
			// truncate table
			ps = con.prepareStatement(truncate);
			ps.executeUpdate();
			System.out.println("Table truncated successfully.");
			
			// reset auto increment
			ps = con.prepareStatement(resetQueue);
			ps.executeUpdate();
			System.out.println("Auto-increment reset sucessfully");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closePsRs();
		}
	}
	
	public static String getAverageWait() {
		String sql = "SELECT SEC_TO_TIME(ROUND(AVG(TIME_TO_SEC(TIMEDIFF(service_start, arrival_time))))) AS ave_wait_time FROM patients WHERE service_start IS NOT NULL AND service_start <> '00:00:00';";
		String averageWait = "";
		try {
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				averageWait = rs.getString("ave_wait_time");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closePsRs();
		}
		return averageWait;
	}
	
	public static String getLongestWait() {
		String sql = "SELECT MAX(TIMEDIFF(service_start, arrival_time)) AS longest_wait FROM patients WHERE service_start IS NOT NULL AND service_start <> '00:00:00';";
		String longestWait = "";
		try {
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				longestWait = rs.getString("longest_wait");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closePsRs();
		}
		return longestWait;
	}
	
	public static int getTotalPatientCount() {
		int count = getIntValue("SELECT COUNT(patient_id) FROM patients;", null);
		return count;
	}
	
	
	private static String safe(String s) {
		return s == null ? "" : s;
	}
	
	public static void closePsRs() {
		try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
		try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
	}
	
	public static void closeConnection() {
		try { 
			if (con == null) {
				System.out.println("No connection exists to close.");
			}
			else if (con.isClosed()){
				System.out.println("Connection is already closed.");
			}
			else {
				con.close(); 
				System.out.println("Connection Closed.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace(); 
		}
	}
	
	public static boolean isConnected() {
		con = DBconn.getConnection();
		try (PreparedStatement ps = con.prepareStatement("SELECT 1")){
			ps.execute();
			return true;
		} catch (SQLException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	// wag pansinin for debugging lang
	public static void main(String[] args) {
		waitingCount = getIntValue("SELECT COUNT(patient_id) FROM patients WHERE service_start = ?;", "00:00:00");
		servedCount = getIntValue("SELECT COUNT(patient_id) FROM patients WHERE service_start != ?;", "00:00:00");
		L1Count = getIntValue("SELECT COUNT(patient_id) FROM patients WHERE triage_level = ?;", "L1");
		L2Count = getIntValue("SELECT COUNT(patient_id) FROM patients WHERE triage_level = ?;", "L2");
		L3Count = getIntValue("SELECT COUNT(patient_id) FROM patients WHERE triage_level = ?;", "L3");
		L4Count = getIntValue("SELECT COUNT(patient_id) FROM patients WHERE triage_level = ?;", "L4");
		L5Count = getIntValue("SELECT COUNT(patient_id) FROM patients WHERE triage_level = ?;", "L5");
		totalPatientsCount = getIntValue("SELECT COUNT(patient_id) FROM patients;", null);
		
		System.out.println("Total Waiting: " + waitingCount);
		System.out.println("Total Served: " + servedCount);
		System.out.println("Resuscitation (L1): " + L1Count);
		System.out.println("Emergent (L2): " + L2Count);
		System.out.println("Urgent (L3): " + L3Count);
		System.out.println("Less Urgent (L4): " + L4Count);
		System.out.println("Non-Urgent (L5): " + L5Count);
		System.out.println("Total Patients: " + totalPatientsCount);
	}

}