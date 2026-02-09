package application;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

public class PatientList {

    @FXML private Label lblDate, lblTableCount;
    @FXML private TextField txtSearch;
    @FXML private Spinner<Integer> spnRows;
    @FXML private ComboBox<String> cbxSort;
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Integer> queueNoColumn;
    @FXML private TableColumn<Patient, String> nameColumn, typeColumn, triageLevelColumn;
    @FXML private AnchorPane mainRoot;
    @FXML private Hyperlink openQueue, openDashboard, openBackup, logOut;
    @FXML private ImageView logoImage;
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private FilteredList<Patient> filteredList;
    private SortedList<Patient> sortedList;
    @FXML public void initialize() {
    	//Date and Time
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
        
        //sort
        cbxSort.setItems(FXCollections.observableArrayList("All", "ERQueue", "REGQueue", "OnGoing", "Latest", "Oldest"));
        spnRows.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10));
        //table
        queueNoColumn.setCellValueFactory(new PropertyValueFactory<>("queueNo"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        triageLevelColumn.setCellValueFactory(new PropertyValueFactory<>("triageLevel"));
        
        filteredList = new FilteredList<>(patientList, p -> true);
        sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(patientTable.comparatorProperty());
        patientTable.setItems(sortedList);
        patientTable.getSortOrder().add(queueNoColumn);
        queueNoColumn.setSortType(TableColumn.SortType.ASCENDING);
        patientTable.sort();

        loadPatients();
        setupFilters();
        sort();
        updateTableCount();
        
        //patientTable.setItems(patientList);
        patientTable.setRowFactory(tv -> {
        	TableRow<Patient> row = new TableRow<>();
        	row.setOnMouseClicked(event -> {
        		if(!row.isEmpty()) {
        			Patient clicked = row.getItem();
        			openPatientModal(clicked);
        		}
        	});
        	return row;
        });
        
        logoImage.setOnMouseClicked(event -> openDashboard());
        logOut.setOnAction(event -> logOut());
        openQueue.setOnAction(event -> openQueue());   
        openDashboard.setOnAction(event -> openDashboard());   
        openBackup.setOnAction(event -> openBackup());  
        
        Platform.runLater(() -> {
            mainRoot.requestFocus(); 
        });
    }
    
    
    @FXML private void btnAddClicked() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("addPatient.fxml"));
    		Scene scene = new Scene(loader.load());
    		
    		addPatientController controller = loader.getController();
    		controller.setAddCallBack(newPatient -> {
    			patientList.add(newPatient);
    			loadPatients();
    		});
    		
    		Stage modal = new Stage();
    		modal.setScene(scene);
    		modal.setTitle("Add New Patient");
    		
            GaussianBlur blur = new GaussianBlur(10);
            mainRoot.setEffect(blur);
    		modal.initModality(Modality.APPLICATION_MODAL);
    		modal.setResizable(false);
            modal.setOnHidden(e -> mainRoot.setEffect(null));
            modal.showAndWait();

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    @FXML private void btnDeleteClicked() {
    	Patient selected = patientTable.getSelectionModel().getSelectedItem();
    	if(selected == null) {
    		showAlert(Alert.AlertType.WARNING,"Warning: No Selected Item","Select Patient to Delete.");
    		return;
    	}
    	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    	confirm.setTitle("Confirm Deletion");
    	confirm.setHeaderText("Delete Patient");
    	confirm.setContentText("Are you sure you want to delete patient: " + selected.getPatientId() + "?");
    	Optional<ButtonType> result = confirm.showAndWait();
    	if(result.isPresent() && result.get() == ButtonType.OK) {
    		boolean success = DBHelper.delete(selected.getPatientId());
    		if(success) {
        		patientList.remove(selected);
        		showAlert(Alert.AlertType.INFORMATION, "Success", "Patient Deleted Successfully.");
        	} else {
        		showAlert(Alert.AlertType.ERROR, "Success", "Failed to Delete Patient.");
        	}
    	}
    	
    	
;    }
    private void showAlert(Alert.AlertType type, String title, String msg) {
    	Alert alert = new Alert(type);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
    private void openPatientModal(Patient patient) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("patientDetailsModal.fxml"));
    		Scene scene = new Scene(loader.load());
    		
    		detailsController controller = loader.getController();
    		controller.setPatient(patient);
    		controller.setUpdateCallback(updated -> {
    			loadPatients();
    		});
    		
    		Stage modal = new Stage();
    		modal.setScene(scene);
    		modal.setTitle("Patient Details -" + patient.getPatientId());
    		
            GaussianBlur blur = new GaussianBlur(10);
            mainRoot.setEffect(blur);
    		modal.initModality(Modality.APPLICATION_MODAL);
    		modal.setResizable(false);
            modal.setOnHidden(e -> mainRoot.setEffect(null));
            modal.showAndWait();

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
            
            Stage current = (Stage) openQueue.getScene().getWindow();
            current.close();
            
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
            
            Stage current = (Stage) openQueue.getScene().getWindow();
            current.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadPatients() {
    	patientList.clear();
    	String query = "SELECT * FROM patients";
    	
    	try(Connection conn = DBconn.getConnection(); 
    		PreparedStatement stmt = conn.prepareStatement(query);
    		ResultSet rs = stmt.executeQuery()){
    		
    		while(rs.next()) {
    			String arrival = rs.getString("arrival_time");
    			String start = rs.getString("service_start");
    			String end = rs.getString("service_end");
    			
    			int waitingSecs = timeDifference(arrival,start);
    			int durationSecs = timeDifference(start,end);
    			Patient p = new Patient (
    					rs.getInt("queue_no"),
    					rs.getString("patient_id"),
    					rs.getString("patient_name"),
    					rs.getString("patient_type"),
    					rs.getString("triage_level"),
    					arrival,waitingSecs, durationSecs, start, end
    			);
    			
    			patientList.add(p);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	updateTableCount(); 
    }
    private void Queue() {
        List<Patient> patients = DBHelper.loadActivePatients();
        if (patients == null) return;
        PriorityQueue<Patient> heap = new PriorityQueue<>(PatientPriority.COMPARATOR);

        for (Patient p : patients) {
            if (p.getType() != null && p.getType().equalsIgnoreCase("Emergency")) {
                heap.add(p);
            }
        }
        List<Patient> sorted = new ArrayList<>();
        while (!heap.isEmpty()) {
            sorted.add(heap.poll());
        }
        patientList.setAll(sorted);

        updateTableCount();
    }
    
    private int timeDifference(String t1, String t2) {
    	if (t1 == null || t2 == null) return 0;
    	
        LocalTime start = LocalTime.parse(t1.replace(" ", "T"));
        LocalTime end = LocalTime.parse(t2.replace(" ", "T"));
    	
    	return (int) java.time.Duration.between(start, end).getSeconds();
    }
    private void setupFilters() {
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        spnRows.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        applyFilters(); 
    }

    private void applyFilters() {
        String searchText = txtSearch.getText();
        int rowLimit = spnRows.getValue();

        filteredList.setPredicate(patient -> {
            boolean matchesSearch = true;
            if (searchText != null && !searchText.isEmpty()) {
                String lower = searchText.toLowerCase();
                matchesSearch = patient.getPatientName().toLowerCase().contains(lower)
                        || patient.getPatientId().toLowerCase().contains(lower)
                        || patient.getType().toLowerCase().contains(lower)
                        || patient.getTriageLevel() != null && patient.getTriageLevel().toLowerCase().contains(lower);
            }
            int index = patientList.indexOf(patient);
            boolean withinLimit = index < rowLimit;
            return matchesSearch && withinLimit;
        });
        
        updateTableCount(); 
    }
    private void sort() {
        cbxSort.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;

            switch (newVal) {
            	case "All":
            		loadPatients();
            		break;
            	case "ERQueue":
            		patientTable.getSortOrder().clear(); 
            		Queue();
            		break;
            	case "REGQueue":
            		patientTable.getSortOrder().clear(); 
            	    List<Patient> patients = DBHelper.loadActivePatients();
            	    if (patients != null) {
            	        List<Patient> regulars = new ArrayList<>();
            	        for (Patient p : patients) {
            	            if (p.getType() != null && p.getType().equalsIgnoreCase("Regular")) {
            	                regulars.add(p);
            	            }
            	        }
            	        patientList.setAll(regulars);
            	        updateTableCount();
            	    }
            		break;
            	case "OnGoing":
            		List<Patient> serving = DBHelper.servingPatients();
            		patientList.setAll(serving);
            		updateTableCount();
            		break;
                case "Latest":
                    queueNoColumn.setSortType(TableColumn.SortType.DESCENDING);
                    patientTable.getSortOrder().clear();
                    patientTable.getSortOrder().add(queueNoColumn);
                    break;

                case "Oldest":
                    queueNoColumn.setSortType(TableColumn.SortType.ASCENDING);
                    patientTable.getSortOrder().clear();
                    patientTable.getSortOrder().add(queueNoColumn);
                    break;
            }
        });
    }
    private void updateTableCount() {
        lblTableCount.setText(
            "Showing " + filteredList.size() + " out of " + patientList.size()
        );
    }
    @FXML private void onRefreshClicked() {
    	loadPatients();

        txtSearch.clear();
        spnRows.getValueFactory().setValue(10);
        filteredList.setPredicate(p -> true);
        cbxSort.setValue("All");
        patientTable.getSortOrder().clear();
        queueNoColumn.setSortType(TableColumn.SortType.ASCENDING);
        patientTable.getSortOrder().add(queueNoColumn);
        patientTable.sort();

        updateTableCount(); 	
    }

}