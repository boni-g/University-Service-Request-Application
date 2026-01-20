package controllers;

import data.Request;
import data.RequestData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import application.Main;

public class ViewRequestsController {

    @FXML private VBox rootPane;
    @FXML private TableView<Request> tableView;
    @FXML private TableColumn<Request, String> usernameColumn;
    @FXML private TableColumn<Request, String> typeColumn;
    @FXML private TableColumn<Request, String> detailsColumn;
    @FXML private TableColumn<Request, String> statusColumn;

    @FXML
    public void initialize() {
        RequestData.loadRequestsFromFile();

        usernameColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("username"));
        typeColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("type"));
        detailsColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("details"));
        statusColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        ObservableList<Request> allRequests = FXCollections.observableArrayList(RequestData.getRequests());

        FilteredList<Request> filteredRequests = new FilteredList<>(allRequests, r ->
                LoginController.loggedInUser.equals("admin") || r.getUsername().equals(LoginController.loggedInUser));

        tableView.setItems(filteredRequests);
    }

    @FXML
    private void handleBackToDashboard() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(Main.dashboardScene); // <-- reuse the existing dashboard scene
        stage.centerOnScreen();
    }
}