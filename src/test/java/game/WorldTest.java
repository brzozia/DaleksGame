package game;

import com.google.inject.Guice;
import com.google.inject.Injector;
import game.entity.Doctor;
import guice.AppModule;
import mainApp.MainApp;
import model.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WorldTest {

    World world;
    WorldMap worldMap;
    Doctor doctor;

    @BeforeEach
    public void init() {
        final Injector injector = Guice.createInjector(new AppModule());
        world = injector.getInstance(World.class);
        world.initializeWorld(0);
        worldMap = world.getWorldMap();
        doctor = world.getDoctor();
    }

    @Test
    public void testBoundaries() {
        doctor.move(new Vector2D(0,0));
        worldMap.positionChange(doctor);
        world.makeMove(1);
        world.makeMove(9);
        world.makeMove(8);
        world.makeMove(7);
        world.makeMove(4);

        assertEquals(new Vector2D(0,0), doctor.getPosition());

        doctor.move(new Vector2D(MainApp.WIDTH-1,MainApp.HEIGHT-1));
        worldMap.positionChange(doctor);
        world.makeMove(1);
        world.makeMove(2);
        world.makeMove(3);
        world.makeMove(6);
        world.makeMove(9);

        assertEquals(new Vector2D(MainApp.WIDTH-1,MainApp.HEIGHT-1), doctor.getPosition());
    }
}
