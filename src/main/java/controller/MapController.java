package controller;

import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.MapObject;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mainApp.MainApp;
import model.Vector2D;

import javafx.scene.input.KeyEvent;
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
        drawScreen();
//        Platform.runLater( () -> {
//            for (int i=0; i<MainApp.HEIGHT; i++) {
//                for (int j=0; j<MainApp.WIDTH; j++) {
//                    Optional<MapObject> object = gameController.getWorldMap().objectAt(new Vector2D(i,j));
//                    ImageView tile = new ImageView(new Image( getClass().getResourceAsStream("/tile.jpg")));
//
//                    if(object.isPresent()){
//                        if(object.get() instanceof Doctor){
//                            tile = new ImageView(new Image( getClass().getResourceAsStream("/doctor.png")));
//                        }
//                        else{
//                            tile = new ImageView(new Image( getClass().getResourceAsStream("/dalek.jpg")));
//                        }
//                    }
//
//                    tile.fitWidthProperty().bind(stage.widthProperty().divide(width));
//                    tile.fitHeightProperty().bind(stage.heightProperty().divide(height));
//                    gridPane.add(tile, i, j);
//                }
//            }
//        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void addEventToScene(Scene scene){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            public void handle(KeyEvent ke) {
                if (ke.getText().matches("[1-4|6-9]")) {

                    Vector2D vec = new Vector2D(Integer.parseInt(ke.getText()));
                    System.out.println("Key Pressed: " + ke.getText()+ " "+ vec.getX()+ " "+ vec.getY());

                    ke.consume(); // <-- stops passing the event to next node
                    onMoveButtonPress(vec);
                    drawScreen();
                }
            }
        });
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

    @FXML
    private void drawScreen(){
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
                            tile = new ImageView(new Image( getClass().getResourceAsStream("/dalek.jpg")));
                        }
                    }

                    tile.fitWidthProperty().bind(stage.widthProperty().divide(width));
                    tile.fitHeightProperty().bind(stage.heightProperty().divide(height));
                    gridPane.add(tile, i, j);
                }
            }
        });
    }
}
