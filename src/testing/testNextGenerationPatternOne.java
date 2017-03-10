package testing;

import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.Test;
import sample.Board;
import sample.Rules;

/**
 * Created by Alex on 22.02.2017.
 */
public class testNextGenerationPatternOne {

    private boolean[][] testBoardGrid = new boolean[4][4];
    private Board testBoard = new Board(testBoardGrid);

    /**
     * Applies a given default pattern to the board being tested.
     * @param testBoardGrid     the array used to represent the board
     */
    private void defaultBoard(boolean[][] testBoardGrid) {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][3] = true;
        testBoardGrid[1][1] = true;
        testBoardGrid[1][2] = true;
        testBoardGrid[2][1] = true;
        testBoardGrid[2][2] = true;
        testBoardGrid[3][0] = true;
        testBoardGrid[3][3] = true;
    }

    /**
     * Used to set up the board again after running {@link Rules#nextGeneration()}
     * @param testBoard     the board used for testing
     */
    private void setBoard(Board testBoard) {
        testBoard.boardGrid = testBoard.rules.getBoard();
    }

    @Test
    public void testInitialBoard() {
        defaultBoard(testBoardGrid);

        org.junit.jupiter.api.Assertions.assertEquals(testBoard.toString(), "1001011001101001");
    }

    @Test
    public void testNextGeneration() {
        defaultBoard(testBoardGrid);
        testBoard.rules.nextGeneration();
        setBoard(testBoard);

        org.junit.jupiter.api.Assertions.assertEquals(testBoard.toString(), "0110100110010110");
    }

}
