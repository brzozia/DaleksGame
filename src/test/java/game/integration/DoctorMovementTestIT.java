package game.integration;

import com.google.inject.Guice;
import game.World;
import game.WorldMap;
import game.entity.Dalek;
import game.entity.Doctor;
import guice.AppModule;
import mainApp.MainApp;
import model.Vector2D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class DoctorMovementTestIT {

    private static World world;
    private static WorldMap worldMap;
    Doctor doctor;

    @BeforeAll
    static void init() {
        world = Guice.createInjector(new AppModule()).getInstance(World.class);
        worldMap = world.getWorldMap();
    }

    @BeforeEach
    void setup() {
        world.initializeWorld(0);
        doctor = world.getDoctor();
    }

    @Test
    public void testDownLeftBoundaries() {
        //given
        doctor.move(new Vector2D(0,0));
        worldMap.positionChange(doctor);

        //when
        world.makeMove(1);
        world.makeMove(9);
        world.makeMove(8);
        world.makeMove(7);
        world.makeMove(4);

        //then
        assertEquals(new Vector2D(0,0), doctor.getPosition());
        assertEquals(doctor, worldMap.objectAt(doctor.getPosition()).get());
    }

    @Test
    public void testTopRightBoundaries() {
        //given
        doctor.move(new Vector2D(MainApp.WIDTH-1,MainApp.HEIGHT-1));
        worldMap.positionChange(doctor);

        //when
        world.makeMove(1);
        world.makeMove(2);
        world.makeMove(3);
        world.makeMove(6);
        world.makeMove(9);

        //then
        assertEquals(new Vector2D(MainApp.WIDTH-1,MainApp.HEIGHT-1), doctor.getPosition());
        assertEquals(doctor, worldMap.objectAt(doctor.getPosition()).get());
    }

    @Test
    public void testDaleksFollowingDoctor() { //TODO: split test to more cases for easier separation?
        //given
        Dalek dalekLeft = new Dalek(new Vector2D(2,2));
        Dalek dalekRight = new Dalek(new Vector2D(8,2));
        Dalek dalekBottom = new Dalek(new Vector2D(4,9));
        Dalek dalekTop = new Dalek(new Vector2D(4,0));
        List<Dalek> daleks = Arrays.asList(dalekLeft,dalekRight, dalekTop, dalekBottom);
        world.getDalekList().addAll(daleks);
        daleks.forEach(worldMap::addEntity);

        doctor.move(new Vector2D(5,2));
        worldMap.positionChange(doctor);

        //when
        world.makeMove(4); // x-=1, y+=0

        //then
        assertEquals(4, world.getDalekList().size());
        assertFalse(world.isGameOver());
        assertEquals(new Vector2D(3,2), dalekLeft.getPosition());
        assertEquals(new Vector2D(7,2), dalekRight.getPosition());
        assertEquals(new Vector2D(4,8), dalekBottom.getPosition());
        assertEquals(new Vector2D(4,1), dalekTop.getPosition());
    }
}
