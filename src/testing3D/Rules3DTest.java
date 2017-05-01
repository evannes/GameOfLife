package testing3D;
import GOL3D.CubeBoard3D;
import org.junit.jupiter.api.Test;
//import org.testing.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Elise Haram Vannes on 21.04.2017.
 */
class Rules3DTest {

    /**
     * public void defaultStartBoard(){
     board2.get(3).set(15,true);
     board2.get(4).set(15,true);
     board2.get(5).set(15,true);
     board2.get(5).set(14,true);
     board2.get(4).set(13,true);
     }
     * @throws Exception
     */

    @Test
    public void getHappyMessage() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        assertEquals(0,cubeboard.countNeighbor(2,0,0));

    }

    @Test
    public void getHappyMessage2() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        assertEquals(0,cubeboard.countNeighbor(2,0,1));

    }

    @Test
    public void getHappyMessage3() throws Exception {
        CubeBoard3D cubeboard = new CubeBoard3D();
        assertEquals(0,cubeboard.countNeighbor(2,0,2));

    }
}