package testing;

import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.Test;
import sample.Board;
import sample.Rules;

/**
 * Created by Alex on 22.02.2017.
 */
public class testClassPatternTwo {

    private boolean[][] testBoardGrid = new boolean[4][4];
    private Board testBoard = new Board(testBoardGrid);


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
     * @param testBoard     the board used for testing
     */
    private void settBrett(Board testBoard) {
        testBoard.boardGrid = testBoard.rules.getBoard();
    }



    @Test
    public void testInitialBoard() {
        defaultBoard(testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testBoard.toString(), "1000110010000000");
    }

    @Test
    public void testNextGenerationFirstRun() {
        defaultBoard(testBoardGrid);
        testBoard.rules.nextGeneration();
        settBrett(testBoard);
        org.junit.jupiter.api.Assertions.assertEquals(testBoard.toString(), "1100110011000000");
    }

    @Test
    public void testNextGenerationSecondRun() {
        defaultBoard(testBoardGrid);
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        settBrett(testBoard);
        org.junit.jupiter.api.Assertions.assertEquals(testBoard.toString(), "1100001011000000");
    }

    @Test
    public void testNextGenerationThirdRun() {
        defaultBoard(testBoardGrid);
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        settBrett(testBoard);
        org.junit.jupiter.api.Assertions.assertEquals(testBoard.toString(), "0100001001000000");
    }

    @Test
    public void testNextGenerationFourthRun() {
        defaultBoard(testBoardGrid);
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        settBrett(testBoard);
        org.junit.jupiter.api.Assertions.assertEquals(testBoard.toString(), "0000011000000000");
    }

    @Test
    public void testNextGenerationFinalRun() {
        defaultBoard(testBoardGrid);
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        testBoard.rules.nextGeneration();
        settBrett(testBoard);
        org.junit.jupiter.api.Assertions.assertEquals(testBoard.toString(), "0000000000000000");
    }
}
