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
    private int score;

    @Inject
    public World(WorldMap worldMap, @Named("DalekNumber") int dalekNumber) {
        this.worldMap = worldMap;
        this.initializeWorld(dalekNumber);
    }

    public void initializeWorld(int dalekNumber) {
        MapGenerationHelper.clearDaleksFromWorldAndList(worldMap, dalekList);

        doctor = MapGenerationHelper.randomPlaceDoctor(worldMap);
        dalekList = MapGenerationHelper.randomPlaceDalek(worldMap, dalekNumber);
        score = 0;
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

    public void useBomb() {
        if(getDoctor().useBomb()) {
            System.out.println("Bombard");
            List<Vector2D> vectorsAround = Vector2D.getPositionsAround(getDoctor().getPosition());
            destroyAfterBomb(vectorsAround);
        }
    }

    private void destroyAfterBomb(List<Vector2D> positionsToDestroy) {
        positionsToDestroy.stream()
                .filter(worldMap::isInMap)
                .filter(worldMap::isOccupied)
                .forEach(position -> {
                    getDalekList()
                        .stream().filter(Dalek::isAlive)
                        .forEach(dalek -> {
                            if(dalek.getPosition().equals(position)) {
                                Dalek obj = (Dalek) worldMap.objectAt(position).get();

                                worldMap.makeDeadPosition(dalek);
                                dalek.setAlive(false);
                                obj.setAlive(false);
                            }
                    });
                });
    }

    private void checkCollisionsAndMoveDaleks(){
        checkDoctorCollision();
        getDalekList().forEach(dalek -> dalek.move( getDoctor().getPosition()) );
        checkDaleksCollisions();
        increaseScore(1);
    }

    private void checkDoctorCollision() {
        if(worldMap.isOccupied(getDoctor().getPosition())){
            MapObject obj = worldMap.objectAt(doctor.getPosition()).get();

            obj.setAlive(false);   // when we will have GAME OVER screen we won't need so many instructions here
            doctor.setAlive(false);
            worldMap.makeDeadPosition(obj);
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

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public boolean isGameOver() {
        return !doctor.isAlive();
    }

    private void increaseScore(int i){
        if(doctor.isAlive())
            this.score += i;
    }

    public int getScore(){
        return score;
    }


    public boolean hasWon(){
        return worldMap.aliveDaleks() == 0;
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
