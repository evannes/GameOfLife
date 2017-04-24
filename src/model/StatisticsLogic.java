package model;


import java.util.ArrayList;
import java.util.List;

/**
 * StatisticsLogic contains all methods related to gathering
 * statistical data to be used for either displaying the data
 * to the user, or to create an animated gif based on a
 * <code>similarity measure</code>.
 * <p>
 * The class contains a number of getters and setters required
 * by the other statistics classes in addition to the base data
 * described in the extension task supplied by the professor.
 *
 * @author  Alexander Kingdon
 * @version %I%, %G%
 * @since   1.0
 */
public class StatisticsLogic {

    private DynamicBoard clonedBoard;
    private DynamicBoard gifBoard;
    private static final double ALPHA = 0.5d;
    private static final double BETA = 3.0d;
    private static final double GAMMA = 0.25d;
    private int similaritySpecifiedNumber = 0;
    private int iterations = 30;
    private List<Integer> sameOccurrencesHelper;
    private int highestSimilarityNumber;
    private boolean createGIF = false;
    private List<Integer> similaritiesOver98;

    /**
     * Initializes the board that was cloned by {@link controller.Controller}.
     * @param clonedBoard   A cloned board to be used for statistics functionality
     */
    public void setClonedBoard(DynamicBoard clonedBoard) {
        this.clonedBoard = clonedBoard;
    }

    /**
     * Checks if user asked to create gif or not.
     * @return  <code>True</code> if the user clicked "Create GIF"
     */
    public DynamicBoard getGifBoard() {
        return this.gifBoard;
    }

    /**
     * Setter method for the number of generations to produce statistics for.
     * @param iterations    Iterations specified by user
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    /**
     * Setter method used if the user wants to compare statistics to a specified generation.
     * @param specifiedNumber   User specified generation number to compare with
     */
    public void setSimilaritySpecifiedNumber(int specifiedNumber) {
        similaritySpecifiedNumber = specifiedNumber;
    }

    /**
     * Getter method used to create a <code>Label</code> text showing the user
     * what the highest similarity measure was, in a given call to {@link StatisticsLogic#getStatistics()}
     * @return  The highest similarity percentage found
     */
    public int getHighestSimilarityNumber() {
        return highestSimilarityNumber;
    }

    /**
     * Getter method for the generations (if more than one) that has a similarity measure
     * equal to or greather than 98.
     * @return  An <code>ArrayList</code> consisting of numbered generations that
     *          were found to be similar to the user specified generation
     */
    public List<Integer> getSameOccurrencesHelper() {
        return sameOccurrencesHelper;
    }

    /**
     * Produces an <code>ArrayList</code> of possible generation to jump back or forwards to
     * when creating an animated gif in {@link StatisticsGIF}.
     * @return  The <code>ArrayList</code> of possible generations
     */
    public List<Integer> getSimilaritiesOver98() {
        return similaritiesOver98;
    }

    /**
     * Setter method used to indicate that the user wishes to create an animated gif.
     */
    public void setCreateGIF() {
        createGIF = true;
    }

    /**
     * Setter method used to switch off the gif creation functionality after it has been used.
     */
    public void unsetCreateGIF() {
        if (createGIF) {
            createGIF = false;
        }
    }

    /**
     * Getter method to check if the user wanted to create an animated gif.
     * @return
     */
    public boolean getCreateGIF() {
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
    public int[][] getStatistics() {
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
            }
        }
        return stats;
    }
}
