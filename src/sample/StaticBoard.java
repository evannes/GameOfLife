package sample;

import javafx.scene.control.Alert;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
@Deprecated
public class StaticBoard extends Board {
    //The canvas is 800 defaultWidth 500 px so in order to create square cells the array must maintain a similar ratio
    public boolean[][] staticBoardArray;
    public boolean[][] clone;

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
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                setValue(i, j, clone[i][j]);
            }
        }
    }

    @Override
    public void setCloneValue(int x, int y, boolean value) {
        clone[x][y] = value;
    }

    @Override
    public void clearBoard() {
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                staticBoardArray[i][j] = false;
            }
        }
    }

    @Override
    public String toString(){
        String boardStringOutput = "";
        for(int i = 0; i < staticBoardArray.length; i++) {
            for(int j = 0; j < staticBoardArray[0].length; j++) {
                if (staticBoardArray[i][j]) {
                    boardStringOutput += "1";
                    } else {
                    boardStringOutput += "0";
                    }
                }
            }
        return boardStringOutput;
    }

    /**
     * The method transfering a pattern to the board.
     * This methos will only allow a pattern with smaller or equal size as the board.
     * In case of a larger pattern this method will give an alert letting the user know the pattern is too large.
     * @param array     the array with the desired pattern to be placed on the board.
     */
    public void transferPatternToBoard(boolean[][] array) {
        // show alert if pattern is too big
        if(array.length > staticBoardArray.length || array[0].length > staticBoardArray[0].length){
            Alert transferErrorAlert = new Alert(Alert.AlertType.INFORMATION);
            transferErrorAlert.setTitle("Error transfering pattern to board");
            transferErrorAlert.setHeaderText("The pattern you chose was too big for the board");
            transferErrorAlert.showAndWait();
        } else {
            System.out.println("transfering pattern");
            for(int i = 0; i < array.length; i++) {
                for(int j = 0; j < array[0].length; j++) {
                    staticBoardArray[i][j] = array[i][j];
                }
            }
        }
    }

    @Override
    public StaticBoard clone() {
        try {
            return (StaticBoard) super.clone();
        } catch (CloneNotSupportedException cnse) {
            return null;
        }
    }


}
