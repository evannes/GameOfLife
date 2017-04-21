package GOL3D;

import javafx.scene.control.Alert;
import model.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Created by Elise Haram Vannes on 28.03.2017.
 */
public class CubeBoard3D {

    private List<List<Boolean>> board1;
    private List<List<Boolean>> board2;
    private List<List<Boolean>> board3;
    private List<List<Boolean>> board4;
    private List<List<Boolean>> board5;
    private List<List<Boolean>> board6;

    private int boardSize = 30;
    private Rules rules = new Rules();
    private List<List<Boolean>> clone;

    public CubeBoard3D(){
        board1 = initBoards(board1);
        board2 = initBoards(board2);
        board3 = initBoards(board3);
        board4 = initBoards(board4);
        board5 = initBoards(board5);
        board6 = initBoards(board6);
        //board6.get(2).set(0,true);
        //board1.get(2).set(0,true);

        board2.get(1).set(10,true);
        board2.get(1).set(11,true);
        board2.get(1).set(12,true);

        defaultStartBoard();
    }

    public List<List<Boolean>> initBoards(List<List<Boolean>> board){
        board = new ArrayList<List<Boolean>>(boardSize);

        for(int i = 0; i < boardSize; i++) {
            board.add(i, new ArrayList<Boolean>(boardSize));
        }

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board.get(i).add(j,false);
            }
        }
        return board;
    }

    public void defaultStartBoard(){
        board2.get(0).set(2,true);
        board2.get(1).set(2,true);
        board2.get(2).set(2,true);
        board2.get(2).set(1,true);
        board2.get(1).set(0,true);
    }

    public int getWidth() {
        return boardSize;
    }

    public int getHeight() {
        return boardSize;
    }

    public void createClone(int numBoard) {
        clone = new ArrayList<List<Boolean>>(getWidth());

        for(int i = 0; i < getWidth(); i++) {
            clone.add(new ArrayList<>(getHeight()));

            for(int j = 0; j < getHeight(); j++) {
                clone.get(i).add(j, getValue(numBoard,i, j));
            }
        }
    }

    public void nextGenerations(){
        for(int i = 1; i < 7; i++){
            nextGeneration(i);
        }
    }

    /**
     * The method creating the next generation of cells to be drawn or removed.
     */
    // får problem med kloning av brett, må klones 6 stk og alle må byttes
    public void nextGeneration(int numBoard) {
        createClone(numBoard);

        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                int neighbors = countNeighbor(numBoard,i, j);
                boolean value = getValue(numBoard,i, j) ? rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                setCloneValue(i, j, value );
            }
        }
        switchBoard(numBoard);
    }

    public void setCloneValue(int x, int y, boolean value) {
        clone.get(x).set(y, value);
    }

    public void switchBoard(int numBoard) {
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                setValue(numBoard,i, j, clone.get(i).get(j));
            }
        }
    }

    /**
     * The method counting the alive cells surrounding the appointed cell
     * @param i     the first column index of the array
     * @param j     the second column index of the array
     * @return      the number of alive neighboring cells
     */
    public int countNeighbor(int numBoard,int i, int j){
        int count = 0;

        //check top
        if (isInCurrentBoard(numBoard,i, j-1))
            count++;

        //check top-left
        if (isInCurrentBoard(numBoard,i-1, j-1))
            count++;

        //check top-right
        if (isInCurrentBoard(numBoard,i+1, j-1))
            count++;

        //check left
        if (isInCurrentBoard(numBoard,i-1, j))
            count++;

        //check right
        if (isInCurrentBoard(numBoard,i+1, j))
            count++;

        //check bottom
        if (isInCurrentBoard(numBoard,i, j+1))
            count++;

        //check bottom-right
        if (isInCurrentBoard(numBoard,i+1, j+1))
            count++;

        //check bottom-left
        if (isInCurrentBoard(numBoard,i-1, j+1))
            count++;

        return count;
    }

    /**
     * The method checking if the appointed position is within the board array.
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @return          <code>false</code> if the position is exceeding the board array
     */
    private boolean isInCurrentBoard(int numBoard, int i, int j){
        // method "inBoard"??
        // "hardkodet" versjon med kun board2

        if(i == -1 && j == -1){
            return false;
        }
        else if(i >= boardSize && j >= boardSize){
            return false;
        }
        else if(i == -1 && j >= boardSize){
            return false;
        }
        else if(i >= boardSize && j == -1){
            return false;
        }

        else if(inBounds(i) && inBounds(j)){
            if(getBoard2().get(i).get(j)){
                return true;
            } else {
                return false;
            }
        }
        else{
            return countBoardNeighbors(numBoard,i,j);
        }
    }

    public boolean inBounds(int m){
        if(m == -1 || m >= boardSize){
            return false;
        } else {
            return true;
        }
    }

    public boolean countBoardNeighbors(int numboard,int i, int j){
        switch(numboard){
            case 1:
                return countBoard1Neighbors(i,j);
            case 2:
                return countBoard2Neighbors(i,j);
            case 3:
                return countBoard3Neighbors(i,j);
            case 4:
                return countBoard4Neighbors(i,j);
            case 5:
                return countBoard5Neighbors(i,j);
            case 6:
                return countBoard6Neighbors(i,j);
            default:
                return false;
        }
    }

    public boolean countBoard1Neighbors(int i, int j){
        return false;
    }

    public boolean countBoard2Neighbors(int i, int j){

            if (i == -1 && inBounds(j)) {
                if (board6.get(j).get(0)) {
                    return true;
                } else {
                    return false;
                }
            }

            if (inBounds(i) && j == -1) {
                if (board1.get(i).get(0)) {
                    return true;
                } else {
                    return false;
                }
            }

            if (i >= boardSize && inBounds(j)) {
                if (board5.get(0).get(j)) {
                    return true;
                } else {
                    return false;
                }
            }

            if (inBounds(i) && j >= boardSize) {
                if (board3.get(i).get(0)) {
                    return true;
                } else {
                    return false;
                }
            }
        return false;
    }

    public boolean countBoard3Neighbors(int i, int j){
        return false;
    }

    public boolean countBoard4Neighbors(int i, int j){
        return false;
    }

    public boolean countBoard5Neighbors(int i, int j){
        return false;
    }

    public boolean countBoard6Neighbors(int i, int j){
        return false;
    }

    public boolean checkActiveBoardValue(List<List<Boolean>> board,int i,int j){
        if(i < 0){
            if(board.get(0).get(j)) {
                return true;
            }
        }else{
            if(board.get(0).get(i)) {
                return true;
            }
        }
        return false;
    }

    public void setValue(int numBoard,int x, int y, boolean value) {
        getBoard(numBoard).get(x).set(y, value);
    }

    public boolean getValue(int numBoard,int x, int y) {
        return getBoard(numBoard).get(x).get(y);
    }

    public void toggleValue(int numBoard, int x, int y) {
        getBoard(numBoard).get(x).set(y, !board2.get(x).get(y));
    }

    /**
     * The method resetting all values of the board to false
     */
    // er no kun for board 2
    public void clearBoard() {

        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(2,i, j, false)));
    }

    /**
     * The method creating a two-dimensional ArrayList our of a boolean two-dimensional array.
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
     * The method placing the input array from filehandler into the board.
     * @param inputArray    the array loaded from file or URL
     */
    public void setInputInBoard(List<List<Boolean>> inputArray,int numBoard) {
        // check if the input array is too large (doesn't look good anymore...)
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
                    setValue(numBoard,i + xStartIndex, j + yStartIndex, value);
                }
            }
        }
    }

    public List<List<Boolean>> getBoard(int board) {
        switch(board){
            case 1:
                return board1;
            case 2:
                return board2;
            case 3:
                return board3;
            case 4:
                return board4;
            case 5:
                return board5;
            case 6:
                return board6;
            default:
                return null;
        }

    }

    public List<List<Boolean>> getBoard1() {
        return board1;
    }

    public List<List<Boolean>> getBoard2() {
        return board2;
    }

    public List<List<Boolean>> getBoard3() {
        return board3;
    }

    public List<List<Boolean>> getBoard4() {
        return board4;
    }

    public List<List<Boolean>> getBoard5() {
        return board5;
    }

    public List<List<Boolean>> getBoard6() {
        return board6;
    }

}
