package sma.behavior;

import utils.Position;

public class GoToTargetPosition implements Behavior {
    public Position currentPos;
    public Position targetPos;

    public void setup(Position currentPos, Position targetPos) {
        this.currentPos = currentPos;
        this.targetPos = targetPos;
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public void action() {

    }
}
