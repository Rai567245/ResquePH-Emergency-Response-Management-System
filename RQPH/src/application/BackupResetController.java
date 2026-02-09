package application;

import java.io.File;
import java.io.IOException;

import java.awt.Desktop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.event.ActionEvent;

public class BackupResetController {
	@FXML
    private Hyperlink dashboard, openQueue, logOut, openPatient;
	
	@FXML private ImageView logoImage;
	
	@FXML
	private AnchorPane backupResetAnchor;
	
	@FXML
    private Label dateTimeLabel;

    @FXML
    private Circle profileCircle; 
    
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
        
        logoImage.setOnMouseClicked(event -> dashboard());
        logOut.setOnAction(event -> logOut());
        dashboard.setOnAction(event -> dashboard());
        openPatient.setOnAction(event -> openPatient());
        openQueue.setOnAction(event -> openQueue());
        
     // listener to close timer when dashboard window is closed
        Platform.runLater(() -> {
        	Stage stage = (Stage) backupResetAnchor.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
            	DBUtils.closeConnection();
            });
        });
        
        Platform.runLater(() -> backupResetAnchor.requestFocus());
        
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
	private void openPatient() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientList(NS).fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Queue Display");
            stage.setScene(new Scene(root));
			stage.setResizable(false);
            stage.show();
            
            Stage current = (Stage) openPatient.getScene().getWindow();
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
            
            Stage current = (Stage) openQueue.getScene().getWindow();
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
    
	@FXML
	private Button createButton, restoreButton, viewButton, resetButton;

	private String username = System.getProperty("user.name");
	private String parentDir = "C:\\Users\\" + username + "\\Downloads\\rqphSystem\\";
	private String backupDir = "C:\\Users\\" + username + "\\Downloads\\rqphSystem\\rqphBackup\\";
	private String reportDir = parentDir + "rqphReport\\";
	private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	private String timestamp = LocalDateTime.now().format(fmt);
	
	public void createBackup(ActionEvent event) {
		String filename = "rqph" + timestamp + ".sql";
		System.out.println(parentDir);
		String mysqldumpPath = "C:\\xampp\\mysql\\bin\\mysqldump.exe";
		
		// check if database is connected. Show error message if not
		if (notConnected()) return;
		
		// check if rqph dir exists create if !exists
		createRqphDir();
		
		// check if rqph backup dir exists create if !exists
		createRqphBackupDir();
		
		
		ProcessBuilder pb = new ProcessBuilder(mysqldumpPath, "-u", "root", "rqph");
		pb.redirectOutput(new File(backupDir + filename));
		pb.redirectErrorStream(true);
		
		try {
			Process process = pb.start();
			
			int exitCode = process.waitFor();
			System.out.println("Backup finished with exit code " + exitCode);
			showAlert(Alert.AlertType.INFORMATION, "", "Backup Sucessful");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void restoreBackup(ActionEvent event) {
		Window window = ((Node) event.getSource()).getScene().getWindow();
		FileChooser fc = new FileChooser();
		File initDir = new File(backupDir);
		// check if rqph backup dir exists
		if(!initDir.exists() || !initDir.isDirectory()) {
			showAlert(Alert.AlertType.INFORMATION, "", "No backups created yet");
			return;
		}
		
		// check if database is connected. Show error message if not
		if (notConnected()) return;
				
		fc.setInitialDirectory(initDir);
		fc.getExtensionFilters().addAll(new ExtensionFilter("SQL File", "*sql"));
		fc.setTitle("RQPH Restore Backup");
		File backupSql = fc.showOpenDialog(window);
		String mysqlPath = "C:\\xampp\\mysql\\bin\\mysql.exe";
		
		if (backupSql != null) {
			File allowedDir = new File(backupDir);
			
			if (!backupSql.getParentFile().equals(allowedDir)) {
				System.out.println("Choose from RQPH Backup Folders only.");
				showAlert(Alert.AlertType.INFORMATION, "", "Choose from RQPH Backup Folders only.");
				return;
			}
			
			ProcessBuilder pb1 = new ProcessBuilder(mysqlPath, "-u", "root", "-e", "CREATE DATABASE IF NOT EXISTS rqph");
			pb1.redirectErrorStream(true);
			
			try {
				Process process = pb1.start();
				
				int exit1 = process.waitFor();
				System.out.println("Create DB finished with exit code " + exit1);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			
			ProcessBuilder pb2 = new ProcessBuilder(mysqlPath, "-u", "root", "rqph");
			pb2.redirectInput(backupSql.getAbsoluteFile());
			pb2.redirectErrorStream(true);
			
			try {
				Process process = pb2.start();
				
				int exit2 = process.waitFor();
				System.out.println("Successful Restoring Backup with exit code " + exit2);
				showAlert(Alert.AlertType.INFORMATION, "", "Backup Restoration Successful");
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void viewBackup(ActionEvent event) throws IOException {
		File backupFolder = new File(backupDir);
		
		if(backupFolder.exists() && backupFolder.isDirectory()) {
			Desktop d = Desktop.getDesktop();
			d.open(backupFolder);
			System.out.println("Viewing backup...");
		}
		else showAlert(Alert.AlertType.INFORMATION, "", "No backups created yet");
	}
	
	public void factoryReset(ActionEvent event) {
		String filename = "rqphReport" + timestamp + ".pdf";
		
		// date time for header in pdf
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dt = LocalDateTime.now().minusDays(1).format(date);
		
		// check if database is connected. Show error message if not
		if (notConnected()) return;
				
		// check if rqph dir exists create if !exists
		createRqphDir();
		
		// check if rqph report dir exists create if !exists
		createRqphReportDir();
		
		// create pdf report
		try {
			PdfWriter pdfW = new PdfWriter(reportDir + filename);
			PdfDocument pdfD = new PdfDocument(pdfW);
			Document doc = new Document(pdfD);
			pdfD.setDefaultPageSize(PageSize.LEGAL.rotate());
			
			float colWidth[] = {300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f, 300f};
			String cols[] = {"queue_no", "patient_id", "patient_name", "patient_type", "triage_level", "arrival_time","status", "service_start", "service_end", "created_at", "updated_at"};
			Table table = new Table(colWidth);
			List<Patient_ver> data = DBUtils.getPatients();
			
			table.addCell(new Cell(0, 11).add(new Paragraph("Resque PH"))
					.setBorder(Border.NO_BORDER)
					.setFontSize(25f)
					.setTextAlignment(TextAlignment.CENTER)
			);
			table.addCell(new Cell(0, 11).add(new Paragraph("Patients List " + dt))
					.setBorder(Border.NO_BORDER)
					.setFontSize(20f)
					.setTextAlignment(TextAlignment.CENTER)
			);
			
			for (String col : cols) {
				table.addCell(new Cell().add(new Paragraph(col)));
			}
			
			for (Patient_ver p : data) {
				table.addCell(new Cell().add(new Paragraph(p.queueNo)));
				table.addCell(new Cell().add(new Paragraph(p.patientId)));
				table.addCell(new Cell().add(new Paragraph(p.patientName)));
				table.addCell(new Cell().add(new Paragraph(p.type)));
				table.addCell(new Cell().add(new Paragraph(p.triageLevel)));
				table.addCell(new Cell().add(new Paragraph(p.arrivalTime)));
				table.addCell(new Cell().add(new Paragraph(p.status)));
				table.addCell(new Cell().add(new Paragraph(p.serviceStart)));
				table.addCell(new Cell().add(new Paragraph(p.serviceEnd)));
				table.addCell(new Cell().add(new Paragraph(p.createdAt)));
				table.addCell(new Cell().add(new Paragraph(p.updatedAt)));
			}
			
			doc.add(table);
			doc.close();
			System.out.println("PDF Created");
			showAlert(Alert.AlertType.INFORMATION, "", "PDF Report Created Sucessfully.");
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		System.out.println("Resetting DB...");
		DBUtils.resetDb();
		showAlert(Alert.AlertType.INFORMATION, "", "Database Reset Successful.");
	}
	
	public void createRqphDir() {
		File pDir = new File(parentDir);
		if(!pDir.exists()) {
			pDir.mkdir();
			System.out.println("RQPH System Directory Created");
			showAlert(Alert.AlertType.INFORMATION, "", "RQPH System Directory Created.");
		}
		else if(pDir.exists() && pDir.isDirectory()) {
			System.out.println("RQPH System Directory already exists. Using existing RQPH System Directory");
			showAlert(Alert.AlertType.INFORMATION, "", "RQPH System Directory already exists. Using existing RQPH System Directory...");
		}
	}
	
	public void createRqphBackupDir() {
		File bDir = new File(backupDir);
		if(!bDir.exists()) {
			bDir.mkdir();
			System.out.println("Backup Directory Created");
			showAlert(Alert.AlertType.INFORMATION, "", "RQPH Backup Directory Created.");
		}
		else if(bDir.exists() && bDir.isDirectory()) {
			System.out.println("Backup Directory already exists. Using existing Backup Directory...");
			showAlert(Alert.AlertType.INFORMATION, "", "RQPH Backup Directory already exists. Using existing Backup Directory...");
		}
	}
	
	public void createRqphReportDir() {
		File rDir = new File(reportDir);
		if(!rDir.exists()) {
			rDir.mkdir();
			System.out.println("RQPH Report Directory Created");
			showAlert(Alert.AlertType.INFORMATION, "", "RQPH Report Directory Created.");
		}
		else if(rDir.exists() && rDir.isDirectory()) {
			System.out.println("RQPH Report Directory already exists. Using existing RQPH Report Directory...");
			showAlert(Alert.AlertType.INFORMATION, "", "RQPH Report Directory already exists. Using existing Backup Directory...");
		}
	}
	
	private boolean notConnected() {
		if(!DBUtils.isConnected()) {
			showAlert(Alert.AlertType.INFORMATION, "", "Database is not connected.");
			return true;
		}
		return false;
	}
	
	private void showAlert(Alert.AlertType type, String title, String msg) {
    	Alert alert = new Alert(type);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(msg);
    	alert.showAndWait();
    }
	

}