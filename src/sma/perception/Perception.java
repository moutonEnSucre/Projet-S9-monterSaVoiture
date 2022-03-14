package sma.perception;

import sma.agent.Agent;
import sma.world.World;
import utils.Position;

public class Perception {
    public Agent parent;
    public World world;
    public Position currentPos;
    public Position targetPos;

    public Perception(World world, Agent parent, Position currentPos, Position targetPos) {
        this.world = world;
        this.parent = parent;
        this.currentPos = currentPos;
        this.targetPos = targetPos;
    }

    public boolean isAtRightPlace() {
        return currentPos.equals(targetPos);
    }
}
