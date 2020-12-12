package game;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import game.entity.MapObject;
import model.Vector2D;

import java.util.*;

public class WorldMap  {

    private int height;
    private int width;

    private final Map<Vector2D, MapObject> positionsOfAlive;
    private final Map<Vector2D, MapObject> positionsOfDead;

    @Inject
    public WorldMap(@Named("Width") int width, @Named("Height") int height) {
        this.width = width;
        this.height = height;
        positionsOfAlive = new HashMap<>();
        positionsOfDead = new HashMap<>();
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
        positionsOfAlive.put(mapObject.getPosition(), mapObject);
    }

    public boolean isOccupied(Vector2D vector2D) {
        return positionsOfAlive.containsKey(vector2D) || positionsOfDead.containsKey(vector2D);
    }

    public Optional<MapObject> objectAt (Vector2D position) {
        Optional<MapObject> optional = Optional.ofNullable(positionsOfAlive.get(position));
        if (optional.isPresent()) {
            return optional;
        }
        return Optional.ofNullable(positionsOfDead.get(position));
    }

    public void clearAllPositions() {
        this.positionsOfAlive.clear();
        this.positionsOfDead.clear();
    }

    public void changeDoctorPosition(MapObject object, Vector2D oldPosition){
        this.positionsOfAlive.remove(oldPosition);
        this.positionsOfAlive.put(object.getPosition(), object);
    }

    public void prepareMapForCheckingCollisions(MapObject object){
        this.positionsOfAlive.clear();
        this.positionsOfAlive.put(object.getPosition(), object);
    }

    public void removeAlivePosition(Vector2D oldPosition) {
        this.positionsOfAlive.remove(oldPosition);
    }

    public void positionChange(MapObject object) {
        this.positionsOfAlive.put(object.getPosition(), object);
    }

    public void makeDeadPosition(MapObject obj){
        if(positionsOfAlive.containsKey(obj.getPosition())) {
            this.positionsOfDead.put(obj.getPosition(), obj);
            this.positionsOfAlive.remove(obj.getPosition());
        }
    }

    public int aliveDaleks(){
        return positionsOfAlive.size() - 1;
    } //TODO: why is it -1, misleading?

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isInMapBounds(Vector2D vec){
        if(vec.getX() >= width || vec.getX() < 0){
            return false;
        }
        else return vec.getY() < width && vec.getY() >= 0;
    }

    public Vector2D getRandomVector(boolean mustBeUnoccupied) {
        Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Vector2D vector = new Vector2D(x,y);
        while(mustBeUnoccupied && this.isOccupied(vector)) {
            x = random.nextInt(this.getWidth());
            y = random.nextInt(this.getHeight());
            vector = new Vector2D(x,y);
        }
        return vector;
    }

    public Map<Vector2D, MapObject> getPositionsOfAlive() {
        return positionsOfAlive;
    }
}
