package command;

import game.World;
import game.entity.Dalek;
import game.entity.Doctor;
import model.Vector2D;

import java.util.LinkedList;
import java.util.List;

public abstract class Command {

    protected final World world;

    protected final Vector2D prevDoctorPos;
    protected final Vector2D doctorPos;
    protected final List<Vector2D> daleksPos;
    protected final List<Vector2D> deadDaleksPos;

    public Command(World world) {
        this.world = world;

        prevDoctorPos = world.getDoctor().getPrevPosition();
        doctorPos = world.getDoctor().getPosition();

        daleksPos = new LinkedList<>();
        daleksPos.addAll(world.getWorldMap().getPositionsOfAlive().keySet());
        daleksPos.remove(doctorPos);

        deadDaleksPos = new LinkedList<>();
        deadDaleksPos.addAll(world.getWorldMap().getPositionsOfDead().keySet());
    }

    public abstract boolean execute();

    public void undo() {
        world.getWorldMap().clearAllEntities();
        world.getDalekList().clear();


        daleksPos.forEach(v -> {
            Dalek dalek = new Dalek(v);
            world.getWorldMap().getPositionsOfAlive().put(v, dalek);
            world.getDalekList().add(dalek);
        });

        deadDaleksPos.forEach(v -> {
            Dalek dalek = new Dalek(v);
            dalek.setAlive(false);
            world.getWorldMap().getPositionsOfDead().put(v, dalek);
            world.getDalekList().add(dalek);
        });

        Doctor doc = world.getDoctor();
        doc.move(prevDoctorPos);
        doc.move(doctorPos);

        world.getWorldMap().getPositionsOfAlive().put(doc.getPosition(), doc);
    }
}

