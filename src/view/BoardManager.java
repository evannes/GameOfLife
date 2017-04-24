package view;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import model.Board;
import model.DynamicBoard;
import model.FileHandling;
import model.StaticBoard;

/**
 * The BoardManager class is the view class related to the main game.
 * It contains all the methods that does something graphically,
 * including the <code>draw</code> method that draws the cells on the canvas.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @version %I%, %G%
 * @since   1.0
 */
public class BoardManager {
    private boolean drawRandomColors;
    private double drawScale = 1;
    private double gridSize = 0.1;
    private double startingPointX;
    private double startingPointY;
    private int speed;
    public Color cellColor = Color.LIGHTSEAGREEN;
    public Color gridColor = Color.GRAY;
    public Color boardColor = Color.WHITE;
    private Canvas canvas;
    private Board board;
    private FileHandling fileHandling = new FileHandling();
    public boolean isRunning = false;
    private long time = System.nanoTime();

    /**
     * The constructor initializing the animation of Game of Life.
     * @param canvas    the canvas to draw the board on
     * @param board     the board to draw on the canvas
     */
    public BoardManager(Canvas canvas, Board board) {
        this.canvas = canvas;
        this.board = board;
        draw();
        AnimationTimer drawTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - time) > getSpeed()) {
                    board.nextGenerationConcurrent();

                    draw();

                    time = System.nanoTime();
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
        int width = board.getWidth();
        int height = board.getHeight();
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        double cellWidth = (canvas.getWidth()*drawScale - gridSize) / width;
        double cellHeight = (canvas.getHeight()*drawScale - gridSize) / height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if(board.getValue(i, j) && drawRandomColors) {
                    gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                }
                else if (board.getValue(i, j)) {
                    gc.setFill(cellColor);
                }
                else {
                    gc.setFill(boardColor);
                }

                double cellX = (cellHeight * i)-startingPointX;
                double cellY = (cellWidth * j)-startingPointY;

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

            double korX = (e.getX()+startingPointX);
            double korY = (e.getY()+startingPointY);

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
    public void setDrawScale(double value) {
        drawScale = value;
        gridSize = 0.1 * value;
        double fullBoardWidth = canvas.getWidth()*drawScale;
        startingPointX = (fullBoardWidth / 2)-(canvas.getWidth()/2);
        double fullBoardHeight = canvas.getHeight()*drawScale;
        startingPointY = (fullBoardHeight / 2)-(canvas.getHeight()/2);
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
        boolean[][] array = fileHandling.readLocalFile("src/model/patterns/halfmax.rle");
        selectPatternLogic(array);
        //board.defaultStartBoard();
        //isRunning = true;
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

    /**
     * The method allowing the user to select a rle pattern from disk.
     */
    public void selectPatternFromDisk() {
        boolean[][] array = fileHandling.readPatternFromDisk();
        selectPatternLogic(array);
    }
    /**
     * The method allowing the user to select a rle pattern from URL.
     */
    public void selectPatternFromURL() {
        boolean[][] array = fileHandling.readPatternFromURL();
        selectPatternLogic(array);
    }

    private void selectPatternLogic(boolean[][] array) {
        try {
            if(board instanceof DynamicBoard) {
                ((DynamicBoard) board).setInputInBoard(((DynamicBoard) board).createArrayListFromArray(array));
                draw();
            } else {
                ((StaticBoard) board).transferPatternToBoard(array);
                draw();
            }
        } catch (NullPointerException cancelException) {
        }
    }

    /**
     * The method returns whether the animation is running or not.
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
        board.clearBoard();
        draw();
    }

    /**
     * The method setting the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    public void setSpeed(int value) {
        speed = value;
    }

    /**
     * The method returning the speed of the animation.
     * @return  the speed of the animation
     */
    private int getSpeed(){
        return speed;
    }

    /**
     * The method setting color to the alive cells.
     * @param colorPicker       the input color to set on the cell
     */
    public void setCellColor(ColorPicker colorPicker){
        cellColor = colorPicker.getValue();
        draw();
    }

    /**
     * The method setting color to the grid.
     * @param colorPicker       the input color to set on the grid
     */
    public void setGridColor(ColorPicker colorPicker) {
        gridColor = colorPicker.getValue();
        draw();
    }

    /**
     * The method setting color to the boards background.
     * @param colorPicker       the input color to set on the boards background
     */
    public void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
        draw();
    }


    /**
     * The method used for setting random colors to the cells.
     * @param value     <code>true</code> if drawRandomColors is to be turned on
     */
    public void setDrawRandomColors(boolean value) {
        drawRandomColors = value;
    }

    /**
     * Nethod used to hide the grid. It works by making the
     * grid color the same as the board color.
     */
    public void switchOffGrid() {
        gridColor = boardColor;
        draw();
    }

    /**
     * Method used to show the grid again. It sets the grid color
     * equal to the <code>ColorPicker</code> related to grid color.
     * @param colorPickerGrid   The <code>ColorPicker</code> related
     *                          to grid color.
     */
    public void switchOnGrid(ColorPicker colorPickerGrid) {
        gridColor = colorPickerGrid.getValue();
        draw();
    }
}
