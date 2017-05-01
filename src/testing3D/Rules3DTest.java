package testing3D;
import GOL3D.CubeBoard3D;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Elise Haram Vannes on 21.04.2017.
 */
class Rules3DTest {

    @Test
    public void countNeighbor1() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(1,cubeboard.countNeighbor(1,0,0));
    }

    @Test
    public void countNeighbor2() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(1,cubeboard.countNeighbor(1,0,1));
    }

    @Test
    public void countNeighbor3() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(1,cubeboard.countNeighbor(1,0,2));
    }

    @Test
    public void countNeighbor4() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(1,cubeboard.countNeighbor(1,1,0));
    }

    @Test
    public void countNeighbor5() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(0,cubeboard.countNeighbor(1,1,1));
    }

    @Test
    public void countNeighbor6() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(1,cubeboard.countNeighbor(1,1,2));
    }

    @Test
    public void countNeighbor7() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(1,cubeboard.countNeighbor(1,2,0));
    }

    @Test
    public void countNeighbor8() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(1,cubeboard.countNeighbor(1,2,1));
    }

    @Test
    public void countNeighbor9() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        cubeboard.setTestBoard();
        assertEquals(1,cubeboard.countNeighbor(1,2,2));
    }
}