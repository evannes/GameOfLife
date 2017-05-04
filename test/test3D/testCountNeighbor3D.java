import GOL3D.Board3D;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Elise Vannes
 */
public class testCountNeighbor3D {

    private final Board3D board = new Board3D();
    // setValue må være public, setTEstBoard må slettes,
    // og javadoc må opprettes på nytt igjen
    @Test
    void countNeighborTopLeft() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(2,board.countNeighbor(0,0,30,30));
    }

    @Test
    void countNeighborTop() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(3,board.countNeighbor(0,1,30,30));
    }

    @Test
    void countNeighborTopRight() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(1,board.countNeighbor(0,2,30,30));
    }

    @Test
    void countNeighborLeft() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(3,board.countNeighbor(1,0,30,30));
    }

    @Test
    void countNeighborMiddle() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(3,board.countNeighbor(1,1,30,30));
    }

    @Test
    void countNeighborRight() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(2,board.countNeighbor(1,2,30,30));
    }

    @Test
    void countNeighborBottomLeft() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(3,board.countNeighbor(2,0,30,30));
    }

    @Test
    void countNeighborBottom() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(2,board.countNeighbor(2,1,30,30));
    }

    @Test
    void countNeighborBottomRight() throws Exception {
        board.clearBoard();
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        assertEquals(2,board.countNeighbor(2,2,30,30));
    }
}