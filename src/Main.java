import puzzle.Puzzle;

public class Main {

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(5, 5);

        for(int i = 0; i < 10; i++) {
            System.out.println(puzzle);
            puzzle.goToRightPosition();
        }

        System.out.println("End");
        System.out.println(puzzle);

    }
}
