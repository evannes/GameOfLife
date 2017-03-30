package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by miinael on 28.03.2017.
 */
public class BoardManager {
    private boolean drawRandomColors;
    private double drawScale = 1;
    private double gridSize = 0.1;
    private int speed;
    private Color cellColor = Color.LIGHTSEAGREEN;
    private Color gridColor = Color.BLACK;
    private Color boardColor = Color.WHITE;
    private Canvas canvas;
    private Board board;
    FileHandling fileHandling = new FileHandling();
    protected boolean isRunning = false;
    protected boolean isClearing = false;
    protected long time = System.nanoTime();
    AnimationTimer drawTimer;


    private int boardSizeX;
    private int boardSizeY;

    public BoardManager(Canvas canvas, Board board) {
        this.canvas = canvas;
        this.board = board;

        board.initStartBoard();
        draw();
        drawTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - time) > getSpeed()) {
                    board.nextGeneration();
                    draw();

                    time = System.nanoTime();
                }

                if (isClearing){
                    isClearing = false;
                    draw();
                }
            }
        };

        drawTimer.start();
    }



    /**
     * The method drawing the board with alive cells, background.
     * and grid. The method will draw the board according to the array applied in the <code>Rules</code> class.
     */
    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(gridColor);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        double cellWidth = (canvas.getWidth()*drawScale - gridSize) / board.getWidth();
        double cellHeight = (canvas.getHeight()*drawScale - gridSize) / board.getHeight();

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {

                if(board.getValue(i, j) == true && drawRandomColors) {
                    gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                }
                else if (board.getValue(i, j) == true) {
                    gc.setFill(cellColor);
                }
                else {
                    gc.setFill(boardColor);
                }

                double cellX = cellHeight * i;
                double cellY = cellWidth * j;

                gc.fillRect(cellX + gridSize, cellY + gridSize, cellWidth - gridSize, cellHeight - gridSize);
            }
        }
    }


    /**
     * The method which lets the user set or remove cells manually from the board.
     */
    public void userDrawCell(){
        canvas.setOnMouseClicked(e -> {
            double cellWidth = ((canvas.getWidth()*drawScale) + gridSize) / board.getWidth();
            double cellHeight = ((canvas.getHeight()*drawScale) + gridSize) / board.getHeight();
            int korX = (int)e.getX();
            int korY = (int)e.getY();
            int arrayX = (int)Math.floor(korX/cellWidth);
            int arrayY = (int)Math.floor(korY/cellHeight);

            board.toggleValue(arrayX, arrayY);
            draw();
        });
    }


    /**
     * The method scaling the board.
     * The higher the value passed in the larger the board will become.
     * @param value     the value used to change the size of the board.
     */
    protected void setDrawScale(double value) {
        drawScale = value;
        gridSize = 0.1 * value;
    }


    /**
     * The method starting the animation of the board
     */
    public void start() {
        isRunning = true;
    }

    /**
     * The method starting the game over again with the preset pattern.
     */
    public void newGame() {
        clearBoard();
        board.defaultStartBoard();
        isRunning = true;
    }

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

    public void selectPatternFromDisk() {
        boolean[][] array = fileHandling.readPatternFromDisk();
        selectPatternLogic(array);
    }

    public void selectPatternFromURL() {
        boolean[][] array = fileHandling.readPatternFromURL();
        selectPatternLogic(array);
    }

    public void selectPatternLogic(boolean[][] array) {
        try {
            ((DynamicBoard)board).set_input_in_board(((DynamicBoard)board).createArrayListFromArray(array));
            draw();
        } catch (NullPointerException cancelException) {
        }
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
   public void clearBoard(){
        isRunning = false;
       ((DynamicBoard)board).resetBoard();
        isClearing = true;
    }


    /**
     * The method setting the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    protected void setSpeed(int value) {
        speed = value;
    }

    protected int getSpeed(){
        return speed;
    }

    /**
     * The method setting color to the alive cells.
     * @param colorPicker       the input color to set on the cell
     */
    public void setCellColor(ColorPicker colorPicker){
        cellColor = colorPicker.getValue();
    }

    /**
     * The method setting color to the grid.
     * @param colorPicker       the input color to set on the grid
     */
    public void setGridColor(ColorPicker colorPicker) {
        gridColor = colorPicker.getValue();
    }

    /**
     * The method setting color to the boards background.
     * @param colorPicker       the input color to set on the boards background
     */
    public void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
    }


    /**
     * The method used for setting random colors to the cells.
     * @param value     <code>true</code> if drawRandomColors is to be turned on
     */
    public void setDrawRandomColors(boolean value) {
        drawRandomColors = value;
    }
}
