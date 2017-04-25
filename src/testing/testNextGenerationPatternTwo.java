
package testing;

import model.StaticBoard;
import org.junit.jupiter.api.Test;



/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public class testNextGenerationPatternTwo {
    private StaticBoard testStaticBoard = new StaticBoard(4,4);


/**
     * Applies a given default pattern to the board being tested.
     * @param testBoardGrid     the array used to represent the board
     */

    private void defaultBoard() {
        testStaticBoard.setValue(0,0,true);
        testStaticBoard.setValue(1,0,true);
        testStaticBoard.setValue(1,1,true);
        testStaticBoard.setValue(2,0,true);
    }

    @Test
    public void testInitialBoard() {
        defaultBoard();
        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "1000110010000000");
    }

    @Test
    public void testNextGenerationFirstRun() {
        defaultBoard();
        testStaticBoard.nextGeneration();

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "1100110011000000");
    }

    @Test
    public void testNextGenerationSecondRun() {
        defaultBoard();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "1100001011000000");
    }

    @Test
    public void testNextGenerationThirdRun() {
        defaultBoard();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "0100001001000000");
    }

    @Test
    public void testNextGenerationFourthRun() {
        defaultBoard();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "0000011000000000");
    }

    @Test
    public void testNextGenerationFinalRun() {
        defaultBoard();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();
        testStaticBoard.nextGeneration();

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "0000000000000000");
    }

}

