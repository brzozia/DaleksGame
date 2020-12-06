package game;

import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.MapObject;
import game.entity.PowerUp;
import game.utils.MapGenerationHelper;
import model.Vector2D;

import java.util.List;

public class World {
    private final WorldMap worldMap;
    private final List<Dalek> dalekList;
    private final Doctor doctor;
    private List<PowerUp> powerUpsList;

    public World(int height, int width, int dalekNumber) {
        worldMap = new WorldMap(height, width);
        doctor = MapGenerationHelper.randomPlaceDoctor(worldMap);
        dalekList = MapGenerationHelper.randomPlaceDalek(worldMap, dalekNumber);
    }

    public List<Dalek> getDalekList() {
        return dalekList;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public List<PowerUp> getPowerUpsList() {
        return powerUpsList;
    }

    public void makeMove(Integer direction) {
        Vector2D vec = parseToVector2D(direction, getDoctor().getPosition());
        if(worldMap.isInMap(vec)){
            getDoctor().move(vec);
            checkCollisionsAndMoveDaleks();
        }
        else{
            System.out.println("What you are trying to do? Wanna run beyond the borders? GL");
        }
    }

    public void makeTeleport() {
        if(getDoctor().teleport(worldMap.getRandomVector())){
            checkCollisionsAndMoveDaleks();
        }
    }

    private void checkCollisionsAndMoveDaleks(){
        checkDoctorCollision();
        getDalekList().forEach(dalek -> dalek.move( getDoctor().getPosition()) );
        checkDaleksCollisions();
    }

    private void checkDoctorCollision() {
        if(worldMap.isOccupied(getDoctor().getPosition())){
            //TODO gameover
            System.out.println("Doctor's Collision detected! - E N D   G A M E");
//            mapController.removeEventFromScene(scene);
            doctor.die();
        }
        else{
            worldMap.positionChanged(getDoctor(), getDoctor().getPrevPosition(), getDoctor().getPosition());
        }
    }

    private void checkDaleksCollisions() {
        getDalekList()
            .stream().filter(Dalek::isAlive)
            .forEach(dalek -> {
                if(worldMap.isOccupied(dalek.getPosition())){
                    MapObject obj = worldMap.objectAt(dalek.getPosition()).get();

                    if(obj instanceof Doctor) {
                        System.out.println("DALEK ATE THR DOCTOR - E N D   G A M E ");
                        worldMap.positionChanged(dalek, dalek.getPrevPosition(), dalek.getPosition());
                        doctor.die();
                        //TODO gameOver
                    } else {
                        Dalek dalek2 = (Dalek) obj;
                        dalek2.setAlive(false);

                        System.out.println("Dalek's Collision detected!");
                    }

                    worldMap.removePosition(dalek.getPrevPosition());
                    dalek.setAlive(false);
                } else {
                    worldMap.positionChanged(dalek, dalek.getPrevPosition(), dalek.getPosition());
                }
            });
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public Vector2D parseToVector2D(int num, Vector2D position){
        int x = position.getX();
        int y = position.getY();

        switch (num) {
            case 1 -> {
                x += -1;
                y += 1;
            }
            case 2 -> {
                x += 0;
                y += 1;
            }
            case 3 -> {
                x += 1;
                y += 1;
            }
            case 6 -> {
                x += 1;
                y += 0;
            }
            case 9 -> {
                x += 1;
                y += -1;
            }
            case 8 -> {
                x += 0;
                y += -1;
            }
            case 7 -> {
                x += -1;
                y += -1;
            }
            case 4 -> {
                x += -1;
                y += 0;
            }
            default -> {
                x += 0;
                y += 0;
            }
        }

        return new Vector2D(x,y);
    }
}
