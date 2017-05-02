import model.DynamicBoard;
import model.FileHandling;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Kingdon on 21.04.2017.
 */
public class testThreadedNextGeneration {

    private DynamicBoard board = new DynamicBoard(160, 100);
    private FileHandling fileHandling = new FileHandling();


    @Test
    void testTenGenerations() {
        List<Integer> performanceList = runThreadedTest(10);

        Assertions.assertTrue(performanceList.get(0) < performanceList.get(1));

    }

    @Test
    void testOneHundredGenerations() {
        List<Integer> performanceList = runThreadedTest(100);

        Assertions.assertTrue(performanceList.get(0) < performanceList.get(1));
    }

    @Test
    void testTwoHundredGenerations() {
        List<Integer> performanceList = runThreadedTest(200);

        Assertions.assertTrue(performanceList.get(0) < performanceList.get(1));
    }

    private List<Integer> runThreadedTest(int generations) {
        boolean[][] array = fileHandling.readLocalFile("src/model/patterns/p160dartgun.rle");
        board.setInputInBoard(board.createArrayListFromArray(array));
        List<Integer> testList = new ArrayList<>(2);

        testList.add(nextGenerationConcurrentPrintPerformance(generations));
        board.setInputInBoard(board.createArrayListFromArray(array));
        testList.add(nextGenerationPrintPerformance(generations));

        return testList;
    }

    private int nextGenerationConcurrentPrintPerformance(int generations) {
        int nextGenerationConcurrentTime = 0;
        for (int i = 0; i < generations; i++) {
            long startThreaded = System.currentTimeMillis();
            board.nextGenerationConcurrent();
            long elapsedThreaded = System.currentTimeMillis() - startThreaded;
            nextGenerationConcurrentTime += elapsedThreaded;

        }
        System.out.println(generations + " generations tid med threads: " + nextGenerationConcurrentTime);
        return nextGenerationConcurrentTime;
    }

    private int nextGenerationPrintPerformance(int generations) {
        int nextGenerationTime = 0;

        for (int i = 1; i < generations; i++) {
            long startNonThreaded = System.currentTimeMillis();
            board.nextGeneration();
            long elapsedNonThreaded = System.currentTimeMillis() - startNonThreaded;
            nextGenerationTime += elapsedNonThreaded;
        }
        System.out.println(generations + " generations tid uten threads: " + nextGenerationTime);
        return nextGenerationTime;
    }
}