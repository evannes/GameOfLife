import GOL3D.CubeBoard3D;
import GOL3D.CubeBoardManager3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Bruker on 21.04.2017.
 */
class Rules3DTest {
    @Test
    public void getHappyMessage() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        assertEquals(2,cubeboard.countNeighbor(5,0,0));

    }

    @Test
    public void getHappyMessage2() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        assertEquals(3,cubeboard.countNeighbor(5,0,1));

    }

    @Test
    public void getHappyMessage3() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        assertEquals(2,cubeboard.countNeighbor(5,0,2));

    }
}