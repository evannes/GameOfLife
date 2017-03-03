package testing;

import org.junit.jupiter.api.Test;
import sample.Board;
import sample.Rules;

/**
 * Created by Alex on 02.03.2017.
 */
public class testCountNeighbor {
    boolean[][] testBoardGrid = new boolean[3][3];
    private Board testBoard = new Board(testBoardGrid);

    /**
     * Used to set up the board again after running {@link Rules#nextGeneration()}
     * @param testBoard     the board used for testing
     */
    private void setBoard(Board testBoard) {
        testBoard.boardGrid = testBoard.rules.getBoard();
    }

    @Test
    public void testCountNeighborTop() {
        testBoardGrid[0][1] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborTopLeft() {
        testBoardGrid[0][0] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborTopRight() {
        testBoardGrid[0][2] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborLeft() {
        testBoardGrid[1][0] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborRight() {
        testBoardGrid[0][2] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottom() {
        testBoardGrid[2][1] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottomRight() {
        testBoardGrid[2][2] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottomLeft() {
        testBoardGrid[2][0] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborZeroNeighbors() {
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 0);
    }

    @Test
    public void testCountNeighborTwoNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 2);
    }

    @Test
    public void testCountNeighborThreeNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 3);
    }

    @Test
    public void testCountNeighborFourNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 4);
    }

    @Test
    public void testCountNeighborFiveNeighbors() {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][2] = true;
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
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
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
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
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
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
        setBoard(testBoard);
        int testCountNeighbor = testBoard.rules.countNeighbor(1, 1, testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 8);
    }
}
