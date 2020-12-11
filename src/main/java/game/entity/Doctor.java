package game.entity;

import model.Vector2D;

public class Doctor extends MapObject {
    private int bombs;
    private int teleports;
    private Vector2D prevPosition;

    public Doctor(Vector2D position, int bombs, int teleports) {
        super(position);
        this.bombs = bombs;
        this.teleports = teleports;
        this.isAlive = true;
        this.prevPosition = position;
    }

    public void move(Vector2D newPosition) {
        //firstly should check if newPosition
        // is close to old
        this.prevPosition = position;
        this.position = newPosition;
    }

    public boolean teleport(Vector2D newPosition) {
        if(teleports > 0) {
            move(newPosition);
            teleports--;
            return true;
        }
        return false;
    }

    public Vector2D getPrevPosition() {
        return this.prevPosition;
    }

    public boolean useBomb() {
        if(bombs > 0) {
            move(getPosition());
            bombs--;
            return true;
        }
        return false;
    }

    public int getBombs() {
        return bombs;
    }

    public int getTeleports() {
        return teleports;
    }

}
