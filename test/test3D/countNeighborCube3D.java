import GOL3D.Board3D;
import GOL3D.CubeBoard3D;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elise Haram Vannes on 03.05.2017.
 */
public class countNeighborCube3D {
    private CubeBoard3D cubeBoard = new CubeBoard3D();

    @Test
    void countNeighbor1() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(2,cubeBoard.countNeighbor(1,0,0));
    }

    @Test
    void countNeighbor2() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(1,cubeBoard.countNeighbor(1,1,0));
    }

    @Test
    void countNeighbor3() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(2,cubeBoard.countNeighbor(1,2,0));
    }

    @Test
    void countNeighbor4() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(3,cubeBoard.countNeighbor(1,0,1));
    }

    @Test
    void countNeighbor5() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(2,cubeBoard.countNeighbor(1,1,1));
    }

    @Test
    void countNeighbor6() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(3,cubeBoard.countNeighbor(1,2,1));
    }

    @Test
    void countNeighbor7() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(2,cubeBoard.countNeighbor(1,0,2));
    }

    @Test
    void countNeighbor8() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(1,cubeBoard.countNeighbor(1,1,2));
    }

    @Test
    void countNeighbor9() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setTestBoard();
        assertEquals(2,cubeBoard.countNeighbor(1,2,2));
    }
}
