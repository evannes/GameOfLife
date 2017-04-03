
package testing;

import org.junit.jupiter.api.Test;
import sample.Board;
import sample.StaticBoard;
import sample.Rules;


/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public class testNextGenerationPatternOne {

    private boolean[][] testBoardGrid = new boolean[4][4];
    private Board testStaticBoard = new StaticBoard(testBoardGrid);


    /**
     * Applies a given default pattern to the board being tested.
     */

    private void defaultBoard() {
        testStaticBoard.setValue(0,0,true);
        testStaticBoard.setValue(0,3,true);
        testStaticBoard.setValue(1,1,true);
        testStaticBoard.setValue(1,2,true);
        testStaticBoard.setValue(2,1,true);
        testStaticBoard.setValue(2,2,true);
        testStaticBoard.setValue(3,0,true);
        testStaticBoard.setValue(3,3,true);

        /*
        testBoardGrid[0][0] = true;
        testBoardGrid[0][3] = true;
        testBoardGrid[1][1] = true;
        testBoardGrid[1][2] = true;
        testBoardGrid[2][1] = true;
        testBoardGrid[2][2] = true;
        testBoardGrid[3][0] = true;
        testBoardGrid[3][3] = true;
        */
    }


    /**
     * Used to set up the board again after running {@link Board#nextGeneration()}
     * @param testStaticBoard     the board used for testing
     */

    private void setBoard(StaticBoard testStaticBoard) {
        //testStaticBoard.staticBoardArray = testStaticBoard.rules.getStaticBoard();

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
        //setBoard(testStaticBoard);

        org.junit.jupiter.api.Assertions.assertEquals(testStaticBoard.toString(), "0110100110010110");
    }

}

