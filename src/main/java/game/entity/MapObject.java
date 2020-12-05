package game.entity;

import model.Vector2D;

public abstract class MapObject {
    protected Vector2D position;
    protected Vector2D prevPosition;

    public MapObject(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public Vector2D getPrevPosition() {
        return this.prevPosition;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public abstract void interact(MapObject mapObject);

}
