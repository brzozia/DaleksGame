package game;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import game.entity.MapObject;
import model.Vector2D;

import java.util.*;

public class WorldMap  {

    private int height;
    private int width;
    private final Map<Vector2D, MapObject> positions;

    @Inject
    public WorldMap() {
        positions = new HashMap<>();
    }
    @Inject
    public void setHeight(@Named("Height") int height) {
        this.height = height;
    }
    @Inject
    public void setWidth(@Named("Width") int width) {
        this.width = width;
    }

    public void addEntity(MapObject mapObject) {
        if (isOccupied(mapObject.getPosition())) {
            System.out.println(mapObject.getPosition().getX() + " "+mapObject.getPosition().getY()+"  " +mapObject);
            throw new RuntimeException("Place is already occupied!");
        }
        positions.put(mapObject.getPosition(), mapObject);
    }

    public void clearAllPositions() {
        this.positions.clear();
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
        else return vec.getY() < width && vec.getY() >= 0;
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
