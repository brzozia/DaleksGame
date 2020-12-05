package game.entity;

import model.Vector2D;

import java.util.Random;

public class Doctor extends MapObject {
    private int bombs;
    private int teleports;

    public Doctor(Vector2D position, int bombs, int teleports) {
        super(position);

        this.bombs = bombs;
        this.teleports = teleports;
    }

    public void move(Vector2D newPosition) {
        this.prevPosition = position;
        this.position = newPosition;
    }

    public void teleport(Vector2D newPosition) {
        if(--teleports>0) {
            this.prevPosition = position;
            this.position = newPosition;
        }
    }

    @Override
    public void interact(MapObject mapObject) {

    }

    public void useBomb() {

    }

}
