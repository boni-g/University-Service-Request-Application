package controllers;

import data.Request;
import data.RequestData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Main;

public class CreateRequestController {

    @FXML private ComboBox<String> requestTypeCombo;
    @FXML private TextArea detailsField;
    @FXML private Label feedbackLabel;
    @FXML private Button submitButton;

    @FXML
    public void initialize() {
        requestTypeCombo.getItems().addAll("IT support", "Facilities", "Academic Adviser", "Repair", "Other");
        RequestData.loadRequestsFromFile();
    }

    @FXML
    private void handleSubmit() {
        String type = requestTypeCombo.getValue();
        String details = detailsField.getText();

        if (type == null || details.isEmpty()) {
            feedbackLabel.setText("Please fill all fields!");
            return;
        }

        Request request = new Request(LoginController.loggedInUser, type, details, "Pending");
        RequestData.addRequest(request);

        feedbackLabel.setText("Request submitted successfully!");
        requestTypeCombo.setValue(null);
        detailsField.clear();
    }

    @FXML
    private void handleBackToDashboard() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.setScene(Main.dashboardScene); // <-- reuse the existing dashboard scene
        stage.centerOnScreen();
    }
}