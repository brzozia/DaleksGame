package controller;

import com.google.inject.Inject;
import game.World;
import game.utils.Direction;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;


//TODO add score, bombs left, teleports left to UI, not console
//TODO: make whole key/button events disabled by boolean flag?
//TODO make better injection to MapDrawer? Maybe pass it canvas somehow?
public class MapController {

    @FXML
    private Canvas canvas;

//    @FXML private Button moveS;
//    @FXML private Button moveN;
//    @FXML private Button moveE;
//    @FXML private Button moveW;
//    @FXML private Button moveSW;
//    @FXML private Button moveSE;
//    @FXML private Button moveNW;
//    @FXML private Button moveNE;
    @FXML
    private VBox movementButtons;

    @FXML
    private VBox powerUpButtons;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button teleportationButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button bombButton;

    @FXML
    private Label remainingTeleports;

    @FXML
    private Label remainingBombs;

    private final World world;
    private final MapDrafter mapDrafter;


    @Inject
    public MapController(World world, MapDrafter mapDrafter) {
        this.world = world;
        this.mapDrafter = mapDrafter;
    }

    public void initialize() {
        mapDrafter.initialize(canvas, world.getWorldMap());
        setButtonsAndLabelsBinding();
        setResetButtonState(true);
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
        if(world.isGameOver()|| world.hasWon()) {
            if(KeyBindings.isResetKey(keyChar)) {
                this.onResetWorld();
                setResetButtonState(true);
                setButtonsAndLabelsBinding();
            }
        }
        else {
            switch (keyChar) {
                case KeyBindings.USE_TELEPORT, KeyBindings.USE_TELEPORT_NUMERICAL -> {
                    onUseTeleport();
                }
                case KeyBindings.USE_BOMB -> {
                    onUseBomb();
                }
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
        setScore();
    }

    private void checkEndGame(){
        if(world.hasWon()) {
            System.out.println("Y O U   W O N!!!");
            mapDrafter.drawTextOnVictory(world.getScore());
            setResetButtonState(false);
        }
        if(world.isGameOver()) {
            System.out.println("Y O U   L O S T  :(");
            mapDrafter.drawTextOnLosing(world.getScore());
            setResetButtonState(false);
        }
    }



    private void setButtonsAndLabelsBinding() {
        movementButtons.disableProperty().bind(restartButton.disabledProperty().not());
        bombButton.disableProperty().bind(world.getDoctor().getBombs().isEqualTo(0).or(restartButton.disabledProperty().not()));
        teleportationButton.disableProperty().bind(world.getDoctor().getTeleports().isEqualTo(0).or(restartButton.disabledProperty().not()));

        remainingTeleports.textProperty().bind(Bindings.format("remaining teleports: %d",world.getDoctor().getTeleports()));
        remainingBombs.textProperty().bind(Bindings.format("remaining bombs: %d",world.getDoctor().getBombs()));
    }

    private void setResetButtonState(boolean disable){
        restartButton.setDisable(disable);
    }


    private void setScore() {
        int score = world.getScore();
        String scoreText = "Score: " + score;
        scoreLabel.setText(scoreText);
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