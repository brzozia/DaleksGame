package command;

import game.World;
import game.entity.Dalek;
import game.entity.Doctor;
import game.entity.MapObject;
import model.Vector2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Command {

    protected final World world;
    protected final Vector2D prevDoctorPosition;
    protected final Vector2D doctorPosition;
    protected List<Vector2D> aliveDaleksPosition;
    protected List<Vector2D> deadDaleksPosition;

    public Command(World world) {
        this.world = world;
        prevDoctorPosition = world.getDoctor().getPrevPosition();
        doctorPosition = world.getDoctor().getPosition();
        aliveDaleksPosition = world.getWorldMap().getVectorsOfAlive().stream()
                .filter(v -> v != doctorPosition).collect(Collectors.toList());
        deadDaleksPosition = new ArrayList<>(world.getWorldMap().getVectorsOfDead());
    }

    public abstract boolean execute();

    public void undo() {
        List<Dalek> dalekList = new LinkedList<>();
        aliveDaleksPosition.forEach(pos -> {
            Dalek dalek = new Dalek(pos);
            dalek.setAlive(true);
            dalekList.add(dalek);
        });
        deadDaleksPosition.forEach(pos -> {
            Dalek dalek = new Dalek(pos);
            dalek.setAlive(false);
            dalekList.add(dalek);
        });

        //undo doctor
        Doctor doctor = world.getDoctor();
        doctor.setPrevPosition(prevDoctorPosition);
        doctor.setPosition(doctorPosition);

        //undo world
        world.getDalekList().clear();
        world.getDalekList().addAll(dalekList);

        //undo worldMap
        List<MapObject> mapObjectList = new LinkedList<>(dalekList);
        mapObjectList.add(doctor);
        world.getWorldMap().clearAllEntities();
        world.getWorldMap().addMapObjectsFromList(mapObjectList);
    }
}