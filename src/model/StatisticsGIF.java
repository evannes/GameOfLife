package model;

import lieng.GIFWriter;


import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * Created by Alexander Kingdon on 10.04.2017.
 */
public class StatisticsGIF {

    private Color cellColor = new Color(32, 178, 170);
    private Color boardColor = Color.WHITE;

    private int width;
    private int height;

    /**
     * This method runs {@link DynamicBoard#nextGeneration()} a set number of times to supply
     * {@link StatisticsGIF#drawGIFFrame(GIFWriter, DynamicBoard)} with board data to draw to a file.
     * @param gifBoard          The cloned board being used to supply data.
     * @param generationsOver98 An array list of generations with a similarity measure >= 98.
     * @param iterations        The number of iterations that the simulation ran, user specified.
     * @throws Exception        An {@link java.io.IOException} possibly thrown by the gif writer.
     */
    public void writeGif(DynamicBoard gifBoard, List<Integer> generationsOver98, int iterations) throws Exception {
        width = gifBoard.getWidth()*3;
        height = gifBoard.getHeight()*3;
        Random gifRandomValue = new Random();
        Random similarityRandomValueGenerator = new Random();

        try {
            DynamicBoard randomBoard = gifBoard.clone();

            String path = "GOL.gif";
            int timePerMilliSecond = 1;

            lieng.GIFWriter gif = new lieng.GIFWriter(width,height, path, timePerMilliSecond);

            for (int i = 0; i < iterations; i ++) {
                if (gifRandomValue.nextDouble() < 0.5) {
                    gifBoard.nextGeneration();
                    drawGIFFrame(gif, gifBoard);
                    gifBoard.nextGeneration();
                    drawGIFFrame(gif, gifBoard);
                } else {
                    int randomIndex = similarityRandomValueGenerator.nextInt(generationsOver98.size());
                    for (int j = 0; j < randomIndex; j ++) {
                        randomBoard.nextGeneration();
                    }
                    drawGIFFrame(gif, randomBoard);
                }
            }
            gif.close();
        } catch (CloneNotSupportedException clone) {
            System.out.println("Couldn't create clone: " + clone.getMessage());
        }
    }

    /**
     * This method uses the GIFLib supplied to draw each frame of the gif file being produced.
     * @param gif           The {@link GIFWriter} being used to draw frames.
     * @param inBoard       The board supplied by the {@link StatisticsGIF#writeGif(DynamicBoard, List, int)} method.
     * @throws Exception    An {@link java.io.IOException} possibly thrown by the gif writer.
     */
    private void drawGIFFrame(GIFWriter gif, DynamicBoard inBoard) throws Exception {
        int cellWidth = width / inBoard.getWidth();
        int cellHeight = height / inBoard.getHeight();

        Color drawColor;

        for (int i = 0; i < inBoard.getWidth(); i++) {
            for (int j = 0; j < inBoard.getHeight(); j++) {
                if (inBoard.getValue(i, j)) {
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
