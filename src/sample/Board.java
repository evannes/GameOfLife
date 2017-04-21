package sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public abstract class Board implements Cloneable {
    protected int defaultWidth = 160;
    protected int defaultHeight = 100;
    public Rules rules = new Rules();

    /**
     * The constructor sets the default width and default height of the board.
     * @param width     the width of the board
     * @param height    the height of the board
     */
    public Board(int width, int height){
        this.defaultWidth = width;
        this.defaultHeight = height;
    }

    /**
     * The method returning the width of the board.
     * @return  the height of the board
     */
    public abstract int getWidth();

    /**
     * The method returning the height of the board.
     * @return the height of the board
     */
    public abstract int getHeight();

    /**
     * The method setting values to the board.
     * @param x the first collumn index
     * @param y the second collumn index
     * @param value the value to be set
     */
    public abstract void setValue(int x, int y, boolean value);

    /**
     * The method returning the value of the appointed position
     * @param x the first collumn index
     * @param y the second collumn index
     * @return the value in this index
     */
    public abstract boolean getValue(int x, int y);

    /**
     * The method toggling the value at the appointed index.
     * @param x the first collumn index
     * @param y the second collumn index
     */
    public abstract void toggleValue(int x, int y);

    /**
     * The method making the board equals to the clone.
     */
    public abstract void switchBoard();

    /**
     * The method setting values to the clone at the appointed index.
     * @param x the first collumn index
     * @param y the second collumn index
     * @param value the value to be set
     */
    public abstract void setCloneValue(int x, int y, boolean value);

    /**
     * Sets all values of the board to false.
     */
    public abstract void clearBoard();

    /**
     * The method creating the next generation of cells to be drawn or removed.
     */
    @Deprecated
    public void nextGeneration() {
        //clearClone();

        for(int i = 0; i < getWidth(); i++){
            for(int j = 0; j < getHeight(); j++){
                int neighbors = countNeighbor(i, j);
                boolean value = getValue(i, j) ? rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                setCloneValue(i, j, value );
            }
        }
        switchBoard();
    }

    /**
     * The method creating the task for the nextGeneration method.
     * @param quarter
     */
     public void nextGenerationThreadTask(int quarter) {
         for(int i = (getWidth()/4)*quarter++; i < (getWidth()/4)*quarter; i++){
             for(int j = 0; j < getHeight(); j++){
                 int neighbors = countNeighbor(i, j);
                 boolean value = getValue(i, j) ? rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                 setCloneValue(i, j, value );
             }
         }
     }

    /**
     * The method creating the next generation of cells to be drawn or removed using Threads.
     */
    public void nextGenerationConcurrent() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.submit(()-> {
            nextGenerationThreadTask(0);});
        executor.submit(()-> {
            nextGenerationThreadTask(1);});
        executor.submit(()-> {
            nextGenerationThreadTask(2);});
        executor.submit(()-> {
            nextGenerationThreadTask(3);});
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException ie){
            System.err.println("interrupted");
        }
        finally {
            if(!executor.isTerminated()){
                System.out.println("IKKE FERDIG!!!");
            }
            executor.shutdownNow();
        }
        switchBoard();
    }

    /**
     * Prints the time it takes to run nextGeneration with Threads and without.
     */
    public void nextGenerationConcurrentPrintPerformance() {
        System.out.println("med Threads: ");
        long start = System.currentTimeMillis();
        nextGenerationConcurrent();
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Counting time (ms): " + elapsed);
        System.out.println("Uten Threads: ");
        long start2 = System.currentTimeMillis();
        nextGeneration();
        long elapsed2 = System.currentTimeMillis() - start2;
        System.out.println("Counting time (ms): " + elapsed2);
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
     * The method checking if the appointed position is within the board array.
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

    @Override
    public Board clone() throws CloneNotSupportedException {
        return (Board) super.clone();
    }
}
