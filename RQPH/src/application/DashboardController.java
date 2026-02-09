package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardController {
	
	@FXML
    private Hyperlink dashboard, backupReset, openQueue, logOut, openPatient;
	
	@FXML
	private AnchorPane dashboardAnchor;

    @FXML
    private Label dateTimeLabel, systemStatusIndicator;
    
    @FXML private ImageView logoImage;

    @FXML
    private Circle statusIndicator; 

    private Timer timer;
    private TimerTask task;
    @FXML
    public void initialize() {
        // 1. Date & Time Section (Updates Regularly)
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTimeLabel.setText(now.format(formatter));
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
         
        // timer to repeat task for dashboard updates
        timer = new Timer();
        task = new TimerTask() {
        	@Override
        	public void run() {
        		Platform.runLater(() -> updateDashboard());
        	}
        };
        
        timer.schedule(task, 0, 1000);
        //15000
        
        // listener to close timer when dashboard window is closed
        Platform.runLater(() -> {
        	Stage stage = (Stage) dashboardAnchor.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
            	stopTimer();
            	DBUtils.closeConnection();
            });
        });
        
        logoImage.setOnMouseClicked(event -> dashboard());
        logOut.setOnAction(event -> logOut());
        backupReset.setOnAction(event -> backupReset());
        openPatient.setOnAction(event -> openPatient());
        openQueue.setOnAction(event -> openQueue());
        
        Platform.runLater(() -> dashboardAnchor.requestFocus());
    }
    private void dashboard() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardSection.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(scene);
			stage.setResizable(false);
            stage.show();
            
            Stage current = (Stage) dashboard.getScene().getWindow();
            current.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void backupReset() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("backup&Reset.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            Stage stage = new Stage();
            stage.setTitle("Backup & Reset");
            stage.setScene(scene);
			stage.setResizable(false);
            stage.show();
            
            stopTimer();
            
            Stage current = (Stage) backupReset.getScene().getWindow();
            current.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openPatient() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientList(NS).fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Queue Display");
            stage.setScene(new Scene(root));
			stage.setResizable(false);
            stage.show();
            
            stopTimer();
            
            Stage current = (Stage) openPatient.getScene().getWindow();
            current.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void logOut() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Queue Display");
            stage.setScene(new Scene(root));
			stage.setResizable(false);
            stage.show();
            
            stopTimer();
            
            Stage current = (Stage) openQueue.getScene().getWindow();
            current.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void openQueue() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QueueDisplay(NS).fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Queue Display");
            stage.setScene(new Scene(root));
			stage.setResizable(false);
            stage.show();
            
            stopTimer();
            
            Stage current = (Stage) openQueue.getScene().getWindow();
            current.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private Label totalWaitingRecords, totalServedRecords, averageWaitingRecords, longestWaitRecords, resuscitationRecords, emergentRecords, urgentRecords, lessUrgentRecords, nonUrgentRecords, regularRecords, totalPatientsRecords;
    @FXML
    private ProgressBar progressBarL1, progressBarL2, progressBarL3, progressBarL4, progressBarL5, progressBarRegular;

    private void updateDashboard() {
    	if(!DBUtils.isConnected()) {
    		Platform.runLater(() -> setSystemStatus(false));
    		System.out.println("Database not Online");
    		return;
    	}
    	Map<String, Integer> tlCount = getTriageCounts();
    	Map<String, Integer> sCount = getStatusCounts();
    	Map<String, Integer> patientTypeCount = getPatientTypeCounts();
    	int waitingCount = sCount.get("waiting");
		int servedCount = sCount.get("served");
		int L1Count = tlCount.get("L1");
		int L2Count = tlCount.get("L2");
		int L3Count = tlCount.get("L3");
		int L4Count = tlCount.get("L4");
		int L5Count = tlCount.get("L5");
		int regularCount = patientTypeCount.get("Regular");
		int totalPatientsCount = DBUtils.getTotalPatientCount();
		String averageWait = DBUtils.getAverageWait();
		String longestWait = DBUtils.getLongestWait();
		
		Platform.runLater(() -> {
			setSystemStatus(true);
			totalWaitingRecords.setText(String.valueOf(waitingCount));
	    	totalServedRecords.setText(String.valueOf(servedCount));
	    	if (averageWait == null) averageWaitingRecords.setText("00:00:00");
	    	else averageWaitingRecords.setText(averageWait);
	    	if (longestWait == null) longestWaitRecords.setText("00:00:00");
	    	else longestWaitRecords.setText(longestWait);
	    	resuscitationRecords.setText(String.valueOf(L1Count));
	    	emergentRecords.setText(String.valueOf(L2Count));
	    	urgentRecords.setText(String.valueOf(L3Count));
	    	lessUrgentRecords.setText(String.valueOf(L4Count));
	    	nonUrgentRecords.setText(String.valueOf(L5Count));
	    	regularRecords.setText(String.valueOf(regularCount));
	    	totalPatientsRecords.setText(String.valueOf(totalPatientsCount));
	    	updateProgressBars(L1Count, L2Count, L3Count, L4Count, L5Count, regularCount);
		});
    }
    
    // use of varargs to take any number of inputs
    // variable arguments allow methods that can take any number of inputs, which simply means we do not have to create more methods for different numbers of parameters.
    // here, counts is treated as an array. Check link below for more info
    // https://www.geeksforgeeks.org/java/variable-arguments-varargs-in-java/
    private void updateProgressBars(int... counts) {
    	ProgressBar[] bars = {progressBarL1, progressBarL2, progressBarL3, progressBarL4, progressBarL5, progressBarRegular};
    	for (int i = 0; i < bars.length; i++) {
    		bars[i].setProgress(toDecimal(counts[i]));
    	}
    }
    
    private double toDecimal(int count) {
    	double base = 50;
    	if(count == 0) {
    		return 0;
    	}
    	double value = count / base;
    	return value;
    }
    
    private Map<String, Integer> getTriageCounts() {
    	Map<String, Integer> triageCounts = new HashMap<>();
    	for (int i = 1; i <= 5; i++) {
    		String level = "L" + i;
    		int count = DBUtils.getIntValue("SELECT COUNT(patient_id) FROM patients WHERE triage_level = ?;", level);
    		triageCounts.put(level, count);
    	}
    	return triageCounts;
    }
    
    private Map<String, Integer> getPatientTypeCounts() {
    	Map<String, Integer> typeCounts = new HashMap<>();
    	String[] type = {"Emergency", "Regular"};
    	for (int i = 0; i < type.length; i++) {
    		int count = DBUtils.getIntValue("SELECT COUNT(patient_id) FROM patients WHERE patient_type = ?;", type[i]);
    		typeCounts.put(type[i], count);
    	}
    	return typeCounts;
    }
    
    private Map<String, Integer> getStatusCounts() {
    	Map<String, Integer> statusCounts = new HashMap<>();
    	String[] status = {"waiting", "served"};
    	for (int i = 0; i < status.length; i++) {
    		int count = DBUtils.getIntValue("SELECT COUNT(patient_id) FROM patients WHERE status = ?;", status[i]);
    		statusCounts.put(status[i], count);
    	}
    	return statusCounts;
    }
    
    private void stopTimer() {
    	// cancel dashboard close timer before closing
        if (timer != null) {
        	timer.cancel();
        }
    }
    
    private void setSystemStatus(boolean isConnected) {
    		if (isConnected) {
        		systemStatusIndicator.setText("Online");
            	statusIndicator.setFill(Color.GREEN);
        	}
        	else {
        		systemStatusIndicator.setText("Offline");
            	statusIndicator.setFill(Color.RED);
        	}
    }

}
