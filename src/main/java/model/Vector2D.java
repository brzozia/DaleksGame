package model;

import java.util.Objects;

public class Vector2D {
    private final int x;
    private final int y;

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

    public String toString(){
        return "x: "+ this.x +", y: "+this.y;
    }

    public Vector2D getCloseTo(Vector2D to){
        int x = this.x;
        int y = this.y;

        if(this.x < to.getX()) x++;
        else if (this.x > to.getX()) x--;

        if(this.y < to.getY()) y++;
        else if (this.y > to.getY()) y--;

        return new Vector2D(x,y);
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
