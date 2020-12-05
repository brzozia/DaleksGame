package game.entity;

import model.Vector2D;

public class Dalek extends MapObject {
    private boolean isAlive;

    public Dalek (Vector2D position) {
        super(position);
        isAlive = true;
    }

    public void move(Vector2D position, Vector2D doctorsPosition) {
        if(isAlive) {
            this.prevPosition = position;
            this.position.getCloseTo(doctorsPosition);
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void interact(MapObject mapObject) {

    }

    public void setAlive(boolean isAlive) {
        this.isAlive =  true;
    }
}
