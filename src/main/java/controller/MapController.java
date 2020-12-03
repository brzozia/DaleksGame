package controller;

import model.Vector2D;
import model.mapobjects.*;

import java.util.List;
import java.util.stream.Stream;

public class MapController {
//    private MapObject[][] map;  //probably unnecessary
    private Doctor doctor;
    private List<Dalek> dalekList;
    private List<Dalek> deadDalekList;

    public void makeMove(Vector2D direction) {

        doctor.move(direction);
        checkDoctorCollision();

        dalekList.forEach(Dalek::move);

        checkDaleksCollisions();

    }
    private void checkDoctorCollision() {
        Stream.concat(dalekList.stream(), deadDalekList.stream())
                .forEach(dalek -> {
                    if(this.doctor.getPosition().equals(dalek.getPosition())) {
                        //TODO gameOver
                    }
                });
    }

    private void checkDaleksCollisions() { //TODO IMPLEMENT
//        dalekList.forEach(dalek -> {
//            //1. check coll with doctor
//            if(this.doctor.getPosition().equals(dalek.getPosition())) {
//                //TODO gameOver
//            }
//            //2. check coll with other alive Daleks
//            dalekList.forEach(otherDalek -> {
//                if(dalek != otherDalek && dalek.getPosition().equals(otherDalek.getPosition())) {
//                    break;
//                }
//            });
//
//        });
    }
}
