package sma.behavior;

import astar.Graph;
import sma.agent.Agent;
import sma.agent.PieceAgent;
import sma.perception.Perception;
import utils.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class GoToTargetPosition implements Behavior {
    private final Perception perception;

    public GoToTargetPosition(Perception perception) {
        this.perception = perception;
    }

    @Override
    public boolean done() {
        if(perception.isAtRightPlace()) {
            Agent next = getAgentWithSmallestIdAndWithBadCurrentPosition();
            if(next != null) {
                perception.parent.sendMessage(next, "GOTO");
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "GoToTargetPosition: " + perception.targetPos.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof GoToTargetPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(perception);
    }

    @Override
    public void action() {
        HashSet<Position> agents = new HashSet<>();
        HashSet<Position> agentsWithGoodPlace = new HashSet<>();
        int size = perception.world.size;

        for(Agent agent : perception.world.getAgents()) {
            agents.add(agent.perception.currentPos);

            if(agent.perception.isAtRightPlace()) {
                agentsWithGoodPlace.add(agent.perception.currentPos);
            }
        }

        //Construction des graphs
        Graph graphWithAgent = new Graph(size, agents);
        Graph graphWithFixedAgent = new Graph(size, agentsWithGoodPlace);
        Graph emptyGraph = new Graph(size, new HashSet<>());

        //Chemin avec agent
        List<Position> path = graphWithFixedAgent.getPath(perception.currentPos, perception.targetPos);
        if(path != null && !path.isEmpty()) {
            Position nextPos = path.get(path.size() - 1);

            Agent checkCaseAgent = perception.world.getAgent(nextPos);
            if(checkCaseAgent == null) {
                perception.world.moveAgent(perception.parent, nextPos);
            }
            else {
                perception.parent.sendMessage(checkCaseAgent, "MOVE");
            }
        }
    }

    public Agent getAgentWithSmallestIdAndWithBadCurrentPosition() {
        for(Agent agent : perception.world.getAgents()){
            if(!agent.perception.isAtRightPlace())
                return agent;
        }
        return null;
    }
}
