package sma.agent;

import sma.behavior.Behavior;

import java.util.Stack;

public abstract class Agent {
    private static int last_id = 0;
    public final int id;

    protected Stack<Behavior> behaviorList = new Stack<>();

    public Agent() {
        this.id = last_id++;
    }

    public void onInit() {
        System.out.println("Agent " + id + " initialisé.");
    }

    public void Update() {
        onBeforeMove();
        behaviorList.peek().action();
        if(behaviorList.peek().done()) {
            behaviorList.pop();
        }
        onAfterMove();
    }

    protected abstract void onBeforeMove();
    protected abstract void onAfterMove();
    protected abstract void onRemove();
}
