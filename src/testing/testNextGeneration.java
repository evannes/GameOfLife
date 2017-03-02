package testing;

import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.Test;
import sample.Board;
import sample.Rules;

/**
 * Created by Alex on 22.02.2017.
 */
public class testNextGeneration {

    boolean[][] testBoardGrid = new boolean[4][4];
    Canvas canvas = new Canvas();
    Board testBrett = new Board(canvas, testBoardGrid);
    Rules rules = new Rules();



    public void defaultBrett(boolean[][] testBoardGrid) {
        testBoardGrid[0][0] = true;
        testBoardGrid[0][3] = true;
        testBoardGrid[1][1] = true;
        testBoardGrid[1][2] = true;
        testBoardGrid[2][1] = true;
        testBoardGrid[2][2] = true;
        testBoardGrid[3][0] = true;
        testBoardGrid[3][3] = true;
    }

    public void settBrett(Board brett) {
        defaultBrett(testBoardGrid);
        brett.rules.setBoard(testBoardGrid);
    }




    @Test
    public void testNextGeneration() {
        //testBrett.newGame();
        //testBrett.clearBoard();
        //testBrett.rules.setBoard(testBoardGrid);
        //settBrett(testBrett);

        //System.out.println("Test 1 passert - Tomt brett");
        //testBrett.start();
        settBrett(testBrett);
        //rules.getBoard();
        //testBrett.rules.nextGeneration();
        org.junit.jupiter.api.Assertions.assertEquals(testBrett.toString(), "1001011001101001");
        System.out.println("Første assert riktig, default brett: " + testBrett.toString());
        testBrett.rules.nextGeneration();
        testBrett.rules.getBoard();
        testBrett.rules.setBoard(testBoardGrid);
        org.junit.jupiter.api.Assertions.assertEquals(testBrett.toString(), "0110100110010110");
        System.out.println("Andre assert riktig, Først nextGeneration kjørt: " + testBrett.toString());
    }
}
