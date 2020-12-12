package game.utils;

import game.WorldMap;
import game.entity.Dalek;
import game.entity.Doctor;
import model.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerationHelper {
    private final static Random random = new Random();

    public static Doctor randomPlaceDoctor(WorldMap worldMap) {
        Vector2D doctorPosition = worldMap.getRandomVector(true);
        Doctor doctor = new Doctor(doctorPosition,  2, 3);
        worldMap.addEntity(doctor);
        return doctor;
    }

    public static List<Dalek> randomPlaceDaleks(WorldMap worldMap, int daleksToCreate){
        List<Dalek> dalekList = new ArrayList<>();

        for(int i = 0; i < daleksToCreate; i++) {
//            int x = random.nextInt(worldMap.getWidth());
//            int y = random.nextInt(worldMap.getHeight());
//            Vector2D dalekPosition = new Vector2D(x,y);
//            while(worldMap.isOccupied(dalekPosition)) {
//                x = random.nextInt(worldMap.getWidth());
//                y = random.nextInt(worldMap.getHeight());
//                dalekPosition = new Vector2D(x,y);
//            }
            Vector2D dalekPosition = worldMap.getRandomVector(true);
            Dalek dalek = new Dalek(dalekPosition);
            worldMap.addEntity(dalek);
            dalekList.add(dalek);
        }

        return dalekList;
    }

    public static void clearDaleksFromWorldAndList(WorldMap worldMap, List<Dalek> dalekList) {
        if(dalekList != null && !dalekList.isEmpty()) {
            dalekList.clear();
        }
        worldMap.clearAllEntities();
    }

}
