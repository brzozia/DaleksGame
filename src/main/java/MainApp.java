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

//        this.primaryStage.setTitle("Dalek Game");
//        this.gameController = new GameController();
//        this.gameController.initRootLayout();

        try {
            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameController.class.getResource("../view/Map.fxml"));
            //TODO gameController -> MainApp and resolve errors
            AnchorPane rootLayout = loader.load();

            //set initial data into controller
            GameController controller = loader.getController();

            // add layout to a scene and show them all
            configureStage(primaryStage, rootLayout);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureStage(Stage primaryStage, AnchorPane rootLayout) {
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dalek Game");
    }

    private void configureModel() {

    }

    private void configureGameController() {

    }

    private void fillBoard() {

    }
}