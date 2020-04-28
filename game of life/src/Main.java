
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int size,seed;
        size = scanner.nextInt();
        seed = scanner.nextInt();

        Board board = new Board(size, seed);
        board.displayState(board.getBoardStart());
        System.out.println("/////////////////////////");
        board.playOnce();
        System.out.println("/////////////////////////");
        board.playOnce();



    }
}
