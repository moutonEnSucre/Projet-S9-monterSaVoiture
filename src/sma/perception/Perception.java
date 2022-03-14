package sma.perception;

import sma.world.World;
import utils.Position;

public class Perception {
    public World world;
    public Position currentPos;
    public Position targetPos;

    public Perception(World world, Position currentPos, Position targetPos) {
        this.world = world;
        this.currentPos = currentPos;
        this.targetPos = targetPos;
    }

    public boolean isAtRightPlace(){
        return currentPos.equals(targetPos);
    }
}
