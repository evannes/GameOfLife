package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * Created by miinael on 23.03.2017.
 */
public abstract class Board {
    protected boolean isRunning = false;
    protected boolean isClearing = false;
    protected int speed;
    protected Color cellColor = Color.LIGHTSEAGREEN;
    protected Color gridColor = Color.BLACK;
    protected Color boardColor = Color.WHITE;
    protected long tid = System.nanoTime();
    protected boolean drawRandomColors;
    protected double drawScale = 1;
    protected double gridSize = 0.1;
    protected double cellWidth;
    protected double cellHeight;
    protected Canvas canvas;
    protected int x = 160;
    protected int y = 100;

    public Rules staticRules = new Rules();


    /**
     * Constructs and initiates the visible playing board.
     * @param canvas        the canvas the board is to be painted on
     */
    public Board(Canvas canvas) {
        this.canvas = canvas;
    }

    public Board() {
    }


    /**
     * The method used for setting random colors to the cells.
     * @param value     <code>true</code> if drawRandomColors is to be turned on
     */
    public void setDrawRandomColors(boolean value) {
        drawRandomColors = value;
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
     * The method drawing the board with alive cells, background.
     * and grid. The method will draw the board according to the array applied in the <code>staticRules</code> class.
     * @param canvas    the canvas to be drawn on.
     */
    abstract void draw(Canvas canvas);

    /**
     * The method scaling the board.
     * The higher the value passed in the larger the board will become.
     * @param value     the value used to change the size of the board.
     */
    protected void setDrawScale(double value) {
        drawScale = value;
        gridSize = 0.1 * value;
        draw(canvas);
    }

    public void start() {
        isRunning = true;
    }

    /**
     * The method which lets the user set or remove cells manually from the board.
     * @param canvas        the canvas to get the coordinates from
     */
    abstract void userDrawCell(Canvas canvas);

    /**
     * The method return whether the animation is running or not.
     * @return      <code>true</code> if the animation
     *              is running.
     */
    public boolean getIsRunning(){
        return isRunning;
    }

    /**
     * The method setting the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    protected void setSpeed(int value) {
        speed = value;
        draw(canvas);
    }

    /**
     * The method setting color to the alive cells.
     * @param colorPicker       the input color to set on the cell
     */
    public void setCellColor(ColorPicker colorPicker){
        cellColor = colorPicker.getValue();
        draw(canvas);
    }

    /**
     * The method setting color to the grid.
     * @param colorPicker       the input color to set on the grid
     */
    public void setGridColor(ColorPicker colorPicker) {
        gridColor = colorPicker.getValue();
        draw(canvas);
    }

    /**
     * The method setting color to the boards background.
     * @param colorPicker       the input color to set on the boards background
     */
    public void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
        draw(canvas);
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


    public abstract void selectPatternFromDisk();
    public abstract void selectPatternFromURL();



}
