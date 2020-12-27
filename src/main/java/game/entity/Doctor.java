package game.entity;

import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import mainApp.MainApp;
import model.Vector2D;

public class Doctor extends MapObject {
    private final SimpleIntegerProperty bombs = new SimpleIntegerProperty();
    private final SimpleIntegerProperty teleports = new SimpleIntegerProperty();
    private final SimpleIntegerProperty rewinds = new SimpleIntegerProperty();
    private Vector2D prevPosition;

    public Doctor(Vector2D position, int bombs, int teleports, int rewinds) {
        super(position);
        this.bombs.set(bombs);
        this.teleports.set(teleports);
        this.rewinds.set(rewinds);
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

    public boolean useRewind() {
        if(rewinds.get() > 0) {
            rewinds.set(rewinds.getValue() - 1);
            return true;
        }
        return false;
    }

    public void setBombs(int bombs) {
//        if(bombs > this.bombs.get())
//            this.bombs.set(bombs);
        this.bombs.set(Math.max(bombs, MainApp.INITIAL_BOMBS));
    }

    public void setTeleports(int teleports) {
//        if(teleports > this.teleports.get())
//            this.teleports.set(teleports);
        this.teleports.set(Math.max(teleports, MainApp.INITIAL_TELEPORTS));
    }

    public void setRewinds(int rewinds) {
        this.rewinds.set(Math.max(rewinds, MainApp.INITIAL_REWINDS));
    }

    public SimpleIntegerProperty getBombs() {
        return bombs;
    }

    public SimpleIntegerProperty getTeleports() {
        return teleports;
    }

    public SimpleIntegerProperty getRewinds() {
        return rewinds;
    }

    public Vector2D getPrevPosition() {
        return this.prevPosition;
    }
}
