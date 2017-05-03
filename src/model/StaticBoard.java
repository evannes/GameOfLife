package model;

import javafx.scene.control.Alert;

/**
 * The StaticBoard class was the initial board being used.
 * It doesn't have the functionality used to expand the board
 * and is no longer in use.
 *
 * @author      Miina Lervik
 * @author      Elise Vannes
 * @author      Alexander Kingdon
 * @since       1.0
 * @deprecated  After adding dynamic board functionality
 *              in {@link DynamicBoard}.
 */
@Deprecated
public class StaticBoard extends Board {
    private boolean[][] staticBoardArray;
    private boolean[][] clone;

    /**
     * Constructs and initiates the board.
     * @param width     the width of the board
     * @param height    the height of the board
     */
    public StaticBoard(int width, int height) {
        super(width, height);
        staticBoardArray = new boolean[defaultWidth][defaultHeight];
        clone = new boolean[defaultWidth][defaultHeight];
    }

    @Override
    public void setValue(int x, int y, boolean value) {
        staticBoardArray[x][y] = value;
    }

    @Override
    public boolean getValue(int x, int y) {
        return staticBoardArray[x][y];
    }

    @Override
    public void toggleValue(int x, int y) {
        staticBoardArray[x][y] = !staticBoardArray[x][y];
    }

    @Override
    public int getWidth() {
        return staticBoardArray.length;
    }

    @Override
    public int getHeight() {
        return staticBoardArray[0].length;
    }

    @Override
    public void switchBoard() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                setValue(x, y, clone[x][y]);
            }
        }
    }

    @Override
    public void setCloneValue(int x, int y, boolean value) {
        clone[x][y] = value;
    }

    @Override
    public void clearBoard() {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                staticBoardArray[x][y] = false;
            }
        }
    }

    @Override
    public String toString(){
        String boardStringOutput = "";

        for (int x = 0; x < staticBoardArray.length; x++) {
            for (int y = 0; y < staticBoardArray[0].length; y++) {
                if (staticBoardArray[x][y]) {
                    boardStringOutput += "1";
                    } else {
                    boardStringOutput += "0";
                    }
                }
            }
        return boardStringOutput;
    }

    /**
     * Transfers a pattern to the board.
     * This method will only allow a pattern with smaller or equal size as the board.
     * In case of a larger pattern this method will give an alert letting the user know the pattern is too large.
     * @param array     the array with the desired pattern to be placed on the board.
     */
    public void transferPatternToBoard(boolean[][] array) {
        // show alert if pattern is too big
        if (array.length > staticBoardArray.length || array[0].length > staticBoardArray[0].length) {
            Alert transferErrorAlert = new Alert(Alert.AlertType.INFORMATION);
            transferErrorAlert.setTitle("Error transfering pattern to board");
            transferErrorAlert.setHeaderText("The pattern you chose was too big for the board");
            transferErrorAlert.showAndWait();
        } else {
            System.out.println("transfering pattern");

            for (int x = 0; x < array.length; x++) {
                for (int y = 0; y < array[0].length; y++) {
                    staticBoardArray[x][y] = array[x][y];
                }
            }
        }
    }

    /**
     * Used to clone the board. The functionality is used both in the
     * next generation methods and in the statistics window.
     * @return                              A cloned board equal to the currently drawn one
     * @throws CloneNotSupportedException   Exception thrown if the clone couldn't be created.
     */
    @Override
    public StaticBoard clone() throws CloneNotSupportedException {
        try {
            return (StaticBoard) super.clone();
        } catch (CloneNotSupportedException cnse) {
            return null;
        }
    }


}
