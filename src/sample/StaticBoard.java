package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class StaticBoard extends Board{

    public Rules staticRules = new Rules();

    //The canvas is 800 x 500 px so in order to create square cells the array must maintain a similar ratio
    //public boolean[][] boardGrid;// = new boolean[160][100];

    public boolean[][] boardGrid = new boolean[x][y];

    AnimationTimer drawTimer;


    /**
     * Constructs and initiates the visible playing board.
     * @param canvas        the canvas the board is to be painted on
     */
    public StaticBoard(Canvas canvas) {
        super(canvas);
        staticRules.setBoard(boardGrid);
        draw(canvas);
        drawTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - tid) > speed) {
                    draw(canvas);
                    staticRules.nextGeneration();
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
    public StaticBoard(boolean[][] board) {
        staticRules.setBoard(board);
        this.boardGrid = board;
    }

    /**
     * The method applying a default pattern of cells to the board.
     */
    @Override
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
    @Override
    public void newGame() {
        clearBoard();
        staticRules.setBoard(boardGrid);
        defaultStartBoard();
        isRunning = true;
    }

    /**
     * The method drawing the board with alive cells, background.
     * and grid. The method will draw the board according to the array applied in the <code>staticRules</code> class.
     * @param canvas    the canvas to be drawn on.
     */
    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        boardGrid = staticRules.getBoard();
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
            staticRules.setBoard(boardGrid);
            draw(canvas);
        });
    }

    /**
     * The method clearing the board.
     */
    @Override
    public void clearBoard(){
        isRunning = false;

        /*for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid[0].length; j++) {*/
        for(int i = 0; i < this.boardGrid.length; i++) {
            for(int j = 0; j < this.boardGrid[0].length; j++) {
                boardGrid[i][j] = false;
            }
        }
        staticRules.setBoard(boardGrid);
        isClearing = true;
    }
    /**
     * Method used to unit test {@link Rules#nextGeneration()}.
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

    public void selectPatternFromDisk(){}
    public void selectPatternFromURL(){}

}
