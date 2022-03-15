package com.sma.taquinsma.model.sma.behavior;

import com.sma.taquinsma.model.astar.Graph;
import com.sma.taquinsma.model.sma.agent.Agent;
import com.sma.taquinsma.model.sma.behavior.Behavior;
import com.sma.taquinsma.model.sma.perception.Perception;
import com.sma.taquinsma.model.utils.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

//Comportement permettant à un agent se déplacer vers sa case cible
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
                //Quand se comportement est fini, on contacte un autre agent pour lui dire de se
                //rendre à sa case cible
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
        //HashSet<Position> agents = new HashSet<>();
        HashSet<Position> agentsWithGoodPlace = new HashSet<>();
        int size = perception.world.size;

        for(Agent agent : perception.world.getAgents()) {
            //agents.add(agent.perception.currentPos);

            if(agent.perception.isAtRightPlace()) {
                agentsWithGoodPlace.add(agent.perception.currentPos);
            }
        }

        //Construction des graphs
        //Graph graphWithAgent = new Graph(size, agents);
        Graph graphWithFixedAgent = new Graph(size, agentsWithGoodPlace);
        //Graph emptyGraph = new Graph(size, new HashSet<>());

        //Chemin d'obtenir le chemin que l'agent va devoir suivre
        List<Position> path = graphWithFixedAgent.getPath(perception.currentPos, perception.targetPos);
        if(path != null && !path.isEmpty()) {
            //Récupère la prochaine case cible
            Position nextPos = path.get(path.size() - 1);

            //On regarde si la case est disponible
            Agent checkCaseAgent = perception.world.getAgent(nextPos);
            if(checkCaseAgent == null) {
                //Si elle est libre on déplace l'agent
                perception.world.moveAgent(perception.parent, nextPos);
            }
            else {
                //Sinon, on envoie un message à l'agent sur la case cible afin de lui demander de bouger
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
