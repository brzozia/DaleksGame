package game.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import game.World;
import game.WorldMap;
import guice.AppModule;
import model.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapObjectTest {

    World world;
    WorldMap worldMap;
    Doctor doctor;
    Dalek dalek;

    @BeforeEach
    public void init() {
        final Injector injector = Guice.createInjector(new AppModule());
        world = injector.getInstance(World.class);
        world.initializeWorld(0);
        worldMap = world.getWorldMap();
        doctor = world.getDoctor();
        dalek = new Dalek(new Vector2D(2,3));
        world.getDalekList().add(dalek);
        worldMap.addEntity(dalek);
    }

    @Test
    public void doctorIntoDalekCollisionTest() {
        //given

        //when
        doctor.move(new Vector2D(2,2));
        worldMap.positionChange(doctor);
        world.makeMove(2);

        //then
        assertTrue(world.isGameOver());
        assertEquals(world.getDalekList().size(), 1);
        assertEquals(doctor.getPosition(), dalek.getPosition());
    }

    @Test
    public void testDoctorAndDalekToNewTileCollision() {
        //given

        //then
        doctor.move(new Vector2D(2, 1));
        worldMap.positionChange(doctor);
        world.makeMove(2);

        //then
        assertTrue(world.isGameOver());
        assertEquals(world.getDalekList().size(), 1);
        assertEquals(doctor.getPosition(), dalek.getPosition());
    }

    @Test
    public void testTwoDalekCollision() {
        //given
        Dalek dalek2 = new Dalek(new Vector2D(3,3));

        //when
        world.getDalekList().add(dalek2);
        worldMap.addEntity(dalek2);
        doctor.move(new Vector2D(3,4));
        worldMap.positionChange(doctor);
        world.makeMove(2); // y+=1, x+=0

        //then
        assertFalse(world.isGameOver());
        assertEquals(2, world.getDalekList().size());
        assertEquals(dalek.getPosition(), dalek2.getPosition());
        assertNotEquals(dalek.getPosition(), doctor.getPosition());
    }

    @Test
    public void testTwoDaleksGoSameDirection() {
        //given
        Dalek dalek2 = new Dalek(new Vector2D(3,3));

        ///when
        world.getDalekList().add(dalek2);
        worldMap.addEntity(dalek2);
        doctor.move(new Vector2D(7,3));
        worldMap.positionChange(doctor);
        world.makeMove(6); // y+=0, x+1

        //then
        assertEquals(2, world.getDalekList().size());
        assertEquals(new Vector2D(3,3), dalek.getPosition());
        assertEquals(new Vector2D(4,3), dalek2.getPosition());
        assertTrue(dalek.isAlive());
        assertTrue(dalek2.isAlive());
        assertNotEquals(dalek.getPosition(), dalek2.getPosition());
    }

    @Test
    public void testDalekMovement() {
        //given
        Dalek dalekRight = new Dalek(new Vector2D(8,2));
        Dalek dalekBottom = new Dalek(new Vector2D(4,9));
        Dalek dalekTop = new Dalek(new Vector2D(4,0));
        List<Dalek> daleks = Arrays.asList(dalekRight, dalekTop, dalekBottom);

        //when
        world.getDalekList().addAll(daleks);
        daleks.forEach(worldMap::addEntity);
        doctor.move(new Vector2D(5,2));
        worldMap.positionChange(doctor);
        world.makeMove(4); // x-=1, y+=0

        //then
        assertEquals(4, world.getDalekList().size());
        assertFalse(world.isGameOver());
        assertEquals(new Vector2D(3,2), dalek.getPosition());
        assertEquals(new Vector2D(7,2), dalekRight.getPosition());
        assertEquals(new Vector2D(4,8), dalekBottom.getPosition());
        assertEquals(new Vector2D(4,1), dalekTop.getPosition());
    }

}
