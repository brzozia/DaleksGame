package controller;

import game.World;
import game.WorldMap;
import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.MapObject;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mainApp.MainApp;
import model.Vector2D;

import java.util.Optional;

//should it implement some IController and guice bind it?
public class MapController {

    @FXML
    public Canvas canvas;

    private WorldMap worldMap;
    private World world;
    private Image doctor;
    private Image dalek;
    private Image rock;
    private GraphicsContext context;
    private int cellWidth;
    private int cellHeight;


    public void initialize() {
        world = new World(MainApp.HEIGHT, MainApp.WIDTH, 5);
        worldMap = world.getWorldMap();
        context = canvas.getGraphicsContext2D();
        doctor = new Image( getClass().getResourceAsStream("/doctor.png"));
        rock = new Image( getClass().getResourceAsStream("/rock.png"));
        dalek = new Image( getClass().getResourceAsStream("/dalek.jpg"));
        cellWidth = ((int) canvas.getWidth() - (worldMap.getWidth()-1) * 2 ) / worldMap.getWidth();
        cellHeight = ( (int) canvas.getHeight() - (worldMap.getHeight()-1) * 2 ) / worldMap.getHeight();

        drawScreen();
    }

    public void addEventToScene(Scene scene){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed: " + ke.getText());
                if (ke.getText().matches("[1-4|6-9]")) {

                    Vector2D vec = new Vector2D(Integer.parseInt(ke.getText()));
                    System.out.println("Key Pressed: " + ke.getText()+ " "+ vec.getX()+ " "+ vec.getY());

                    ke.consume(); // <-- stops passing the event to next node
                    onMoveButtonPress(vec);
                    drawScreen();
                }
                else if(ke.getText().equals("t")){
                    System.out.println("Teleportation!");
                    ke.consume();
                    onUseTeleport();
                    drawScreen();
                }

            }
        });
    }



    public void initRootLayout() {}

    public void bindToView() {}

    private void onMoveButtonPress(Vector2D direction) {
        world.makeMove(direction);
    }

    private void onUseTeleport() {
        world.makeTeleport();
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
                    Optional<MapObject> object = worldMap.objectAt(new Vector2D(i,j));
                    if(object.isPresent()){
                        if(object.get() instanceof Doctor){
                            context.drawImage(doctor, (cellWidth*i)+i*2, (cellHeight*j)+j*2, cellWidth-1, cellHeight-1);
                        }
                        else{
                            Dalek daleki = (Dalek) object.get();
                            if(daleki.isAlive()){
                                context.drawImage(dalek, (cellWidth*i)+i*2, (cellHeight*j)+j*2, cellWidth-1, cellHeight-1);
                            }
                            else{
                                context.drawImage(rock, (cellWidth*i)+i*2, (cellHeight*j)+j*2, cellWidth-1, cellHeight-1);
                            }

                           //else tile = new ImageView(new Image(getClass().getResourceAsStream("/rock.png")));
                        }
                    }
                }
            }
        });
    }
}
