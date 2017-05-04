package model;


import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This is the replacement class for the previously used {@link StaticBoard}.
 * The dynamic board will automatically resize itself when a pattern
 * larger than the default size of 160x100 cells is loaded. Our group decided
 * not to implement a dynamically expanding board when the current pattern
 * outgrew the current size. This because our board is drawn to the canvas
 * in a way which fills it completely. If the board array expands while running
 * it would have to readjust to the canvas and it would be hard to look at for the user.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @since   1.0
 */

public class DynamicBoard extends Board {
    private List<List<Boolean>> dynamicBoardArray;
    private List<List<Boolean>> clone;

    /**
     * Constructs and initiates the board to be used.
     * @param width     the width of the board.
     * @param height    the height of the board.
     */
    public DynamicBoard(int width, int height) {
        super(width, height);
        initStartBoard();
    }

    /**
     * Initializes the board with all values set to false.
     */
    private void initStartBoard(){
        dynamicBoardArray = initStartBoard(defaultWidth, defaultHeight);
        clone = initStartBoard(defaultWidth, defaultHeight);
    }

    /**
     * Returns a two dimensional boolean arrayList with all values set to false
     * @param x     the width of the array
     * @param y     the height of the array
     * @return      the arrayList
     */
    private List<List<Boolean>> initStartBoard(int x, int y) {
        List<List<Boolean>> tmp = new ArrayList<>(x);

        for(int i = 0; i < x; i++) {
            tmp.add(new ArrayList<>(y));

            for(int j = 0; j < y; j++) {
                tmp.get(i).add(j, false);
            }
        }

        return tmp;
    }

    @Override
    public void setValue(int x, int y, boolean value) {
        dynamicBoardArray.get(x).set(y, value);
    }

    @Override
    public boolean getValue(int x, int y) {
        return dynamicBoardArray.get(x).get(y);
    }

    @Override
    public void toggleValue(int x, int y) {
        dynamicBoardArray.get(x).set(y, !dynamicBoardArray.get(x).get(y));
    }

    @Override
    public int getWidth() {
        return dynamicBoardArray.size();
    }

    @Override
    public int getHeight() {
        return dynamicBoardArray.get(0).size();
    }

    @Override
    public void setCloneValue(int x, int y, boolean value) {
        clone.get(x).set(y, value);
    }

    @Override
    public void switchBoard() {
        List<List<Boolean>> tmp = dynamicBoardArray;
        dynamicBoardArray = clone;
        clone = tmp;
    }

    @Override
    public void clearBoard() {
        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(i, j, false)));
    }

    @Override
    public String toString(){
        String boardStringOutput = "";
        for(int i = 0; i < defaultWidth; i++) {
            for(int j = 0; j < defaultHeight; j++) {
                if (dynamicBoardArray.get(i).get(j)) {
                    boardStringOutput += "1";
                } else {
                    boardStringOutput += "0";
                }
            }
        }
        return boardStringOutput;
    }

    /**
     * Creates a two-dimensional ArrayList our of a boolean two-dimensional array.
     * @param array     the two-dimensional array to be converted
     * @return          a two-dimensional ArrayList with the same content as the input array
     */
    public List<List<Boolean>> createArrayListFromArray(boolean[][] array) {
        List<List<Boolean>> listArray = new ArrayList<>();
        for(int i = 0; i < array.length; i++){
            listArray.add(new ArrayList<>());
            for(int j = 0; j < array[0].length; j++){
                Boolean b = array[i][j];
                listArray.get(i).add(b);
            }
        }
        return listArray;
    }

    /**
     * Places the input array from filehandler into the board.
     * The array will not be set if the size is too big as this will compromise the user experience.
     * @param inputArray    the array loaded from file or URL
     */
    public void setInputInBoard(List<List<Boolean>> inputArray) {
        // check if the input array is too large (doesn't look good anymore...)
        if(inputArray.size() > 1040 || inputArray.get(0).size() > 650) {
            Alert sizeErrorAlert = new Alert(Alert.AlertType.INFORMATION);
            sizeErrorAlert.setTitle("Error with size of pattern");
            sizeErrorAlert.setHeaderText("The pattern is too large for the board");
            sizeErrorAlert.showAndWait();
            return;
        }

        resize(inputArray.size(), inputArray.get(0).size());

        //clear previous pattern
        clearBoard();

        //find the corner to start placing the pattern
        int xStartIndex = (dynamicBoardArray.size() - inputArray.size()) / 2;
        int yStartIndex = (dynamicBoardArray.get(0).size() - inputArray.get(0).size()) / 2;

        // set new pattern in middle of board
        for (int i = 0; i < inputArray.size(); i++) {
            for (int j = 0; j < inputArray.get(0).size(); j++) {
                Boolean value = inputArray.get(i).get(j);
                setValue(i + xStartIndex, j + yStartIndex, value);
            }
        }

    }

    /**
     * Resize the board. This method will either enlarge or decrease the size of the board.
     * The minimum size will be equal to the default size set in the constructor of the board.
     * @param width  the new width we wish to resize to
     * @param height the new height we wish to resize to
     */
    private void resize(int width, int height) {
        // finds the factor of width and height
        float width_factor = 1.0f / getWidth() * width;
        float height_factor = 1.0f / getHeight() * height;

        //if the width factor is larger than the height factor:
        if(width_factor > height_factor) {
            height = (int)Math.ceil((float)height/height_factor * width_factor);
        } else {
            width = (int)Math.ceil((float)width/width_factor * height_factor);
        }

        // if the new size is smaller than the default size we choose the default size
        height = Math.max(height, defaultHeight);
        width = Math.max(width, defaultWidth);

        // create new board with new size
        List<List<Boolean>> newArray = initStartBoard(width, height);

        // sets value from dynamicBoard to the new array
        for(int i = 0; i < newArray.size() && i < defaultWidth; i++) {
            for(int j = 0; j < newArray.get(0).size() && j < defaultHeight; j++) {
                newArray.get(i).set(j, getValue(i, j));
            }
        }

        // switching the arrays
        dynamicBoardArray = newArray;
        clone = initStartBoard(width, height);
    }

    /**
     * Used to clone the board. The functionality is used both in the
     * next generation methods and in the statistics window.
     * @return                              A cloned board equal to the currently drawn one
     * @throws CloneNotSupportedException   Exception thrown if the clone couldn't be created.
     */
    @Override
    public DynamicBoard clone() throws CloneNotSupportedException {
        DynamicBoard clonedBoard = (DynamicBoard) super.clone();
        clonedBoard.dynamicBoardArray = initStartBoard(getWidth(), getHeight());

        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                clonedBoard.setValue(i, j, getValue(i, j));
            }
        }

        return clonedBoard;
    }
}
