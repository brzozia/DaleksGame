package controller;

import game.World;
import game.WorldMap;
import game.entity.Dalek;
import mainApp.MainApp;
import model.Vector2D;

public class GameController {
    private final WorldMap worldMap;
    private final World world;

    public GameController() {
        this.world = new World(MainApp.HEIGHT, MainApp.WIDTH, 5);
        worldMap = world.getWorldMap();
    }

    public void makeMove(Vector2D direction) {
        world.getDoctor().move(direction);
        checkDoctorCollision();
        world.getDalekList().forEach(dalek -> dalek.move(direction,world.getDoctor().getPosition()) );
        checkDaleksCollisions();
        System.out.println(world.getDoctor().getPosition().toString());
        world.getDalekList().forEach(dalek -> System.out.println(dalek.getPosition().toString()) );

    }

    public void makeTeleport(Vector2D newPosition) {
        world.getDoctor().teleport(newPosition);
    }

    private void checkDoctorCollision() {
//        world.getDalekList().forEach(dalek -> {
//                if(dalek.getPosition().equals(world.getDoctor().getPosition())) {
//                    //TODO gameover
//                }
//        });

        // we have map of positions in WorldMap - it is enough to check whether new doctor place is Occupied by anything

        if(worldMap.isOccupied(world.getDoctor().getPosition())){
            //TODO gameover
            System.out.println("Doctor's Collision detected!");
        }
        else{
            // update map of positions in WorldMap
            worldMap.positionChanged(world.getDoctor(), world.getDoctor().getPrevPosition(), world.getDoctor().getPosition());
        }
    }

    private void checkDaleksCollisions() {
        world.getDalekList()
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

    public World getWorld() {
        return world;
    }

}
