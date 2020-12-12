package controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import game.World;
import game.WorldMap;
import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.MapObject;
import game.utils.Direction;
import guice.AppModule;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import mainApp.MainApp;
import model.Vector2D;

import java.util.Optional;


public class MapController {

    @FXML
    public Canvas canvas;

    private final World world;
    private Image doctor;
    private Image dalek;
    private Image rock;
    private GraphicsContext context;
    private int cellWidth;
    private int cellHeight;

    @Inject
    public MapController(World world) {
        this.world = world;
    }

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        doctor = new Image( getClass().getResourceAsStream("/doctor.png"));
        rock = new Image( getClass().getResourceAsStream("/rock.png"));
        dalek = new Image( getClass().getResourceAsStream("/dalek.jpg"));

        WorldMap worldMap = world.getWorldMap();
        cellWidth = ((int) canvas.getWidth() - (worldMap.getWidth()-1) * 2 ) / worldMap.getWidth();
        cellHeight = ( (int) canvas.getHeight() - (worldMap.getHeight()-1) * 2 ) / worldMap.getHeight();
        drawScreen();
    }

    public void addKeyboardEventToScene(Scene scene){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                String keyChar = ke.getText();
                ke.consume();// <-- stops passing the event to next node
                //  if\else is used to disable other buttons when the game is over
                if(world.isGameOver() || world.hasWon()) {
                    if(KeyBindings.isResetKey(keyChar)) {
                        //TODO add reset game prompt to UI
                        onResetWorld();
                    }
                }
                else {
                    if (KeyBindings.isMovementKey(keyChar)) {
                        onMoveButtonPress(KeyBindings.keyToDirection(keyChar));
                        System.out.println("Your score: " + world.getScore());
                        //TODO add score to UI, not console
                    }
                    else if(KeyBindings.isTeleportKey(keyChar)){
                        onUseTeleport();
                    }
                    else if(KeyBindings.isBombKey(keyChar)){
                        onUseBomb();
                    }
                    if(world.hasWon()){
                        System.out.println("Y O U   W O N!!!");
                    }
                    if(world.isGameOver()) {
                        System.out.println("Y O U   L O S T  :(");
                    }
                }
                drawScreen();
            }
        });
    }

    private void onMoveButtonPress(Direction direction) {
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

    private void drawScreen(){
        Platform.runLater( () -> {
            context.setFill(Color.WHITE);
            context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

            for (int i=0; i<MainApp.HEIGHT-1; i++) {
                context.setLineWidth(2.0);
                context.setFill(Color.BLACK);
                context.strokeLine(0,  0.5+(i+1)*cellHeight + i*2, canvas.getWidth(), 0.5+(i+1)*cellHeight + i*2);
            }
            for (int i=0; i<MainApp.WIDTH-1; i++) {
                context.setLineWidth(2.0);
                context.setFill(Color.BLACK);
                context.strokeLine( 0.5+(i+1)*cellWidth + i*2, 0, 0.5+(i+1)*cellWidth + i*2, canvas.getHeight());
            }

            for (int i=0; i<MainApp.HEIGHT; i++) {
                for (int j=0; j<MainApp.WIDTH; j++) {
                    Optional<MapObject> object = world.getWorldMap().objectAt(new Vector2D(i,j));
                    if(object.isPresent()){
                        if(object.get() instanceof Doctor && object.get().isAlive()){
                            context.drawImage(doctor, (cellWidth*i)+i*2, (cellHeight*j)+j*2, cellWidth-1, cellHeight-1);
                        }
                        else{
                            MapObject daleki = object.get();
                            if(daleki.isAlive()){
                                context.drawImage(dalek, (cellWidth*i)+i*2, (cellHeight*j)+j*2, cellWidth-1, cellHeight-1);
                            }
                            else{
                                context.drawImage(rock, (cellWidth*i)+i*2, (cellHeight*j)+j*2, cellWidth-1, cellHeight-1);
                            }
                        }
                    }
                }
            }
        });
    }
}
