package sample;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Canvas canvas;

    Board board = new Board();

    GraphicsContext gc;

    private int cellSize = 100;
    // get-metode for cellsize
    private int korX = 0;
    private int korY = 0;

    protected byte[][] boardGrid = {
            {1,0,0,1},
            {0,0,1,1},
            {0,1,0,1},
            {1,0,0,1}
    };
    private long tid = System.nanoTime();
    public void draw(){
        new AnimationTimer() {
            public void handle(long now) {
                if ((now - tid) > 2500000000.0) {
                    for (int i = 0; i < boardGrid.length; i++) {
                        korX = i*cellSize;
                        for (int j = 0; j < boardGrid.length; j++) {
                            korY = j*cellSize;
                            if (boardGrid[i][j] == 1) {
                                gc.setFill(Color.LIGHTSEAGREEN);
                                gc.fillRect(korX, korY, cellSize, cellSize);
                                boardGrid[i][j] = 0;

                                // x = kolonner, y = rad



                                /*if(korX % 3 == 0){
                                    korY += cellSize;
                                    korX = 0;
                                } else {
                                    korX += cellSize;
                                }*/

                            } else {
                                gc.setFill(Color.WHITE);
                                gc.fillRect(korX, korY, cellSize, cellSize);
                                boardGrid[i][j] = 1;

                                /*if(korX % 3 == 0){
                                    korY += cellSize;
                                    korX = 0;
                                } else {
                                    korX += cellSize;
                                }*/
                            }
                        }
                    }


                }
            }
        }.start();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
    }

}
