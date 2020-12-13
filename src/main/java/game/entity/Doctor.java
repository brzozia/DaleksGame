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
        this.prevPosition = position;
    }

    public void move(Vector2D newPosition) {
        this.prevPosition = this.position;
        this.position = newPosition;
    }

    public boolean teleport(Vector2D newPosition) {
        if(teleports > 0) {
            this.move(newPosition);
            teleports--;
            return true;
        }
        return false;
    }

    public boolean useBomb() {
        if(bombs > 0) {
            this.move(getPosition());
            bombs--;
            return true;
        }
        return false;
    }

    public int getBombs() {
        return bombs;
    }
    public Vector2D getPrevPosition() {
        return this.prevPosition;
    }
    public int getTeleports() {
        return teleports;
    }
}
