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
        if(worldMap.isOccupied(getDoctor().getPosition())){
            //TODO gameover
            System.out.println("Doctor's Collision detected! - E N D   G A M E");
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

                    if(obj instanceof Doctor){
                        System.out.println("DALEK ATE THR DOCTOR - E N D   G A M E ");
                        worldMap.positionChanged(dalek, dalek.getPrevPosition(), dalek.getPosition());
                        //TODO gameOver

                    }else{
                        Dalek dalek2 = (Dalek) obj;
                        dalek2.setAlive(false);

                        System.out.println("Dalek's Collision detected!");
                    }

                    worldMap.removePosition(dalek.getPrevPosition());
                    dalek.setAlive(false);

                }
                else{
                    worldMap.positionChanged(dalek, dalek.getPrevPosition(), dalek.getPosition());
                }

            });
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

}
