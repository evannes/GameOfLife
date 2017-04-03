package testing;

import org.junit.jupiter.api.Test;
import sample.Board;
import sample.StaticBoard;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public class testCountNeighbor {
    boolean[][] testBoardGrid = new boolean[3][3];
    private Board testStaticBoard = new StaticBoard(testBoardGrid);

    /**
     * Used to set up the board again after running {@link Board#nextGeneration()}.
     * @param testStaticBoard     the board used for testing
     */

    private void setBoard(StaticBoard testStaticBoard) {
        //testStaticBoard.staticBoardArray = testStaticBoard.rules.getStaticBoard();
    }

    @Test
    public void testCountNeighborTop() {
        testStaticBoard.setValue(0, 0, false);
        testStaticBoard.setValue(0, 1, true);
        testStaticBoard.setValue(0, 2, false);
        testStaticBoard.setValue(1, 0, false);
        testStaticBoard.setValue(1, 2, false);
        testStaticBoard.setValue(2, 0, false);
        testStaticBoard.setValue(2, 1, false);
        testStaticBoard.setValue(2, 2, false);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][1] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborTopLeft() {
        testStaticBoard.setValue(0, 0, true);
        testStaticBoard.setValue(0, 1, false);
        testStaticBoard.setValue(0, 2, false);
        testStaticBoard.setValue(1, 0, false);
        testStaticBoard.setValue(1, 2, false);
        testStaticBoard.setValue(2, 0, false);
        testStaticBoard.setValue(2, 1, false);
        testStaticBoard.setValue(2, 2, false);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborTopRight() {
        testStaticBoard.setValue(0, 0, false);
        testStaticBoard.setValue(0, 1, false);
        testStaticBoard.setValue(0, 2, true);
        testStaticBoard.setValue(1, 0, false);
        testStaticBoard.setValue(1, 2, false);
        testStaticBoard.setValue(2, 0, false);
        testStaticBoard.setValue(2, 1, false);
        testStaticBoard.setValue(2, 2, false);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborLeft() {
        testStaticBoard.setValue(0, 0, false);
        testStaticBoard.setValue(0, 1, false);
        testStaticBoard.setValue(0, 2, false);
        testStaticBoard.setValue(1, 0, true);
        testStaticBoard.setValue(1, 2, false);
        testStaticBoard.setValue(2, 0, false);
        testStaticBoard.setValue(2, 1, false);
        testStaticBoard.setValue(2, 2, false);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[1][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborRight() {
        testStaticBoard.setValue(0, 0, false);
        testStaticBoard.setValue(0, 1, false);
        testStaticBoard.setValue(0, 2, false);
        testStaticBoard.setValue(1, 0, false);
        testStaticBoard.setValue(1, 2, true);
        testStaticBoard.setValue(2, 0, false);
        testStaticBoard.setValue(2, 1, false);
        testStaticBoard.setValue(2, 2, false);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottom() {
        testStaticBoard.setValue(0, 0, false);
        testStaticBoard.setValue(0, 1, false);
        testStaticBoard.setValue(0, 2, false);
        testStaticBoard.setValue(1, 0, false);
        testStaticBoard.setValue(1, 2, false);
        testStaticBoard.setValue(2, 0, false);
        testStaticBoard.setValue(2, 1, true);
        testStaticBoard.setValue(2, 2, false);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[2][1] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottomRight() {
        testStaticBoard.setValue(0, 0, false);
        testStaticBoard.setValue(0, 1, false);
        testStaticBoard.setValue(0, 2, false);
        testStaticBoard.setValue(1, 0, false);
        testStaticBoard.setValue(1, 2, false);
        testStaticBoard.setValue(2, 0, false);
        testStaticBoard.setValue(2, 1, false);
        testStaticBoard.setValue(2, 2, true);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[2][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborBottomLeft() {
        testStaticBoard.setValue(0, 0, false);
        testStaticBoard.setValue(0, 1, false);
        testStaticBoard.setValue(0, 2, false);
        testStaticBoard.setValue(1, 0, false);
        testStaticBoard.setValue(1, 2, false);
        testStaticBoard.setValue(2, 0, true);
        testStaticBoard.setValue(2, 1, false);
        testStaticBoard.setValue(2, 2, false);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[2][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 1);
    }

    @Test
    public void testCountNeighborZeroNeighbors() {
        testStaticBoard.setValue(0, 0, false);
        testStaticBoard.setValue(0, 1, false);
        testStaticBoard.setValue(0, 2, false);
        testStaticBoard.setValue(1, 0, false);
        testStaticBoard.setValue(1, 2, false);
        testStaticBoard.setValue(2, 0, false);
        testStaticBoard.setValue(2, 1, false);
        testStaticBoard.setValue(2, 2, false);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 0);
    }

    @Test
    public void testCountNeighborTwoNeighbors() {
        testStaticBoard.setValue(0, 0, true);
        testStaticBoard.setValue(0, 1, true);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 2);
    }

    @Test
    public void testCountNeighborThreeNeighbors() {
        testStaticBoard.setValue(0, 0, true);
        testStaticBoard.setValue(0, 1, true);
        testStaticBoard.setValue(0, 2, true);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 3);
    }

    @Test
    public void testCountNeighborFourNeighbors() {
        testStaticBoard.setValue(0, 0, true);
        testStaticBoard.setValue(0, 1, true);
        testStaticBoard.setValue(0, 2, true);
        testStaticBoard.setValue(1, 0, true);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 4);
    }

    @Test
    public void testCountNeighborFiveNeighbors() {
        testStaticBoard.setValue(0, 0, true);
        testStaticBoard.setValue(0, 1, true);
        testStaticBoard.setValue(0, 2, true);
        testStaticBoard.setValue(1, 0, true);
        testStaticBoard.setValue(1, 2, true);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][2] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 5);
    }

    @Test
    public void testCountNeighborSixNeighbors() {
        testStaticBoard.setValue(0, 0, true);
        testStaticBoard.setValue(0, 1, true);
        testStaticBoard.setValue(0, 2, true);
        testStaticBoard.setValue(1, 0, true);
        testStaticBoard.setValue(1, 2, true);
        testStaticBoard.setValue(2, 0, true);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][2] = true;
        testBoardGrid[2][0] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 6);
    }

    @Test
    public void testCountNeighborSevenNeighbors() {
        testStaticBoard.setValue(0, 0, true);
        testStaticBoard.setValue(0, 1, true);
        testStaticBoard.setValue(0, 2, true);
        testStaticBoard.setValue(1, 0, true);
        testStaticBoard.setValue(1, 2, true);
        testStaticBoard.setValue(2, 0, true);
        testStaticBoard.setValue(2, 1, true);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
        testBoardGrid[0][0] = true;
        testBoardGrid[0][1] = true;
        testBoardGrid[0][2] = true;
        testBoardGrid[1][0] = true;
        testBoardGrid[1][2] = true;
        testBoardGrid[2][0] = true;
        testBoardGrid[2][1] = true;
        setBoard(testStaticBoard);
        int testCountNeighbor = testStaticBoard.rules.countNeighbor(1, 1, testBoardGrid);
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 7);
    }

    @Test
    public void testCountNeighborEightNeighbors() {
        testStaticBoard.setValue(0, 0, true);
        testStaticBoard.setValue(0, 1, true);
        testStaticBoard.setValue(0, 2, true);
        testStaticBoard.setValue(1, 0, true);
        testStaticBoard.setValue(1, 2, true);
        testStaticBoard.setValue(2, 0, true);
        testStaticBoard.setValue(2, 1, true);
        testStaticBoard.setValue(2, 2, true);
        int testCountNeighbor = testStaticBoard.countNeighbor(1,1);
        /*
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
        */
        org.junit.jupiter.api.Assertions.assertEquals(testCountNeighbor, 8);
    }

}

