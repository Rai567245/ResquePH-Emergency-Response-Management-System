package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private PasswordField passwordHiddenField;
    @FXML private TextField passwordVisibleField;
    @FXML private CheckBox showPasswordCheckbox;
    @FXML private TextField usernameField; 
    @FXML private Label errorLabel; 

    @FXML public void togglePasswordVisibility(ActionEvent event) {
        if (showPasswordCheckbox.isSelected()) {
            passwordVisibleField.setText(passwordHiddenField.getText());
            passwordVisibleField.setVisible(true);
            passwordHiddenField.setVisible(false);
        } else {
            passwordHiddenField.setText(passwordVisibleField.getText());
            passwordHiddenField.setVisible(true);
            passwordVisibleField.setVisible(false);
        }
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = showPasswordCheckbox.isSelected() ? passwordVisibleField.getText() : passwordHiddenField.getText();
        if (username.isBlank() || password.isBlank()) {
            errorLabel.setText("Please enter username and password.");
            errorLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        DatabaseConnection.LoginResult result = DatabaseConnection.validateLogin(username, password);
        
        switch(result) {
        case success:
        	try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardSection.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 500);
                
                stage.setScene(scene);
                stage.setTitle("ResquePH - Dashboard");
                stage.centerOnScreen();
                stage.show();
                
            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Error loading dashboard.");
            }
        	break;
        case invalid_credentials:
        	errorLabel.setText("Invalid Username or Password.");
        	errorLabel.setStyle("-fx-text-fill:red");
        	break;
        case max_attempts:
        	errorLabel.setText("Reached Max Attempts. Account Locked for 10 mins");
        	errorLabel.setStyle("-fx-text-fill:red");
        	break;
        case account_locked:
        	errorLabel.setText("Account Locked for 10 mins.");
        	errorLabel.setStyle("-fx-text-fill:red");
        	break;
        default:
        	errorLabel.setText("Database error. Try again.");
        	errorLabel.setStyle("-fx-text-fill:red");
        }
    }

    @FXML public void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
