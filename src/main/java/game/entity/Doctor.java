package game.entity;

import model.Vector2D;

public class Doctor extends MapObject {
    private int bombs;
    private int teleports;


    public Doctor(Vector2D position, int bombs, int teleports) {
        super(position);

        this.bombs = bombs;
        this.teleports = teleports;
//        isAlive = true;
    }

    public void move(Vector2D newPosition) {
        this.prevPosition = position;
        this.position = newPosition;
    }

    public boolean teleport(Vector2D newPosition) {
        if(teleports > 0) {
            this.prevPosition = position;
            this.position = newPosition;
            teleports--;
            return true;
        }
        return false;
    }

//    public void die() {
//        isAlive = false;
//    }
//
//    public void setAlive(boolean isAlive) {
//        this.isAlive = isAlive;
//    }
//
//    public boolean isAlive() {
//        return isAlive;
//    }

    @Override
    public void interact(MapObject mapObject) {

    }

    public void useBomb() {

    }

}
