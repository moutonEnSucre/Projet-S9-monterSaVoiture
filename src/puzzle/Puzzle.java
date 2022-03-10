package puzzle;

import astar.Astar;
import astar.Graph;
import utils.Position;
import java.util.*;

public class Puzzle {

    private final Agent[][] puzzle;
    private List<Agent> agents = new ArrayList<>();
    private final List<Position> positions = new ArrayList<>();

    public Puzzle(int nbAgent, int tab_size) {
        this.puzzle = new Agent[tab_size][tab_size];
        for(int i=0; i < puzzle.length; i++){
            for(int j=0; j < puzzle[i].length; j++){
                positions.add(new Position(i, j));
            }
        }
        for(int i = 0; i < nbAgent; i++) {
            setCase(new Agent(), tab_size);
        }
    }

    private void setCase(Agent agent, int size) {
        int x = new Random().nextInt(size);
        int y = new Random().nextInt(size);
        while(puzzle[x][y] != null) {
            x = new Random().nextInt(size);
            y = new Random().nextInt(size);
        }
        agent.targetPos = positions.get(agent.id);
        agent.currentPosition = new Position(x, y);
        puzzle[x][y] = agent;
        agents.add(agent);
    }

    public Agent getAgentWithSmallestIdAndWithBadCurrentPosition() {
            for(Agent agent : agents){
                if(!agent.rightPosition())
                    return agent;
            }
            return null;
    }

    public void goToRightPosition(){
        Agent agent = getAgentWithSmallestIdAndWithBadCurrentPosition();

        HashSet<Position> agentListWithoutCurrentAgent = new HashSet<>();
        HashSet<Position> agentWithGoodPlaceListWithoutCurrentAgent = new HashSet<>();

        for(Agent notCurrentAgent : agents) {
            if(notCurrentAgent != agent) {
                agentListWithoutCurrentAgent.add(notCurrentAgent.currentPosition);
                if(notCurrentAgent.rightPosition()) {
                    agentWithGoodPlaceListWithoutCurrentAgent.add(notCurrentAgent.currentPosition);
                }
            }
        }

        Graph graphWithAgent = new Graph(new Position(puzzle.length, puzzle.length), "WithAgent", agentListWithoutCurrentAgent);
        Graph graphWithoutAgent = new Graph(new Position(puzzle.length, puzzle.length), "WithGoodPlaceAgent", agentWithGoodPlaceListWithoutCurrentAgent);

        Astar.getInstance().addGraph(graphWithAgent);
        Astar.getInstance().addGraph(graphWithoutAgent);

        List<Position> path = graphWithAgent.getPath(agent.currentPosition, agent.targetPos);
        if(path != null) {
            for(Position pos : path) {
                System.out.println(pos);
            }
            if(!path.isEmpty()) {
                Position nextPos = path.get(path.size() - 1);
                moveAgent(agent, nextPos);
            }
        }
        else {
            path = graphWithoutAgent.getPath(agent.currentPosition, agent.targetPos);
            for(Agent a : agents){
                for (Position p : path){
                    if (a.currentPosition.equals(p)){
                       System.out.println("Il faut bouger :) => " + a.id);
                       Stack<Agent> moving_agents = new Stack<>();
                       moving_agents.push(a);

                       List<Position> validPositions = getValidPosition(a, path);
                       System.out.println(validPositions);

                       if(validPositions.isEmpty()) {
                            Agent agentToMove = getAgentToMove(a, path);
                            if(agentToMove != null) {
                                System.out.println("Agent to move: " + agentToMove.id);
                            }
                       }
                    }
                }
            }
        }
    }

    private Agent getAgentToMove(Agent agent, List<Position> path){
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
                Agent caseTested = puzzle[testPos.x][testPos.y];
                if(caseTested != null && !caseTested.rightPosition()) {
                    return caseTested;
                }
            }
        }
        return null;
    }

    //Get valid position
    private List<Position> getValidPosition(Agent agent, List<Position> path) {
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
                Agent caseTested = puzzle[testPos.x][testPos.y];
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

    private void moveAgent(Agent agentToMove, Position nextPosition){
        puzzle[agentToMove.currentPosition.x][agentToMove.currentPosition.y] = null;
        agentToMove.currentPosition = nextPosition;
        puzzle[agentToMove.currentPosition.x][agentToMove.currentPosition.y] = agentToMove;
    }


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
