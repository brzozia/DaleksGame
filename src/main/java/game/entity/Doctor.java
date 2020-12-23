package game.entity;

import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import mainApp.MainApp;
import model.Vector2D;

public class Doctor extends MapObject {
    private final SimpleIntegerProperty bombs ;
    private final SimpleIntegerProperty teleports;
    private Vector2D prevPosition;

    public Doctor(Vector2D position, int bombs, int teleports) {
        super(position);
        this.bombs = new SimpleIntegerProperty(bombs);
        this.teleports = new SimpleIntegerProperty(teleports);
        this.prevPosition = position;
    }

    public void move(Vector2D newPosition) {
        this.prevPosition = this.position;
        this.position = newPosition;
    }

    public boolean teleport(Vector2D newPosition) {
        if(teleports.get() > 0) {
            this.move(newPosition);
            teleports.set(teleports.getValue() - 1);
            return true;
        }
        return false;
    }

    public boolean useBomb() {
        if(bombs.get() > 0) {
            this.move(getPosition());
            bombs.set(bombs.getValue() - 1);
            return true;
        }
        return false;
    }

    public void setBombs(int bombs) {
        if(bombs > this.bombs.get())
            this.bombs.set(bombs);
    }

    public void setTeleports(int teleports) {
        if(teleports > this.teleports.get())
            this.teleports.set(teleports);
    }

    public SimpleIntegerProperty getBombs() {
        return bombs;
    }

    public SimpleIntegerProperty getTeleports() {
        return teleports;
    }

    public Vector2D getPrevPosition() {
        return this.prevPosition;
    }
}
