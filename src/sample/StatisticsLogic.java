package sample;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Kingdon on 05.04.2017.
 */
public class StatisticsLogic {

    private DynamicBoard clonedBoard;
    private DynamicBoard gifBoard;
    private final static double ALPHA = 0.5d;
    private final static double BETA = 3.0d;
    private final static double GAMMA = 0.25d;
    private int similaritySpecifiedNumber = 0;
    private int iterations = 30;
    private List<Integer> sameOccurrencesHelper;
    private int highestSimilarityNumber;
    private boolean createGIF = false;
    //private List<Integer> similarOccurrencesHelper;
    private List<Integer> similaritiesOver98;

    void setClonedBoard(DynamicBoard clonedBoard) {
        this.clonedBoard = clonedBoard;
    }
    DynamicBoard getGifBoard() {
        return this.gifBoard;
    }
    void setIterations(int iterations) {
        this.iterations = iterations;
    }
    void setSimilaritySpecifiedNumber(int specifiedNumber) {
        similaritySpecifiedNumber = specifiedNumber;
    }
    int getHighestSimilarityNumber() {
        return highestSimilarityNumber;
    }
    List<Integer> getSameOccurrencesHelper() {
        return sameOccurrencesHelper;
    }
    /*List<Integer> getSimilarOccurrencesHelper() {
        return similarOccurrencesHelper;
    }*/
    List<Integer> getSimilaritiesOver98() {
        return similaritiesOver98;
    }
    void setCreateGIF() {
        createGIF = true;
    }
    void unsetCreateGIF() {
        if (createGIF) {
            createGIF = false;
        }
    }
    boolean getCreateGIF() {
        return createGIF;
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

    /**
     * This method does all the calculations needed to produce game statistics
     * @return  An int[][] array containing the game statistics.
     */
    int[][] getStatistics() {
        try {
            gifBoard = clonedBoard.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Couldn't create cloned board for gif creation: " + e.getMessage());
        }

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

        // Correcting last value for living cells
        stats[0][iterations] = countAlive();

        // Correcting last value for initial similarity measure
        similarityMeasureHelper[iterations-1] = similarityMeasure(stats[0][iterations-1], stats[1][iterations-1],
                livingCellsHelper[iterations-1]);

        // Applying correct values for similarity measure
        if (similaritySpecifiedNumber == 0) {
            for (int i = 0; i < iterations; i++) {
                stats[2][i] = similarityCalculation(similarityMeasureHelper[i], similarityMeasureHelper[i + 1]);
            }
            // Correcting last value - cannot compare with empty next value
            stats[2][iterations-1] = similarityCalculation(
                    similarityMeasureHelper[iterations-1], similarityMeasureHelper[iterations-2]);

            if (createGIF) {
                similaritiesOver98 = new ArrayList<>(1);
                for (int i = 0; i < stats[2].length; i ++) {
                    if (stats[2][i] >= 98) {
                        similaritiesOver98.add(stats[2][i]);
                    }
                }
            }
        } else {
            for (int i = 0; i < iterations; i++) {
                similaritySpecifiedHelper[i][0] = i;
                similaritySpecifiedHelper[i][1] =
                        similarityCalculation(
                                similarityMeasureHelper[similaritySpecifiedNumber], similarityMeasureHelper[i]);
            }

            // Cannot compare with the same iteration
            similaritySpecifiedHelper[similaritySpecifiedNumber][1] = 0;

            if (!createGIF) {
                // Find the highest similarity
                highestSimilarityNumber = similaritySpecifiedHelper[0][1];
                sameOccurrencesHelper = new ArrayList<>(1);

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

                // Finally, add the data back to the original array
                for (int k = 0; k < iterations; k++) {
                    stats[2][k] = similaritySpecifiedHelper[k][1];
                }

                // used to print data concerning a specified iteration
                /*System.out.println("Compare with iteration: " + similaritySpecifiedNumber);
                System.out.print("The highest similarity measure found was " + highestSimilarityNumber +
                        ", found in the following generations: ");
                for (Integer generations : sameOccurrencesHelper) {
                    System.out.print(generations + ", ");
                }
                System.out.println("");*/
            } /*else {
                 This is the value used to specify how similar two generations
                 should be in the gif creation process using video textures.
                highestSimilarityNumber = 98;
                similarOccurrencesHelper = new ArrayList<>(1);

                for (int j = 0; j < iterations; j++) {
                    if (similaritySpecifiedHelper[j][1] >= highestSimilarityNumber) {
                        similarOccurrencesHelper.add(j);
                    }
                }
            }*/
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
