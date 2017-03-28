package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public abstract class Board {
    protected boolean isRunning = false;
    protected boolean isClearing = false;
    protected long tid = System.nanoTime();
    public BoardGraphics boardGraphics;
    protected int x = 160;
    protected int y = 100;
    public FileHandling fileHandling = new FileHandling();

    public Rules rules = Rules.getInstance();
    public Board(){}

    /**
     * Constructor setting the BoardGraphics variable.
     * @param boardGraphics
     */
    public Board(BoardGraphics boardGraphics) {
        this.boardGraphics = boardGraphics;
    }

    /**
     * The method applying a default pattern of cells to the board.
     */
    abstract void defaultStartBoard();

    /**
     * The method starting the game over again with the preset pattern.
     */
    public abstract void newGame();


    /**
     * The method starting the animation of the board
     */
    public void start() {
        isRunning = true;
    }


    /**
     * The method return whether the animation is running or not.
     * @return      <code>true</code> if the animation
     *              is running.
     */
    public boolean getIsRunning(){
        return isRunning;
    }

    /**
     * The method clearing the board.
     */
    public abstract void clearBoard();

    /**
     * The method pausing the game by stopping the animation.
     */
    public void pauseGame(){
        isRunning = false;
    }

    /**
     * The method resuming the game by starting the animation again.
     */
    public void resumeGame(){
        isRunning = true;
    }

    /**
     * The method exiting the game.
     */
    public void exitGame(){
        System.exit(0);
    }

    /**
     * Method used to unit test {@link Rules#nextGeneration()}.
     * @return  The board array in an easy to read String format
     */
    @Override
    public abstract String toString();

    /**
     * The method allowing the user to select a pattern from disk
     */
    public abstract void selectPatternFromDisk();

    /**
     * The method allowing the user to select a pattern from a URL
     */
    public abstract void selectPatternFromURL();



}
