package game.entity;

import model.Vector2D;

public abstract class MapObject {
    protected Vector2D position;
    protected boolean isAlive;

    public MapObject(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
