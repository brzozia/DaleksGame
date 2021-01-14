package model.entity;

import model.utils.Vector2D;

public class Dalek extends MapObject {

    public Dalek (Vector2D position) {
        super(position);
    }

    public void moveTowards(Vector2D doctorsPosition) {
        if(isAlive) {
            position = position.getCloseTo(doctorsPosition);
        }
    }

}
