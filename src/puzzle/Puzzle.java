package puzzle;

import astar.Astar;
import astar.Graph;
import astar.Node;
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
            for(Agent notCurrentAgent : agents) {
                if(notCurrentAgent != agent) {
                    agentListWithoutCurrentAgent.add(notCurrentAgent.currentPosition);
                }
            }

            Graph graphWithAgent = new Graph(new Position(puzzle.length, puzzle.length), "WithAgent", agentListWithoutCurrentAgent);

            Astar.getInstance().addGraph(graphWithAgent);


            List<Position> path = graphWithAgent.getPath(agent.currentPosition, agent.targetPos);
            if(path != null) {
                for(Position pos : path) {
                    System.out.println(pos);
                }
            }

           /* Astar aStar = new Astar(rows, cols, initialNode, finalNode);
            List<Position> positionList = new ArrayList<>();
            for(Agent notCurrentAgent : agents) {
                if(notCurrentAgent != agent) {
                    positionList.add(notCurrentAgent.currentPosition);
                }
            }

            int[][] blocksArray = new int[positionList.size()][2];
            for(int i = 0; i < positionList.size(); i++){
                blocksArray[i][0] = positionList.get(i).x;
                blocksArray[i][1] = positionList.get(i).y;
            }
            //aStar.setBlocks(blocksArray);
            List<Node> path = aStar.findPath();
            for (Node node : path) {
                System.out.println(node);
            }*/
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
