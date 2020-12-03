package model;

import java.util.Objects;

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
