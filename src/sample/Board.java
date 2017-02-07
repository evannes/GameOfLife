package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * Created by Bruker on 03.02.2017.
 */
public class Board {
    private int cellSize = 100-1;
    // get-metode for cellsize
    private int korX = 0;
    private int korY = 0;
    private Color cellColor = Color.LIGHTSEAGREEN;
    private Color gridColor = Color.BLACK;
    private Color boardColor = Color.WHITE;


    protected byte[][] boardGrid = {
            {1,0,0,1},
            {0,0,1,1},
            {0,1,0,1},
            {1,0,0,1}
    };
    private long tid = System.nanoTime();
    public void draw(GraphicsContext gc, Canvas canvas) {
        new AnimationTimer() {
            public void handle(long now) {

                if ((now - tid) > 200000000.0) {
                    gc.setFill(gridColor);
                    gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
                    for (int i = 0; i < boardGrid.length; i++) {
                        korX = i * cellSize;
                        for (int j = 0; j < boardGrid.length; j++) {
                            korY = j * cellSize;
                            if (boardGrid[i][j] == 1) {

                                gc.setFill(cellColor);
                                gc.fillRect(korX, korY, cellSize, cellSize);
                                boardGrid[i][j] = 0;

                                // x = kolonner, y = rad
                            } else {
                                gc.setFill(boardColor);
                                gc.fillRect(korX, korY, cellSize, cellSize);
                                boardGrid[i][j] = 1;
                            }
                        }
                    }
                    tid = System.nanoTime();
                }
            }
        }.start();

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
}
