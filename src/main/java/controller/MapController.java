package controller;

import com.google.inject.Inject;
import game.World;
import game.utils.Direction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;


//TODO add score, bombs left, teleports left to UI, not console
//TODO: make whole key/button events disabled by boolean flag?
//TODO make better injection to MapDrawer? Maybe pass it canvas somehow?
public class MapController {

    @FXML
    private Canvas canvas;

    @FXML
    private Button restartButton;
    private boolean disable;

    private final World world;
    private final MapDrafter mapDrafter;


    @Inject
    public MapController(World world, MapDrafter mapDrafter) {
        this.world = world;
        this.mapDrafter = mapDrafter;
    }

    public void initialize() {
        mapDrafter.initialize(canvas, world.getWorldMap());
    }

    public void addKeyboardEventToScene(Scene scene){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            String keyChar = ke.getText();
            ke.consume();// <-- stops passing the event to next node
            executeKeyFunction(keyChar);
        });
    }

    private void executeKeyFunction(String keyChar){
        //  if\else used to disable other buttons when the game is over
        if(world.isGameOver() || world.hasWon()) {
            if(KeyBindings.isResetKey(keyChar)) {
                this.onResetWorld();
                disable = true;
            }
        }
        else {
            switch (keyChar) {
                case KeyBindings.USE_TELEPORT, KeyBindings.USE_TELEPORT_NUMERICAL -> onUseTeleport();
                case KeyBindings.USE_BOMB -> onUseBomb();
                default -> {
                    if (KeyBindings.isMovementKey(keyChar)) {
                        this.onMoveKeyPress(KeyBindings.keyToDirection(keyChar));
                        System.out.println("Your score: " + world.getScore());
                    }
                }
            }
        }

        mapDrafter.drawScreen(world.getWorldMap());
        this.checkEndGame();
    }

    private void checkEndGame(){
        if(world.hasWon()){
            disable = false;
            System.out.println("Y O U   W O N!!!");
            mapDrafter.drawTextOnVictory(world.getScore());
        }
        if(world.isGameOver()) {
            disable = false;
            System.out.println("Y O U   L O S T  :(");
            mapDrafter.drawTextOnLosing(world.getScore());
        }

        restartButton.setDisable(disable);
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
}