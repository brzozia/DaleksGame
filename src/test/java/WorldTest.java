import game.World;
import game.WorldMap;
import game.entity.Dalek;
import game.entity.Doctor;
import model.Vector2D;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WorldTest {

    World world;
    WorldMap worldMap;
    Doctor doctor;

    @BeforeEach
    public void setUp() {
        world = new World(10,10, 0);
        worldMap = world.getWorldMap();
        doctor = world.getDoctor();
    }

    @Test
    public void testBoundaries() {
        setUp();
        doctor.move(new Vector2D(0,0));
        worldMap.positionChanged(doctor, doctor.getPrevPosition(), doctor.getPosition());
        world.makeMove(1);
        world.makeMove(9);
        world.makeMove(8);
        world.makeMove(7);
        world.makeMove(4);

        assertEquals(new Vector2D(0,0), doctor.getPosition());

        doctor.move(new Vector2D(9,9));
        worldMap.positionChanged(doctor, doctor.getPrevPosition(), doctor.getPosition());
        world.makeMove(1);
        world.makeMove(2);
        world.makeMove(3);
        world.makeMove(6);
        world.makeMove(9);

        assertEquals(new Vector2D(9,9), doctor.getPosition());
    }

    @Test
    public void testMapAfterReset() {
        //TODO implement
        assertFalse(true);
    }
}
