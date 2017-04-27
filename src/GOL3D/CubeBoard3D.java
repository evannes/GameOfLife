package GOL3D;

import javafx.scene.control.Alert;
import model.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


/**
 * This class creates the boards of boolean values for the cube,
 * to keep track of Game of Life and the logic associated with it.
 * Created by Elise Haram Vannes on 28.03.2017.
 */
public class CubeBoard3D {

    private List<List<Boolean>> board1;
    private List<List<Boolean>> board2;
    private List<List<Boolean>> board3;
    private List<List<Boolean>> board4;
    private List<List<Boolean>> board5;
    private List<List<Boolean>> board6;
    private List<List<Boolean>>[] boardArrays;

    private int boardSize = 30;
    private Rules rules = new Rules();
    private List<List<Boolean>> clone1;
    private List<List<Boolean>> clone2;
    private List<List<Boolean>> clone3;
    private List<List<Boolean>> clone4;
    private List<List<Boolean>> clone5;
    private List<List<Boolean>> clone6;
    private List<List<Boolean>>[] cloneArrays;

    /**
     * Constructor that initializes all arrays for the cubes boolean values.
     */
    public CubeBoard3D(){
        board1 = initBoards(board1);
        board2 = initBoards(board2);
        board3 = initBoards(board3);
        board4 = initBoards(board4);
        board5 = initBoards(board5);
        board6 = initBoards(board6);

        clone1 = initBoards(clone1);
        clone2 = initBoards(clone2);
        clone3 = initBoards(clone3);
        clone4 = initBoards(clone4);
        clone5 = initBoards(clone5);
        clone6 = initBoards(clone6);
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
        initCloneArray();
        defaultStartBoard();
    }

    /**
     * Initializes the board.
     * @param board board to be initialized
     * @return returns the initialized board
     */
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

    /**
     * Creates an initial pattern to be shown on the cube.
     */
    public void defaultStartBoard(){

        /*board2.get(0).set(2,true); // akkurat denne teller feil naboer, teller 2 men har 1
        board2.get(1).set(2,true);
        board2.get(2).set(2,true);
        board2.get(2).set(1,true);
        board2.get(1).set(0,true);*/
        //board6.get(0).set(0,true);
        //board6.get(0).set(1,true);
        //board6.get(0).set(2,true);
        //board1.get(1).set(0,true);
        //board2.get(1).set(1,true);

        board2.get(3).set(15,true);
        board2.get(4).set(15,true);
        board2.get(5).set(15,true);
        board2.get(5).set(14,true);
        board2.get(4).set(13,true);
        /*board6.get(0).set(2,true);
        board6.get(1).set(2,true);
        board6.get(2).set(2,true);
        board6.get(2).set(1,true);
        board6.get(1).set(0,true);*/
    }

    /**
     * Returns the width of the board.
     * @return the width of this board
     */
    public int getWidth() {
        return boardSize;
    }

    /**
     * Returns the height of the board.
     * @return the height of this board
     */
    public int getHeight() {
        return boardSize;
    }

    /**
     * Creates clone of the current array.
     */
    public void createClone(int indexBoard) {
        cloneArrays[indexBoard] = new ArrayList<List<Boolean>>(getWidth());

        for(int i = 0; i < getWidth(); i++) {
            cloneArrays[indexBoard].add(new ArrayList<>(getHeight()));

            for(int j = 0; j < getHeight(); j++) {
                cloneArrays[indexBoard].get(i).add(j, getValue(indexBoard,i, j));
            }
        }
    }

    /**
     * Runs through the nextGeneration-method for all the arrays of the cube.
     */
    public void nextGenerations(){
        for(int i = 0; i < 6; i++){
            nextGeneration(i);
        }
    }

    /**
     * Creates the next generation of cells to be drawn or removed.
     */
    // får problem med kloning av brett, må klones 6 stk og alle må byttes
    public void nextGeneration(int indexBoard) {
        createClone(indexBoard);

        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                int neighbors = countNeighbor(indexBoard,i, j);
                boolean value = getValue(indexBoard,i, j) ? rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                setCloneValue(indexBoard,i, j, value );
            }
        }
        //switchBoard(indexBoard);
    }

    /**
     * Sets values to the clone at the appointed index.
     * @param x the first column index
     * @param y the second column index
     * @param value the value to be set
     */
    public void setCloneValue(int indexBoard, int x, int y, boolean value) {
        cloneArrays[indexBoard].get(x).set(y, value);
    }

    /**
     * Makes all the boards equal to their clone.
     */
    public void switchBoards(){
        // TODO: bytte alle bretta samtidig, kjøre den etter alle nextgenerations
        for(int i = 0; i < cloneArrays.length; i++){
            switchBoard(i);
        }
    }

    /**
     * Makes the board equal to the clone.
     */
    public void switchBoard(int indexBoard) {
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                setValue(indexBoard,i, j, cloneArrays[indexBoard].get(i).get(j));
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

    /**
     * Checks if the cell exists in the cube
     * @param cell  cell in cube
     * @return <code>true</code> if the cell exists in the cube
     */
    public boolean inBounds(int cell){
        if(cell == -1 || cell >= boardSize){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Chooses the method for counting neighbors at neighboring
     * arrays when the cell is at the end of a board, according
     * to which is the current board.
     * @param indexBoard index of current board in array of boards
     * @param i          the first column index of the array
     * @param j          the second column index of the array
     * @return       <code>true</code> if the neighboring cell is alive
     */
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

    /**
     * Checks if the neighbor at neighboring boards is alive for board1
     * @param i  the first column index of the array
     * @param j  the second column index of the array
     * @return   <code>true</code> if the neighboring cell is alive
     */
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

    /**
     * Checks if the neighbor at neighboring boards is alive for board2
     * @param i  the first column index of the array
     * @param j  the second column index of the array
     * @return   <code>true</code> if the neighboring cell is alive
     */
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

    /**
     * Checks if the neighbor at neighboring boards is alive for board3
     * @param i  the first column index of the array
     * @param j  the second column index of the array
     * @return   <code>true</code> if the neighboring cell is alive
     */
    public boolean countBoard3Neighbors(int i, int j){
        // har gjort på nytt
        if (i == -1 && inBounds(j)) {
            if (board2.get(j).get(board6.size()-1)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j == -1) {
            if (board6.get(i).get(board2.size()-1)) {
                return true;
            } else {
                return false;
            }
        }

        if (i >= boardSize && inBounds(j)) {
            if (board4.get(j).get(board5.size()-1)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j >= boardSize) {
            if (board5.get(i).get(board4.size()-1)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Checks if the neighbor at neighboring boards is alive for board4
     * @param i  the first column index of the array
     * @param j  the second column index of the array
     * @return   <code>true</code> if the neighboring cell is alive
     */
    public boolean countBoard4Neighbors(int i, int j){
        // har gjort på nytt
        if (i == -1 && inBounds(j)) {
            if (board6.get(board1.size()-1).get(j)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j == -1) {
            if (board1.get(board6.size()-1).get(i)) {
                return true;
            } else {
                return false;
            }
        }

        if (i >= boardSize && inBounds(j)) {
            if (board5.get(board3.size()-1).get(j)){
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j >= boardSize) {
            if (board3.get(board5.size()-1).get(i)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Checks if the neighbor at neighboring boards is alive for board5
     * @param i  the first column index of the array
     * @param j  the second column index of the array
     * @return   <code>true</code> if the neighboring cell is alive
     */
    public boolean countBoard5Neighbors(int i, int j){
        //
        if (i == -1 && inBounds(j)) {
            if (board2.get(board1.size()-1).get(j)) {
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j == -1) {
            if (board1.get(i).get(board2.size()-1)) {
                return true;
            } else {
                return false;
            }
        }

        if (i >= boardSize && inBounds(j)) {
            if (board4.get(board3.size()-1).get(j)) { // eller if (board4.get(0).get(j)) { //
                return true;
            } else {
                return false;
            }
        }

        if (inBounds(i) && j >= boardSize) {
            if (board3.get(i).get(board4.size()-1)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Checks if the neighbor at neighboring boards is alive for board6
     * @param i  the first column index of the array
     * @param j  the second column index of the array
     * @return   <code>true</code> if the neighboring cell is alive
     */
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

    /**
     * Sets <code>boolean</code> values to the board.
     * @param x     the first column index
     * @param y     the second column index
     * @param value the <code>boolean</code> value to be set
     */
    public void setValue(int numBoard,int x, int y, boolean value) {
        boardArrays[numBoard].get(x).set(y, value);
    }

    /**
     * Returns the <code>boolean</code> value of the appointed position
     * @param x the first column index
     * @param y the second column index
     * @return  the <code>boolean</code> value in this index
     */
    public boolean getValue(int numBoard,int x, int y) {
        return boardArrays[numBoard].get(x).get(y);
    }

    /**
     * Toggles the <code>boolean</code> value at the appointed index.
     * @param x the first column index
     * @param y the second column index
     */
    public void toggleValue(int numBoard, int x, int y) {
        boardArrays[numBoard].get(x).set(y, !board2.get(x).get(y));
    }

    /**
     * Resets all values of the board to false
     */
    // er no kun for board 2
    public void clearBoards() {
        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(0, i, j, false)));
        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(1, i, j, false)));
        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(2, i, j, false)));
        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(3, i, j, false)));
        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(4, i, j, false)));
        IntStream.range(0, getWidth()).forEach(i -> IntStream.range(0, getHeight()).forEach(j -> setValue(5, i, j, false)));
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
    public void setInputInBoard(List<List<Boolean>> inputArray,int numBoard) {
        // check if the input array is too large (doesn't look good anymore...)
        if(inputArray.size() > boardSize || inputArray.get(0).size() > boardSize) {
            Alert sizeErrorAlert = new Alert(Alert.AlertType.INFORMATION);
            sizeErrorAlert.setTitle("Error with size of pattern");
            sizeErrorAlert.setHeaderText("The pattern is too large for the board");
            sizeErrorAlert.showAndWait();
        } else {
            //clear previous pattern
            clearBoards();

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

    /**
     * Initializes array containing all the arrays with boolean values of the cube.
     */
    public void initBoardArray(){
        boardArrays = new ArrayList[6];
        boardArrays[0] = board1;
        boardArrays[1] = board2;
        boardArrays[2] = board3;
        boardArrays[3] = board4;
        boardArrays[4] = board5;
        boardArrays[5] = board6;
    }

    /**
     * Returns the array containing all the arrays with boolean values of the cube.
     * @return the array of arrays
     */
    public List<List<Boolean>>[] getBoardArrays(){
        return boardArrays;
    }

    /**
     * Returns board2, one of the arrays with boolean values of the cube.
     * @return the array board2
     */
    public List<List<Boolean>> getBoard2() {
        return board2;
    }

    public void initCloneArray(){
        cloneArrays = new ArrayList[6];
        cloneArrays[0] = clone1;
        cloneArrays[1] = clone2;
        cloneArrays[2] = clone3;
        cloneArrays[3] = clone4;
        cloneArrays[4] = clone5;
        cloneArrays[5] = clone6;
    }
}
