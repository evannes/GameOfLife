package model;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
 * @since   1.0
 */

public abstract class Board implements Cloneable {
    int defaultWidth = 160;
    int defaultHeight = 100;
    protected Rules rules = new Rules();
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * Default constructor.
     */
    public Board(){}

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
     * Returns the width of the board.
     * @return  the width of the board
     */
    public abstract int getWidth();

    /**
     * Returns the height of the board.
     * @return the height of the board
     */
    public abstract int getHeight();

    /**
     * Sets <code>boolean</code> values to the board.
     * @param x     the first column index
     * @param y     the second column index
     * @param value the <code>boolean</code> value to be set
     */
    public abstract void setValue(int x, int y, boolean value);

    /**
     * Returns the <code>boolean</code> value of the appointed position
     * @param x the first column index
     * @param y the second column index
     * @return  the <code>boolean</code> value in this index
     */
    public abstract boolean getValue(int x, int y);

    /**
     * Toggles the <code>boolean</code> value at the appointed index.
     * @param x the first column index
     * @param y the second column index
     */
    public abstract void toggleValue(int x, int y);

    /**
     * Makes the board equal to the clone.
     */
    public abstract void switchBoard();

    /**
     * Sets values to the clone at the appointed index.
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
     * Creates the next generation of cells to be drawn or removed.
     */
    //@Deprecated
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
     * Creates the next generation of cells to be drawn by using threads.
     * To make this method thread safe the for-loop is divided into different
     * pieces to the threads never work on the same part of the arrayList.
     * @param start     the start position of the for-loop
     * @param end       the end position of the for-loop
     */
     private void nextGenerationThreadTask(int start, int end) {
         int width = getWidth();
         int height = getHeight();

         for(int i = start; i < end; i++){
             for(int j = 0; j < height; j++){
                 int neighbors = countNeighbor(i, j, width, height);
                 boolean value = getValue(i, j) ? Rules.shouldStayAlive(neighbors) : rules.shouldSpawnActiveCell(neighbors);
                 setCloneValue(i, j, value );
             }
         }
     }

    /**
     * Creates the next generation of cells to be drawn or removed using Threads.
     */
    public void nextGenerationConcurrent() {
        int width = getWidth();

        // Making start and end positions for the thread task
        // This will divide the for-loop in 4 pieces
        int x1 = width/4;
        int x2 = width/8;
        int x3 = width/12;


        Future future1 = executor.submit(()-> {
            nextGenerationThreadTask(0, x1);});
        Future future2 = executor.submit(()-> {
            nextGenerationThreadTask(x1, x2);});
        Future future3 = executor.submit(()-> {
            nextGenerationThreadTask(x2, x3);});
        Future future4 = executor.submit(()-> {
            nextGenerationThreadTask(x3, width);});

        try {
            if(future1.get() == null && future2.get() == null && future3.get() == null && future4.get()== null)
                switchBoard();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the time it takes to run nextGeneration with Threads and without.
     */
    public void nextGenerationConcurrentPrintPerformance() {
        System.out.println("med Threads: ");
        long start = System.currentTimeMillis();
        nextGenerationConcurrent();
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Med threads - Counting time (ms): " + elapsed);
        System.out.println("Uten Threads: ");
        long start2 = System.currentTimeMillis();
        nextGeneration();
        long elapsed2 = System.currentTimeMillis() - start2;
        System.out.println("Uten Threads - Counting time (ms): " + elapsed2);
    }

    /**
     * Counts the alive cells surrounding the appointed cell.
     * The width and height parameter has been included to improve performance
     * as opposed to calling the getHeight and getBoardSize methods.
     * @param x         the first column index of the array
     * @param y         the second column index of the array
     * @param width     the width of the board
     * @param height    the height of the board
     * @return      the number of living neighboring cells
     */
    public int countNeighbor(int x, int y, int width, int height){
        int count = 0;

        // checks all cells from -1 to +1 around the appointed cell
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (i == x && j == y)
                    continue;

                if(isActiveCell(i, j, width, height))
                    count++;
            }
        }      count++;

        return count;
    }

    /**
     * Checks if the cell is alive and inbounds the board.
     * The width and height parameter has been included to improve performance
     * as opposed to calling the getHeight and getBoardSize methods.
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
     * Used to unit test {@link #nextGeneration()}.
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
