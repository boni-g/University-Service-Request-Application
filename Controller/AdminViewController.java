package controllers;

import data.Request;
import data.RequestData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;// linking java with FXML
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.Main;

public class AdminViewController {

    @FXML private TableView<Request> tableView;
    @FXML private TableColumn<Request, String> userColumn;
    @FXML private TableColumn<Request, String> typeColumn;
    @FXML private TableColumn<Request, String> detailsColumn;
    @FXML private TableColumn<Request, String> statusColumn;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Label feedbackLabel;
    @FXML private Button backButton;

    private ObservableList<Request> observableRequests;

    @FXML
    public void initialize() {
        RequestData.loadRequestsFromFile();

        userColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));
        typeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType()));
        detailsColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDetails()));
        statusColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        observableRequests = FXCollections.observableArrayList(RequestData.getRequests());
        tableView.setItems(observableRequests);

        statusComboBox.getItems().addAll("Pending", "Approved", "Rejected");
    }

    @FXML
    private void handleUpdateStatus() {
        Request selected = tableView.getSelectionModel().getSelectedItem();
        String status = statusComboBox.getValue();

        if (selected == null || status == null) {
            feedbackLabel.setText("Select a request and status!");
            return;
        }

        RequestData.updateRequestStatus(selected, status);
        tableView.refresh();
        feedbackLabel.setText("Status updated successfully!");
    }

    @FXML
    private void handleBackToDashboard() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(Main.dashboardScene); // <-- reuse the existing dashboard scene
        stage.centerOnScreen();
    }
}