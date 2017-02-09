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
    CheckBox randomColors;

    private int korX = 0;
    private int korY = 0;
    private Color cellColor = Color.LIGHTSEAGREEN;
    private Color gridColor = Color.BLACK;
    private Color boardColor = Color.WHITE;
    private GraphicsContext gc;
    private Canvas canvas;
    private boolean isRandomColors = false;

    protected byte[][] boardGrid = new byte[16][16];

    AnimationTimer drawTimer;

    private long tid = System.nanoTime();

    public Board(Canvas canvas){
        this.canvas = canvas;
    }

    public void initBoard(){
        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid.length; j++) {
                boardGrid[i][j] = (byte)(Math.random()*2);
            }
        }
    }

    public void draw(GraphicsContext gc,CheckBox randomColors) {
        drawTimer = new AnimationTimer() {
            public void handle(long now) {

                if ((now - tid) > 200000000.0) {
                    initBoard();
                    gc.setFill(gridColor);
                    gc.fillRect(0,0, 400, 400);

                    for (int i = 0; i < boardGrid.length; i++) {
                        korX = i * cellSize;

                        for (int j = 0; j < boardGrid.length; j++) {
                            korY = j * cellSize;
                            int cellSizeWithGrid = cellSize - 1;
                            if (boardGrid[i][j] == 1) {
                                if(randomColors.isSelected()) {
                                    setRandomColors();
                                }
                                gc.setFill(cellColor);
                                gc.fillRect(korX, korY, cellSizeWithGrid, cellSizeWithGrid);
                            } else {
                                gc.setFill(boardColor);
                                gc.fillRect(korX, korY, cellSizeWithGrid, cellSizeWithGrid);
                            }
                        }
                    }
                    tid = System.nanoTime();
                }
            }
        };
        drawTimer.start();
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

    public void setRandomColors(){
        cellColor = new Color(Math.random(),Math.random(),Math.random(),1);
    }
    /*
    public void clearBoard(){
        if(drawTimer != null){
            drawTimer.stop();
        }
        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid.length; j++) {
                boardGrid[i][j] = 0;
            }
        }
        draw(gc);
    }*/

    public void pauseGame(){
        drawTimer.stop();
    }

    public void exitGame(){
        System.exit(0);
    }
}