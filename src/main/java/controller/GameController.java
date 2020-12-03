package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import model.Vector2D;
import model.mapobjects.*;

//should it implement some IController and guice bind it?
public class GameController {

    @FXML
    private ImageView doctorImage;

    //model
    MapController mapController;

    //view

    @FXML
    public void initialize() {



    }

    public void initRootLayout() {

    }

    public void bindToView() {
        //...
    }

    private void onMoveButtonPress(Vector2D direction) {
        mapController.makeMove(direction);
    }


}
