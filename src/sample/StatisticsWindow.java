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
    private int similaritySpecifiedNumber = 0;
    private int iterations = 30;

    public void setClonedBoard(DynamicBoard clonedBoard) {
        this.clonedBoard = clonedBoard;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
    public void setSimilaritySpecifiedNumber(int specifiedNumber) {
        similaritySpecifiedNumber = specifiedNumber;
    }

    private int countAlive() {
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

    private int sumLivingCellCoordinates() {
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

    private double similarityMeasure(int alive, int change, int living) {
        return (ALPHA * alive) + (BETA * change) + (GAMMA * living);
    }

    private int similarityCalculation(double t1, double t2) {
        double phi = Math.min(t1, t2) / Math.max(t1, t2);
        return (int)Math.floor(phi * 100);
    }


    public int[][] getStatistics() {
        int[][] stats = new int[3][iterations+1];
        int[][] similaritySpecifiedHelper = new int[iterations+1][iterations+1];
        int[] livingCellsHelper = new int[iterations+1];
        double[] similarityMeasureHelper = new double[iterations+1];

        stats[0][0] = countAlive();
        livingCellsHelper[0] = sumLivingCellCoordinates();
        clonedBoard.nextGeneration();

        for (int i = 1; i < iterations; i++) {
            // Populate countAlive part of the array
            stats[0][i] = countAlive();

            // Populate change in living cells
            stats[1][i-1] = stats[0][i] - stats[0][i-1];

            // Populate helper table with living cells
            livingCellsHelper[i] = sumLivingCellCoordinates();

            // Populate initial value of similarity measure
            similarityMeasureHelper[i-1] = similarityMeasure(stats[0][i-1], stats[1][i-1], livingCellsHelper[i-1]);

            clonedBoard.nextGeneration();
        }

        // Correcting last value for initial similarity measure
        similarityMeasureHelper[iterations-1] = similarityMeasure(stats[0][iterations-1], stats[1][iterations-1],
                livingCellsHelper[iterations-1]);

        // Applying correct values for similarity measure
        if (similaritySpecifiedNumber == 0) {
            for (int i = 0; i < iterations; i++) {
                stats[2][i] = similarityCalculation(similarityMeasureHelper[i], similarityMeasureHelper[i + 1]);
            }
            // Correcting last value - cannot compare with empty next value
            stats[2][iterations-1] = similarityCalculation(similarityMeasureHelper[iterations-1], similarityMeasureHelper[iterations-2]);
        } else {
            for (int i = 0; i < iterations; i++) {
                similaritySpecifiedHelper[i][0] = i;
                similaritySpecifiedHelper[i][1] = similarityCalculation(similarityMeasureHelper[similaritySpecifiedNumber],
                        similarityMeasureHelper[i]);
            }
            // Cannot compare with the same iteration
            similaritySpecifiedHelper[similaritySpecifiedNumber][1] = 0;

            // Find the highest similarity
            int highestSimilarityNumber = similaritySpecifiedHelper[0][1];
            List<Integer> sameOccurrencesHelper = new ArrayList<>(1);

            for (int i = 0; i < iterations; i++) {
                if (similaritySpecifiedHelper[i][1] > highestSimilarityNumber) {
                    highestSimilarityNumber = similaritySpecifiedHelper[i][1];
                   // highestSimilarityIteration = i;
                }
            }

            // Find other occurrences of the same similarity
            for (int j = 0; j < iterations; j++) {
                if (similaritySpecifiedHelper[j][1] == highestSimilarityNumber) {
                    sameOccurrencesHelper.add(j);
                }
            }

            System.out.println("Compare with iteration: " + similaritySpecifiedNumber);
            System.out.print("The highest similarity measure found was " + highestSimilarityNumber +
            ", found in the following generations: ");
            for (Integer aSameOccurrencesHelper : sameOccurrencesHelper) {
                System.out.print(aSameOccurrencesHelper + ", ");
            }
            System.out.println("");

        }
        // used to print out all collected data
        /*for (int j = 0; j < iterations + 1; j++) {
            System.out.println(j + ": Alive " + stats[0][j] + ", Change: " + stats[1][j] +
                    ", sum living: " + livingCellsHelper[j] + ", Sim helper: " + similarityMeasureHelper[j] +
            ", final phi: " + stats[2][j] + ", specified phi: " + similaritySpecifiedHelper[j][1] +
            ", found in iteration: " + similaritySpecifiedHelper[j][0]);
        }*/
        return stats;
    }
}
