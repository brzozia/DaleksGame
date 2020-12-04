package controller;

import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.MapObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mainApp.MainApp;
import model.Vector2D;

import java.io.IOException;
import java.util.Optional;

//should it implement some IController and guice bind it?
public class MapController {

    @FXML
    public GridPane gridPane;

    Stage stage;
    GameController gameController;

    private int width;
    private int height;


    @FXML
    public void initialize() {
        setGameController();

        Platform.runLater( () -> {
            for (int i=0; i<MainApp.HEIGHT; i++) {
                for (int j=0; j<MainApp.WIDTH; j++) {
                    Optional<MapObject> object = gameController.getWorldMap().objectAt(new Vector2D(i,j));
                    ImageView tile = new ImageView(new Image( getClass().getResourceAsStream("/tile.jpg")));
                    if(object.isPresent()){
                        if(object.get() instanceof Doctor){
                            tile = new ImageView(new Image( getClass().getResourceAsStream("/doctor.png")));
                        }
                        else{
                            // tile = dalek.png
                        }
                    }

//                    tile = new ImageView(new Image( getClass().getResourceAsStream("/tile.jpg")));

                    tile.fitWidthProperty().bind(stage.widthProperty().divide(width));
                    tile.fitHeightProperty().bind(stage.heightProperty().divide(height));
                    gridPane.add(tile, i, j);
                }
            }

        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGameController() {
        this.gameController = new GameController();
    }


    public void setSize(int width, int height) {
        this.width  = width;
        this.height = height;
    }

    public void initRootLayout() {}

    public void bindToView() {}

    private void onMoveButtonPress(Vector2D direction) {
        gameController.makeMove(direction);
    }

    private void onUseTeleport(Vector2D newPosition) {
        gameController.makeTeleport(newPosition);
    }


}
