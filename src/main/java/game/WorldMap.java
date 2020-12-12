package game;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import game.entity.Dalek;
import game.entity.Doctor;
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

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public Map<Vector2D, MapObject> getPositionsOfAlive() {
        return positionsOfAlive;
    }
    public int aliveDaleks(){
        return positionsOfAlive.size() - 1;
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

    public boolean isOccupied(Vector2D vector2D) {
        return positionsOfAlive.containsKey(vector2D) || positionsOfDead.containsKey(vector2D);
    }

    public boolean isInMapBounds(Vector2D vec){
        if(vec.getX() >= width || vec.getX() < 0){
            return false;
        }
        else return vec.getY() < width && vec.getY() >= 0;
    }


    public void addEntity(MapObject mapObject) {
        if (isOccupied(mapObject.getPosition())) {
            System.out.println(mapObject.getPosition().getX() + " "+mapObject.getPosition().getY()+"  " +mapObject);
            throw new RuntimeException("Place is already occupied!");
        }
        positionsOfAlive.put(mapObject.getPosition(), mapObject);
    }

    private void makeEntityDead(MapObject obj){
        if(positionsOfAlive.containsKey(obj.getPosition())) {
            this.positionsOfAlive.remove(obj.getPosition());
        }
        else {
            this.positionsOfDead.remove(obj.getPosition());
        }
        this.positionsOfDead.put(obj.getPosition(), obj);
    }

    public void clearAllEntities() {
        this.positionsOfAlive.clear();
        this.positionsOfDead.clear();
    }

    public Optional<MapObject> objectAt (Vector2D position) {
        Optional<MapObject> optional = Optional.ofNullable(positionsOfAlive.get(position));
        if (optional.isPresent()) {
            return optional;
        }
        return Optional.ofNullable(positionsOfDead.get(position));
    }

    public void positionChange(MapObject object) { //TODO make it private
        this.positionsOfAlive.put(object.getPosition(), object);
    }


    private void checkObjectCollision(MapObject mapObject) {
        if(this.isOccupied(mapObject.getPosition())) {
            MapObject otherObject = this.objectAt(mapObject.getPosition()).get(); //cannot be null because of if fun

            this.makeEntityDead(mapObject);
            mapObject.setAlive(false);
            otherObject.setAlive(false);
        }
        else {
            this.positionChange(mapObject);
        }
    }

    public void checkDoctorCollision(Doctor doctor) {
        //removes doctor's previous position so he won't collide with himself wile using bomb or teleporting to same place
        this.positionsOfAlive.remove(doctor.getPrevPosition());
        checkObjectCollision(doctor);
    }

    public void checkDaleksCollisions(List<Dalek> dalekList, Doctor doctor) {
        //clears all positions but doctors so daleks won't bump into each other while moving same direction
        this.positionsOfAlive.clear();
        if(doctor.isAlive()) this.addEntity(doctor);

        dalekList.stream()
                .filter(Dalek::isAlive)
                .forEach(this::checkObjectCollision);
    }


    public void destroyObjectsOnVectors(List<Vector2D> positionsToDestroy) {
        positionsToDestroy.stream()
                .filter(this::isInMapBounds)
                .filter(this::isOccupied)
                .forEach(position -> {
                    MapObject obj = this.objectAt(position).get();
                    this.makeEntityDead(obj);
                    obj.setAlive(false);

//                    getDalekList()
//                        .stream().filter(Dalek::isAlive)
//                        .forEach(dalek -> {
//                            if(dalek.getPosition().equals(position)) {
//                                Dalek obj = (Dalek) worldMap.objectAt(position).get();
//
//                                worldMap.makeDeadPosition(dalek);
//                                dalek.setAlive(false);
//                                obj.setAlive(false);
//                            }
//                    });
                });
    }


//    private void prepareMapForCheckingCollisions(MapObject object){
//        this.positionsOfAlive.clear();
//        if(object.isAlive()) {
//            this.positionsOfAlive.put(object.getPosition(), object);
//        }
//    }


//    private void changeDoctorPosition(MapObject object, Vector2D oldPosition){
//        this.positionsOfAlive.remove(oldPosition);
//        this.positionsOfAlive.put(object.getPosition(), object);
//    }

    //    private void removeAlivePosition(Vector2D oldPosition) {
//        this.positionsOfAlive.remove(oldPosition);
//    }
}
