package application;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class updateController {

    @FXML private TextField txtName, txtPatientID, txtQueue, txtArrivalTime, txtStartTime, txtEndTime;
    @FXML private ComboBox<String> cbxType, cbxTriage;
    @FXML private Label lblTriage;
    @FXML private Button btnEnd;
    private UpdateCallBack callback;
 

    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
        populateFields();
    }

    private void populateFields() {
    	cbxType.setItems(FXCollections.observableArrayList("Emergency", "Regular"));
    	cbxType.valueProperty().addListener((obs, oldVal, newVal) -> {
    	    if ("Emergency".equals(newVal)) {
    	        cbxTriage.setVisible(true);
    	        lblTriage.setVisible(true);
    	        cbxTriage.setItems(FXCollections.observableArrayList("L1","L2","L3","L4","L5"));
    	    } else {
    	        cbxTriage.setVisible(false);
    	        lblTriage.setVisible(false);
    	    }
    	});

        txtName.setText(patient.getPatientName());
        txtPatientID.setText(patient.getPatientId());
        txtPatientID.setEditable(false);
        txtQueue.setText(String.valueOf(patient.getQueueNo()));
        txtQueue.setEditable(false);
        cbxType.setValue(patient.getType());
        cbxTriage.setValue(patient.getTriageLevel());
        txtArrivalTime.setText(patient.getArrivalTime());
        txtArrivalTime.setEditable(false);
        txtStartTime.setText(patient.getServiceStart());
        txtStartTime.setEditable(false);
        txtEndTime.setText(patient.getServiceEnd());
        txtEndTime.setEditable(false);
        if (txtEndTime.getText().equals("00:00:00") && !txtStartTime.getText().equals("00:00:00")){
        	btnEnd.setVisible(true);
        } else {
        	btnEnd.setVisible(false);
        }
        	
        
    }
    @FXML private void btnEndClicked() {
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        txtEndTime.setText(currentTime);
        patient.setServiceEnd(currentTime);        
    }
    @FXML private void btnSaveClicked() {
    	if(txtName.getText().trim().isEmpty()) {
    		showAlert(Alert.AlertType.WARNING, "Empty Field", "Please fill out all the fields.");
    		return;
    	}
    	if(!txtName.getText().matches("^[A-Za-z\\s.-]+$")) {
    		showAlert(Alert.AlertType.WARNING, "Invalid Name", "Name can only contain letters, spaces, period(.) and hyphens (-).");
    		return;
    	}
    	patient.setPatientName(txtName.getText());
    	patient.setType(cbxType.getValue());
    	patient.setTriageLevel (cbxTriage.getValue());
    	patient.setServiceStart(txtStartTime.getText());
    	patient.setServiceEnd(txtEndTime.getText());
    	
    	boolean success = DBHelper.updatePatientDB(patient);  	
    	if(success) {
    		if(callback != null) {
        		callback.onPatientUpdated(patient);
        	}
    		showAlert(Alert.AlertType.INFORMATION,"Success","Detail Updated Successfully");
    	}else {
    		showAlert(Alert.AlertType.ERROR,"Error", "Failed to Update Detail.");
    	}
    	((Stage) txtName.getScene().getWindow()).close();
    }
    public interface UpdateCallBack{
    	void onPatientUpdated(Patient updatedPatient);
    }
    public void setUpdateCallBack(UpdateCallBack callback) {
    	this.callback = callback;
    }
    private void showAlert(Alert.AlertType type, String title, String msg) {
    	Alert alert = new Alert(type);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
}