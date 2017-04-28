package controller;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Board;
import model.DynamicBoard;
import model.FileHandling;
import model.StaticBoard;

/**
 * The BoardManager binds the logic from the model classes together with the view.
 * It contains all the methods related to the canvas,
 * including the <code>draw</code> method that draws the cells to the game board.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @since   1.0
 */
public class BoardManager {
    private boolean drawRandomColors;
    private double drawScale = 1;
    private double gridSize = 0.1;
    private double startingPointX;
    private double startingPointY;
    private int speed;
    Color cellColor = Color.LIGHTSEAGREEN;
    Color gridColor = Color.GRAY;
    Color boardColor = Color.WHITE;
    private Canvas canvas;
    private Board board;
    private FileHandling fileHandling = new FileHandling();
    boolean isRunning = false;
    private long time = System.nanoTime();
    private double fullBoardWidth;
    private double fullBoardHeight;
    private int scalefactorX = 80;
    private int scalefactorY = 50;
    private double boardIncrease = 1.4;
    private int scaledX = 0;
    private int scaledY = 0;

    /**
     * The constructor initializing the animation of Game of Life.
     * @param canvas    the canvas to draw the board on
     * @param board     the board to draw on the canvas
     */
    BoardManager(Canvas canvas, Board board) {
        this.canvas = canvas;
        this.board = board;
        draw();
        AnimationTimer drawTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - time) > getSpeed()) {
                    board.nextGenerationConcurrent();
                    //board.nextGenerationConcurrentPrintPerformance();

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
    void draw() {
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

                double cellX = (cellHeight * i) - startingPointX + scaledX;
                double cellY = (cellWidth * j) - startingPointY + scaledY;

                gc.fillRect(cellX + gridSize, cellY + gridSize, cellWidth - gridSize, cellHeight - gridSize);
            }
        }
    }

    /**
     * The method which lets the user set or remove cells manually from the board.
     */
    void userDrawCell(){
        canvas.setOnMouseClicked(e -> {
            double cellWidth = ((canvas.getWidth()*drawScale) + gridSize) / board.getWidth();
            double cellHeight = ((canvas.getHeight()*drawScale) + gridSize) / board.getHeight();

            double korX = (e.getX()+startingPointX - scaledX);
            double korY = (e.getY()+startingPointY) - scaledY;

            int arrayX = (int)Math.floor(korX/cellWidth);
            int arrayY = (int)Math.floor(korY/cellHeight);

            board.toggleValue(arrayX, arrayY);
            draw();
        });
    }

    /**
     * Increases the canvas size to fit a maximized stage.
     */
    public void maxCanvasSize() {
        if(canvas.getHeight() == 650)
            return;

        canvas.setHeight(canvas.getHeight()*boardIncrease);
        canvas.setWidth(canvas.getWidth()*boardIncrease);
        fullBoardWidth = canvas.getWidth()*drawScale;
        fullBoardHeight = canvas.getHeight()*drawScale;
        scalefactorX *=boardIncrease;
        scalefactorY *=boardIncrease;
        scaledX *=boardIncrease;
        scaledY *=boardIncrease;
        startingPointX = (fullBoardWidth / 2)-(canvas.getWidth()/2);
        startingPointY = (fullBoardHeight / 2)-(canvas.getHeight()/2);
        System.out.println("maximize starting x " + startingPointX);
        System.out.println("maximize starting y " + startingPointY);
        draw();
    }

    /**
     * Decreases the canvas size to fit the normal sized stage.
     */
    public void normalCanvasSize() {
        if(canvas.getHeight() == 500)
            return;

        canvas.setHeight(500);
        canvas.setWidth(800);
        fullBoardWidth = canvas.getWidth()*drawScale;
        fullBoardHeight = canvas.getHeight()*drawScale;
        scalefactorX /= boardIncrease;
        scalefactorY /= boardIncrease;
        scaledX /=boardIncrease;
        scaledY /=boardIncrease;
        startingPointX = (fullBoardWidth / 2)-(canvas.getWidth()/2);
        startingPointY = (fullBoardHeight / 2)-(canvas.getHeight()/2);
        draw();
    }

    /**
     * Moves the canvas by using the w,a,s,d keys on the keyboard.
     * It will not move the canvas outside the limit boundaries so
     * this will only work when user zooms in on the canvas.
     * @param keyEvent      the KeyEvent
     */
    public void moveCanvas(KeyEvent keyEvent) {

        if(keyEvent.getCode() == KeyCode.S) {
            if(scaledY <= -startingPointY)
                return;

            scaledY -= scalefactorY;
            draw();
        }

        if(keyEvent.getCode() == KeyCode.W) {
            if(startingPointY <= scaledY)
                return;

            scaledY += scalefactorY;
            draw();
        }
        if(keyEvent.getCode() == KeyCode.D){
            if(scaledX <= -startingPointX)
                return;

            scaledX -= scalefactorX;
            draw();
        }

        if(keyEvent.getCode() == KeyCode.A) {
            if(startingPointX <= scaledX)
                return;

            scaledX += scalefactorX;
            draw();
        }
    }

    /**
     * The method scaling the board.
     * The higher the value passed in the larger the board will become.
     * @param value     the value used to change the size of the board.
     */
    void scaleBoard(double value) {
        drawScale = value;
        gridSize = 0.1 * value;
        fullBoardWidth = canvas.getWidth()*drawScale;
        startingPointX = (fullBoardWidth / 2)-(canvas.getWidth()/2);
        fullBoardHeight = canvas.getHeight()*drawScale;
        startingPointY = (fullBoardHeight / 2)-(canvas.getHeight()/2);

        if(scaledX > startingPointX)
            scaledX -= scalefactorX;

        if(scaledX < -startingPointX)
            scaledX += scalefactorX;

        if(scaledY > startingPointY)
            scaledY -= scalefactorY;

        if(scaledY < -startingPointY)
            scaledY += scalefactorY;

        draw();

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
    void newGame() {
        clearBoard();
        scaledX = 0;
        scaledY = 0;
        boolean[][] array = fileHandling.readLocalFile("src/model/patterns/halfmax.rle");
        selectPatternLogic(array);
        //board.defaultStartBoard();
        //isRunning = true;
    }

    /**
     * The method pausing the game by stopping the animation.
     */
    void pauseGame(){
        isRunning = false;
    }

    /**
     * The method resuming the game by starting the animation again.
     */
    void resumeGame(){
        isRunning = true;
    }

    /**
     * The method exiting the game.
     */
    void exitGame(){
        System.exit(0);
    }

    /**
     * Lets the user to select a rle pattern from disk.
     */
    void selectPatternFromDisk() {
        boolean[][] array = fileHandling.readPatternFromDisk();
        selectPatternLogic(array);
    }
    /**
     * Lets the user to select a rle pattern from URL.
     */
    void selectPatternFromURL() {
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
     * Returns whether the animation is running or not.
     * @return      <code>true</code> if the animation
     *              is running.
     */
    boolean getIsRunning(){
        return isRunning;
    }

    /**
     * Clears the board.
     */
   public void clearBoard(){
        isRunning = false;
        board.clearBoard();
        draw();
    }

    /**
     * Sets the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    void setSpeed(int value) {
        speed = value;
    }

    /**
     * Returns the speed of the animation.
     * @return  the speed of the animation
     */
    private int getSpeed(){
        return speed;
    }

    /**
     * Sets color to the alive cells.
     * @param colorPicker       the input color to set on the cell
     */
    void setCellColor(ColorPicker colorPicker){
        cellColor = colorPicker.getValue();
        draw();
    }

    /**
     * Sets color to the grid.
     * @param colorPicker       the input color to set on the grid
     */
    void setGridColor(ColorPicker colorPicker) {
        gridColor = colorPicker.getValue();
        draw();
    }

    /**
     * Sets color to the boards background.
     * @param colorPicker       the input color to set on the boards background
     */
    void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
        draw();
    }


    /**
     * Sets random colors to the cells.
     * @param value     <code>true</code> if drawRandomColors is to be turned on
     */
    void setDrawRandomColors(boolean value) {
        drawRandomColors = value;
    }

    /**
     * Hides the grid. It works by making the
     * grid color the same as the board color.
     */
    void switchOffGrid() {
        gridColor = boardColor;
        draw();
    }

    /**
     * Shows the grid again. It sets the grid color
     * equal to the <code>ColorPicker</code> related to grid color.
     * @param colorPickerGrid   The <code>ColorPicker</code> related
     *                          to grid color.
     */
    void switchOnGrid(ColorPicker colorPickerGrid) {
        gridColor = colorPickerGrid.getValue();
        draw();
    }
}
