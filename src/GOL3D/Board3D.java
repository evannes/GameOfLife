package GOL3D;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import model.*;

/**
 * This class creates the board of boolean values to keep track of Game of Life
 * and the logic associated with it.
 * Created by Elise Haram Vannes on 03.04.2017.
 */

public class Board3D extends Board{

    protected List<List<Boolean>> board;
    private int boardSize = 50;
    public List<List<Boolean>> clone;

    /**
     * Constructor that initializes the 3D board and its clone.
     */
    public Board3D(){
        board = initStartBoard(boardSize,boardSize);
        clone = initStartBoard(boardSize, boardSize);
        defaultStartBoard();
    }

    /**
     * Sets a default starting pattern to the board.
     */
    public void defaultStartBoard(){
        board.get(0).set(2,true);
        board.get(1).set(2,true);
        board.get(2).set(2,true);
        board.get(2).set(1,true);
        board.get(1).set(0,true);
    }

    @Override
    public int getWidth() {
        return board.size();
    }

    @Override
    public int getHeight() {
        return board.get(0).size();
    }

    /**
     * Initializes the lists that the board consists of.
     * @param width The width of the board
     * @param height The height of the board
     * @return the board that has been initialized.
     */
    private List<List<Boolean>> initStartBoard(int width, int height) {
        List<List<Boolean>> tmp = new ArrayList<List<Boolean>>(width);

        for(int i = 0; i < width; i++) {
            tmp.add(new ArrayList<>(height));

            for(int j = 0; j < height; j++) {
                tmp.get(i).add(j, false);
            }
        }

        return tmp;
    }

    @Override
    public void setCloneValue(int x, int y, boolean value) {
        clone.get(x).set(y, value);
    }

    @Override
    public void switchBoard() {
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                setValue(i, j, clone.get(i).get(j));
            }
        }
    }

    @Override
    public void setValue(int x, int y, boolean value) {
        board.get(x).set(y, value);
    }

    @Override
    public boolean getValue(int x, int y) {
        return board.get(x).get(y);
    }

    @Override
    public void toggleValue(int x, int y) {
        board.get(x).set(y, !board.get(x).get(y));
    }

    @Override
    public void clearBoard() {
        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(i, j, false)));
    }

    @Override
    public String toString(){
        String boardStringOutput = "";
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                if (board.get(i).get(j)) {
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
     * @param inputArray    the array loaded from file or URL
     */
    public void setInputInBoard(List<List<Boolean>> inputArray) {
        if(inputArray.size() > boardSize || inputArray.get(0).size() > boardSize) {
            Alert sizeErrorAlert = new Alert(Alert.AlertType.INFORMATION);
            sizeErrorAlert.setTitle("Error with size of pattern");
            sizeErrorAlert.setHeaderText("The pattern is too large for the board");
            sizeErrorAlert.showAndWait();
        } else {
            //clear previous pattern
            clearBoard();

            //find the corner to start placing the pattern
            int xStartIndex = (boardSize - inputArray.size()) / 2;
            int yStartIndex = (boardSize - inputArray.get(0).size()) / 2;

            // set new pattern in middle of board
            for (int i = 0; i < inputArray.size(); i++) {
                for (int j = 0; j < inputArray.get(0).size(); j++) {
                    Boolean value = inputArray.get(i).get(j);
                    setValue(i + xStartIndex, j + yStartIndex, value);
                }
            }
        }
    }
}


