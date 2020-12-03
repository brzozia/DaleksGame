package game.entity;

import model.Vector2D;

public class Dalek extends MapObject {
    private boolean isAlive;

    public Dalek (Vector2D position) {
        super(position);
    }

    public void move(Vector2D position) {
        if(isAlive) {
            this.getPosition().add(position);
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void interact(MapObject mapObject) {

    }

    public void setAlive(boolean isAlive) {
        this.isAlive =  isAlive;
    }
}
