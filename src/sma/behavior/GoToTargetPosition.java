package sma.behavior;

import sma.perception.Perception;

public class GoToTargetPosition implements Behavior {
    private Perception perception;

    public GoToTargetPosition(Perception perception) {
        this.perception = perception;
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public void action() {

    }
}
