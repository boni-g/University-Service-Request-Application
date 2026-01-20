package controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;// LINKING YOUR JAVA FIELDS TO FXML ELEMENTS
import javafx.scene.control.Alert;// POPUP MESSAGE
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;//for animation timing
import application.Main;// to access scenes and dashboard

import java.util.HashMap;// to store usernames, passwords and roles
import java.util.Map;// to store usernames, passwords and roles

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Label showPasswordLabel;

    public static String loggedInUser = "";//STORE: which user logged in
    public static String userRole = "";//STORE: the role (admin, student, etc)

    // Store valid credentials and roles
    private final Map<String, String> credentials = new HashMap<>();// maps username to password
    private final Map<String, String> roles = new HashMap<>();//maps username to role

    @FXML
    public void initialize() {// automatically called by javaFx when the FXML is loaded
        // Default credentials
        credentials.put("admin", "admin123");
        credentials.put("student", "123");
        credentials.put("lecturer", "123");
        credentials.put("staff", "123");

        roles.put("admin", "admin");
        roles.put("student", "student");
        roles.put("lecturer", "lecturer");
        roles.put("staff", "staff");

        // Click event for showing all valid credentials
        showPasswordLabel.setOnMouseClicked(event -> handleShowPassword());
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Check empty fields
        if (username.isEmpty() && password.isEmpty()) {
            showError("Please enter username and password!");
            return;
        }
        if (username.isEmpty()) {
            showError("Please enter username!");
            return;
        }
        if (password.isEmpty()) {
            showError("Please enter password!");
            return;
        }

        // Check if username exists
        if (!credentials.containsKey(username)) {
            showError("Username not found!");
            return;
        }

        // Check password
        if (!credentials.get(username).equals(password)) {
            showError("Incorrect password!");
            return;
        }

        // Successful login
        loggedInUser = username;
        userRole = roles.get(username);
        openDashboard();
    }

    // Display error messages with fade-in animation
    private void showError(String message) {
        errorLabel.setText(message);
        FadeTransition fade = new FadeTransition(Duration.millis(400), errorLabel);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void openDashboard() {//SWITCHES TO THE DASHBOARD SCENE
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(Main.dashboardScene);
        stage.centerOnScreen();

        // Refresh dashboard for logged-in user
        Main.dashboardController.refreshDashboard();

        // Fade-in dashboard
        FadeTransition fade = new FadeTransition(Duration.millis(600), Main.dashboardScene.getRoot());
        fade.setFromValue(0.1);
        fade.setToValue(1);
        fade.play();
    }

    // Show all valid credentials
    private void handleShowPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Valid Credentials");
        alert.setHeaderText("Default Username and Passwords");
        alert.setContentText("""
                Username\tPassword
                admin\tadmin123
                student\t123
                lecturer\t123
                staff\t123
                """);
        alert.showAndWait();
    }
}