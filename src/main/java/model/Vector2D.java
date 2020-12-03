package model;

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

    public boolean equals(Vector2D second) {
        return this.x == second.getX() && this.y == second.getY();
    }

}
