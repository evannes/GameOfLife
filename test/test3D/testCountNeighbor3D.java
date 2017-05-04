import GOL3D.Board3D;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Elise Vannes
 */
public class testCountNeighbor3D {

    private final Board3D board = new Board3D();

    @Test
    void countNeighborTopLeft() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                0,0, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,2);
    }

    @Test
    void countNeighborTop() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                0,1, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,3);
    }

    @Test
    void countNeighborTopRight() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                0,2, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,1);
    }

    @Test
    void countNeighborLeft() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                1,0, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,3);
    }

    @Test
    void countNeighborMiddle() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                1,1, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,3);
    }

    @Test
    void countNeighborRight() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                1,2, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,2);
    }

    @Test
    void countNeighborBottomLeft() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                2,0, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,3);
    }

    @Test
    void countNeighborBottom() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                2,1, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,2);
    }

    @Test
    void countNeighborBottomRight() throws Exception {
        board.setValue(0,0,true);
        board.setValue(1,0,true);
        board.setValue(2,0,false);
        board.setValue(0,1,false);
        board.setValue(1,1,true);
        board.setValue(2,1,true);
        board.setValue(0,2,false);
        board.setValue(1,2,false);
        board.setValue(2,2,false);
        int testCountNeighbor = board.countNeighbor(
                2,2, board.getWidth(), board.getHeight());
        assertEquals(testCountNeighbor,2);
    }
}