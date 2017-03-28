package testing;

import org.junit.jupiter.api.Test;
import sample.StaticBoard;
import sample.Rules;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class testNextGenerationPatternTwo {

    private boolean[][] testBoardGrid = new boolean[4][4];
    private StaticBoard testStaticBoard = new StaticBoard(testBoardGrid);

    /**
     * Applies a given default pattern to the board being tested.
     * @param testBoardGrid     the array used to represent the board
     */
    private void defaultBoard(boolean[][] testBoardGrid) {
        testBoardGrid[0][0] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][1] = true;
        testBoardGrid[2][0] = true;
    }

    /**
     * Used to set up the board again after running {@link Rules#nextGeneration()}
     * @param testStaticBoard     the board used for testing
     */
    private void setBoard(StaticBoard testStaticBoard) {
        testStaticBoard.staticBoardArray = testStaticBoard.rules.getStaticBoard();
    }

    @Test
    public void testInitialBoard() {
        defaultBoard(testBoardGrid);

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "1000110010000000");
    }

    @Test
    public void testNextGenerationFirstRun() {
        defaultBoard(testBoardGrid);
        testStaticBoard.rules.nextGeneration();
        setBoard(testStaticBoard);

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "1100110011000000");
    }

    @Test
    public void testNextGenerationSecondRun() {
        defaultBoard(testBoardGrid);
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        setBoard(testStaticBoard);

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "1100001011000000");
    }

    @Test
    public void testNextGenerationThirdRun() {
        defaultBoard(testBoardGrid);
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        setBoard(testStaticBoard);

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "0100001001000000");
    }

    @Test
    public void testNextGenerationFourthRun() {
        defaultBoard(testBoardGrid);
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        setBoard(testStaticBoard);

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "0000011000000000");
    }

    @Test
    public void testNextGenerationFinalRun() {
        defaultBoard(testBoardGrid);
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        testStaticBoard.rules.nextGeneration();
        setBoard(testStaticBoard);

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "0000000000000000");
    }

}
