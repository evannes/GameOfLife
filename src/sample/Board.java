package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class Board {
    private boolean isRunning = false;
    private boolean isClearing = false;
    private int speed;
    private Color cellColor = Color.LIGHTSEAGREEN;
    private Color gridColor = Color.BLACK;
    private Color boardColor = Color.WHITE;
    private long tid = System.nanoTime();
    private boolean drawRandomColors;
    private double drawScale = 1;
    private double gridSize = 0.1;
    private double cellWidth;
    private double cellHeight;

    public Rules rules = new Rules();

    //The canvas is 800 x 500 px so in order to create square cells the array must maintain a similar ratio
    //public boolean[][] boardGrid;// = new boolean[160][100];

    public boolean[][] boardGrid = new boolean[160][100];

    AnimationTimer drawTimer;


    /**
     * Constructs and initiates the visible playing board.
     * @param canvas        the canvas the board is to be painted on
     */
    public Board(Canvas canvas) {
        rules.setBoard(boardGrid);
        draw(canvas);
        drawTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - tid) > speed) {
                    draw(canvas);
                    rules.nextGeneration();
                    tid = System.nanoTime();
                }

                if (isClearing){
                    isClearing = false;
                    draw(canvas);
                }
            }
        };

        drawTimer.start();
    }

    /**
     * Constructs and initiates the playing board used for unit testing.
     * @param board     the board used instead of the default board
     */
    public Board(boolean[][] board) {
        rules.setBoard(board);
        this.boardGrid = board;
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
    public void defaultStartBoard(){
        boardGrid[0][2] = true;
        boardGrid[1][2] = true;
        boardGrid[2][2] = true;
        boardGrid[2][1] = true;
        boardGrid[1][0] = true;
    }

    /**
     * The method starting the game over again with the preset pattern.
     */
    public void newGame() {
        clearBoard();
        rules.setBoard(boardGrid);
        defaultStartBoard();
        isRunning = true;
    }

    /**
     * The method drawing the board with alive cells, background
     * and grid. The method will draw the board according to the array applied in the <code>rules</code> class.
     * @param canvas    the canvas to be drawn on.
     */
    private void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        boardGrid = rules.getBoard();
        gc.setFill(gridColor);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        cellWidth = (canvas.getWidth()*drawScale - gridSize) / boardGrid.length;
        cellHeight = (canvas.getHeight()*drawScale - gridSize) / boardGrid[0].length;

        for (int i = 0; i < boardGrid.length; i++) {
            for (int j = 0; j < boardGrid[0].length; j++) {

                if(boardGrid[i][j] == true && drawRandomColors) {
                    gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                }
                else if (boardGrid[i][j] == true) {
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
     * The method scaling the board.
     * The higher the value passed in the larger the board will become.
     * @param value     the value used to change the size of the board.
     */
    protected void setDrawScale(double value) {
        drawScale = value;
        gridSize = 0.1 * value;
    }

    public void start() {
        isRunning = true;
    }

    /**
     * The method which lets the user set or remove cells manually from the board.
     * @param canvas        the canvas to get the coordinates from
     */
    public void userDrawCell(Canvas canvas){
        canvas.setOnMouseClicked(e -> {
            cellWidth = (canvas.getWidth()*drawScale + gridSize) / boardGrid.length;
            cellHeight = (canvas.getHeight()*drawScale + gridSize) / boardGrid[0].length;
            int korX = (int)e.getX();
            int korY = (int)e.getY();
            int arrayX = (int)Math.floor(korX/(int)cellWidth);
            int arrayY = (int)Math.floor(korY/(int)cellHeight);

            if(boardGrid[arrayX][arrayY] == false) {
                boardGrid[arrayX][arrayY] = true;
            } else {
                boardGrid[arrayX][arrayY] = false;
            }
            rules.setBoard(boardGrid);
            draw(canvas);
        });
    }


    ///Her er koden som laget ny størrelse på arrayet istedenfor å zoome inn og ut.
/*
    protected void setCellSize(int value) {
        isRunning = false;
       boardGrid = ConvertBoard(boardGrid, value);

        rules.setBoard(boardGrid);
        isRunning = true;
    }

    private boolean[][] ConvertBoard(boolean[][] oldBoard, int boardSize) {
        boolean[][] newBoard = new boolean[boardSize][boardSize];

        for (int i = 0; i < oldBoard.length && i < newBoard.length; i++) {
            for (int j = 0; j < oldBoard[0].length && j < newBoard[0].length; j++) {
                newBoard[i][j] = oldBoard[i][j];
            }
        }

        return newBoard;
    }
*/


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
    }

    /**
     * The method setting color to the alive cells
     * @param colorPicker       the input color to set on the cell
     */
    public void setCellColor(ColorPicker colorPicker){
        cellColor = colorPicker.getValue();
    }

    /**
     * The method setting color to the grid
     * @param colorPicker       the input color to set on the grid
     */
    public void setGridColor(ColorPicker colorPicker) {
        gridColor = colorPicker.getValue();
    }

    /**
     * The method setting color to the boards background
     * @param colorPicker       the input color to set on the boards background
     */
    public void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
    }

    /**
     * The method clearing the board.
     */
    public void clearBoard(){
        isRunning = false;

        /*for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid[0].length; j++) {*/
        for(int i = 0; i < this.boardGrid.length; i++) {
            for(int j = 0; j < this.boardGrid[0].length; j++) {
                boardGrid[i][j] = false;
            }
        }
        rules.setBoard(boardGrid);
        isClearing = true;
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
     * Method used to unit test {@link Rules#nextGeneration()}
     * @return  The board array in an easy to read String format
     */
    @Override
    public String toString(){
        String boardStringOutput = "";
        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid[0].length; j++) {
                if (boardGrid[i][j]) {
                    boardStringOutput += "1";
                    } else {
                    boardStringOutput += "0";
                    }
                }
            }
        return boardStringOutput;
    }

}
