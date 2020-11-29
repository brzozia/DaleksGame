import controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private GameController gameController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
//        this.primaryStage.setTitle("Dalek Game");
        this.gameController = new GameController(primaryStage);
        this.gameController.initRootLayout();

        try {
            this.primaryStage.setTitle("Dalek Game");

            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameController.class.getResource("../view/Map.fxml"));
            AnchorPane rootLayout = loader.load();

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureModel() {

    }

    private void configureGameController() {

    }

    private void configureView() {

    }
}