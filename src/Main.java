import astar.*;
import puzzle.Puzzle;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(5, 5);
        System.out.println(puzzle);

        Node initialNode = new Node(2, 1);
        Node finalNode = new Node(2, 5);
        int rows = 5, cols = 5;
        Astar aStar = new Astar(rows, cols, initialNode, finalNode);
        int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
        aStar.setBlocks(blocksArray);
        List<Node> path = aStar.findPath();
        for (Node node : path) {
            System.out.println(node);
        }
    }


}
