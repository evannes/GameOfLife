package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

/**
 * Created by Bruker on 03.02.2017.
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


    Rules rules = new Rules();

 // canvas er 800 x 500, så for å få kvadratiske celler må arrayet også følge en lignende ratio
    protected boolean[][] boardGrid = new boolean[160][100];

    AnimationTimer drawTimer;

    public void setDrawRandomColors(boolean value) {
        drawRandomColors = value;
    }

/*
    public void initBoard(){
        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid.length; j++) {
                boardGrid[i][j] = (byte)(Math.random()*2);
            }
        }
    }
*/

    public Board(Canvas canvas) {
        //boardGrid = new boolean[boardSize][boardSize];
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

    public void defaultStartBoard(){
        boardGrid[0][2] = true;
        boardGrid[1][2] = true;
        boardGrid[2][2] = true;
        boardGrid[2][1] = true;
        boardGrid[1][0] = true;
    }


    public void start() {
        rules.setBoard(boardGrid);
        defaultStartBoard();
        isRunning = true;
    }

    private void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //rules.nextGeneration();
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

    protected void setDrawScale(double value) {
        drawScale = value;
        gridSize = 0.1 * value;
    }

    public void userDrawCell(Canvas canvas){
        canvas.setOnMouseClicked(e -> {
            cellWidth = (canvas.getWidth()*drawScale + gridSize) / boardGrid.length;
            cellHeight = (canvas.getHeight()*drawScale + gridSize) / boardGrid[0].length;
            int korX = (int)e.getX();
            int korY = (int)e.getY();
            System.out.println("X: " + korX);
            System.out.println("Y: " + korY);
            int arrayX = (int)Math.floor(korX/(int)cellWidth);
            System.out.println("Array X:" + arrayX);
            int arrayY = (int)Math.floor(korY/(int)cellHeight);
            System.out.println("Array Y:" + arrayY);

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

    public boolean getIsRunning(){
        return isRunning;
    }

    protected void setSpeed(int value) {
        speed = value;
    }

    public void setCellColor(ColorPicker colorPicker){
        cellColor = colorPicker.getValue();
    }

    public void setGridColor(ColorPicker colorPicker) {
        gridColor = colorPicker.getValue();
    }

    public void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
    }

    public void clearBoard(){
        isRunning = false;

        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid[0].length; j++) {
                boardGrid[i][j] = false;
            }
        }

        isClearing = true;
    }

    public void pauseGame(){
        isRunning = false;
    }

    public void resumeGame(){
        isRunning = true;
    }

    public void exitGame(){
        System.exit(0);
    }

    public boolean getIsRunning(){
        return isRunning;
    }


}
