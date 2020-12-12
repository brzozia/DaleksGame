package game;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.MapObject;
import game.utils.Direction;
import game.utils.MapGenerationHelper;
import mainApp.MainApp;
import model.Vector2D;

import java.util.List;

public class World {
    private final WorldMap worldMap;
    private  List<Dalek> dalekList;
    private  Doctor doctor;
//    private List<PowerUp> powerUpsList;
    private int score = 0;
    private int dalekNumber;

    @Inject
    public World(WorldMap worldMap, @Named("DalekNumber") int dalekNumber) {
        this.worldMap = worldMap;
        this.dalekNumber = dalekNumber;
        this.initializeWorld(dalekNumber);
    }

    public void initializeWorld(int dalekNumber) { //what if doctor keeps bomb and tp count towards next game?
        if(doctor != null && isGameOver()) score = 0;
        MapGenerationHelper.clearDaleksFromWorldAndList(worldMap, dalekList);
        doctor = MapGenerationHelper.randomPlaceDoctor(worldMap);
        dalekList = MapGenerationHelper.randomPlaceDaleks(worldMap, dalekNumber);
    }

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
//        public List<PowerUp> getPowerUpsList() {
//        return powerUpsList;
//    }

    public boolean isGameOver() {
        return !doctor.isAlive();
    }
    public boolean hasWon(){
        return doctor.isAlive() && worldMap.aliveDaleks() == 0;
    }

    //actions
    public void resetWorld() {
        if(hasWon()) {
            this.dalekNumber++;
            initializeWorld(this.dalekNumber);
        }
        if(isGameOver()) {
            this.dalekNumber = MainApp.DALEK_NUMBER;
            initializeWorld(this.dalekNumber);
        }
    }

    public void makeMove(Direction direction) {
        Vector2D newDocPosition = getDoctor().getPosition().add(direction.toVector());

        if(worldMap.isInMapBounds(newDocPosition)){
            getDoctor().move(newDocPosition);
            onWorldAction();
        }
        else {
            System.out.println("What you are trying to do? Wanna run beyond the borders? GL");
        }
    }

    public void makeTeleport() {
        if(getDoctor().teleport(worldMap.getRandomVector(false))) {
            System.out.println("Teleportation!");
            onWorldAction();
        }
        else {
            System.out.println("You've ran out of teleportations!");
        }
    }

    public void useBomb() {
        if(getDoctor().useBomb()) {
            System.out.println("Bombard");
            List<Vector2D> vectorsAround = Vector2D.getPositionsAround(getDoctor().getPosition());
            worldMap.destroyObjectsOnVectors(vectorsAround);
            onWorldAction();  // can be here also Move(0) (but now doctor's positions are change in Doctor class in useBomb())
        }
        else {
            System.out.println("You've ran out of bombs!");
        }
    }

    private void onWorldAction(){
        worldMap.checkDoctorCollision(getDoctor());
        getDalekList().forEach(dalek -> dalek.moveTowards(doctor.getPosition()));
        worldMap.checkDaleksCollisions(getDalekList(), getDoctor());
        increaseScore(1);
    }

    private void increaseScore(int i){
        if(doctor.isAlive())
            this.score += i;
    }



//    public static Vector2D parseToVector2D(int num, Vector2D position){
//        int x = position.getX();
//        int y = position.getY();
//
//        switch (num) {
//            case 1 -> {
//                x += -1;
//                y += 1;
//            }
//            case 2 -> {
//                x += 0;
//                y += 1;
//            }
//            case 3 -> {
//                x += 1;
//                y += 1;
//            }
//            case 6 -> {
//                x += 1;
//                y += 0;
//            }
//            case 9 -> {
//                x += 1;
//                y += -1;
//            }
//            case 8 -> {
//                x += 0;
//                y += -1;
//            }
//            case 7 -> {
//                x += -1;
//                y += -1;
//            }
//            case 4 -> {
//                x += -1;
//                y += 0;
//            }
//            default -> {
//                x += 0;
//                y += 0;
//            }
//        }
//
//        return new Vector2D(x,y);
//    }
}
