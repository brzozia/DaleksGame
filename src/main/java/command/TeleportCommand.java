package command;

import game.World;

public class TeleportCommand extends Command {

    public TeleportCommand(World world) {
        super(world);
    }

    @Override
    public void execute() {
        world.makeTeleport();
    }

    @Override
    public void undo() {
        super.undo();
        int oldTp = this.world.getDoctor().getRewinds().getValue();
        this.world.getDoctor().setRewinds(oldTp + 1);
    }
}
