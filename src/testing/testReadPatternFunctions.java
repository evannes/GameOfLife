package testing;

import model.FileHandling;

import java.net.URL;
import java.nio.file.Path;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
class testReadPatternFunctions {

    private String boardStringOutput = "";
    private FileHandling fileHandling = new FileHandling();

    /**
     * Method used to get the bounding box for the pattern provided.
     * @param gameBoardArray     The pattern array in a ready-to-be-applied format for the game board.
     * @return          The string representation of the game board.
     */
    String getBoundingBoxPattern(boolean[][] gameBoardArray) {
        if(gameBoardArray.length == 0) return "";
        int[] boundingBox = getBoundingBox(gameBoardArray);
        String str = "";
        for(int i = boundingBox[0]; i <= boundingBox[1]; i++) {
            for(int j = boundingBox[2]; j <= boundingBox[3]; j++) {
                if(gameBoardArray[i][j]) {
                    str = str + "1";
                } else {
                    str = str + "0";
                }
            }
        }
        return str;
    }

    /**
     * A helping method for {@link testReadPatternFunctions#getBoundingBoxPattern(boolean[][])}.
     * It provides the int array needed to complete the toString-operation.
     * @param gameBoardArray     The pattern array in a ready-to-be-applied format for the game board.
     * @return          The int array used in {@link testReadPatternFunctions#getBoundingBoxPattern(boolean[][])}.
     */
    private int[] getBoundingBox(boolean[][] gameBoardArray) {
        int[] boundingBox = new int[4]; // minrow maxrow mincolumn maxcolumn
        boundingBox[0] = gameBoardArray.length;
        boundingBox[1] = 0;
        boundingBox[2] = gameBoardArray[0].length;
        boundingBox[3] = 0;
        for(int i = 0; i < gameBoardArray.length; i++) {
            for(int j = 0; j < gameBoardArray[i].length; j++) {
                if(!gameBoardArray[i][j]) continue;
                if(i < boundingBox[0]) {
                    boundingBox[0] = i;
                }
                if(i > boundingBox[1]) {
                    boundingBox[1] = i;
                }
                if(j < boundingBox[2]) {
                    boundingBox[2] = j;
                }
                if(j > boundingBox[3]) {
                    boundingBox[3] = j;
                }
            }
        }
        return boundingBox;
    }

    /**
     * Simple method that mimics a toString-method. It gives a representation of the game board in a format that
     * programmers or users can read.
     * @param gameBoardArray     The pattern array in a ready-to-be-applied format for the game board.
     * @return          A string representation of the game board.
     */
    String boardStringOutput(boolean[][] gameBoardArray) {
        for (int i = 0; i < gameBoardArray.length; i++) {
            for (int j = 0; j < gameBoardArray[0].length; j++) {
                if (gameBoardArray[i][j]) {
                    boardStringOutput += "1";
                } else {
                    boardStringOutput += "0";
                }
            }
        }
        return boardStringOutput;
    }

    /**
     * This method parses the .rle file from either {@link testReadPatternFromDisk#testPatternString(Path)} or
     * {@link testReadPatternFromURL#testPatternString(URL)} into an array ready to be applied to the game board.
     * @param patternString     The string representation of an .rle file.
     * @return                  The pattern array in a ready-to-be-applied format for the game board.
     */
    boolean[][] parsePattern(String patternString) {
        String code = fileHandling.getCode(patternString);
        int x = Integer.parseInt(fileHandling.getMatchGroup(patternString, "x = (\\d+)", 1));
        int y = Integer.parseInt(fileHandling.getMatchGroup(patternString, "y = (\\d+)", 1));
        String expandedCode = fileHandling.expand(code);
        boolean[][] gameBoardArray = fileHandling.createArray(expandedCode, x, y);

        return gameBoardArray;
    }
}
