import model.FileHandling;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
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

    private boolean[][] gameBoardArray;
    private Charset charset = Charset.forName("US-ASCII");
    private testReadPatternFunctions patterns = new testReadPatternFunctions();
    private String errorString;

    @Test
    public void testFirstPattern() {
        Path inFile = Paths.get(
                "test/patterns/rpentomino.rle").toAbsolutePath();
        testPatternString(inFile);
        Assertions.assertEquals(
                patterns.getBoundingBoxPattern(gameBoardArray), "010111100");
    }

    @Test
    public void testSecondPattern() {
        Path inFile = Paths.get(
                "test/patterns/prebeehive.rle").toAbsolutePath();
        testPatternString(inFile);
        Assertions.assertEquals(
                patterns.getBoundingBoxPattern(gameBoardArray), "111111");
    }

    @Test
    public void testThirdPattern() {
        Path inFile = Paths.get(
                "test/patterns/thunderbird.rle").toAbsolutePath();
        testPatternString(inFile);
        Assertions.assertEquals(
                patterns.getBoundingBoxPattern(gameBoardArray), "100001011110000");
    }

    @Test
    public void testFourthPattern() {
        Path inFile = Paths.get(
                "test/patterns/stairstephexomino.rle").toAbsolutePath();
        testPatternString(inFile);
        Assertions.assertEquals(
                patterns.getBoundingBoxPattern(gameBoardArray), "100110011001");
    }

    @Test
    public void testFifthPattern() {
        Path inFile = Paths.get(
                "test/patterns/aircraftcarrier.rle").toAbsolutePath();
        testPatternString(inFile);
        Assertions.assertEquals(
                patterns.getBoundingBoxPattern(gameBoardArray), "110100001011");
    }

    @Test
    public void testNoSuchFileException() {
        Path inFile = Paths.get("laksjdlaksdj").toAbsolutePath();
        testPatternString(inFile);
        Assertions.assertEquals("java.nio.file.NoSuchFileException", errorString);
    }

    /**
     * This method mimics {@link FileHandling#readPatternFromDisk()}.
     * It is used to get the pattern from a local file and parse it into a usable array for the game board.
     * @param inFile      The .rle file on the disk.
     * @return          The array being used to draw the game board.
     */
    private boolean[][] testPatternString(Path inFile) {
        try {
            BufferedReader reader = Files.newBufferedReader(inFile, charset);

            String currentLine = null;
            String patternString = "";

            while ((currentLine = reader.readLine()) != null) {
                patternString += currentLine + "\n";
            }
            gameBoardArray = patterns.parsePattern(patternString);
        } catch (IOException ioe) {
            errorString = ioe.getClass().getName();
        }
        return gameBoardArray;
    }


}
