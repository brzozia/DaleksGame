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

    private void changeDoctorPosition(MapObject object, Vector2D oldPosition){
        this.positionsOfAlive.remove(oldPosition);
        this.positionsOfAlive.put(object.getPosition(), object);
    }

    public void positionChange(MapObject object) { //TODO make it private
        this.positionsOfAlive.put(object.getPosition(), object);
    }

    private void prepareMapForCheckingCollisions(MapObject object){
        this.positionsOfAlive.clear();
        this.positionsOfAlive.put(object.getPosition(), object);
    }

    private void removeAlivePosition(Vector2D oldPosition) {
        this.positionsOfAlive.remove(oldPosition);
    }


    private void makeDeadPosition(MapObject obj){
        if(positionsOfAlive.containsKey(obj.getPosition())) {
            this.positionsOfDead.put(obj.getPosition(), obj);
            this.positionsOfAlive.remove(obj.getPosition());
        }
    }

    public int aliveDaleks(){
        return positionsOfAlive.size() - 1;
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



    public void checkDoctorCollision(Doctor doctor) {
        if(doctor.getPosition().equals(doctor.getPrevPosition())) {
            return;
        }
        if (this.isOccupied(doctor.getPosition())) {
            doctor.setAlive(false);

            MapObject obj = this.objectAt(doctor.getPosition()).get();
            obj.setAlive(false);   // when we will have GAME OVER screen we won't need so many instructions here
            this.makeDeadPosition(obj);
            this.removeAlivePosition(doctor.getPrevPosition());

        } else {
            this.changeDoctorPosition(doctor, doctor.getPrevPosition());
        }

    }

    public void checkDaleksCollisions(List<Dalek> dalekList, Doctor doctor) { //TODO refactor
        this.prepareMapForCheckingCollisions(doctor);

        dalekList.stream().filter(Dalek::isAlive)
                .forEach(dalek -> {
                    if(this.isOccupied(dalek.getPosition())) {
                        MapObject obj = this.objectAt(dalek.getPosition()).get();

                        this.makeDeadPosition(dalek);
                        dalek.setAlive(false);
                        obj.setAlive(false);

                    }
                    else {
                        this.positionChange(dalek);
                    }
                });
    }

    public void destroyObjectsOnVectors(List<Vector2D> positionsToDestroy) {
        positionsToDestroy.stream()
                .filter(this::isInMapBounds)
                .filter(this::isOccupied)
                .forEach(position -> {
                    MapObject obj = this.objectAt(position).get();
                    this.makeDeadPosition(obj);
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

}
