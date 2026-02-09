package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    public static boolean updatePatientDB(Patient patient) {
        String query = "UPDATE patients SET patient_name = ?, patient_type = ?, triage_level = ?, service_start = ?, service_end = ? WHERE patient_id = ?";

        try (Connection conn = DBconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getPatientName());
            stmt.setString(2, patient.getType());
            stmt.setString(3, patient.getTriageLevel());
            stmt.setString(4, patient.getServiceStart());
            stmt.setString(5, patient.getServiceEnd());
            stmt.setString(6, patient.getPatientId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int getQueueNo() {
    	int nextQueue = 1;
    	String query = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'patients' ";
    	try (Connection conn = DBconn.getConnection();
    			PreparedStatement stmt = conn.prepareStatement(query);
    			ResultSet rs = stmt.executeQuery()){
    				if(rs.next()) {
    					nextQueue = rs.getInt("AUTO_INCREMENT");
    				}
        } catch(Exception e) {
        	e.printStackTrace();
        }
    	return nextQueue;
    }
    
    public static boolean addPatientDB(Patient patient) {
    	String query = "INSERT INTO patients (patient_id, patient_name, patient_type, triage_level, arrival_time)"
    			+ "VALUES (?, ?, ?, ?, ?)";
    	try(Connection conn = DBconn.getConnection();
    		PreparedStatement stmt = conn.prepareStatement(query)){
    		
    		stmt.setString(1, patient.getPatientId());
    		stmt.setString(2, patient.getPatientName());
    		stmt.setString(3, patient.getType());
    		if (patient.getTriageLevel() == null) {
    		    stmt.setNull(4, Types.VARCHAR);
    		} else {
    		    stmt.setString(4, patient.getTriageLevel());
    		}
    		stmt.setString(5, patient.getArrivalTime());
    		stmt.executeUpdate();
    		//System.out.println("New Patient Inserted Successfully");
    		return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    public static boolean delete(String patientId) {
    	String query = "DELETE FROM patients WHERE patient_id = ?";
    	
    	try(Connection conn = DBconn.getConnection();
    		 PreparedStatement stmt = conn.prepareStatement(query)){
    		stmt.setString(1, patientId);
    		stmt.executeUpdate();
    		return true;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    public static List<Patient> loadActivePatients() {
    	List<Patient> list = new ArrayList<>();
    	String query = "SELECT * FROM patients WHERE service_start = '00:00:00'";    	
    	try(Connection conn = DBconn.getConnection(); 
    		PreparedStatement stmt = conn.prepareStatement(query);
    		ResultSet rs = stmt.executeQuery()){
    		
    		while(rs.next()) {
    			Patient p = new Patient (
    					rs.getInt("queue_no"),
    					rs.getString("patient_id"),
    					rs.getString("patient_name"),
    					rs.getString("patient_type"),
    					rs.getString("triage_level"),
    					rs.getString("arrival_time"),
    					0,
    					0,
    					rs.getString("service_start"),
    					rs.getString("service_end")
    			);
    			list.add(p);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return list;
    }
    public static List<Patient> servingPatients() {
    	List<Patient> list = new ArrayList<>();
    	String query = "SELECT * FROM patients WHERE service_start != '00:00:00' AND service_end = '00:00:00' ";    	
    	try(Connection conn = DBconn.getConnection(); 
    		PreparedStatement stmt = conn.prepareStatement(query);
    		ResultSet rs = stmt.executeQuery()){
    		
    		while(rs.next()) {
    			Patient p = new Patient (
    					rs.getInt("queue_no"),
    					rs.getString("patient_id"),
    					rs.getString("patient_name"),
    					rs.getString("patient_type"),
    					rs.getString("triage_level"),
    					rs.getString("arrival_time"),
    					0,
    					0,
    					rs.getString("service_start"),
    					rs.getString("service_end")
    			);
    			list.add(p);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return list;
    }
    public static boolean serviceStart(int queueNo) {
        String query = "UPDATE patients SET service_start = NOW() WHERE queue_no = ?";

        try (Connection conn = DBconn.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, queueNo);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
