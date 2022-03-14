package sma.world;

import sma.agent.Agent;
import sma.agent.PieceAgent;
import astar.Graph;
import sma.perception.Perception;
import utils.Position;
import java.util.*;

public class World {

    public final Agent[][] puzzle;
    public final int size;

    private final List<Position> positions = new ArrayList<>();

    public World(int size) {
        this.size = size;
        this.puzzle = new PieceAgent[size][size];

        for(int i=0; i < puzzle.length; i++){
            for(int j=0; j < puzzle[i].length; j++){
                positions.add(new Position(i, j));
            }
        }
    }

    public Position getTargetPositionFromIdAgent(int id) {
        return positions.get(id);
    }

    public Position setCase(PieceAgent agent) {
        int x = new Random().nextInt(size);
        int y = new Random().nextInt(size);
        while(puzzle[x][y] != null) {
            x = new Random().nextInt(size);
            y = new Random().nextInt(size);
        }
        puzzle[x][y] = agent;

        return new Position(x, y);
    }
/*
    public PieceAgent getAgentWithSmallestIdAndWithBadCurrentPosition() {
        for(PieceAgent agent : agents){
            if(!agent.rightPosition())
                return agent;
        }
        return null;
    }

    public void update(){
        //On récupère l'agent qui a le plus petit id
        PieceAgent agent = getAgentWithSmallestIdAndWithBadCurrentPosition();

        HashSet<Position> agentListWithoutCurrentAgent = new HashSet<>();
        HashSet<Position> agentWithGoodPlaceListWithoutCurrentAgent = new HashSet<>();

        for(PieceAgent notCurrentAgent : agents) {
            if(notCurrentAgent != agent) {
                agentListWithoutCurrentAgent.add(notCurrentAgent.currentPosition);
                if(notCurrentAgent.rightPosition()) {
                    agentWithGoodPlaceListWithoutCurrentAgent.add(notCurrentAgent.currentPosition);
                }
            }
        }

        //Construction des graphs
        Graph graphWithAgent = new Graph(new Position(size, size), agentListWithoutCurrentAgent);
        Graph graphWithFixedAgent = new Graph(new Position(size, size), agentWithGoodPlaceListWithoutCurrentAgent);

        //Chemin avec agent
        List<Position> path = graphWithAgent.getPath(agent.currentPosition, agent.targetPos);
        if(path != null) {
            if(!path.isEmpty()) {
                Position nextPos = path.get(path.size() - 1);
                moveAgent(agent, nextPos);
            }
        }
        else {
            //Chemin avec uniquement les agents terminés
            path = graphWithFixedAgent.getPath(agent.currentPosition, agent.targetPos);

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
        }
    }

    private PieceAgent getAgentToMove(PieceAgent agent, List<Position> path){
        List<Position> offset = new ArrayList<>() {
            {
                add(new Position(-1, 0));
                add(new Position(1, 0));
                add(new Position(0, -1));
                add(new Position(0, 1));
            }
        };
        for(Position o : offset) {
            Position testPos = new Position(agent.currentPosition.x + o.x, agent.currentPosition.y + o.y);
            if(testPos.x >= 0 && testPos.x < puzzle.length && testPos.y >= 0 && testPos.y < puzzle.length) {
                PieceAgent caseTested = puzzle[testPos.x][testPos.y];
                if(caseTested != null && !caseTested.rightPosition()) {
                    return caseTested;
                }
            }
        }
        return null;
    }

    //Get valid position
    private List<Position> getValidPosition(PieceAgent agent, List<Position> path) {
        List<Position> validPosition = new ArrayList<>();

        List<Position> offset = new ArrayList<>() {
            {
                add(new Position(-1, 0));
                add(new Position(1, 0));
                add(new Position(0, -1));
                add(new Position(0, 1));
            }
        };

        for(Position o : offset) {
            Position testPos = new Position(agent.currentPosition.x + o.x, agent.currentPosition.y + o.y);
            if(testPos.x >= 0 && testPos.x < puzzle.length && testPos.y >= 0 && testPos.y < puzzle.length) {
                PieceAgent caseTested = puzzle[testPos.x][testPos.y];
                if(caseTested == null && isNotInAgentPath(testPos, path)) {
                    validPosition.add(testPos);
                }
            }
        }
        return validPosition;
    }

    private boolean isNotInAgentPath(Position position, List<Position> path) {
        for(Position pos : path) {
            if(position.equals(pos)) return false;
        }
        return true;
    }

    private void moveAgent(PieceAgent agentToMove, Position nextPosition){
        puzzle[agentToMove.currentPosition.x][agentToMove.currentPosition.y] = null;
        agentToMove.currentPosition = nextPosition;
        puzzle[agentToMove.currentPosition.x][agentToMove.currentPosition.y] = agentToMove;
    }*/


    @Override
    public String toString() {
        String value = "";
        for(int i=0; i < puzzle.length; i++){
            value += "\n";
            for(int j=0; j < puzzle.length; j++){
                if(j == 0)
                    value += "| ";
                if(puzzle[i][j] == null)
                    value += " ";
                else
                    value += puzzle[i][j].id;
                value += " | ";
            }
        }
        return value;
    }
}
