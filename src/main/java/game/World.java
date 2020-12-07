package game;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.MapObject;
import game.entity.PowerUp;
import game.utils.MapGenerationHelper;
import model.Vector2D;

import java.util.List;

public class World {
    private final WorldMap worldMap;
    private  List<Dalek> dalekList;
    private  Doctor doctor;
    private List<PowerUp> powerUpsList;

    @Inject
    public World(WorldMap worldMap, @Named("DalekNumber") int dalekNumber) {
        this.worldMap = worldMap;
        this.initializeWorld(dalekNumber);
    }

    public void initializeWorld(int dalekNumber) {
        MapGenerationHelper.clearDaleksFromWorldAndList(worldMap, dalekList);

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
        else {
            System.out.println("What you are trying to do? Wanna run beyond the borders? GL");
        }
    }

    public void makeTeleport() {
        if(getDoctor().teleport(worldMap.getRandomVector())) {
            System.out.println("Teleportation!");
            checkCollisionsAndMoveDaleks();
        }
    }

    private void checkCollisionsAndMoveDaleks(){
        checkDoctorCollision();
        if(!isGameOver()) {
            getDalekList().forEach(dalek -> dalek.move( getDoctor().getPosition()) );
            checkDaleksCollisions();
        }
    }

    private void checkDoctorCollision() {
        if(worldMap.isOccupied(getDoctor().getPosition())){
            doctor.setAlive(false);
            worldMap.removeAlivePosition(doctor.getPrevPosition());
            System.out.println("Doctor's Collision detected! - E N D   G A M E");

        }
        else{
            worldMap.changeDoctorsPosition(doctor);
        }
    }

    private void checkDaleksCollisions() {
        getDalekList()
            .stream().filter(Dalek::isAlive)
            .forEach(dalek -> {
                if(worldMap.isOccupiedByDead(dalek.getPosition())){ // can be also without this if but then it does "objectAt" and "makeDeadPosition" witout any sense - so now it's faster
                    dalek.setAlive(false);
                }
                else if(worldMap.isOccupiedByAlive(dalek.getPosition())) { //without above if there should be "isOccupied", not "isOccupiedByAlive"
                    MapObject obj = worldMap.objectAt(dalek.getPosition()).get();

                    worldMap.makeDeadPosition(dalek);
                    dalek.setAlive(false);
                    obj.setAlive(false);
                }
                else {
                    worldMap.positionChange(dalek);
                }
            });
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public boolean isGameOver() {
        return !doctor.isAlive();
    }

    public static Vector2D parseToVector2D(int num, Vector2D position){
        int x = position.getX();
        int y = position.getY();

        //TODO make enum making it more explicit
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
