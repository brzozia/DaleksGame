package game.entity;

import model.Vector2D;

public class Doctor extends MapObject {
    private int bombs;
    private int teleports;

    public Doctor(Vector2D position, int bombs, int teleports) {
        super(position);

        this.bombs = bombs;
        this.teleports = teleports;
    }

    public void move(Vector2D direction) {
        this.position.add(direction);
    }

    public void teleport(Vector2D newPosition) {
        this.setPosition(newPosition);
    }

    @Override
    public void interact(MapObject mapObject) {

    }

    public void useBomb() {

    }

}