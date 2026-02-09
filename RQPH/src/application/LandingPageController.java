package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.Node;          
import javafx.scene.Parent;        
import javafx.scene.Scene;         
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LandingPageController {

    @FXML private Label dateTimeLabel;
    
    @FXML public void initialize() {
        startRealTimeClock();
    }
    
    @FXML public void goToLogin(ActionEvent event) {
        System.out.println("Login button clicked!"); 
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Error loading Login.fxml. Check if the file exists in the same package.");
            e.printStackTrace();
        }
    }

    private void startRealTimeClock() {
    	Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTimeLabel.setText(now.format(formatter));
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();   
    }
}
