package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class queueController {
	
	@FXML private AnchorPane queueRoot;
    @FXML private Label lblDate,lblQ1,lblQ2,lblQ3,lblQ4,lblQ5;
    @FXML private Label lblEQ1,lblEQ2,lblEQ3,lblEQ4,lblEQ5;
    @FXML private Label lblRQ1,lblRQ2,lblRQ3,lblRQ4,lblRQ5;
    @FXML private Circle c1, c2, c3, c4, c5 ;
    @FXML private Circle e1, e2, e3, e4, e5;
    @FXML private Circle r1, r2, r3, r4, r5;
    @FXML private Hyperlink openPatientList, openDashboard, openBackup, logOut;
    @FXML private ImageView logoImage;
    private Label[] qLbl, eLbl, rLbl; 
    private Circle[] qCirc, eCirc, rCirc;
    private Timeline refresh;
    
    private PriorityQueue<Patient> heapQueue;
    private List<Patient> serving;
   // private Queue<Patient> regularQueue;
    private CircularQueue regularQueue;
 
    @FXML public void initialize() {
    	
    	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String formattedDateTime = LocalDateTime.now().format(formatter);
        //lblDate.setText(formattedDateTime);
        
    	Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            lblDate.setText(now.format(formatter));
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play(); 
    	
    	qLbl = new Label[] {lblQ1, lblQ2, lblQ3, lblQ4, lblQ5};
    	qCirc = new Circle[] {c1, c2, c3, c4, c5};
    	eLbl = new Label[] {lblEQ1, lblEQ2, lblEQ3, lblEQ4, lblEQ5};
    	eCirc = new Circle[] {e1, e2, e3, e4, e5};
    	rLbl = new Label[] {lblRQ1, lblRQ2, lblRQ3, lblRQ4, lblRQ5};
    	rCirc = new Circle[] {r1,r2,r3,r4,r5}; 
    	
    	heapQueue = new PriorityQueue<>(PatientPriority.COMPARATOR);
    	//regularQueue = new LinkedList<>();
    	regularQueue = new CircularQueue(5);
    	    	
    	serving = DBHelper.servingPatients();
    	if (serving == null) {
    	    serving = new LinkedList<>();
    	}
    	updateServing(serving, qLbl, qCirc);
    	
    	refresh = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateQueue()));
    	refresh.setCycleCount(Timeline.INDEFINITE);
    	refresh.play();
    	
    	logoImage.setOnMouseClicked(event -> openDashboard());
    	logOut.setOnAction(event -> logOut());
    	openPatientList.setOnAction(event -> openPatientList());
    	openDashboard.setOnAction(event -> openDashboard());   
        openBackup.setOnAction(event -> openBackup());   
    	
        Platform.runLater(() -> {
            queueRoot.requestFocus(); 
        });
    }
    public void updateQueue() {
        List<Patient> patients = DBHelper.loadActivePatients();
        if (patients == null) return;
        
        heapQueue.clear();
        regularQueue.clear();

        for (Patient p : patients) {
            if (p.getType().equalsIgnoreCase("Emergency")) {
                heapQueue.add(p);
            }     
            else
                //regularQueue.add(p);
            	regularQueue.enqueue(p);
        }
        updateHeapSummary();
        updateCircularQueue(regularQueue, rLbl, rCirc);
    }
    private void updateHeapSummary() {
        PriorityQueue<Patient> temp = new PriorityQueue<>(PatientPriority.COMPARATOR);
        temp.addAll(heapQueue);

        List<Patient> top5 = new ArrayList<>();
        for (int i = 0; i < 5 && !temp.isEmpty(); i++) {
            top5.add(temp.poll());
        }

        for (int i = 0; i < 5; i++) {
            if (i < top5.size()) {
                Patient p = top5.get(i);
                eLbl[i].setText(String.valueOf(p.getQueueNo()));
                if (p.getTriageLevel() != null) {
                    eCirc[i].setFill(getColor(p.getTriageLevel()));
                } else {
                    eCirc[i].setFill(Color.WHITE);
                }
            } else {
                eLbl[i].setText("");
                eCirc[i].setFill(Color.WHITE);
            }
        }
    }
    //Queue<Patient> queue
    private void updateCircularQueue(CircularQueue queue, Label[] labels, Circle[] circles) {

       // List<Patient> list = new ArrayList<>(queue);

        /**for (int i = 0; i < 5; i++) {
            if (i < list.size()) {
                Patient p = list.get(i);
                labels[i].setText(String.valueOf(p.getQueueNo()));
                circles[i].setFill(Color.WHITE);
            } else {
                labels[i].setText("");
                circles[i].setFill(Color.WHITE);
            }
        } **/
    	for (int i = 0; i < 5; i++) {
    		Patient p = queue.getAt(i);
            if (p != null) {
                labels[i].setText(String.valueOf(p.getQueueNo()));
                circles[i].setFill(Color.WHITE);
            } else {
                labels[i].setText("");
                circles[i].setFill(Color.WHITE);
            }
    	}
    	
    }
    private Color getColor(String triage) {
        switch (triage) {
            case "L1": return Color.BLUE ; 
            case "L2": return Color.RED;   
            case "L3": return Color.ORANGE;
            case "L4": return Color.GREEN;
            case "L5": return Color.GRAY;
            default: return Color.WHITE;
        }
    }
    private void updateServing(List<Patient> queue, Label[] labels, Circle[] circles) {
    	List<Patient> list = new ArrayList<>(queue);

        for (int i = 0; i < 5; i++) {
            if (i < list.size()) {
                Patient p = list.get(i);
                labels[i].setText(String.valueOf(p.getQueueNo()));
                if (p.getType().equalsIgnoreCase("Emergency")) {
                    circles[i].setFill(getColor(p.getTriageLevel()));
                    labels[i].setTextFill(Color.WHITE);
                } else {
                	circles[i].setFill(Color.WHITE);
                	labels[i].setTextFill(Color.BLACK);
                }
            } else {
                labels[i].setText("");
                circles[i].setFill(Color.WHITE);
            }
        }
    }
    @FXML private void startREGClicked() {
    	if(regularQueue.isEmpty()) return;
    	Patient p = regularQueue.dequeue();
    	boolean success = DBHelper.serviceStart(p.getQueueNo());
    	if(success) {
    		serving.add(p);
        	updateCircularQueue(regularQueue, rLbl,rCirc);
        	updateServing(serving, qLbl, qCirc);
        	showAlert(Alert.AlertType.INFORMATION, "Service Start", "Service Start For: " + p.getQueueNo());
    	} else {
    		showAlert(Alert.AlertType.ERROR, "Error", "Failed to update service start.");
    	}
    	
    }
    @FXML private void startERClicked() {
    	if(heapQueue.isEmpty()) return;
    	Patient p = heapQueue.poll();
    	boolean success = DBHelper.serviceStart(p.getQueueNo());
    	if(success) {
    		serving.add(p);
        	updateHeapSummary();
        	updateServing(serving, qLbl, qCirc);
        	showAlert(Alert.AlertType.INFORMATION, "Service Start", "Service Start For: " + p.getQueueNo());
    	} else {
    		showAlert(Alert.AlertType.ERROR, "Error", "Failed to update service start.");
    	}
    	
    }    
    private void openPatientList() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientList(NS).fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("PatientList");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            ((Stage)lblDate.getScene().getWindow()).close();
            refresh.stop();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openDashboard() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardSection.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Queue Display");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            Stage current = (Stage) openDashboard.getScene().getWindow();
            current.close();
            refresh.stop();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openBackup() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("backup&Reset.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Queue Display");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            Stage current = (Stage) openBackup.getScene().getWindow();
            current.close();
            refresh.stop();
            
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
            
            Stage current = (Stage) logOut.getScene().getWindow();
            current.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType type, String title, String msg) {
    	Alert alert = new Alert(type);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
    
 }
