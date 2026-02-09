package application;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class addPatientController {

    @FXML private TextField txtName, txtPatientID, txtQueue;
    @FXML private ComboBox<String> cbxType, cbxTriage;
    @FXML private Label lblTriage;
    private int nextQueue = DBHelper.getQueueNo();
 
    public interface addCallBack{
    	void onPatientAdded(Patient updatedPatient);
    }
    private addCallBack callback;
    
    public void setAddCallBack(addCallBack callback) {
    	this.callback = callback;
    }      
    @FXML
    private void initialize() {
    	cbxType.setItems(FXCollections.observableArrayList("Emergency", "Regular"));
    	cbxType.setValue("Emergency");
    	cbxTriage.setVisible(true);
	    lblTriage.setVisible(true);
	    cbxTriage.setItems(FXCollections.observableArrayList("L1","L2","L3","L4","L5"));
	    cbxTriage.setValue("L1");
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

    	txtPatientID.setText(String.format("PQM-%03d", nextQueue));
    	txtQueue.setText(String.valueOf(nextQueue));
    	txtPatientID.setEditable(false);
    	txtQueue.setEditable(false);
    }
    
    @FXML private void btnSaveClicked() {
    	String name = txtName.getText();
    	String type = cbxType.getValue();
    	String triage = cbxTriage.getValue();
    	String patientId = txtPatientID.getText();
    	
    	String arrival = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    	if (!name.matches("^[A-Za-z\\s.-]+$")) {
    	    showAlert(Alert.AlertType.WARNING, "Invalid Name", "Name can only contain letters, spaces, period(.) and hyphens (-).");
    	    return;
    	}
    	if(name != null && type != null && patientId != null) {
    		Patient newPatient = new Patient(nextQueue, patientId,name,type,triage,arrival,0,0,"","");
    		boolean success = DBHelper.addPatientDB(newPatient);
    		if(success) {
    			if(callback != null) {
            		callback.onPatientAdded(newPatient);
            	}
    			showAlert(Alert.AlertType.INFORMATION,"Success", "Patient Added Successfully.");
    		} else {
    			showAlert(Alert.AlertType.ERROR, "Error", "Failed to Add New Patient.");
    		}
    		
    	} else {
    		showAlert(Alert.AlertType.WARNING, "Field Incomplete", "Fill Out the Form Completely.");
    		return;
    	}
    	((Stage) txtName.getScene().getWindow()).close();
    }
    
    private void showAlert(Alert.AlertType type, String title, String msg) {
    	Alert alert = new Alert(type);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
}