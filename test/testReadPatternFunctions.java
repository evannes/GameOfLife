import model.FileHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
class testReadPatternFunctions {

    private String boardStringOutput = "";
    private FileHandling fileHandling = new FileHandling();
    private boolean[][] fileArray;
    private Charset charset = Charset.forName("US-ASCII");

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

//    boolean[][] parsePattern(String fileLocation) {
//        String code = fileHandling.getCode(patternString);
//        int x = Integer.parseInt(fileHandling.getMatchGroup(patternString, "x = (\\d+)", 1));
//        int y = Integer.parseInt(fileHandling.getMatchGroup(patternString, "y = (\\d+)", 1));
//        String expandedCode = fileHandling.expand(code);
//
//        return fileHandling.createArray(expandedCode, x, y);
//
//    }
}
