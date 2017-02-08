package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    private GraphicsContext gc;
    private Canvas canvas;

    /*
    protected byte[][] boardGrid = {
            {1,0,0,1},
            {0,0,1,1},
            {0,1,0,1},
            {1,0,0,1}
    };*/

    protected byte[][] boardGrid = new byte[16][16];

    AnimationTimer drawTimer;

    private long tid = System.nanoTime();

    public Board(Canvas canvas){
        this.canvas = canvas;
        initBoard();
    }

    public void initBoard(){
        for(int i = 0; i < boardGrid.length; i++) {
            for(int j = 0; j < boardGrid.length; j++) {
                boardGrid[i][j] = (byte)(Math.random()*2);
                System.out.println(boardGrid[i][j]);
            }
        }
    }

    public void draw(GraphicsContext gc) {
        drawTimer = new AnimationTimer() {
            public void handle(long now) {

                if ((now - tid) > 200000000.0) {

                    gc.setFill(gridColor);
                    gc.fillRect(0,0, 400, 400);

                    for (int i = 0; i < boardGrid.length; i++) {
                        korX = i * cellSize;

                        for (int j = 0; j < boardGrid.length; j++) {
                            korY = j * cellSize;
                            if (boardGrid[i][j] == 1) {

                                gc.setFill(cellColor);
                                gc.fillRect(korX, korY, cellSize - 1, cellSize - 1);
                                boardGrid[i][j] = 0;

                                // x = kolonner, y = rad
                            } else {
                                gc.setFill(boardColor);
                                gc.fillRect(korX, korY, cellSize - 1, cellSize - 1);
                                boardGrid[i][j] = 1;
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
                    //gc.setFill(gridColor); // gridColor?
                    gc.fillRect(0, 0, 400, 400); // canvas.getWidth(), canvas.getHeight()
                    double nyCellSize = (double)newValue;
                    cellSize = 400 / (int)nyCellSize;
                    System.out.println(cellSize);
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
    }

    public void pauseGame(){
        drawTimer.stop();
    }

    public void exitGame(){
        System.exit(0);
    }
}
