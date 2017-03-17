package testing;

import org.junit.jupiter.api.Test;
import sample.FileHandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class testReadPatternFromDisk {

    private boolean[][] array;
    private Charset charset = Charset.forName("US-ASCII");
    private testReadPatternFunctions patterns = new testReadPatternFunctions();

    @Test
    public void testFirstPattern() {
        File selectedFile = Paths.get(
                "/Users/Alex/IdeaProjects/TestProsjekt/src/testing/patterns/rpentomino.rle").toFile();
        testPatternString(selectedFile);
    }

    @Test
    public void testSecondPattern() {
        File selectedFile = Paths.get(
                "/Users/Alex/IdeaProjects/TestProsjekt/src/testing/patterns/coesp8.rle").toFile();
        testPatternString(selectedFile);
    }

    @Test
    public void testThirdPattern() {
        File selectedFile = Paths.get(
                "/Users/Alex/IdeaProjects/TestProsjekt/src/testing/patterns/halfmax.rle").toFile();
        testPatternString(selectedFile);
    }

    @Test
    public void testFourthPattern() {
        File selectedFile = Paths.get(
                "/Users/Alex/IdeaProjects/TestProsjekt/src/testing/patterns/blinkerpuffer2.rle").toFile();
        testPatternString(selectedFile);
    }

    @Test
    public void testFifthPattern() {
        File selectedFile = Paths.get(
                "/Users/Alex/IdeaProjects/TestProsjekt/src/testing/patterns/eaterblockfrob.rle").toFile();
        testPatternString(selectedFile);
    }

    /**
     * This method mimics {@link FileHandling#readPatternFromDisk()}.
     * It is used to get the pattern from a local file and parse it into a usable array for the game board.
     * @param file      The .rle file on the disk.
     * @return          The array being used to draw the game board.
     */
    private boolean[][] testPatternString(File file) {
        try {

            Path inFile = file.toPath();
            BufferedReader reader = Files.newBufferedReader(inFile, charset);

            String currentLine = null;
            String patternString = "";

            while ((currentLine = reader.readLine()) != null) {
                patternString += currentLine + "\n";
            }

            array = patterns.parsePattern(patternString);
            String boardStringOutput = patterns.boardStringOutput(array);

            System.out.println("Board size: " + boardStringOutput.length() + ", Bounding box size: " +
                    patterns.getBoundingBoxPattern(array).length());
            System.out.println("Content of getBoundingBoxPattern: " + patterns.getBoundingBoxPattern(array) + "\n");
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
        return array;
    }


}
