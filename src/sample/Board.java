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
    private int cellSize = 100;
    // get-metode for cellsize
    Slider slider;


    private int korX = 0;
    private int korY = 0;
    private Color cellColor = Color.LIGHTSEAGREEN;
    private Color gridColor = Color.BLACK;
    private Color boardColor = Color.WHITE;
    private Canvas canvas;

    protected boolean[][] boardGrid = {
            {false,false,false,false,false,false,false},
            {false,false,false,true,false,false,false},
            {false,false,false,false,true,false,false},
            {false,false,true,true,true,false,false},
            {false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false},
    };
    protected boolean[][] updatedBoard;

    Rules rules = new Rules(boardGrid);

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
    public void start(GraphicsContext gc) {
        drawTimer = new AnimationTimer() {
            public void handle(long now) {

                if ((now - tid) > 200000000.0) {
                    //initBoard();
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
        gc.fillRect(0,0, 400, 400);

        for (int i = 0; i < updatedBoard.length; i++) {
            korX = i * cellSize;

            for (int j = 0; j < updatedBoard.length; j++) {
                korY = j * cellSize;
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
        slider.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    double nyCellSize = (double)newValue;
                    cellSize = 400 / (int)nyCellSize;
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
