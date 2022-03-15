package sma.world;

import sma.agent.Agent;
import sma.agent.PieceAgent;
import utils.Position;
import java.util.*;

public class World {

    public Agent[][] puzzle;
    public final int size;

    private final List<Agent> agents = new ArrayList<>();
    private final List<Position> positions = new ArrayList<>();

    public Random random;

    public World(int size) {
        this.size = size;
        //Création du puzzle
        this.puzzle = new PieceAgent[size][size];

        //On récupère toutes les positions du puzzle
        for(int i=0; i < puzzle.length; i++){
            for(int j=0; j < puzzle[i].length; j++){
                positions.add(new Position(i, j));
            }
        }

        long seed = 260;
        random = new Random(seed);
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public Position getTargetPositionFromIdAgent(int id) {
        return positions.get(id);
    }

    //Permet d'assigner aléatoirement un agent à une case
    public Position setCase(PieceAgent agent) {
        int x = random.nextInt(size);
        int y = random.nextInt(size);

        while(puzzle[x][y] != null) {
            x = random.nextInt(size);
            y = random.nextInt(size);
        }
        puzzle[x][y] = agent;
        agents.add(agent);
        return new Position(x, y);
    }

    public Agent getAgent(Position pos) {
        return this.puzzle[pos.x][pos.y];
    }

    //Permet de déplacer un agent dans le puzzle
    public void moveAgent(Agent agentToMove, Position nextPos){
        Position currentPos = agentToMove.perception.currentPos;
        puzzle[currentPos.x][currentPos.y] = null;
        puzzle[nextPos.x][nextPos.y] = agentToMove;
        agentToMove.perception.currentPos = nextPos;
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        for (Agent[] agents : puzzle) {
            value.append("\n");
            for (int j = 0; j < puzzle.length; j++) {
                if (j == 0)
                    value.append("| ");
                if (agents[j] == null)
                    value.append(" ");
                else
                    value.append(agents[j].id);
                value.append(" | ");
            }
        }
        return value.toString();
    }
}
