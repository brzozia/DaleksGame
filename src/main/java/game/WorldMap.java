package game;

import game.entity.MapObject;
import model.Vector2D;

import java.util.*;

public class WorldMap {


    private final int height;
    private final int width;

    private final Map<Vector2D, MapObject> positions = new HashMap<>();

    public WorldMap(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void addEntity(MapObject mapObject) {
        if (isOccupied(mapObject.getPosition())) {
            throw new RuntimeException("Place is already occupied!");
        }
        positions.put(mapObject.getPosition(), mapObject);
    }

    public boolean isOccupied(Vector2D vector2D) {
        return !positions.containsKey(vector2D);
    }

    public Optional<MapObject> objectAt (Vector2D position) {
        return Optional.of(positions.get(position));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
