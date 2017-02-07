package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Bruker on 03.02.2017.
 */
public class Board {
    private double cellSize = 100;
    // get-metode for cellsize

    protected double getCellSize() {
        return cellSize;
    }

    protected void setCellSize(double cellSize) {
        this.cellSize = cellSize;
    }

    private double korX = 0;
    private double korY = 0;

    protected byte[][] boardGrid = {
            {1,0,0,1},
            {0,0,1,1},
            {0,1,0,1},
            {1,0,0,1}
    };
    private long tid = System.nanoTime();
    public void draw(GraphicsContext gc) {
        new AnimationTimer() {
            public void handle(long now) {
                if ((now - tid) > 200000000.0) {
                    for (int i = 0; i < boardGrid.length; i++) {
                        korX = i * cellSize;
                        for (int j = 0; j < boardGrid.length; j++) {
                            korY = j * cellSize;
                            if (boardGrid[i][j] == 1) {
                                gc.setFill(Color.LIGHTSEAGREEN);
                                gc.fillRect(korX, korY, cellSize, cellSize);
                                boardGrid[i][j] = 0;

                                // x = kolonner, y = rad
                            } else {
                                gc.setFill(Color.WHITE);
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
}
