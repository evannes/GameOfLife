package model;

import javafx.concurrent.Task;
import lieng.GIFWriter;


import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * StatisticsGIF uses the {@link GIFWriter} library to draw an
 * animated gif file frame by frame. It works in conjunction with
 * {@link StatisticsLogic} which supplies the statistical data.
 *
 * @author  Alexander Kingdon
 * @since   1.0
 */
public class StatisticsGIF extends Task<Void> {

    private Color cellColor = new Color(32, 178, 170);
    private Color boardColor = Color.WHITE;

    private int width;
    private int height;
    private int iterations;

    private DynamicBoard gifBoard;
    private List<Integer> generationsOver98;

    StatisticsGIF(DynamicBoard gifBoard, List<Integer> generationsOver98, int iterations) {
        width = gifBoard.getWidth()*3;
        height = gifBoard.getHeight()*3;
        this.iterations = iterations;
        this.gifBoard = gifBoard;
        this.generationsOver98 = generationsOver98;
    }

    /**
     * Runs {@link DynamicBoard#nextGenerationConcurrent()} a set number of times to supply
     * {@link StatisticsGIF#drawGIFFrame(GIFWriter, DynamicBoard)} with board data to draw to a file.
     *
     */
    private void writeGif() {

        Random gifRandomValue = new Random();
        Random similarityRandomValueGenerator = new Random();

        try {
            DynamicBoard randomBoard = gifBoard.clone();

            String path = "GOL.gif";
            int timePerMilliSecond = 1;

            lieng.GIFWriter gif = new lieng.GIFWriter(width,height, path, timePerMilliSecond);

            for (int i = 0; i < iterations; i ++) {

                updateMessage((i+1) + " / " + (iterations));
                updateProgress(i+1, iterations);

                if (gifRandomValue.nextDouble() < 0.5) {
                    gifBoard.nextGenerationConcurrent();
                    drawGIFFrame(gif, gifBoard);
                    gifBoard.nextGenerationConcurrent();
                    drawGIFFrame(gif, gifBoard);
                } else {
                    int randomIndex = similarityRandomValueGenerator.nextInt(generationsOver98.size());
                    for (int j = 0; j < randomIndex; j ++) {
                        randomBoard.nextGenerationConcurrent();
                    }
                    drawGIFFrame(gif, randomBoard);
                }
            }
            gif.close();
            updateMessage(path + " created.");
        } catch (CloneNotSupportedException clone) {
            System.out.println("Couldn't create clone: " + clone.getMessage());
        } catch (IOException ioe) {
            System.out.println("Error creating GIFWriter: " + ioe);
        } catch (Exception e) {
            System.out.println("GIFWriter error: " + e);
        }
    }

    /**
     * Uses the GIFLib supplied to draw each frame of the gif file being produced.
     * @param gif           The {@link GIFWriter} being used to draw frames.
     * @param inBoard       The board supplied by the {@link StatisticsGIF#writeGif()} method.
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

    public Void call() {
        try {
            writeGif();
        } catch (Exception e) {
            System.err.println("GIFWriter error: " + e);
        }
        return null;
    }
}
