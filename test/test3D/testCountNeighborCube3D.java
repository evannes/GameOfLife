import GOL3D.CubeBoard3D;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Elise Vannes
 */
public class testCountNeighborCube3D {
    private final CubeBoard3D cubeBoard = new CubeBoard3D();

     // sette setValue-metoden til public i CubeBoard3D og Board3D
     /*  private void setValue(int indexBoard, int x, int y, boolean value) {
        boardArrays[indexBoard].get(x).set(y, value);
     }
     testStaticBoard.setValue(0, 0, false);
     testStaticBoard.setValue(0, 1, true);
     testStaticBoard.setValue(0, 2, false);
     testStaticBoard.setValue(1, 0, false);
     testStaticBoard.setValue(1, 2, false);
     testStaticBoard.setValue(2, 0, false);
     testStaticBoard.setValue(2, 1, false);
     testStaticBoard.setValue(2, 2, false);
     int testCountNeighbor = testStaticBoard.countNeighbor(
     1,1, testStaticBoard.getWidth(), testStaticBoard.getHeight());
     * @throws Exception
     */

    @Test
    void countNeighborTopLeft() throws Exception {
        cubeBoard.clearBoards();

        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);

        assertEquals(2,cubeBoard.countNeighbor(1,0,0));
    }

    @Test
    void countNeighborTop() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        assertEquals(3,cubeBoard.countNeighbor(1,1,0));
    }

    @Test
    void countNeighborTopRight() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        assertEquals(1,cubeBoard.countNeighbor(1,2,0));
    }

    @Test
    void countNeighborLeft() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        assertEquals(3,cubeBoard.countNeighbor(1,0,1));
    }

    @Test
    void countNeighborMiddle() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        assertEquals(3,cubeBoard.countNeighbor(1,1,1));
    }

    @Test
    void countNeighborRight() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        assertEquals(2,cubeBoard.countNeighbor(1,2,1));
    }

    @Test
    void countNeighborBottomLeft() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        assertEquals(3,cubeBoard.countNeighbor(1,0,2));
    }

    @Test
    void countNeighborBottom() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        assertEquals(2,cubeBoard.countNeighbor(1,1,2));
    }

    @Test
    void countNeighborBottomRight() throws Exception {
        cubeBoard.clearBoards();
        cubeBoard.setValue(1,0,0,true);
        cubeBoard.setValue(1,1,0,false);
        cubeBoard.setValue(1,2,0,false);
        cubeBoard.setValue(1,0,1,true);
        cubeBoard.setValue(1,1,1,true);
        cubeBoard.setValue(1,2,1,false);
        cubeBoard.setValue(1,0,2,false);
        cubeBoard.setValue(1,1,2,true);
        cubeBoard.setValue(1,2,2,false);
        assertEquals(2,cubeBoard.countNeighbor(1,2,2));
    }
}
