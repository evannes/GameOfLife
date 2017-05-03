package model;




import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * The FileHandling class contains all functions related to
 * importing custom board patterns. They can be imported
 * from either the local file system or from the internet.
 * <p>
 * The functions have been created not to be too large,
 * so that everything related to <code>regex</code> will be
 * done in its own functions, and the actual file reading
 * also has its own functions.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @since   1.0
 */
public class FileHandling {
    private boolean[][] boardArray;
    private Charset charset = Charset.forName("US-ASCII");

    /**
     * Reads a local file.
     * @param fileLocation  the <code>String</code> containing the path to the rle-file
     * @return  the boolean two-dimensional array corresponding to the code from the rle-file.
     */
    public boolean[][] readLocalFile(String fileLocation) {
        Path file = Paths.get(
                fileLocation).toAbsolutePath();

        try {
            BufferedReader reader = Files.newBufferedReader(file, charset);
            String currentLine = null;
            String patternString = "";

            while ((currentLine = reader.readLine()) != null) {
                patternString += currentLine + "\n";
            }
            String code = getCode(patternString);
            int x = Integer.parseInt(getMatchGroup(patternString, "x = (\\d+)", 1));
            int y = Integer.parseInt(getMatchGroup(patternString, "y = (\\d+)", 1));
            String expandedCode = expand(code);
            boardArray = createArray(expandedCode, x, y);
        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the pattern file", ioe);
        }

        if (file != null) {
            return boardArray;
        }

        return null;
    }

    /**
     * Reads pattern files from disk.
     * @return the boolean two-dimensional array corresponding to the code from the rle-file.
     */
    public boolean[][] readPatternFromDisk() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = null;

        try {
            fileChooser.setTitle("Open RLE file from disk");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("RLE file", "*.rle"));
            selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                Path inFile = selectedFile.toPath();
                BufferedReader reader = Files.newBufferedReader(inFile, charset);
                boardArray = getBoardArray(reader);
            } else {
                throw new FileNotFoundException("Cancel was pressed - File");
            }

        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the pattern file", ioe);
        }

        if (selectedFile != null) {
            return boardArray;
        }
        return null;
    }

    /**
     * Reads pattern files from URL.
     * @return the boolean two-dimensional array corresponding to the code from the rle-file.
     */
    public boolean[][] readPatternFromURL() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter URL");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the URL below:");
        String enteredURL = "";

        try {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                enteredURL = result.get();
                if (enteredURL.endsWith(".rle")) {
                    URL url = new URL(enteredURL);
                    URLConnection conn = url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    boardArray = getBoardArray(reader);
                } else {
                    throw new Exception("You tried to use a different file format. \n" +
                            "Only .rle files are allowed.");
                }
            } else {
                throw new NullPointerException("Cancel was pressed");
            }
        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the file", ioe);
        } catch (NullPointerException npe) {
            dialog.close();
        } catch (Exception e) {
            showErrorMessage("Only .rle files can be submitted", e);
        }

        if (!enteredURL.isEmpty()) {
            return boardArray;
        }
        return null;
    }

    private boolean[][] getBoardArray(BufferedReader reader) throws IOException {
        String currentLine;
        String patternString = "";

        while ((currentLine = reader.readLine()) != null) {
            patternString += currentLine + "\n";
        }
        String code = getCode(patternString);
        int x = Integer.parseInt(getMatchGroup(patternString, "x = (\\d+)", 1));
        int y = Integer.parseInt(getMatchGroup(patternString, "y = (\\d+)", 1));
        String expandedCode = expand(code);
        return createArray(expandedCode, x, y);
    }

    /**
     * Extracts the code for the cells.
     * @param fileContent   the <code>String</code>containing all information from the file
     * @return      the <code>String</code> containing only the code related to the cell state
     */
    public String getCode(String fileContent) {
        String regex = "^[\\$ob0-9]+[!]*$";
        Pattern pattern = Pattern.compile(regex);
        StringBuilder cellPositionCode = new StringBuilder();
        Scanner scan = new Scanner(fileContent);

        while(scan.hasNext()){
            String content = scan.nextLine();
            Matcher match = pattern.matcher(content);

            if(match.find()){
                cellPositionCode.append(match.group());
            }
        }
        return cellPositionCode.toString();
    }

    /**
     * Expands the rle code so that the new string does not contain any numbers.
     * @param input the <code>String</code> containing the code regarding cell state
     * @return      the expanded <code>String</code> with no numbers
     */
    public String expand(String input) {
        String regex = "([0-9]+)([$bo])";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        while (match.find()) {
            // keeps the charachter we want to expand
            String tmp = "";

            // in group 1 we find the number which we use to expand the character in group 2
            for (int i = 0; i < Integer.parseInt(match.group(1)); i++) {
                tmp += match.group(2);
            }
            match.appendReplacement(result, Matcher.quoteReplacement(tmp));
        }
        match.appendTail(result);
        return result.toString();
    }

    /**
     * Returns the desired group matched from the regex input.
     * @param input     the <code>String</code> to extract information from
     * @param regex     the regex expression to capture the desired information
     * @param group     the group we wish to extract information from
     * @return          the information from the matched group
     */
    public String getMatchGroup(String input, String regex, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(input);

        match.find();
        return match.group(group);
    }

    /**
     * Creates <code>boolean</code> two-dimensional array out of the rle input file content.
     * @param input     the content of the input file
     * @param x         the size of the x index of the array
     * @param y         the size of the y index of the array
     * @return          the array corresponding to the code from the rle-file with <code>true</code> and <code>false</code> values
     */
    public boolean[][] createArray(String input, int x, int y) {
        boolean[][] result = new boolean[x][y];
        int xIndex = 0;
        int yIndex = 0;
        char[] charArray = input.toCharArray();

        for(char charOutput : charArray) {
            if (charOutput == '$') {
                xIndex = 0;
                yIndex++;
            }
            else if (charOutput == 'o') {
                result[xIndex][yIndex] = true;
                xIndex++;
            }
            else if (charOutput == 'b') {
                xIndex++;
            }
        }
        return result;
    }

    /**
     * Generates the error message box.
     * @param HeaderText    The text to be shown depending on the type of error produced.
     * @param ioe           The type of exception being handled.
     */
    private void showErrorMessage(String HeaderText, Exception ioe) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(HeaderText);

        if (ioe.toString().contains("UnknownHostException")) {
            alert.setContentText("The URL entered was not valid (UnknownHostException).");
        } else if (ioe.toString().contains("MalformedURLException")) {
            alert.setContentText("The URL entered was not valid (MalformedURLException).");
            alert.showAndWait();
        } else if (ioe.toString().contains("Cancel")) {
            return;
        } else if (ioe.toString().contains("FileNotFoundException")) {
            alert.setContentText("The file could not be found (FileNotFoundException).");
            alert.showAndWait();
        } else if (ioe.toString().contains("NoSuchFileException")) {
            alert.setContentText("The file could not be found (NoSuchFileException).");
            alert.showAndWait();
        } else {
            alert.setContentText("Error: " + ioe);
            alert.showAndWait();
        }
    }

}