package sma.behavior;

import sma.agent.Agent;
import sma.perception.Perception;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class NeedToMove implements Behavior {
    private final Perception perception;
    private final int agentIdWhoContactMe;
    private boolean isDone = false;

    public NeedToMove(Perception perception, int agentIdWhoContactMe) {
        this.agentIdWhoContactMe = agentIdWhoContactMe;
        this.perception = perception;
    }

    @Override
    public String toString() {
        return "NeedToMove: " + perception.currentPos.toString();
    }

    @Override
    public boolean done() {
        return isDone;
    }

    @Override
    public void action() {
        List<Position> validPositions = getValidPosition();
        if(!validPositions.isEmpty()) {
            Position nextPos = validPositions.get(0);
            perception.world.moveAgent(perception.parent, nextPos);
            isDone = true;
        }
        else {
            contactValidNeighborToMove();
        }
    }

    private void contactValidNeighborToMove() {
        List<Position> offset = new ArrayList<>() {
            {
                add(new Position(-1, 0));
                add(new Position(1, 0));
                add(new Position(0, -1));
                add(new Position(0, 1));
            }
        };

        Position currentPos = perception.currentPos;

        for(Position o : offset) {
            Position testPos = new Position(currentPos.x + o.x, currentPos.y + o.y);
            if(testPos.x >= 0 && testPos.x < perception.world.size && testPos.y >= 0 && testPos.y < perception.world.size) {
                Agent agentCaseTested = perception.world.getAgent(testPos);
                if(agentCaseTested != null && agentCaseTested.id != agentIdWhoContactMe) {
                    perception.parent.sendMessage(agentCaseTested, "MOVE");
                }
            }
        }
    }

    private List<Position> getValidPosition() {
        List<Position> validPosition = new ArrayList<>();

        List<Position> offset = new ArrayList<>() {
            {
                add(new Position(-1, 0));
                add(new Position(1, 0));
                add(new Position(0, -1));
                add(new Position(0, 1));
            }
        };

        Position currentPos = perception.currentPos;

        for(Position o : offset) {
            Position testPos = new Position(currentPos.x + o.x, currentPos.y + o.y);
            if(testPos.x >= 0 && testPos.x < perception.world.size && testPos.y >= 0 && testPos.y < perception.world.size) {
                Agent agentCaseTested = perception.world.getAgent(testPos);
                if(agentCaseTested == null) {
                    validPosition.add(testPos);
                }
            }
        }
        return validPosition;
    }
}
