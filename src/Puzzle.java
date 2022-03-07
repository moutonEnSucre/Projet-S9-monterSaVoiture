import java.util.Random;

public class Puzzle {

    private Agent [][] puzzle;

    public Puzzle(int tab_size) {
        this.puzzle = new Agent[tab_size][tab_size];
        for(int i=0; i < tab_size; i++){
            for (int j=0; j < tab_size; j++){
                this.puzzle[i][j] = new Agent();
            }
        }
        setCase(Agent.AgentType.sun, tab_size);
        setCase(Agent.AgentType.cross, tab_size);
        setCase(Agent.AgentType.timer, tab_size);
        setCase(Agent.AgentType.sun, tab_size);
        setCase(Agent.AgentType.star, tab_size);
    }

    private void setCase(Agent.AgentType type, int size) {
        int maxIteration = 100;
        int x = new Random().nextInt(size);
        int y = new Random().nextInt(size);
        while(puzzle[x][y].agent_type != Agent.AgentType.empty && maxIteration > 0) {
            x = new Random().nextInt(size);
            y = new Random().nextInt(size);
            maxIteration++;
        }
        puzzle[x][y].agent_type = type;
    }

    @Override
    public String toString() {
        String value = "";
        for(int i=0; i < puzzle.length; i++){
            value += "\n";
            for(int j=0; j < puzzle.length; j++){
                if(j == 0)
                    value += "| ";
                if(puzzle[i][j].agent_type == Agent.AgentType.star)
                    value += "1 | ";
                else if(puzzle[i][j].agent_type == Agent.AgentType.cross)
                    value += "2 | ";
                else if(puzzle[i][j].agent_type == Agent.AgentType.sun)
                    value += "3 | ";
                else if(puzzle[i][j].agent_type == Agent.AgentType.timer)
                    value += "4 | ";
                else
                    value += "  | ";
            }
        }
        return value;
    }
}
