package integration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import model.World;
import model.WorldMap;
import model.entity.Dalek;
import model.entity.Doctor;
import model.utils.Direction;
import guice.AppModule;
import model.utils.Vector2D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTestIT {

    private static World world;
    private static WorldMap worldMap;
    Doctor doctor;
    Dalek dalek;

    private Dalek createPlaceDalek(Vector2D position) {
        Dalek dal = new Dalek(position);
        world.getDalekList().add(dal);
        worldMap.addEntity(dal);
        return dal;
    }

    private void moveDoctor(Vector2D position){
        doctor.move(position);
        world.getWorldMap().removeAlivePosition(doctor.getPrevPosition());
        world.getWorldMap().positionChange(doctor);
    }

    @BeforeAll
    static void init() {
        final Injector injector = Guice.createInjector(new AppModule());
        world = injector.getInstance(World.class);
        worldMap = world.getWorldMap();
    }

    @BeforeEach
    void setup() {
        world.initializeWorld(0);
        doctor = world.getDoctor();
    }

    @Test
    public void doctorIntoDalekCollisionTest() {
        //given
        moveDoctor(new Vector2D(2,2));
        dalek = createPlaceDalek(new Vector2D(2,3));

        //when
        world.makeMove(Direction.SOUTH);

        //then
        assertTrue(world.isGameOver());
        assertEquals(world.getDalekList().size(), 1);
        assertEquals(doctor.getPosition(), dalek.getPosition());
    }

    @Test
    public void doctorIntoDeadDalekCollisionTest() {
        //given
        moveDoctor(new Vector2D(2,2));
        dalek = createPlaceDalek(new Vector2D(2,3));
        Dalek dalek2 = createPlaceDalek(new Vector2D(3,3));
        world.makeMove(Direction.NORTHEAST);

        //when
        world.makeMove(Direction.SOUTH);

        //then
        assertTrue(world.isGameOver());
        assertFalse(doctor.isAlive());
        assertEquals(world.getDalekList().size(), 2);
        assertEquals(doctor.getPosition(), dalek.getPosition());
        assertEquals(dalek2.getPosition(), dalek.getPosition());
    }

    @Test
    public void doctorAndDalekToNewTileCollisionTest() {
        //given
        moveDoctor(new Vector2D(2, 1));
        dalek = createPlaceDalek(new Vector2D(2,3));

        //then
        world.makeMove(Direction.SOUTH);

        //then
        assertTrue(world.isGameOver());
        assertEquals(world.getDalekList().size(), 1);
        assertEquals(doctor.getPosition(), dalek.getPosition());
    }

    @Test
    public void twoDalekCollisionTest() {
        //given
        moveDoctor(new Vector2D(3,4));
        dalek = createPlaceDalek(new Vector2D(2,3));
        Dalek dalek2 = createPlaceDalek(new Vector2D(3,3));

        //when
        world.makeMove(Direction.SOUTH); // y+=1, x+=0

        //then
        assertFalse(world.isGameOver());
        assertEquals(2, world.getDalekList().size());
        assertEquals(dalek.getPosition(), dalek2.getPosition());
        assertNotEquals(dalek.getPosition(), doctor.getPosition());
    }

    @Test
    public void dalekAndDeadDalekCollisionTest(){
        //given

        moveDoctor(new Vector2D(3,4));
        dalek = createPlaceDalek(new Vector2D(2,3));
        Dalek dalek2 = createPlaceDalek(new Vector2D(3,3));
        Dalek dalek3 = createPlaceDalek(new Vector2D(3,2));
        world.makeMove(Direction.SOUTH);

        //when
        world.makeMove(Direction.SOUTH);

        //then
        assertFalse(world.isGameOver());
        assertFalse(dalek2.isAlive());
        assertFalse(dalek3.isAlive());
        assertEquals(3, world.getDalekList().size());
        assertEquals(dalek.getPosition(), dalek2.getPosition());
        assertEquals(dalek2.getPosition(), dalek3.getPosition());
    }

    @Test
    public void twoDaleksGoSameDirectionNotCollidingTest() {
        //given
        moveDoctor(new Vector2D(7,3));
        dalek = createPlaceDalek(new Vector2D(2,3));
        Dalek dalek2 = createPlaceDalek(new Vector2D(3,3));

        ///when
        world.makeMove(Direction.EAST); // y+=0, x+1

        //then
        assertEquals(2, world.getDalekList().size());
        assertEquals(new Vector2D(3,3), dalek.getPosition());
        assertEquals(new Vector2D(4,3), dalek2.getPosition());
        assertTrue(dalek.isAlive());
        assertTrue(dalek2.isAlive());
        assertNotEquals(dalek.getPosition(), dalek2.getPosition());
    }
}
