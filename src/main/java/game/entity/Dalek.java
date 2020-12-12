package game.entity;

import model.Vector2D;

public class Dalek extends MapObject {

    public Dalek (Vector2D position) {
        super(position);
        this.isAlive = true;
    }

    public void moveTowards(Vector2D doctorsPosition) {
        if(isAlive) {
            this.position = position.getCloseTo(doctorsPosition);
        }
    }

}
