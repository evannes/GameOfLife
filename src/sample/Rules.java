package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miinael on 15.02.2017.
 */
public class Rules {
    private static Rules rules = null;
    private boolean[][] board;
    private List<List<Boolean>> listBoard;
    private List<List<Boolean>> newListBoard;
    private int outerListSize;
    private int innerListSize;

    public static Rules getInstance() {
        if(rules == null) {
            rules = new Rules();
        }
        return rules;
    }
    /**
     * The method setting the board the methods in this class should work with.
     * @param board     The board to work with.
     */
    public void setBoard(boolean[][] board) {
        this.board = board;
    }

    /**
     * The method setting the board the methods in this class should work with.
     * @param board     The board to work with.
     */
    public void setBoard(List<List<Boolean>> board) {
        outerListSize = board.size();
        innerListSize = board.get(0).size();
        this.listBoard = board;
    }

    /**
     * The method returning the updated board from this class
     * @return      the updated board
     */
    /*public void setBoard(List<List<Boolean>> board){
        int boardheight;
        int boardwidth;

        boolean[][] newboard= new boolean[boardheight][boardwidth];
        for boardhieght i
                for boardwidth j
                    newboard[height][width] = board.get(i).get(j);



    }*/
    public boolean[][] getStaticBoard() {
        return board;
    }

    /**
     * The method returning the ListArray the Rules class is working with.
     * @return      The ListArray the class is working with
     */

    public List<List<Boolean>> getListBoard() {
        return listBoard;
    }

    /**
     * The method creating the next generation of cells to be drawn or removed.
     */
    public void nextGeneration(){
        boolean[][] newBoard = new boolean[board.length][board[0].length];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){

                int neighbors = countNeighbor(i, j, board);
                if(board[i][j] == false) {
                    newBoard[i][j] = shouldSpawnActiveCell(neighbors) ? true : false;
                } else {
                    newBoard[i][j] = shouldStayAlive(neighbors) ? true : false;
                }
            }
        }
        board = newBoard;
    }

    /**
     * The method creating the next generation of cells to be drawn or removed.
     */
    public void nextListGeneration(){
        newListBoard = new ArrayList<List<Boolean>>(listBoard.size());
        for(int i = 0; i < listBoard.size(); i++) {
            newListBoard.add(i, new ArrayList<Boolean>(100));
        }

        for(int i = 0; i < listBoard.size(); i++){
            for(int j = 0; j < listBoard.get(0).size(); j++){
                newListBoard.get(i).add(j,false);
            }
        }

        for(int i = 0; i < outerListSize; i++){
            for(int j = 0; j < innerListSize; j++){

                int neighbors = countNeighbor(i, j, listBoard);
                if(listBoard.get(i).get(j) == false) {
                    newListBoard.get(i).set(j,shouldSpawnActiveCell(neighbors) ? true : false);
                } else {
                    newListBoard.get(i).set(j,shouldStayAlive(neighbors) ? true : false);
                }
            }
        }
        listBoard = newListBoard;
    }

    /**
     * The method counting the alive cells surrounding the appointed cell
     * @param i     the first column index of the array
     * @param j     the second column index of the array
     * @param board the board which contains the assigned coordinates
     * @return      the number of alive neighboring cells
     */
    public static int countNeighbor(int i, int j, boolean[][] board){
        int count = 0;

        //check top
        if (isActiveCell(i, j-1, board))
            count++;

        //check top-left
        if (isActiveCell(i-1, j-1, board))
            count++;

        //check top-right
        if (isActiveCell(i+1, j-1, board))
            count++;

        //check left
        if (isActiveCell(i-1, j, board))
            count++;

        //check right
        if (isActiveCell(i+1, j, board))
            count++;

        //check bottom
        if (isActiveCell(i, j+1, board))
            count++;

        //check bottom-right
        if (isActiveCell(i+1, j+1, board))
            count++;

        //check bottom-left
        if (isActiveCell(i-1, j+1, board))
            count++;

        return count;
    }

    /**
     * The method counting the alive cells surrounding the appointed cell
     * @param i     the first column index of the array
     * @param j     the second column index of the array
     * @param board the board which contains the assigned coordinates
     * @return      the number of alive neighboring cells
     */
    public int countNeighbor(int i, int j, List<List<Boolean>> board){
        int count = 0;

        //check top
        if (isActiveCell(i, j-1, board))
            count++;

        //check top-left
        if (isActiveCell(i-1, j-1, board))
            count++;

        //check top-right
        if (isActiveCell(i+1, j-1, board))
            count++;

        //check left
        if (isActiveCell(i-1, j, board))
            count++;

        //check right
        if (isActiveCell(i+1, j, board))
            count++;

        //check bottom
        if (isActiveCell(i, j+1, board))
            count++;

        //check bottom-right
        if (isActiveCell(i+1, j+1, board))
            count++;

        //check bottom-left
        if (isActiveCell(i-1, j+1, board))
            count++;

        return count;
    }

    /**
     * The method checking if a cell should be born.
     * @param counter   the amount of alive neighbors to the cell
     * @return          <code>true</code> if the cell has exactly 3 neighbors
     */
    private static boolean shouldSpawnActiveCell(int counter) {
        return counter == 3;
    }

    /**
     * The method checking if an alive cell should stay alive or die.
     * @param counter   the amount of alive neighboring cells
     * @return          <code>true</code> if the amount of neighboring
     *                  cells is 2 or 3
     */
    private static boolean shouldStayAlive(int counter) {
        return counter == 2 || counter == 3;
    }

    /**
     * The method checking if the cell is alive.
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @param board     the board which contains the assigned coordinates
     * @return          <code>true</code> if the cell is alive
     *                  and not exceeding the board array
     */
    private static boolean isActiveCell(int i, int j, boolean[][] board) {
        return inBounds(i, j, board) && board[i][j] == true;
    }

    /**
     * The method checking if the cell is alive.
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @param board     the board which contains the assigned coordinates
     * @return          <code>true</code> if the cell is alive
     *                  and not exceeding the board array
     */
    // endret disse 2 fra statisk til ikke-statiske metoder
    private boolean isActiveCell(int i, int j, List<List<Boolean>> board) {
        return inBounds(i, j) && board.get(i).get(j) == true;
    }

    /**
     * The method checking if the appointed position is within the board array
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @param board     the board which contains the assigned coordinates
     * @return          <code>false</code> if the position is exceeding the board array
     */
    private static boolean inBounds(int i, int j, boolean[][] board){
        if(i == -1 || j == -1){
            return false;
        }

        if(i >= board.length || j >= board[0].length){
            return false;
        }

        return true;
    }

    /**
     * The method checking if the appointed position is within the board array
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @return          <code>false</code> if the position is exceeding the board array
     */
    private boolean inBounds(int i, int j){
        if(i == -1 || j == -1){
            return false;
        }

        if(i >= outerListSize || j >= innerListSize){

            return false;
        }

        return true;
    }
}


