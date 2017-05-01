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
    private double drawScale;
    private double gridSize;
    private double startingPointX;
    private double startingPointY;
    private int speed;
    Color cellColor = Color.LIGHTSEAGREEN;
    Color gridColor = Color.GRAY;
    Color boardColor = Color.WHITE;
    private Canvas canvas;
    private Canvas bgCanvas;
    private Canvas gridCanvas;
    private Board board;
    private FileHandling fileHandling = new FileHandling();
    boolean isRunning = false;
    private long time = System.nanoTime();
    private int scalefactorX = 80;
    private int scalefactorY = 50;
    private double canvasIncrease = 1.4;
    private int scaledX;
    private int scaledY;
    private boolean gridIsOn = true;
    private int canvasDefaultHeight;
    private int canvasDefaultWidth;

    /**
     * The constructor initializing the animation of Game of Life.
     * @param canvas    the canvas to draw the board on
     * @param board     the board to draw on the canvas
     */
    BoardManager(Canvas canvas, Canvas bgCanvas, Canvas gridCanvas, Board board) {
        this.canvas = canvas;
        this.bgCanvas = bgCanvas;
        this.gridCanvas = gridCanvas;
        this.board = board;
        canvasDefaultHeight = 500;
        canvasDefaultWidth = 800;
        drawBackground();
        //draw();
        drawGrid();

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
     * Draws the grid of the board.
     */
    void drawGrid() {
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(gridColor);
        gc.setLineWidth(gridSize);
        double cellWidth = (canvas.getWidth() * drawScale) / board.getWidth();
        double cellHeight = (canvas.getHeight() * drawScale) / board.getHeight();

        for (int x = 0; x < board.getWidth(); x++)
        {
            double cellX = (cellHeight * x) - startingPointX;
            gc.strokeLine(cellX, 0,cellX, canvas.getHeight());
        }

        for (int y = 0; y < board.getHeight(); y++) {
            double cellY = (cellWidth * y) - startingPointY;
            gc.strokeLine(0, cellY, canvas.getWidth(), cellY);

        }
    }

    /**
     * Draws the background of the board.
     */
    void drawBackground() {
        GraphicsContext gc = bgCanvas.getGraphicsContext2D();
        gc.setFill(boardColor);
        gc.fillRect(0,0, bgCanvas.getWidth(), bgCanvas.getHeight());
    }

    /**
     * The method drawing the active cells to the board.
     */
    void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        // find the starting points so the zoom will go towards the middle
        startingPointX = (canvas.getWidth() / 2 - scaledX) * drawScale - canvas.getWidth() / 2;
        startingPointY = (canvas.getHeight() / 2 - scaledY) * drawScale - canvas.getHeight() / 2;
        int width = board.getWidth();
        int height = board.getHeight();
        double cellWidth = (canvas.getWidth() * drawScale) / width;
        double cellHeight = (canvas.getHeight() * drawScale) / height;

        // draw alive and dead cells
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if(!board.getValue(i,j))
                    continue;

                if(board.getValue(i, j) && drawRandomColors) {
                    gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                }
                else {
                    gc.setFill(cellColor);
                }

                double cellX = (cellHeight * i) - startingPointX;
                double cellY = (cellWidth * j) - startingPointY;
                gc.fillRect(cellX, cellY, cellWidth, cellHeight);
            }
        }
    }

    /**
     * The method which lets the user set or remove cells manually from the board.
     */
    void userDrawCell(){
        gridCanvas.setOnMouseClicked(e -> {
            double cellWidth = (canvas.getWidth() * drawScale + gridSize) / board.getWidth();
            double cellHeight = (canvas.getHeight() * drawScale + gridSize) / board.getHeight();
            double coordinateX = e.getX()+startingPointX;
            double coordinateY = e.getY()+startingPointY;
            int arrayX = (int)Math.floor(coordinateX/cellWidth);
            int arrayY = (int)Math.floor(coordinateY/cellHeight);

            board.toggleValue(arrayX, arrayY);
            draw();
        });
    }

    /**
     * Increases the canvas size to fit a maximized stage.
     */
    void maxCanvasSize() {
        if(canvas.getHeight() == canvasDefaultHeight* canvasIncrease)
            return;
        int width = (int)(canvasDefaultWidth * canvasIncrease);
        int height = (int)(canvasDefaultHeight * canvasIncrease);

        canvas.setHeight(height);
        canvas.setWidth(width);
        bgCanvas.setHeight(height);
        bgCanvas.setWidth(width);
        gridCanvas.setHeight(height);
        gridCanvas.setWidth(width);

        scalefactorX *= canvasIncrease;
        scalefactorY *= canvasIncrease;
        scaledX *= canvasIncrease;
        scaledY *= canvasIncrease;
        drawBackground();
        draw();
        drawGrid();
    }

    /**
     * Decreases the canvas size to fit the normal sized stage.
     */
    void normalCanvasSize() {
        if(canvas.getHeight() == canvasDefaultHeight)
            return;

        canvas.setHeight(canvasDefaultHeight);
        canvas.setWidth(canvasDefaultWidth);
        bgCanvas.setHeight(canvasDefaultHeight);
        bgCanvas.setWidth(canvasDefaultWidth);
        gridCanvas.setHeight(canvasDefaultHeight);
        gridCanvas.setWidth(canvasDefaultWidth);
        scalefactorX /= canvasIncrease;
        scalefactorY /= canvasIncrease;
        scaledX /= canvasIncrease;
        scaledY /= canvasIncrease;
        drawBackground();
        draw();
        drawGrid();
    }

    /**
     * Moves the canvas by using the w,a,s,d keys on the keyboard.
     * It will not be possible to move up or left inside the board when it reaches the position 0x0.
     * @param keyEvent      the KeyEvent
     */
    void moveCanvas(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.S) {
            scaledY -= (int)(scalefactorY / drawScale);
        }

        if(keyEvent.getCode() == KeyCode.W) {
            scaledY += (int)(scalefactorY / drawScale);
            int maxScale = (int)(((canvas.getHeight() / 2) * drawScale - canvas.getHeight() / 2) / drawScale);
            // to lock the canvas in the position 0x0 or lower
            if (scaledY > maxScale)
                scaledY = maxScale;
        }
        if(keyEvent.getCode() == KeyCode.D){
            scaledX -= (int)(scalefactorX / drawScale);
        }

        if(keyEvent.getCode() == KeyCode.A) {
            scaledX += (int)(scalefactorX / drawScale);
            int maxScale = (int)(((canvas.getWidth() / 2) * drawScale - canvas.getWidth() / 2) / drawScale);
            // to lock the canvas in the position 0x0 or lower
            if (scaledX > maxScale)
                scaledX = maxScale;
        }

        draw();
        drawGrid();
    }

    /**
     * The method scaling the board.
     * The higher the value passed in the larger the board will become.
     * @param value     the value used to change the size of the board.
     */
    void scaleBoard(double value) {
        drawScale = value;
        gridSize = 0.1 * drawScale;
        draw();
        drawGrid();
    }

    /**
     * Starting the animation of the board.
     */
    public void start() {
        isRunning = true;
    }

    /**
     * Starting the game over again with the preset pattern.
     */
    void newGame() {
        clearBoard();
        scaledX = 0;
        scaledY = 0;
        boolean[][] array = fileHandling.readLocalFile("src/model/patterns/halfmax.rle");
        selectPatternLogic(array);
    }

    /**
     * Pausing the game by stopping the animation.
     */
    void pauseGame(){
        isRunning = false;
    }

    /**
     * Resuming the game by starting the animation again.
     */
    void resumeGame(){
        isRunning = true;
    }

    /**
     * Exiting the game.
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
                drawGrid();
            } else {
                ((StaticBoard) board).transferPatternToBoard(array);
                draw();
                drawGrid();
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
        draw();
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
        drawGrid();
    }

    /**
     * Sets color to the boards background.
     * @param colorPicker       the input color to set on the boards background
     */
    void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
        drawBackground();
        if(!gridIsOn){
            gridColor = boardColor;
        }
        drawBackground();
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
        gridIsOn = false;
        gridColor = boardColor;
        drawGrid();
    }

    /**
     * Shows the grid again. It sets the grid color
     * equal to the <code>ColorPicker</code> related to grid color.
     * @param colorPickerGrid   The <code>ColorPicker</code> related
     *                          to grid color.
     */
    void switchOnGrid(ColorPicker colorPickerGrid) {
        gridIsOn = true;
        gridColor = colorPickerGrid.getValue();
        drawGrid();
    }
}
