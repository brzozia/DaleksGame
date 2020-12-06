package game;

import game.entity.MapObject;
import model.Vector2D;

import java.util.*;

public class WorldMap  {

    private final int height;
    private final int width;
    private final Map<Vector2D, MapObject> positions;

    public WorldMap(int height, int width) {
        this.height = height;
        this.width = width;
        positions = new HashMap<>();
    }

    public void addEntity(MapObject mapObject) {
        if (isOccupied(mapObject.getPosition())) {
            System.out.println(mapObject.getPosition().getX() + " "+mapObject.getPosition().getY()+"  " +mapObject);
            throw new RuntimeException("Place is already occupied!");
        }
        positions.put(mapObject.getPosition(), mapObject);
    }

    public boolean isOccupied(Vector2D vector2D) {
        return positions.containsKey(vector2D);
    }

    public Optional<MapObject> objectAt (Vector2D position) {
        return Optional.ofNullable(positions.get(position));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isInMap(Vector2D vec){
        if(vec.getX() >= width || vec.getX() < 0){
            return false;
        }
        else if(vec.getY() >= width || vec.getY() < 0){
            return false;
        }
        return true;
    }

    public void positionChanged(MapObject object, Vector2D oldPosition, Vector2D newPosition) {
        positions.remove(oldPosition);
        positions.put(newPosition, object);
    }


    public void removePosition(Vector2D oldPosition) {
        positions.remove(oldPosition);
    }

    public Vector2D getRandomVector() {
        Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);

        return new Vector2D(x,y);
    }
}
