import puzzle.Puzzle;

public class Main {

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(0.5f, 5);

        for(int i = 0; i < 20; i++) {
            System.out.println(puzzle);
            puzzle.update();
        }

        System.out.println("<===== END =====>");
        System.out.println(puzzle);

    }
}
