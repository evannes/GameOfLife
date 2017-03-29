package sample;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public abstract class Board {
    protected int x;
    protected int y;
    protected IGrid grid;

    public Board() {
        // The canvas is 800 x 500 px so in order to create square cells the array must maintain a similar ratio
        x = 160;
        y = 100;
        grid = new Grid(x, y);
    }

    public Board(IGrid grid) {
        x = grid.getWidth();
        y = grid.getHeight();
        this.grid = grid;
    }

    public IGrid getGrid() {
        return grid;
    }

    public void defaultStartBoard(){
        grid.setValue(0,2,true);
        grid.setValue(1,2,true);
        grid.setValue(2,2,true);
        grid.setValue(2,1,true);
        grid.setValue(1,0,true);
    }

    /**
     * The method creating the next generation of cells to be drawn or removed.
     */
    public void nextGeneration(Rules rules) {
        IGrid clone = grid.getClone();

        for (int x = 0; x < clone.getWidth(); x++) {
            for (int y = 0; y < clone.getHeight(); y++) {
                int neighbors = countNeighbor(x, y);
                boolean value = grid.getValue(x, y) ? rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                clone.setValue(x, y, value);
            }
        }

        grid = clone;
    }


    /**
     * The method counting the alive cells surrounding the appointed cell
     * @param i     the first column index of the array
     * @param j     the second column index of the array
     * @return      the number of alive neighboring cells
     */
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
    }

    /**
     * The method checking if the cell is alive.
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @return          <code>true</code> if the cell is alive
     *                  and not exceeding the board array
     */
    private boolean isActiveCell(int i, int j) {
        return inBounds(i, j) && grid.getValue(i,j) == true;
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

        if(i >= grid.getWidth() || j >= grid.getHeight()){
            return false;
        }

        return true;
    }
}
