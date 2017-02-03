package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Bruker on 03.02.2017.
 */
public class Board {
    private int cellSize = 150;
    // get-metode for cellsize
    private int korX = 0;
    private int korY = 0;

    protected byte[][] boardGrid = {
                            {1,0,0,1},
                            {0,0,1,1},
                            {0,1,0,1},
                            {1,0,0,1}
                            };

    protected void draw(GraphicsContext gc){
        new AnimationTimer() {
            public void handle(long now) {
                gc.setFill(Color.LIGHTSEAGREEN);
                for (
                        int i = 0;
                        i < boardGrid.length; i++)

                {
                    for (int j = 0; j < boardGrid.length; j++) {
                        if (boardGrid[i][j] == 1) {
                            gc.fillRect(korX, korY, cellSize, cellSize);
                            boardGrid[i][j] = 0;
                        } else {
                            gc.setFill(Color.WHITE);
                            gc.fillRect(korX, korY, cellSize, cellSize);
                            boardGrid[i][j] = 1;
                        }
                    }
                }

                korX += cellSize;
                korY += cellSize;
            }
        }.start();
    }


}
