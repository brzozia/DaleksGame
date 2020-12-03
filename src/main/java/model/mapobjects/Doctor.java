package model.mapobjects;

import model.Vector2D;

public class Doctor extends MapObject {
//    private int shields;
//    private int bombs;

    public void move(Vector2D direction) {
        this.position.add(direction);
//        this.map.updatePosition(this); //TODO controller updates doc and daleks on map or themselves
    }

//    public void useBomb() {
//
//    }
//
//    public void useShield() {
//
//    }
}
