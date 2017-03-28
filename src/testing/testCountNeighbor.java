package testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.StaticBoard;
import sample.Rules;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class testCountNeighbor {
    boolean[][] testBoardGrid = new boolean[3][3];
    private StaticBoard testStaticBoard = new StaticBoard(testBoardGrid);

    /**
     * Used to set up the board again after running {@link Rules#nextGeneration()}.
     * @param testStaticBoard     the board used for testing
     */
    private void setBoard(StaticBoard testStaticBoard) {
        testStaticBoard.staticBoardArray = testStaticBoard.rules.getStaticBoard();
    }

    @Test
    public void testCountNeighborTop() {
        testBoardGrid[0][1] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborTopLeft() {
        testBoardGrid[0][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborTopRight() {
        testBoardGrid[0][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborLeft() {
        testBoardGrid[1][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborRight() {
        testBoardGrid[0][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottom() {
        testBoardGrid[2][1] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottomRight() {
        testBoardGrid[2][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottomLeft() {
        testBoardGrid[2][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborZeroNeighbors() {
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 0);
    }

    @Test
    public void testCountNeighborTwoNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 2);
    }

    @Test
    public void testCountNeighborThreeNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 3);
    }

    @Test
    public void testCountNeighborFourNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 4);
    }

    @Test
    public void testCountNeighborFiveNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 5);
    }

    @Test
    public void testCountNeighborSixNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][2] = true;
        testBoardGrid[2][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 6);
    }

    @Test
    public void testCountNeighborSevenNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][2] = true;
        testBoardGrid[2][0] = true;
        testBoardGrid[2][1] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 7);
    }

    @Test
    public void testCountNeighborEightNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][2] = true;
        testBoardGrid[2][0] = true;
        testBoardGrid[2][1] = true;
        testBoardGrid[2][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 8);
    }

}
