package sample;

import javafx.scene.control.Label;
import lieng.GIFWriter;


import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 10.04.2017.
 */
public class StatisticsGIF {

    private Color cellColor = new Color(32, 178, 170);
    private Color boardColor = Color.WHITE;

    private int width;
    private int height;

    void writeGif(DynamicBoard gifBoard, List<Integer> generations) {

        width = gifBoard.getWidth()*3;
        height = gifBoard.getHeight()*3;

        String path = "testgif.gif";
        int timePerMilliSecond = 1;

        try {
            lieng.GIFWriter gif = new lieng.GIFWriter(width,height, path, timePerMilliSecond);

            int currentGen = generations.get(0);
            for (int i = 1; i < generations.size(); i ++) {
                for (int j = 0; j < currentGen; j ++) {
                    gifBoard.nextGeneration();
                }
                drawGIFFrame(gif, gifBoard);
                currentGen = generations.get(i) - generations.get(i-1);
            }
            for (int k = 0; k < currentGen; k ++) {
                gifBoard.nextGeneration();
            }
            drawGIFFrame(gif, gifBoard);
            gif.insertCurrentImage();
            gif.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void drawGIFFrame(GIFWriter gif, DynamicBoard gifBoard) throws Exception {
        int cellWidth = width / gifBoard.getWidth();
        int cellHeight = height / gifBoard.getHeight();

        Color drawColor;

        for (int i = 0; i < gifBoard.getWidth(); i++) {
            for (int j = 0; j < gifBoard.getHeight(); j++) {
                if (gifBoard.getValue(i, j)) {
                    drawColor = cellColor;
                }
                else {
                    drawColor = boardColor;
                }
                int cellX = cellWidth * i;
                int cellY = cellHeight * j;

                gif.fillRect(cellX, (cellX+cellWidth)-1, cellY, (cellY + cellHeight)-1, drawColor);
            }
        }
        gif.insertAndProceed();
    }
}
