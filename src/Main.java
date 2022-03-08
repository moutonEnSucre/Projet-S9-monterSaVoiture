import astar.*;
import puzzle.Puzzle;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(5, 5);
        System.out.println(puzzle);

        puzzle.goToRightPosition();
    }
}
