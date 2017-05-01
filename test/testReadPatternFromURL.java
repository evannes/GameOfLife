import model.FileHandling;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    private boolean[][] gameBoardArray;
    private testReadPatternFunctions patterns = new testReadPatternFunctions();

    @Test
    public void testFirstPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/rpentomino.rle");
            testPatternString(url);
            Assertions.assertEquals(
                    patterns.getBoundingBoxPattern(gameBoardArray), "010111100");
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testSecondPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/prebeehive.rle");
            testPatternString(url);
            Assertions.assertEquals(
                    patterns.getBoundingBoxPattern(gameBoardArray), "111111");
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testThirdPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/thunderbird.rle");
            testPatternString(url);
            Assertions.assertEquals(
                    patterns.getBoundingBoxPattern(gameBoardArray), "100001011110000");
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testFourthPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/stairstephexomino.rle");
            testPatternString(url);
            Assertions.assertEquals(
                    patterns.getBoundingBoxPattern(gameBoardArray), "100110011001");
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testFifthPattern() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/aircraftcarrier.rle");
            testPatternString(url);
            Assertions.assertEquals(
                    patterns.getBoundingBoxPattern(gameBoardArray), "110100001011");
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
    }

    @Test
    public void testUnknownHostException() {
        try {
            URL url = new URL("http://saasece");
            testPatternString(url);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
            Assertions.assertEquals("The URL entered was not valid (UnknownHostException).", ioe.getMessage());
        }
    }

    @Test
    public void testNoSuchFileException() {
        try {
            URL url = new URL("http://www.conwaylife.com/patterns/halfmax.rleeee");
            testPatternString(url);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
            Assertions.assertEquals("The file could not be found (FileNotFoundException).", ioe.getMessage());
        }
    }

    @Test
    public void testWrongFileFormat() {
        try {
            URL url = new URL("https://docs.oracle.com/en/dcommon/img/oracle-doc-logo.png");
            testPatternString(url);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            Assertions.assertEquals("java.lang.IllegalStateException", e.getClass().getName());
        }
    }

    @Test
    public void testPressedCancel() {
        try {
            URL url = new URL("");
            testPatternString(url);
            Throwable exception =
            Assertions.assertThrows(NullPointerException.class, () -> {
                throw new NullPointerException("Cancel was pressed");
            });
            Assertions.assertEquals("Cancel was pressed", exception.getMessage());
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

            gameBoardArray = patterns.parsePattern(patternString);
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        }
        return gameBoardArray;
    }

}
