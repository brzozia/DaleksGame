package controller;

import com.google.inject.Inject;
import game.World;
import game.WorldMap;
import game.entity.Doctor;
import game.utils.Direction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import mainApp.MainApp;
import model.Vector2D;



public class MapController {

    @FXML
    private Canvas canvas;

    @FXML
    private Button restartButton;

    private final World world;
    private Image doctorImage;
    private Image dalekImage;
    private Image rockImage;
    private GraphicsContext context;
    private int cellWidth;
    private int cellHeight;
    private boolean disable;

    private final static String DOCTOR_PATH = "/doctor.png";
    private final static String ROCK_PATH = "/rock.png";
    private final static String DALEK_PATH = "/dalek.png";

    private static final double TILE_LINE_WIDTH = 2.0;


    @Inject
    public MapController(World world) {
        this.world = world;
    }

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        doctorImage = new Image( getClass().getResourceAsStream(DOCTOR_PATH));
        rockImage = new Image( getClass().getResourceAsStream(ROCK_PATH));
        dalekImage = new Image( getClass().getResourceAsStream(DALEK_PATH));

        WorldMap worldMap = world.getWorldMap();
        cellWidth = ((int) canvas.getWidth() - (worldMap.getWidth()-1) * 2 ) / worldMap.getWidth();
        cellHeight = ( (int) canvas.getHeight() - (worldMap.getHeight()-1) * 2 ) / worldMap.getHeight();
        disable = true;
        this.drawScreen(disable);
    }

    public void addKeyboardEventToScene(Scene scene){ //TODO: refactor this function while adding EndGame screens and UI
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                String keyChar = ke.getText();
                ke.consume();// <-- stops passing the event to next node
                //  if\else used to disable other buttons when the game is over
                executeKeyFunction(keyChar);
            }
        });
    }

    private void executeKeyFunction(String keyChar){
        if(world.isGameOver() || world.hasWon()) {
            if(KeyBindings.isResetKey(keyChar)) {
                //TODO add reset game prompt to UI
                onResetWorld();
                disable = true;
            }
        }
        else {
            switch (keyChar) {
                case KeyBindings.USE_TELEPORT, KeyBindings.USE_TELEPORT_NUMERICAL -> onUseTeleport();
                case KeyBindings.USE_BOMB -> onUseBomb();
                default -> {
                    if (KeyBindings.isMovementKey(keyChar)) {
                        onMoveKeyPress(KeyBindings.keyToDirection(keyChar));
                        System.out.println("Your score: " + world.getScore());
                        //TODO add score to UI, not console
                    }
                }
            }
        }

        checkEndGame();
        drawScreen(disable);
    }

    private void checkEndGame(){
        if(world.hasWon()){
            disable = false;
            System.out.println("Y O U   W O N!!!");
        }
        if(world.isGameOver()) {
            disable = false;
            System.out.println("Y O U   L O S T  :(");
        }
    }

    @FXML
    private void onTeleportationButtonPress(){
        executeKeyFunction(KeyBindings.USE_TELEPORT);
    }

    @FXML
    private void onBombButtonPress(){
        executeKeyFunction(KeyBindings.USE_BOMB);
    }

    @FXML
    private void onResetButtonPress(){
        executeKeyFunction(KeyBindings.USE_RESET);
    }

    @FXML
    private void onMoveButtonPress(ActionEvent event){
        String key = ((Button)event.getSource()).getId();
        executeKeyFunction(key);
    }

    private void onMoveKeyPress(Direction direction) {
        world.makeMove(direction);
    }

    private void onUseTeleport() {
        world.makeTeleport();
    }

    private void onUseBomb() {
        world.useBomb();
    }

    private void onResetWorld() {
        world.resetWorld();
    }

    private void drawScreen(boolean disable){
        Platform.runLater( () -> {
            context.setFill(Color.WHITE);
            context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
            restartButton.setDisable(disable);

            for (int i=0; i<MainApp.HEIGHT-1; i++) {
                context.setLineWidth(2.0);
                context.setFill(Color.BLACK);
                context.strokeLine(0,  0.5+(i+1)*cellHeight + i*2, canvas.getWidth(), 0.5+(i+1)*cellHeight + i*2);
            }
            for (int i=0; i<MainApp.WIDTH; i++) {
                context.setLineWidth(TILE_LINE_WIDTH);
                context.setFill(Color.BLACK);
                context.strokeLine( 0.5+(i+1)*cellWidth + i*2, 0, 0.5+(i+1)*cellWidth + i*2, canvas.getHeight());
            }

            for (int i=0; i<MainApp.HEIGHT; i++) {
                for (int j=0; j<MainApp.WIDTH; j++) {
                    int finalI = i;
                    int finalJ = j;
                    world.getWorldMap()
                    .objectAt(new Vector2D(i,j))
                    .ifPresent( object -> {
                        if (object instanceof Doctor && object.isAlive()) {
                            context.drawImage(doctorImage, (cellWidth * finalI) + finalI * 2, (cellHeight * finalJ) + finalJ * 2, cellWidth - 1, cellHeight - 1);
                        } else {
                            if (object.isAlive()) {
                                context.drawImage(dalekImage, (cellWidth * finalI) + finalI * 2, (cellHeight * finalJ) + finalJ * 2, cellWidth - 1, cellHeight - 1);
                            } else {
                                context.drawImage(rockImage, (cellWidth * finalI) + finalI * 2, (cellHeight * finalJ) + finalJ * 2, cellWidth - 1, cellHeight - 1);
                            }
                        }
                    });
                }
            }
        });
    }
}
