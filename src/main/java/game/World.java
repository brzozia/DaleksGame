package game;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import game.entity.Dalek;
import game.entity.Doctor;
import game.utils.Direction;
import game.utils.MapGenerationHelper;
import mainApp.MainApp;
import model.Vector2D;

import java.util.List;

public class World {
    private final WorldMap worldMap;
    private final WorldCollisions worldCollisions;
    private  List<Dalek> dalekList;
    private  Doctor doctor;
    private int score = 0;
    private int dalekNumber;

    @Inject
    public World(WorldMap worldMap, @Named("DalekNumber") int dalekNumber) {
        this.worldMap = worldMap;
        this.worldCollisions = new WorldCollisions(worldMap);
        this.dalekNumber = dalekNumber;
        this.initializeWorld(dalekNumber);
    }

    public boolean isGameOver() {
        return doctor == null || !doctor.isAlive();
    }
    public boolean hasWon(){
        return doctor.isAlive() && worldMap.aliveDaleks() == 0;
    }
    private void increaseScoreBy(int i){
        if(doctor.isAlive())
            score += i;
    }

    public void initializeWorld(int dalekNumber) { //right now doctor resets bomb and tp every won game
        if(this.isGameOver()) score = 0;
        MapGenerationHelper.clearDaleksFromWorldAndList(worldMap, dalekList);
        doctor = MapGenerationHelper.randomPlaceDoctor(worldMap);
        dalekList = MapGenerationHelper.randomPlaceDaleks(worldMap, dalekNumber);
    }

    //actions
    public void resetWorld() {
        if(hasWon()) {
            dalekNumber++;
            this.initializeWorld(dalekNumber);
        }
        if(isGameOver()) {
            dalekNumber = MainApp.DALEK_NUMBER;
            this.initializeWorld(dalekNumber);
        }
    }

    private void onWorldAction(){
        worldCollisions.checkDoctorCollision(getDoctor());
        dalekList.forEach(dalek -> dalek.moveTowards(doctor.getPosition()));
        worldCollisions.checkDaleksCollisions(getDalekList(), getDoctor());
        this.increaseScoreBy(1);
    }

    public void makeMove(Direction direction) {
        Vector2D newDocPosition = doctor.getPosition().add(direction.toVector());
        if(worldMap.isInMapBounds(newDocPosition)){
            doctor.move(newDocPosition);
            onWorldAction();
        }
        else {
            System.out.println("What you are trying to do? Wanna run beyond the borders? GL");
        }
    }

    public void makeTeleport() {
        if(doctor.teleport(worldMap.getRandomVector(false))) {
            System.out.println("Teleportation!");
            this.onWorldAction();
        }
        else {
            System.out.println("You've ran out of teleportations!");
        }
    }

    public void useBomb() {
        if(doctor.useBomb()) {
            System.out.println("Bombard");
            List<Vector2D> vectorsAround = Vector2D.getPositionsAround(getDoctor().getPosition());
            worldMap.destroyObjectsOnVectors(vectorsAround);
            this.onWorldAction();  // can be here also Move(0) (but now doctor's positions are change in Doctor class in useBomb())
        }
        else {
            System.out.println("You've ran out of bombs!");
        }
    }

    //getters/setters
    public List<Dalek> getDalekList() {
        return dalekList;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public WorldMap getWorldMap() {
        return worldMap;
    }
    public int getScore(){
        return score;
    }
}
