package model.mapobjects;

import model.Vector2D;

public abstract class MapObject {
    protected Vector2D position;
    protected MapObjectType type;

    public MapObjectType getType() {
        return type;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public void destroy() {

    }
}
