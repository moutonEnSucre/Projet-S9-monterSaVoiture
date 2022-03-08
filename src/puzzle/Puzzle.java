package puzzle;

import java.util.HashMap;
import java.util.Random;

public class Puzzle {

    private Agent[][] puzzle;

    public Puzzle(int nbAgent, int tab_size) {
        this.puzzle = new Agent[tab_size][tab_size];

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
        puzzle[x][y] = agent;
    }

    @Override
    public String toString() {
        String value = "";
        for(int i=0; i < puzzle.length; i++){
            value += "\n";
            for(int j=0; j < puzzle.length; j++){
                if(j == 0)
                    value += "| ";
                if(puzzle[i][j] == null) {
                    value += " ";
                }
                else {
                    value += puzzle[i][j].id;
                }

                value += " | ";
            }
        }
        return value;
    }
}
