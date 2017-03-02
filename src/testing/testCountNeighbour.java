package testing;

import org.junit.jupiter.api.Test;
import sample.Board;
import sample.Rules;

/**
 * Created by Alex on 02.03.2017.
 */
public class testCountNeighbour {
    boolean[][] testBoardGrid = new boolean[3][3];
    private Board testBoard = new Board(testBoardGrid);

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
}
