package sample;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 05.04.2017.
 */
public class StatisticsWindow {

    private DynamicBoard clonedBoard;
    private final static double ALPHA = 0.5d;
    private final static double BETA = 3.0d;
    private final static double GAMMA = 0.25d;

    public void setClonedBoard(DynamicBoard clonedBoard) {
        this.clonedBoard = clonedBoard;
        getStatistics(clonedBoard, 30);
    }

    public int countAlive() {
        int livingCells = 0;
        for(int i = 0; i < clonedBoard.getWidth(); i++) {
            for(int j = 0; j < clonedBoard.getHeight(); j++) {
                if (clonedBoard.getValue(i, j)) {
                    livingCells ++;
                }
            }
        }
        return livingCells;
    }

    public int sumLivingCellCoordinates() {
        int livingCells = 0;
        for(int i = 0; i < clonedBoard.getWidth(); i++) {
            for(int j = 0; j < clonedBoard.getHeight(); j++) {
                if (clonedBoard.getValue(i, j)) {
                    livingCells += i;
                    livingCells += j;
                }
            }
        }
        return livingCells;
    }

    public int similarityMeasure(int alive, int change, int living) {
        double phi = (ALPHA * alive) + (BETA * change) + (GAMMA * living);
        return (int) phi;
    }


    public int[][] getStatistics(Board clonedBoard, int iterations) {
        int[][] stats = new int[3][iterations+1];
        int[] livingCellsHelper = new int[iterations+1];

        stats[0][0] = countAlive();
        livingCellsHelper[0] = sumLivingCellCoordinates();
        System.out.println("Gamma ved t(0) = " + sumLivingCellCoordinates());
        clonedBoard.nextGeneration();
        System.out.println("Gamma ved t(1) = " + sumLivingCellCoordinates());

        for (int i = 1; i < iterations+1; i++) {
            // Populate countAlive part of the array
            stats[0][i] = countAlive();

            // Populate change in living cells
            stats[1][i-1] = stats[0][i] - stats[0][i-1];

            // Poulate helper table with living cells
            livingCellsHelper[i] = sumLivingCellCoordinates();

            // Populates initial value of similarity measure
            stats[2][i-1] = similarityMeasure(stats[0][i-1], stats[1][i-1], livingCellsHelper[i-1]);

            clonedBoard.nextGeneration();
        }

        // Correcting values for the last iteration
        stats[1][iterations] = 0;
        stats[2][iterations] = similarityMeasure(stats[0][iterations], stats[1][iterations], livingCellsHelper[30]);

        for (int j = 0; j < iterations + 1; j++) {
            System.out.println(j + ": Alive " + stats[0][j] + ", Change: " + stats[1][j] +
                    ", Initial phi: " + stats[2][j]);
        }
        return stats;
    }


}
