import model.DynamicBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class testCloning {

    private final DynamicBoard dynamicBoard = new DynamicBoard(4,4);

    private void defaultStartBoard(){
        dynamicBoard.setValue(0, 2, true);
        dynamicBoard.setValue(1, 2, true);
        dynamicBoard.setValue(2, 2, true);
        dynamicBoard.setValue(2, 1, true);
        dynamicBoard.setValue(1, 0, true);
    }

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
        defaultStartBoard();
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
            defaultStartBoard();
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
        defaultStartBoard();
        if (cloneBoard != null) {
            Assertions.assertNotEquals(cloneBoard.toString(), dynamicBoard.toString());
        }
    }
}
