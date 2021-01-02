package command;

import game.World;

public class BombCommand extends Command {

    public BombCommand(World world) {
        super(world);
    }

    @Override
    public boolean execute() {
        return world.useBomb();
    }

    @Override
    public void undo() {
        super.undo();
        int oldB = world.getDoctor().getBombs().get();
        world.getDoctor().setBombs(oldB + 1);
    }
}
