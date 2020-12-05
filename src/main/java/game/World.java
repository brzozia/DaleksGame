package game;

import game.entity.Dalek;
import game.entity.Doctor;
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


    public void makeMove(Vector2D direction) {
        getDoctor().move(direction);
        checkDoctorCollision();
        getDalekList().forEach(dalek -> dalek.move( getDoctor().getPosition()) );
        checkDaleksCollisions();
        System.out.println(getDoctor().getPosition().toString());
        getDalekList().forEach(dalek -> System.out.println(dalek.getPosition().toString()) );

    }

    public void makeTeleport() {
        getDoctor().teleport(worldMap.getRandomVector());
    }

    private void checkDoctorCollision() {
//        world.getDalekList().forEach(dalek -> {
//                if(dalek.getPosition().equals(world.getDoctor().getPosition())) {
//                    //TODO gameover
//                }
//        });

        // we have map of positions in WorldMap - it is enough to check whether new doctor place is Occupied by anything

        if(worldMap.isOccupied(getDoctor().getPosition())){
            //TODO gameover
            System.out.println("Doctor's Collision detected!");
        }
        else{
            // update map of positions in WorldMap
            worldMap.positionChanged(getDoctor(), getDoctor().getPrevPosition(), getDoctor().getPosition());
        }
    }

    private void checkDaleksCollisions() {
        getDalekList()
                .stream().filter(Dalek::isAlive)
                .forEach(dalek -> {
//                if(world.getDoctor().getPosition().equals(dalek.getPosition())) {
//                    //TODO gameOver
//                }

                    if(worldMap.isOccupied(dalek.getPosition())){
                        //TODO gameOver
                        System.out.println("Dalek's Collision detected!");
                    }
                    else{
                        worldMap.positionChanged(dalek, dalek.getPrevPosition(), dalek.getPosition());
                    }

//                world.getDalekList().forEach(otherDalek -> {
//                if(dalek != otherDalek && dalek.getPosition().equals(otherDalek.getPosition())) {
//                    dalek.setAlive(false);
//                }
//            });

                });
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

}
