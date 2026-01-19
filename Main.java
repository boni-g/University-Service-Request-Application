package application;

import controllers.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;// root node of the scene graph
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final double SCENE_WIDTH = 800;
    public static final double SCENE_HEIGHT = 600;

    // Make dashboard scene and controller accessible globally
    public static Scene dashboardScene;
    public static DashboardController dashboardController;

    @Override
    public void start(Stage primaryStage) throws Exception {//main window
        // Load login scene
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent loginRoot = loginLoader.load();// reads the FXML and builds the UI
        Scene loginScene = new Scene(loginRoot, SCENE_WIDTH, SCENE_HEIGHT);

        // Load dashboard scene and store controller
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
        Parent dashboardRoot = dashboardLoader.load();
        dashboardController = dashboardLoader.getController();
        dashboardScene = new Scene(dashboardRoot, SCENE_WIDTH, SCENE_HEIGHT);

        // Apply CSS
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {// if CSS missing, avoid crash d
            loginScene.getStylesheets().add(cssUrl.toExternalForm());
            dashboardScene.getStylesheets().add(cssUrl.toExternalForm());
        }

        // Show login
        primaryStage.setTitle("University Service Request App");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);// user cannot resize window 
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}