package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruker on 17.03.2017.
 */
public class DynamicRules {
    //private boolean[][] board;
    private List<List<Boolean>> board;
    private List<List<Boolean>> newBoard;
    private int outerListSize;
    private int innerListSize;

    /**
     * The method setting the board the methods in this class should work with.
     * @param board     The board to work with.
     */
    public void setBoard(List<List<Boolean>> board) {
        outerListSize = board.size();
        innerListSize = board.get(0).size();
        this.board = board;
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
    public List<List<Boolean>> getBoard() {
        return board;
    }

    /**
     * The method creating the next generation of cells to be drawn or removed.
     */
    public void nextGeneration(){
        newBoard = new ArrayList<List<Boolean>>(board.size());
        for(int i = 0; i < 160; i++) {
            newBoard.add(i, new ArrayList<Boolean>(100));
        }

        for(int i = 0; i < 160; i++){
            for(int j = 0; j < 100; j++){
                newBoard.get(i).add(j,false);
            }
        }
        //boolean[][] newBoard = new boolean[board.length][board[0].length];

        System.out.println("Outerlistsize: " + outerListSize);
        System.out.println("Innerlistsize: " + innerListSize);

        for(int i = 0; i < outerListSize; i++){
            for(int j = 0; j < innerListSize; j++){

                int neighbors = countNeighbor(i, j, board);
                if(board.get(i).get(j) == false) {
                    newBoard.get(i).set(j,shouldSpawnActiveCell(neighbors) ? true : false);
                } else {
                    newBoard.get(i).set(j,shouldStayAlive(neighbors) ? true : false);
                }
            }
        }
        board = newBoard;
    }

    /**
     * The method counting the alive cells surrounding the appointed cell
     * @param i     the first column index of the array
     * @param j     the second column index of the array
     * @param board the board which contains the assigned coordinates
     * @return      the number of alive neighboring cells
     */

    // Was private, ikke lenger static
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
    // endret disse 2 fra statisk til ikke-statiske metoder
    private boolean isActiveCell(int i, int j, List<List<Boolean>> board) {
        return inBounds(i, j, board) && board.get(i).get(j) == true;
    }

    /**
     * The method checking if the appointed position is within the board array
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @param board     the board which contains the assigned coordinates
     * @return          <code>false</code> if the position is exceeding the board array
     */
    private boolean inBounds(int i, int j, List<List<Boolean>> board){
        if(i == -1 || j == -1){
            return false;
        }

        if(i >= outerListSize || j >= innerListSize){
            //newBoard.get(i).add(j,false);
           newBoard.add(new ArrayList<Boolean>(160));
            for(int k = 0; k < 160; k++) {
                for(int m = 0; m < 100; m++) {
                    newBoard.get(k).add(k, false);
                }
            }

            /*
            for(int i = 0; i < 160; i++) {
                newBoard.add(i, new ArrayList<Boolean>(100));
            }
            for(int i = 0; i < 160; i++){
                for(int j = 0; j < 100; j++){
                    newBoard.get(i).add(j,false);
                }
            }*/
            return false;
        }

        return true;
    }
}



