package GOL3D;

import javafx.scene.control.Alert;
import model.Rules;

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
    private int boardSize = 30;
    private Rules rules = new Rules();
    public List<List<Boolean>> clone;

    // er så lik Board/DynamicBoard-klassen at kanskje burde arve??? og kun overskrive
    // dei få nødvendige metodane, enten arve board eller dynamicBoard

    // får feilmelding ved innlasting av fil hvis man trykker avbryt og det
    // forrige opplastede mønsteret kjøres på nytt igjen
    // har ikke exception handling ved innlasting av for store brett, får exceptions

    /**
     * Constructor that initializes the 3D board.
     */
    public Board3D(){
        initStartBoard();
    }

    /**
     * Initializes the lists that the board consists of.
     */
    public void initStartBoard(){

        board = new ArrayList<List<Boolean>>(boardSize);

        for(int i = 0; i < boardSize; i++) {
            board.add(i, new ArrayList<Boolean>(boardSize));
        }

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board.get(i).add(j,false);
            }
        }
        defaultStartBoard();
        clone = getBoard(boardSize, boardSize);
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
     * Creates clone of the current board.
     */
    public void createClone() {
        clone = new ArrayList<List<Boolean>>(getWidth());

        for(int i = 0; i < getWidth(); i++) {
            clone.add(new ArrayList<>(getHeight()));

            for(int j = 0; j < getHeight(); j++) {
                clone.get(i).add(j, getValue(i, j));
            }
        }
    }

    // både for initialize clone og vanlig board
    private List<List<Boolean>> getBoard(int x, int y) {
        List<List<Boolean>> tmp = new ArrayList<List<Boolean>>(x);

        for(int i = 0; i < x; i++) {
            tmp.add(new ArrayList<>(y));

            for(int j = 0; j < y; j++) {
                tmp.get(i).add(j, false);
            }
        }

        return tmp;
    }
    /*
    @Override
    public void nextGeneration() {
        createClone();

        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                int neighbors = countNeighbor(i, j,getWidth(),getHeight());
                boolean value = getValue(i, j) ? rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                setCloneValue(i, j, value );
            }
        }
        switchBoard();
    }*/

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

    /**
     * Counts the alive cells surrounding the appointed cell
     * @param i     the first column index of the array
     * @param j     the second column index of the array
     * @return      the number of alive neighboring cells
     */
    /*
    public int countNeighbor(int i, int j){
        int count = 0;

        //check top
        if (isActiveCell(i, j-1))
            count++;

        //check top-left
        if (isActiveCell(i-1, j-1))
            count++;

        //check top-right
        if (isActiveCell(i+1, j-1))
            count++;

        //check left
        if (isActiveCell(i-1, j))
            count++;

        //check right
        if (isActiveCell(i+1, j))
            count++;

        //check bottom
        if (isActiveCell(i, j+1))
            count++;

        //check bottom-right
        if (isActiveCell(i+1, j+1))
            count++;

        //check bottom-left
        if (isActiveCell(i-1, j+1))
            count++;

        return count;
    }*/

    /**
     * Checks if the cell is alive.
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @return          <code>true</code> if the cell is alive
     *                  and not exceeding the board array
     */
    /*private boolean isActiveCell(int i, int j) {
        return inBounds(i, j) && getValue(i,j) == true;
    }*/

    /**
     * Checks if the appointed position is within the board array.
     * @param /i         the first column index of the array
     * @param /j         the second column index of the array
     * @return          <code>false</code> if the position is exceeding the board array
     */
    /*private boolean inBounds(int i, int j){
        if(i == -1 || j == -1){
            return false;
        }

        if(i >= getWidth() || j >= getHeight()){
            return false;
        }

        return true;
    }*/

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
    // var en resize-nextGenerationThreadTask, men skal ikkje resizes
}


