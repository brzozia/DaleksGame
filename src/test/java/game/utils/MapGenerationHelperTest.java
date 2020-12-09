package game.utils;

import game.WorldMap;
import game.entity.Dalek;
import game.entity.Doctor;
import game.utils.MapGenerationHelper;
import mainApp.MainApp;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MapGenerationHelperTest {

//    cannot use Random in tests?
    @Test
    public void checkCreatingDoctor() {
        //given
        WorldMap worldMap = new WorldMap();

        //when
        Doctor generatedDoctor = MapGenerationHelper.randomPlaceDoctor(worldMap);

        //then
        assertEquals(0, generatedDoctor.getBombs());
        assertEquals(3, generatedDoctor.getTeleports());
        assertTrue(generatedDoctor.getPosition().getX() < MainApp.WIDTH);
        assertTrue(generatedDoctor.getPosition().getY() < MainApp.HEIGHT);
        assertTrue(worldMap.getPositionsOfAlive().containsKey(generatedDoctor));
    }


//    cannot use Random in tests?
    @Test
    public void checkCreatingDaleks() {
        //given
        WorldMap worldMap = new WorldMap();
        int numberOfDaleksToCreate = 10;

        //when
        List<Dalek> generatedDaleks = MapGenerationHelper.randomPlaceDalek(worldMap, numberOfDaleksToCreate);

        //then
        assertEquals(numberOfDaleksToCreate, generatedDaleks.size());
        assertEquals(numberOfDaleksToCreate, worldMap.getPositionsOfAlive().size());
        assertTrue(worldMap.getPositionsOfAlive().get(0).getPosition().getX() < MainApp.WIDTH);
        assertTrue(worldMap.getPositionsOfAlive().get(0).getPosition().getY() < MainApp.HEIGHT);
    }
}