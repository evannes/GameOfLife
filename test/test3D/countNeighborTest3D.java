import GOL3D.Board3D;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elise Haram Vannes on 01.05.2017.
 */
public class countNeighborTest3D {

    private Board3D board = new Board3D();

    @Test
    void countNeighbor1() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(1,board.countNeighbor(1,0,30,30));
    }

    @Test
    void countNeighbor2() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(2,board.countNeighbor(1,1,30,30));
    }

    @Test
    void countNeighbor3() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(1,board.countNeighbor(1,2,30,30));
    }

    @Test
    void countNeighbor4() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(2,board.countNeighbor(0,0,30,30));
    }

    @Test
    void countNeighbor5() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(3,board.countNeighbor(0,1,30,30));
    }

    @Test
    void countNeighbor6() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(2,board.countNeighbor(0,2,30,30));
    }

    @Test
    void countNeighbor7() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(2,board.countNeighbor(2,0,30,30));
    }

    @Test
    void countNeighbor8() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(3,board.countNeighbor(2,1,30,30));
    }

    @Test
    void countNeighbor9() throws Exception {
        board.clearBoard();
        board.setTestBoard();
        assertEquals(2,board.countNeighbor(2,2,30,30));
    }
}