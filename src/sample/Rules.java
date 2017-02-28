package sample;

/**
 * Created by miinael on 15.02.2017.
 */
public class Rules {
    private boolean[][] board;

    /**
     * The method setting the board the methods in this class should work with.
     * @param board     The board to work on.
     */
    public void setBoard(boolean[][] board) {
        this.board = board;
    }

    /**
     * The method returning the updated board from this class
     * @return      the updated board
     */
    public boolean[][] getBoard() {
        return board;
    }

    /**
     * The method creating the next generation of cells to be drawn or removed
     */
    public void nextGeneration(){
        boolean[][] newBoard = new boolean[board.length][board[0].length];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){

                int neighbors = countNeighbor(i, j, board);
                //System.out.print(neighbors);
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
     * The method counting the alive cells surrounding the appointed cell
     * @param i     the first column index of the array
     * @param j     the second collumn index of the array
     * @param board the board we are working on
     * @return      the number of alive neighboring cells
     */
    private static int countNeighbor(int i, int j, boolean[][] board){
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
     * The method checking if a dead cell should be born.
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
     * @param board     the board we are working on
     * @return          <code>true</code> if the cell is alive(true)
     *                  and not exceeding the board array
     */
    private static boolean isActiveCell(int i, int j, boolean[][] board) {
        return inBounds(i, j, board) && board[i][j] == true;
    }

    /**
     * The method checking if the appointed position is within the board array
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @param board     the board we are working on
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
}


