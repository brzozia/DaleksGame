package command;

import game.World;
import game.utils.Direction;

public class MoveCommand extends Command {
    private final Direction direction;

    public MoveCommand(World world, Direction dir) {
        super(world);
        this.direction = dir;
    }

    @Override
    public void execute() {
        world.makeMove(direction);
    }
}
