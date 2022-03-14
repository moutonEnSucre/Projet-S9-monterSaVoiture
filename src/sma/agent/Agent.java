package sma.agent;

import sma.behavior.Behavior;
import sma.behavior.GoToTargetPosition;
import sma.behavior.NeedToMove;
import sma.perception.Perception;
import sma.world.World;
import utils.Position;

import java.util.Objects;
import java.util.Stack;

public abstract class Agent {
    private static int last_id = 0;
    public final int id;
    public Perception perception;

    protected Stack<Behavior> behaviorList = new Stack<>();

    public Agent() {
        this.id = last_id++;
    }

    public void sendMessage(Agent agentToContact, String message) {
        agentToContact.onMessageReceived(this, message);
    }

    public void onMessageReceived(Agent agentWhoContactMe, String message) {
        System.out.println("Message: " + message + " received, id: " + id + " from: " + agentWhoContactMe.id);
        if(message.equals("MOVE")) {
            addBehavior(new NeedToMove(perception, agentWhoContactMe));
        }
        else if(message.equals("GOTO")) {
            addBehavior(new GoToTargetPosition(perception));
        }
    }

    public void onInit(Perception perception) {
        System.out.println("Agent " + id + " initialisé.");
        this.perception = perception;
    }

    public void addBehavior(Behavior behavior) {
        behaviorList.remove(behavior);
        behaviorList.add(behavior);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return id == agent.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
