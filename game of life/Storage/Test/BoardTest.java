import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private static Board board;
    private int x;
    @BeforeAll
    static void prepare(){
        board = new Board(7,100);
        board.playOnce();
    }

    @Test
    void countNeighbors(){
       x = board.checkNeighbor(0,0);
       Assertions.assertEquals(2,x);
       x = board.checkNeighbor(0,4);
       Assertions.assertEquals(2,x);
       x = board.checkNeighbor(4,3);
       Assertions.assertEquals(1,x);
    }

    @Test
    void updateCell1(){
        System.out.println("Aktualizowanie komórki o wspolrzednych 2,5");
        x = board.checkNeighbor(2,5);
        char y = board.updateCell(x,2,5);
        Assertions.assertEquals('O',y);
        board.displayState(board.getBoardUpdate());
        System.out.println("============");
    }
    @Test
    void updateCell2(){
        System.out.println("Aktualizowanie komórki o wspolrzednych 3,1");
        x = board.checkNeighbor(3,1);
        char y = board.updateCell(x,3,1);
        Assertions.assertEquals(' ',y);
        board.displayState(board.getBoardUpdate());
        System.out.println("============");
    }

    @Test
    void updateCell3(){
        System.out.println("Aktualizowanie komórki o wspolrzednych 1,1");
        x = board.checkNeighbor(1,1);
        char y = board.updateCell(x,1,1);
        Assertions.assertEquals('O',y);
        board.displayState(board.getBoardUpdate());
        System.out.println("============");
    }
    @Test
    void updateCell4(){
        System.out.println("Aktualizowanie komórki o wspolrzednych 5,5");
        x = board.checkNeighbor(5,5);
        char y = board.updateCell(x,5,5);
        Assertions.assertEquals(' ',y);
        board.displayState(board.getBoardUpdate());
        System.out.println("============");
    }
}
