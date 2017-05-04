import controller.BoardManager;
import model.DynamicBoard;
import model.FileHandling;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * These unit tests are meant to show that the game will run faster when threaded.
 * In practice, that is not always the case and the tests will sometimes pass and
 * sometimes fail. We assume that this is because it takes time to initialize the
 * thread pool and run tasks on the threads.
 *
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class testThreadedNextGeneration {

    private DynamicBoard board = new DynamicBoard(160, 100);
    private FileHandling fileHandling = new FileHandling();
    private Charset charset = Charset.forName("US-ASCII");


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
        boolean[][] array = null;
        Path inFile = Paths.get(
                "test/patterns/p160dartgun.rle").toAbsolutePath();

        try {
            BufferedReader reader = Files.newBufferedReader(inFile, charset);
            array = fileHandling.getPatternFromFile(reader);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }

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