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

    public void initializeWorld(int dalekNumber) { // could be private but we use it on tests
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

    public boolean isGameOver() {
        return !doctor.isAlive();
    }
    public boolean hasWon(){
        return worldMap.aliveDaleks() == 0;
    }

//    public List<PowerUp> getPowerUpsList() {
//        return powerUpsList;
//    }

    //actions
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
        //TODO in reference app having TPs made safe teleport, else you could tp to enemies. Do we make it the same?
        if(getDoctor().teleport(worldMap.getRandomVector(false))) {
            System.out.println("Teleportation!");
            onWorldAction();
        }
        else {
            System.out.println("You've ran out of teleportations!");
        }
    }

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

    public void useBomb() {
        if(getDoctor().useBomb()) {
            System.out.println("Bombard");
            List<Vector2D> vectorsAround = Vector2D.getPositionsAround(getDoctor().getPosition());
            destroyAfterBomb(vectorsAround);
            onWorldAction();  // can be here also Move(0) (but now doctor's positions are change in Doctor class in useBomb())
        }
        else {
            System.out.println("You've ran out of bombs!");
        }
    }

    private void destroyAfterBomb(List<Vector2D> positionsToDestroy) {
        positionsToDestroy.stream()
                .filter(worldMap::isInMapBounds)
                .filter(worldMap::isOccupied)
                .forEach(position -> {
                    if(worldMap.isOccupied(position)) {
                        MapObject obj = worldMap.objectAt(position).get();

                        worldMap.makeDeadPosition(obj);
                        obj.setAlive(false);

                    }
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

    //collisions
    private void onWorldAction(){
        checkDoctorCollision();
        getDalekList().forEach(dalek -> dalek.moveTowards( doctor.getPosition()) );
        checkDaleksCollisions();
        increaseScore(1);
    }

    private void checkDoctorCollision() {

        if(doctor.getPosition().equals(doctor.getPrevPosition())) {
            return;
        }
        if (worldMap.isOccupied(getDoctor().getPosition())) {
            MapObject obj = worldMap.objectAt(doctor.getPosition()).get();

            obj.setAlive(false);   // when we will have GAME OVER screen we won't need so many instructions here
            doctor.setAlive(false);
            worldMap.makeDeadPosition(obj);
            worldMap.removeAlivePosition(doctor.getPrevPosition());
            System.out.println("Doctor's Collision detected! - E N D   G A M E");

        } else {
            worldMap.changeDoctorPosition(doctor, doctor.getPrevPosition());
        }

    }

    private void checkDaleksCollisions() {
        worldMap.prepareMapForCheckingCollisions(doctor);

        getDalekList()
            .stream().filter(Dalek::isAlive)
            .forEach(dalek -> {
                if(worldMap.isOccupied(dalek.getPosition())) {
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
