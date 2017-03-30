package sample;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public abstract class Board {
    protected int x = 160;
    protected int y = 100;
    public Rules rules = Rules.getInstance();

    public Board(){}


    /**
     * The method applying a default pattern of cells to the board.
     */
    abstract void defaultStartBoard();

    abstract void initStartBoard();

    abstract int getWidth();

    abstract int getHeight();

    abstract void setValue(int x, int y, boolean value);

    abstract boolean getValue(int x, int y);

    abstract void toggleValue(int x, int y);

    abstract void createClone();

    abstract void toggleBoards();

    abstract void setCloneValue(int x, int y, boolean value);

    /**
     * The method creating the next generation of cells to be drawn or removed.
     */
    public void nextGeneration() {
        createClone();

        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                int neighbors = countNeighbor(i, j);
                boolean value = getValue(i, j) ? rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                setCloneValue(i, j, value );
            }
        }
        toggleBoards();
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
        return inBounds(i, j) && getValue(i,j) == true;
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

        if(i >= getWidth() || j >= getHeight()){
            return false;
        }

        return true;
    }



    /**
     * Method used to unit test {@link #nextGeneration()}.
     * @return  The board array in an easy to read String format
     */
    @Override
    public abstract String toString();





}
