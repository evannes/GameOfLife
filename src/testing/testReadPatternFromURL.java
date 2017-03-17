package testing;

import org.junit.jupiter.api.Test;
import sample.FileHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class testReadPatternFromURL {

    private boolean[][] array;
    private testReadPatternFunctions patterns = new testReadPatternFunctions();

    @Test
    public void testFirstPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/rpentomino.rle");
            testPatternString(url);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testSecondPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/coesp8.rle");
            testPatternString(url);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testThirdPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/halfmax.rle");
            testPatternString(url);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testFourthPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/blinkerpuffer2.rle");
            testPatternString(url);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testFifthPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/eaterblockfrob.rle");
            testPatternString(url);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    /**
     * This method mimics {@link FileHandling#readPatternFromURL()}.
     * It is used to get the pattern from an URL and parse it into a usable array for the game board.
     * @param url   The URL where the .rle pattern is located.
     * @return      The array being used to draw the game board.
     */
    private boolean[][] testPatternString(URL url) {
        try {
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

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
