package game;

import game.entity.Dalek;
import game.entity.Doctor;
import model.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WorldMapTest {

    private static WorldMap worldMap;
    private static Dalek dalekOne;
    private static Vector2D positionOne;

    @BeforeEach
    public void init() {
        int mapWidth = 10;
        int mapHeight = 10;
        worldMap = new WorldMap(mapWidth, mapHeight);
        positionOne = new Vector2D(2,2);
        dalekOne = new Dalek(positionOne);
        worldMap.addEntity(dalekOne);
    }

    @Test
    public void addTwoDaleksOnProperPlaceTest() {
        //given
        Vector2D positionTwo = new Vector2D(3,3);
        Dalek dalekTwo = new Dalek(positionTwo);
        Map<Vector2D, Dalek> dalekMap = new HashMap<>();
        dalekMap.put(positionOne, dalekOne);
        dalekMap.put(positionTwo, dalekTwo);

        //when
        worldMap.addEntity(dalekTwo);

        //then
        assertEquals(dalekMap, worldMap.getPositionsOfAlive());
    }

    @Test
    public void addTwoDaleksOnOnePlaceTest() {
        //given
        Dalek dalekTwo = new Dalek(positionOne);

        //when

        //then
        assertThrows(RuntimeException.class, () -> worldMap.addEntity(dalekTwo));
    }

    @Test
    public void isOccupiedPlaceTest() {
        //given
        Vector2D emptyPosition = new Vector2D(6,6);

        //when

        //then
        assertTrue(worldMap.isOccupied(positionOne));
        assertFalse(worldMap.isOccupied(emptyPosition));
    }

    @Test
    public void checkIfDalekIsOnPlaceTest() {
        //given

        //when

        //then
        assertTrue(worldMap.objectAt(positionOne).isPresent());
        assertEquals(dalekOne, worldMap.objectAt(positionOne).get());
    }

    @Test
    public void checkIfDoctorIsOnPlaceTest() {
        //given
        Vector2D doctorPosition = new Vector2D(3,3);
        Doctor doctor = new Doctor(doctorPosition, 0, 3);

        //when
        worldMap.addEntity(doctor);

        //then
        assertTrue(worldMap.objectAt(doctorPosition).isPresent());
        assertEquals(doctor, worldMap.objectAt(doctorPosition).get());
    }

    @Test
    public void checkEmptyPositionTest() {
        //given
        Vector2D emptyPosition = new Vector2D(5,5);

        //when

        //then
        assertFalse(worldMap.objectAt(emptyPosition).isPresent());
    }

    @Test
    public void clearAllPositionsTest() {
        //given

        //when

        //then
        assertFalse(worldMap.getPositionsOfAlive().isEmpty());
        worldMap.clearAllPositions();
        assertTrue(worldMap.getPositionsOfAlive().isEmpty());
    }

    @Test
    public void isInMapTest() {
        //given
        Vector2D inMapPosition = new Vector2D(0,0);
        Vector2D notInMapPosition = new Vector2D(10,10);

        //when

        //then
        assertTrue(worldMap.isInMapBounds(inMapPosition));
        assertFalse(worldMap.isInMapBounds(notInMapPosition));
    }

    @Test
    public void randomVectorInMapTest() {
        //given
        Vector2D inMapPositionOne = worldMap.getRandomVector(true);
        Vector2D inMapPositionTwo = worldMap.getRandomVector(false);

        //when

        //then
        assertTrue(worldMap.isInMapBounds(inMapPositionOne));
        assertTrue(worldMap.isInMapBounds(inMapPositionTwo));
    }
}
