package sma.agent;

import sma.behavior.Behavior;
import sma.perception.Perception;

import java.util.Stack;

public abstract class Agent {
    private static int last_id = 0;
    public final int id;
    protected Perception perception;

    protected Stack<Behavior> behaviorList = new Stack<>();

    public Agent() {
        this.id = last_id++;
    }

    public void onInit(Perception perception) {
        System.out.println("Agent " + id + " initialisé.");
        this.perception = perception;
    }

    public void update() {
        if(!behaviorList.isEmpty()){
            onBeforeMove();
            behaviorList.peek().action();
            if(behaviorList.peek().done()) {
                behaviorList.pop();
            }
            onAfterMove();
        }
    }

    protected abstract void onBeforeMove();
    protected abstract void onAfterMove();
    protected abstract void onRemove();
}
