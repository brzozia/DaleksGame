package model;

import java.util.Objects;
import java.util.Vector;

public class Vector2D {
    private int x;
    private int y;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D cloneVector(){
        return new Vector2D(this.x,this.y);
    }

    public Vector2D(int num) {
        parseToVector2D(num);
    }

    public String toString(){
        return "x:  "+ this.x +",  y: "+this.y;
    }

    public void add(Vector2D move) {
        this.x += move.getX();
        this.y += move.getY();
    }

    public void substract(Vector2D move) {
        this.x -= move.getX();
        this.y -= move.getY();
    }

    public Vector2D follows(Vector2D move) {
        return new Vector2D(move.x+1, move.y+1);
    }

    public Vector2D precedes(Vector2D move) {
        return new Vector2D(move.x-1, move.y-1);
    }

    public void getCloseTo(Vector2D to){
        if(this.x < to.getX()) this.x++;
        else if (this.x > to.getX()) this.x--;

        if(this.y < to.getY()) this.y++;
        else if (this.y > to.getY()) this.y--;

    }


    public void parseToVector2D(int num){
        switch (num) {
            case 1 -> {
                this.x = -1;
                this.y = 1;
            }
            case 2 -> {
                this.x = 0;
                this.y = 1;
            }
            case 3 -> {
                this.x = 1;
                this.y = 1;
            }
            case 6 -> {
                this.x = 1;
                this.y = 0;
            }
            case 9 -> {
                this.x = 1;
                this.y = -1;
            }
            case 8 -> {
                this.x = 0;
                this.y = -1;
            }
            case 7 -> {
                this.x = -1;
                this.y = -1;
            }
            case 4 -> {
                this.x = -1;
                this.y = 0;
            }
            default -> {
                this.x = 0;
                this.y = 0;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return x == vector2D.x && y == vector2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
