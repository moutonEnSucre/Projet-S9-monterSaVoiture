package sma.perception;

import sma.agent.Agent;
import sma.world.World;
import utils.Position;

//Cette place permet à un agent d'avoir conscience du monde dans lequel il se trouve
//ainsi que la notion de position
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
