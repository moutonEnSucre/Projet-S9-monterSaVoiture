package sma.behavior;

import astar.Graph;
import sma.agent.Agent;
import sma.agent.PieceAgent;
import sma.perception.Perception;
import utils.Position;

import java.util.HashSet;
import java.util.List;

public class GoToTargetPosition implements Behavior {
    private final Perception perception;

    public GoToTargetPosition(Perception perception) {
        this.perception = perception;
    }

    @Override
    public boolean done() {
        return perception.isAtRightPlace();
        //return false;
    }

    @Override
    public String toString() {
        return "GoToTargetPosition: " + perception.targetPos.toString();
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

        /*else {
            //Chemin avec uniquement les agents terminés
            path = graphWithFixedAgent.getPath(perception.currentPos, perception.targetPos);

            for(int i = path.size() - 1; i > 0; i--) {

            }

            for(PieceAgent a : agents){
                for (Position pathPos : path){
                    if (a.currentPosition.equals(pathPos)){
                        System.out.println("Il faut bouger :) => " + a.id);

                        Stack<PieceAgent> moving_agents = new Stack<>();
                        moving_agents.push(a);

                        List<Position> validPositions = getValidPosition(a, path);

                        if(validPositions.isEmpty()) {
                            PieceAgent agentToMove = getAgentToMove(a, path);
                            if(agentToMove != null) {
                                System.out.println("Agent to move: " + agentToMove.id);
                            }
                        }
                        else {
                            this.moveAgent(a, validPositions.get(0));
                        }

                    }
                }
            }
        }*/

    }
}
