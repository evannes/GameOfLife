package model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Board is the abstract base class for game boards.
 * It has two child classes, {@link DynamicBoard} and
 * {@link StaticBoard} (Deprecated).
 * <p>
 * The methods related to calculating the game's next generation
 * have been implemented so that they work both with the static board
 * using two dimensional <code>boolean</code> arrays, and the dynamic board
 * using two dimensional boolean <code>ArrayList</code> arrays.
 * <p>
 * The base representation of the board is a size of 160x100 cells,
 * and each cell has a 1 pixel border around it. The border thus acts
 * as a grid surrounding the cells. It was eventually decided to
 * implement a function for the user to remove the grid
 * (setting its color equal to the board color), as the representation
 * doesn't look particularly good when a large dynamic board is drawn.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @version %I%, %G%
 * @since   1.0
 */

public abstract class Board implements Cloneable {
    int defaultWidth = 160;
    int defaultHeight = 100;
    public Rules rules = new Rules();

    /**
     * The constructor sets the default width and default height of the board.
     * @param width     the default width of the board
     * @param height    the default height of the board
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
     * The method setting <code>boolean</code> values to the board.
     * @param x     the first column index
     * @param y     the second column index
     * @param value the <code>boolean</code> value to be set
     */
    public abstract void setValue(int x, int y, boolean value);

    /**
     * The method returning the <code>boolean</code> value of the appointed position
     * @param x the first column index
     * @param y the second column index
     * @return  the <code>boolean</code> value in this index
     */
    public abstract boolean getValue(int x, int y);

    /**
     * The method toggling the <code>boolean</code> value at the appointed index.
     * @param x the first column index
     * @param y the second column index
     */
    public abstract void toggleValue(int x, int y);

    /**
     * The method making the board equals to the clone.
     */
    public abstract void switchBoard();

    /**
     * The method setting values to the clone at the appointed index.
     * @param x the first column index
     * @param y the second column index
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
        int width = getWidth();
        int height = getHeight();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int neighbors = countNeighbor(i, j, width, height);
                boolean value = getValue(i, j) ? Rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                setCloneValue(i, j, value );
            }
        }
        switchBoard();
    }

    /**
     * The method creating the task for the nextGeneration method.
     * @param quarter   Indicated which quarter section of the board to be worked on.
     *                  The board is divided into four equal quarters, split horizontally.
     */
     private void nextGenerationThreadTask(int quarter) {
         int width = getWidth();
         int height = getHeight();
         for(int i = (width/4)*quarter++; i < (width/4)*quarter; i++){
             for(int j = 0; j < height; j++){
                 int neighbors = countNeighbor(i, j, width, height);
                 boolean value = getValue(i, j) ? Rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
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
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @param width     the width of the board
     * @param height    the height of the board
     * @return      the number of living neighboring cells
     */
    public int countNeighbor(int i, int j, int width, int height){
        int count = 0;

        //check top
        if (isActiveCell(i, j-1, width, height))
            count++;

        //check top-left
        if (isActiveCell(i-1, j-1, width, height))
            count++;

        //check top-right
        if (isActiveCell(i+1, j-1, width, height))
            count++;

        //check left
        if (isActiveCell(i-1, j, width, height))
            count++;

        //check right
        if (isActiveCell(i+1, j, width, height))
            count++;

        //check bottom
        if (isActiveCell(i, j+1, width, height))
            count++;

        //check bottom-right
        if (isActiveCell(i+1, j+1, width, height))
            count++;

        //check bottom-left
        if (isActiveCell(i-1, j+1, width, height))
            count++;

        return count;
    }

    /**
     * * The method checking if the cell is alive and inbounds the board.
     * @param i         the first column index of the array
     * @param j         the second column index of the array
     * @param width     the width of the board
     * @param height    the height of the board
     * @return          <code>true</code> if the cell is alive
     *                  and not exceeding the board array
     */
    private boolean isActiveCell(int i, int j, int width, int height) {
        if(i < 0 || j < 0 || i >= width || j >= height){
            return false;
        }

        return getValue(i,j);
    }

    /**
     * Method used to unit test {@link #nextGeneration()}.
     * @return  The board array in an easy to read String format
     */
    @Override
    public abstract String toString();

    /**
     * Used to clone the board. The functionality is used both in the
     * next generation methods and in the statistics window.
     * @return                              A cloned board equal to the currently drawn one
     * @throws CloneNotSupportedException   Exception thrown if the clone couldn't be created.
     */
    @Override
    public Board clone() throws CloneNotSupportedException {
        return (Board) super.clone();
    }
}
