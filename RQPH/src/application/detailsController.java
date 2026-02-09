package application;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class detailsController {

    @FXML private Label lblName, lblPatientId, lblQueue,lblType, lblTriage, lblArrivalTime, lblWaitingTime, lblServiceDuration;
    @FXML private Shape circleTriage, rectangleType;
    @FXML private Button btnUpdate;
    @FXML private AnchorPane mainRoot;
    private UpdateCallback updateCallback;

    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
        populateFields();
    }

    private void populateFields() {
        lblName.setText(patient.getPatientName());
        lblPatientId.setText(patient.getPatientId());
        lblQueue.setText(String.valueOf(patient.getQueueNo()));
        lblType.setText(patient.getType());
        
        if(patient.getTriageLevel() != null) {
        	lblTriage.setText(patient.getTriageLevel());
        } else {
        	lblTriage.setVisible(false);
        	circleTriage.setVisible(false);
        	circleTriage.setManaged(false);
        	lblTriage.setManaged(false);
        }
        lblArrivalTime.setText(patient.getArrivalTime());
        
        if (!isUnset(patient.getArrivalTime())&& !isUnset(patient.getServiceStart())) {
            int waitingSecs = computeSeconds(patient.getArrivalTime(), patient.getServiceStart());
            lblWaitingTime.setText(formatSeconds(waitingSecs));
        } else {
        	lblWaitingTime.setText("00:00:00");
        }
        if (!isUnset(patient.getServiceStart()) && !isUnset(patient.getServiceEnd())) {
            int durationSecs = computeSeconds(patient.getServiceStart(), patient.getServiceEnd());
            lblServiceDuration.setText(formatSeconds(durationSecs));
        }  else {
        	lblServiceDuration.setText("00:00:00");
        }
        switch(lblTriage.getText()) {
    	case "L1": circleTriage.setFill(Color.BLUE); break;
    	case "L2": circleTriage.setFill(Color.RED); break;
    	case "L3": circleTriage.setFill(Color.ORANGE); break;
    	case "L4": circleTriage.setFill(Color.GREEN); break;
    	case "L5": circleTriage.setFill(Color.GRAY);break;
    	}
    	
        switch(lblType.getText()) {
        case "Emergency": rectangleType.setFill(Color.RED); break;
        case "Regular":rectangleType.setFill(Color.GREEN); break;
        }
        
        btnUpdate.setOnAction(e -> openUpdateModal(patient));   
    }
    private String formatSeconds(int totalSecs) {
    	int hours = totalSecs /3600;
    	int minutes = (totalSecs % 3600)/60;
    	int seconds = totalSecs % 60;
    	return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    private void openUpdateModal(Patient patient) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("updateModal.fxml"));
    		Scene scene = new Scene(loader.load());
    		
    		updateController controller = loader.getController();
    		controller.setPatient(patient);
    		controller.setUpdateCallBack(updatedPatient -> {
    			DBHelper.updatePatientDB(updatedPatient);
    			this.patient = updatedPatient;
    			populateFields();
    			
    			if (updateCallback != null) updateCallback.onUpdated(updatedPatient);
    		});
    		
    		Stage modal = new Stage();
    		modal.setScene(scene);
    		modal.setTitle("Patient Details -" + patient.getPatientId());
            GaussianBlur blur = new GaussianBlur(10);
            mainRoot.setEffect(blur);
    		modal.initModality(Modality.APPLICATION_MODAL);
            modal.setOnHidden(e -> mainRoot.setEffect(null));
            modal.showAndWait(); 
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    private int computeSeconds(String start, String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime tStart = LocalTime.parse(start, formatter);
        LocalTime tEnd = LocalTime.parse(end, formatter);
        return (int) Duration.between(tStart, tEnd).getSeconds();
    }
    private boolean isUnset(String time) {
        return time == null || time.isBlank() || time.equals("00:00:00");
    }
    public interface UpdateCallback {
        void onUpdated(Patient p);
    }
    public void setUpdateCallback(UpdateCallback callback) {
        this.updateCallback = callback;
    }
 }