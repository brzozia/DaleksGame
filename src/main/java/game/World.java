package game;

import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.PowerUp;
import game.utils.MapGenerationHelper;

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

    public WorldMap getWorldMap() {
        return worldMap;
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

}
