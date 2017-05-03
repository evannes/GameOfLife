import model.Board;
import model.StaticBoard;
import org.junit.Test;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public class testNextGenerationPatternOne {

    private Board testStaticBoard = new StaticBoard(4,4);

    private void defaultBoard() {
        testStaticBoard.setValue(0,0,true);
        testStaticBoard.setValue(0,3,true);
        testStaticBoard.setValue(1,1,true);
        testStaticBoard.setValue(1,2,true);
        testStaticBoard.setValue(2,1,true);
        testStaticBoard.setValue(2,2,true);
        testStaticBoard.setValue(3,0,true);
        testStaticBoard.setValue(3,3,true);
    }

    @Test
    public void testInitialBoard() {
        defaultBoard();
        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "1001011001101001");
    }

    @Test
    public void testNextGeneration() {
        defaultBoard();
        testStaticBoard.nextGeneration();

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "0110100110010110");
    }

}

