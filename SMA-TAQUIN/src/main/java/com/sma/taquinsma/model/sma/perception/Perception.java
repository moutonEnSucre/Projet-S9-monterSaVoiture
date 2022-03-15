package com.sma.taquinsma.model.sma.perception;

import com.sma.taquinsma.model.sma.agent.Agent;
import com.sma.taquinsma.model.sma.world.World;
import com.sma.taquinsma.model.utils.Position;

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
