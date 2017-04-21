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
    List<List<Boolean>>[] boardArrays;

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
        //board1.get(0).set(1,true);
        //board1.get(0).set(2,true);
        /*
        board2.get(1).set(10,true);
        board2.get(1).set(11,true);
        board2.get(1).set(12,true);

        board2.get(0).set(0,true);
        board2.get(0).set(1,true);
        board2.get(0).set(2,true);
        board2.get(0).set(3,true);
        board2.get(0).set(4,true);
        board2.get(0).set(5,true);
        board2.get(0).set(6,true);
        board2.get(0).set(7,true);
        board2.get(0).set(8,true);
        */
        initBoardArray();
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

        /*board2.get(0).set(2,true); // akkurat denne teller feil naboer, teller 2 men har 1
        board2.get(1).set(2,true);
        board2.get(2).set(2,true);
        board2.get(2).set(1,true);
        board2.get(1).set(0,true);*/
        board6.get(0).set(0,true);
        board6.get(0).set(1,true);
        board6.get(0).set(2,true);
        //board1.get(1).set(0,true);
        //board2.get(1).set(1,true);

        /*board2.get(1).set(3,true);
        board2.get(2).set(3,true);
        board2.get(3).set(3,true);
        board2.get(3).set(2,true);
        board2.get(2).set(1,true);*/
        /*board6.get(0).set(2,true);
        board6.get(1).set(2,true);
        board6.get(2).set(2,true);
        board6.get(2).set(1,true);
        board6.get(1).set(0,true);*/
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
        for(int i = 0; i < 6; i++){
            nextGeneration(i);
        }
    }

    /**
     * The method creating the next generation of cells to be drawn or removed.
     */
    // får problem med kloning av brett, må klones 6 stk og alle må byttes
    public void nextGeneration(int indexBoard) {
        createClone(indexBoard);

        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                int neighbors = countNeighbor(indexBoard,i, j);
                boolean value = getValue(indexBoard,i, j) ? rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                setCloneValue(i, j, value );
            }
        }
        switchBoard(indexBoard);
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
    public int countNeighbor(int indexBoard,int i, int j){
        int count = 0;

        //check top
        if (isInCurrentBoard(indexBoard,i, j-1))
            count++;

        //check top-left
        if (isInCurrentBoard(indexBoard,i-1, j-1))
            count++;

        //check top-right
        if (isInCurrentBoard(indexBoard,i+1, j-1))
            count++;

        //check left
        if (isInCurrentBoard(indexBoard,i-1, j))
            count++;

        //check right
        if (isInCurrentBoard(indexBoard,i+1, j))
            count++;

        //check bottom
        if (isInCurrentBoard(indexBoard,i, j+1))
            count++;

        //check bottom-right
        if (isInCurrentBoard(indexBoard,i+1, j+1))
            count++;

        //check bottom-left
        if (isInCurrentBoard(indexBoard,i-1, j+1))
            count++;

        return count;
    }

    /**
     * The method checking if the appointed position is within the board array.
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @return          <code>false</code> if the position is exceeding the board array
     */
    private boolean isInCurrentBoard(int indexBoard, int i, int j){
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
            if(boardArrays[indexBoard].get(i).get(j)){
                return true;
            } else {
                return false;
            }
        }
        else{
            return countBoardNeighbors(indexBoard,i,j);
        }
    }

    public boolean inBounds(int m){
        if(m == -1 || m >= boardSize){
            return false;
        } else {
            return true;
        }
    }

    public boolean countBoardNeighbors(int indexBoard,int i, int j){
        // TODO: switch on enum
        switch(indexBoard){
            case 0:
                return countBoard1Neighbors(i,j);
            case 1:
                return countBoard2Neighbors(i,j);
            case 2:
                return countBoard3Neighbors(i,j);
            case 3:
                return countBoard4Neighbors(i,j);
            case 4:
                return countBoard5Neighbors(i,j);
            case 5:
                return countBoard6Neighbors(i,j);
            default:
                return false;
        }
    }

    public boolean countBoard1Neighbors(int i, int j){
        if (i == -1 && inBounds(j)) {
            if (board2.get(j).get(0)) {
                return true;
            } else {
                return false;
            }
        }
        // feiiil brett
        if (inBounds(i) && j == -1) {
            if (board6.get(0).get(i)) {
                return true;
            } else {
                return false;
            }
        }

        if (i >= boardSize && inBounds(j)) {
            if (board4.get(j).get(0)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j >= boardSize) {
            if (board5.get(0).get(i)) {
                return true;
            } else {
                return false;
            }
        }
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
                if (board1.get(0).get(i)) {
                    return true;
                } else {
                    return false;
                }
            }

            if (i >= boardSize && inBounds(j)) {
                if (board5.get(j).get(0)) {
                    return true;
                } else {
                    return false;
                }
            }

            if (inBounds(i) && j >= boardSize) {
                if (board3.get(0).get(i)) {
                    return true;
                } else {
                    return false;
                }
            }
        return false;
    }

    public boolean countBoard3Neighbors(int i, int j){
        if (i == -1 && inBounds(j)) {
            if (board6.get(board6.size()-1).get(j)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j == -1) {
            if (board2.get(board2.size()-1).get(i)) {
                return true;
            } else {
                return false;
            }
        }

        if (i >= boardSize && inBounds(j)) {
            if (board5.get(board5.size()-1).get(j)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j >= boardSize) {
            if (board4.get((board4.size()-1)).get(i)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean countBoard4Neighbors(int i, int j){
        if (i == -1 && inBounds(j)) {
            if (board1.get(j).get(board1.size()-1)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j == -1) {
            if (board6.get(i).get(board6.size()-1)) {
                return true;
            } else {
                return false;
            }
        }

        if (i >= boardSize && inBounds(j)) {
            if (board3.get(j).get(board3.size()-1)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j >= boardSize) {
            if (board5.get(i).get(board5.size()-1)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean countBoard5Neighbors(int i, int j){
        if (i == -1 && inBounds(j)) {
            if (board1.get(board1.size()-1).get(j)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j == -1) {
            if (board2.get(i).get(board2.size()-1)) {
                return true;
            } else {
                return false;
            }
        }

        if (i >= boardSize && inBounds(j)) {
            if (board3.get(board3.size()-1).get(j)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j >= boardSize) {
            if (board4.get(i).get(board4.size()-1)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean countBoard6Neighbors(int i, int j){
        if (i == -1 && inBounds(j)) {
            if (board1.get(j).get(0)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j == -1) {
            if (board2.get(0).get(i)) {
                return true;
            } else {
                return false;
            }
        }

        if (i >= boardSize && inBounds(j)) {
            if (board3.get(j).get(0)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j >= boardSize) {
            if (board4.get(0).get(i)) {
                return true;
            } else {
                return false;
            }
        }
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
        boardArrays[numBoard].get(x).set(y, value);
    }

    public boolean getValue(int numBoard,int x, int y) {
        return boardArrays[numBoard].get(x).get(y);
    }

    public void toggleValue(int numBoard, int x, int y) {
        boardArrays[numBoard].get(x).set(y, !board2.get(x).get(y));
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

    public void initBoardArray(){
        boardArrays = new ArrayList[6];
        boardArrays[0] = board1;
        boardArrays[1] = board2;
        boardArrays[2] = board3;
        boardArrays[3] = board4;
        boardArrays[4] = board5;
        boardArrays[5] = board6;
    }

    public List<List<Boolean>>[] getBoardArrays(){
        return boardArrays;
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
