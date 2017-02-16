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
    private int cellSize = 1 + 15;

    Slider slider;
    CheckBox randomColors;

    private int speed = 300000000;

    private int korX = 0;
    private int korY = 0;
    private Color cellColor = Color.LIGHTSEAGREEN;
    private Color gridColor = Color.BLACK;
    private Color boardColor = Color.WHITE;
    private Canvas canvas;

    Rules rules = new Rules();

    protected boolean[][] boardGrid = new boolean[100][100];
    protected boolean[][] updatedBoard;



    AnimationTimer drawTimer;

    private long tid = System.nanoTime();

    private boolean drawRandomColors;

    public void setDrawRandomColors(boolean value) {
        drawRandomColors = value;
    }

    public Board(Canvas canvas){
        this.canvas = canvas;
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

    public void defaultStartBoard(){
        boardGrid[0][2] = true;
        boardGrid[1][2] = true;
        boardGrid[2][2] = true;
        boardGrid[2][1] = true;
        boardGrid[1][0] = true;
    }



    public void start(GraphicsContext gc) {
        rules.setBoard(boardGrid);
        drawTimer = new AnimationTimer() {
            public void handle(long now) {

                if ((now - tid) > speed) {
                    //initBoard();
                    defaultStartBoard();
                    draw(gc);
                    tid = System.nanoTime();
                }
            }
        };
        drawTimer.start();
    }

    public void draw(GraphicsContext gc) {
        rules.nextGeneration();
        updatedBoard = rules.getBoard();
        gc.setFill(gridColor);
        gc.fillRect(0,0, 401, 401);

        for (int i = 0; i < updatedBoard.length; i++) {
            korX = 1 + (i * cellSize);

            for (int j = 0; j < updatedBoard.length; j++) {
                korY = 1 + (j * cellSize);
                int cellSizeWithGrid = cellSize - 1;
                if (updatedBoard[i][j] == true) {
                    if(drawRandomColors) {
                        gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                    }
                    else {
                        gc.setFill(cellColor);
                    }

                    gc.fillRect(korX, korY, cellSizeWithGrid, cellSizeWithGrid);
                } else {
                    gc.setFill(boardColor);
                    gc.fillRect(korX, korY, cellSizeWithGrid, cellSizeWithGrid);
                }
            }
        }
    }

    protected void setCellSize(GraphicsContext gc, Slider slider) {
        this.slider = slider;
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    double newCellSize = (double)newValue;
                    cellSize = 1 + (int)newCellSize;
                });
    }

    protected void setSpeed(Slider slider) {
        this.slider = slider;
        slider.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    double nySpeed = (double)newValue;
                    speed = (int)nySpeed;
                });
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

    public void clearBoard(GraphicsContext gc){
        if(drawTimer != null){
            drawTimer.stop();
        }
        for(int i = 0; i < updatedBoard.length; i++) {
            for(int j = 0; j < updatedBoard.length; j++) {
                updatedBoard[i][j] = false;
            }
        }
        draw(gc);
    }

    public void pauseGame(){
        drawTimer.stop();
    }

    public void exitGame(){
        System.exit(0);
    }
}
