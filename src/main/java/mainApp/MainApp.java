package mainApp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.MapController;
import guice.AppModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.FxmlLoaderService;

import java.io.IOException;

public class MainApp extends Application {
    public static final int TILE_SIZE = 50;
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;

    @Override
    public void start(Stage primaryStage) {

        final Injector injector = Guice.createInjector(new AppModule());

        final FxmlLoaderService fxmlLoaderService = injector.getInstance(FxmlLoaderService.class);

        try {
            FXMLLoader loader = fxmlLoaderService.getLoader(getClass().getResource("/view/Map.fxml"));
            Parent root = loader.load();

            MapController mapController = loader.getController();

            Scene scene = new Scene(root, WIDTH*TILE_SIZE, HEIGHT*TILE_SIZE);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Dalek Game");
            mapController.addEventToScene(scene);

            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}