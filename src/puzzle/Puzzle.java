package puzzle;

import utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Puzzle {

    private final Agent[][] puzzle;
    private HashMap<Agent, Position> agent_pos;
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
        puzzle[x][y] = agent;
        agent_pos.put(agent, new Position(x, y));
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
