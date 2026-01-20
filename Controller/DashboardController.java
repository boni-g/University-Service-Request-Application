package controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.Main;

public class DashboardController {

    @FXML private VBox rootPane;// main container of the dashboard
    @FXML private Button adminButton;// the "admin view" button

    @FXML
    public void initialize() {// runs automatically when FXML is loaded
        refreshDashboard();// to adjust UI based on logged-in user role
    }

    public void refreshDashboard() {// CHECKS IF THE LOGGED-IN USER IS AN ADMIN
        // Show admin button only for admin user
        adminButton.setVisible(LoginController.userRole.equals("admin"));// SHOWS THE ADMIN BUTTON ONLY WHEN THE CONDITION IS TRUE
    }

    @FXML
    private void handleCreateRequest() {
        switchScene("/create_request.fxml");
    }

    @FXML
    private void handleViewRequests() {
        switchScene("/view_requests.fxml");
    }

    @FXML
    private void handleAdminView() {
        switchScene("/admin_view.fxml");
    }

    @FXML
    private void handleLogout() {
        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();// gets the current window(STAGE)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));// loads login.fxml
            Parent root = loader.load();//It loads the FXML and converts it into a real JavaFX UI object.
            Scene scene = new Scene(root, Main.SCENE_WIDTH, Main.SCENE_HEIGHT);

            java.net.URL cssUrl = getClass().getResource("/style.css");//add css style
            if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());

            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();// prints the details of that error to the console
        }
    }

    private void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();// gets the current stage

            Scene scene = new Scene(root, Main.SCENE_WIDTH, Main.SCENE_HEIGHT);
            java.net.URL cssUrl = getClass().getResource("/style.css");
            if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());// if the CSS file doesnt exist, we skip the next step instead of crashing

            stage.setScene(scene);
            stage.centerOnScreen();

            FadeTransition fade = new FadeTransition(Duration.millis(600), root);// 0.6 sec
            fade.setFromValue(0.1);//The scene starts almost invisible → 10% opacity
            fade.setToValue(1);//It gradually becomes 100% visible
            fade.play();// starts/plays the animation {means the screen doesnt suddenly appear, instead its smoothly fades in }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}