package testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.DynamicBoard;

/**
 * Created by Alex on 05.04.2017.
 */
public class testCloning {

    private DynamicBoard dynamicBoard = new DynamicBoard(4,4);

    @Test
    public void testIfObjectsAreDifferent() {
        DynamicBoard cloneBoard = null;
        try {
            cloneBoard = dynamicBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Assertions.assertNotEquals(cloneBoard, dynamicBoard);
    }

    @Test
    public void testIfClassIsEqual() {
        DynamicBoard cloneBoard = null;
        try {
            cloneBoard = dynamicBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (cloneBoard != null) {
            Assertions.assertEquals(cloneBoard.getClass(), dynamicBoard.getClass());
        }
    }

    @Test
    public void testDefaultPatternIsEqual() {
        dynamicBoard.defaultStartBoard();
        DynamicBoard cloneBoard = null;
        try {
            cloneBoard = dynamicBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (cloneBoard != null) {
            Assertions.assertEquals(cloneBoard.toString(), dynamicBoard.toString());
        }
    }

    @Test
    public void testEmptyBoardIsEqual() {
        DynamicBoard cloneBoard = null;
        try {
            cloneBoard = dynamicBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (cloneBoard != null) {
            Assertions.assertEquals(cloneBoard.toString(), dynamicBoard.toString());
        }
    }

    @Test
    public void testIfChangingCloneAffectsOriginal() {
        DynamicBoard cloneBoard = null;
        try {
            cloneBoard = dynamicBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (cloneBoard != null) {
            cloneBoard.defaultStartBoard();
            Assertions.assertNotEquals(cloneBoard.toString(), dynamicBoard.toString());
        }
    }

    @Test
    public void testIfChangingOriginalAffectsClone() {
        DynamicBoard cloneBoard = null;
        try {
            cloneBoard = dynamicBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        dynamicBoard.defaultStartBoard();
        if (cloneBoard != null) {
            Assertions.assertNotEquals(cloneBoard.toString(), dynamicBoard.toString());
        }
    }
}
