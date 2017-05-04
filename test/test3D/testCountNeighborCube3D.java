import GOL3D.CubeBoard3D;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Elise Vannes
 */
public class testCountNeighborCube3D {
    private final CubeBoard3D cubeBoard = new CubeBoard3D();

    @Test
    void countNeighborTopLeft() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,0,0);
        assertEquals(testCountNeighbor,2);
    }

    @Test
    void countNeighborTop() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,1,0);
        assertEquals(testCountNeighbor,3);
    }

    @Test
    void countNeighborTopRight() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,2,0);
        assertEquals(testCountNeighbor,1);
    }

    @Test
    void countNeighborLeft() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,0,1);
        assertEquals(testCountNeighbor,3);
    }

    @Test
    void countNeighborMiddle() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,1,1);
        assertEquals(testCountNeighbor,3);
    }

    @Test
    void countNeighborRight() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,2,1);
        assertEquals(testCountNeighbor,2);
    }

    @Test
    void countNeighborBottomLeft() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,0,2);
        assertEquals(testCountNeighbor,3);
    }

    @Test
    void countNeighborBottom() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,1,2);
        assertEquals(testCountNeighbor,2);
    }

    @Test
    void countNeighborBottomRight() throws Exception {
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        int testCountNeighbor = cubeBoard.countNeighbor(
                1,2,2);
        assertEquals(testCountNeighbor,2);
    }
}
