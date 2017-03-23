package sample;




import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class FileHandling {
    Rules staticRules = new Rules();

    Charset charset = Charset.forName("US-ASCII");
    private boolean[][] array;

    public FileHandling() {
    }


    public void readGameBoard(Reader r) throws IOException {

    }

    /**
     * Method for reading pattern files from disk.
     */
    public boolean[][] readPatternFromDisk() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile;

        try {
            fileChooser.setTitle("Open RLE file from disk");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("RLE file", "*.rle"));
            selectedFile = fileChooser.showOpenDialog(null);
            //selectedFile = Paths.get("fil.txt").toFile();
            if (selectedFile != null) {
                System.out.println("File selected: " + selectedFile.getName());
                System.out.println(selectedFile.toPath());

                Path inFile = selectedFile.toPath();
                BufferedReader reader = Files.newBufferedReader(inFile, charset);
                String currentLine = null;
                String patternString = "";

                while ((currentLine = reader.readLine()) != null) {
                    patternString += currentLine + "\n";
                }

                String code = getCode(patternString);
                int x = Integer.parseInt(getMatchGroup(patternString, "x = (\\d+)", 1));
                int y = Integer.parseInt(getMatchGroup(patternString, "y = (\\d+)", 1));
                String expandedCode = expand(code);
                array = createArray(expandedCode, x, y);






            } else {
                throw new FileNotFoundException("");
            }

        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the pattern file", ioe);
        }
        return array;
    }

    /**
     * Method for reading pattern files from URL.
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
                //}
                System.out.println(enteredURL);

                URL url = new URL(enteredURL);
                URLConnection conn = url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String currentLine = null;
                String patternString = "";

                while ((currentLine = reader.readLine()) != null) {
                    patternString += currentLine + "\n";
                }

            String code = getCode(patternString);
            int x = Integer.parseInt(getMatchGroup(patternString, "x = (\\d+)", 1));
            int y = Integer.parseInt(getMatchGroup(patternString, "y = (\\d+)", 1));
            String expandedCode = expand(code);
            array = createArray(expandedCode, x, y);
            }
        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the file", ioe);
        }
        return array;
    }

    public String getCode(String fileContent) {
        //System.out.println(path);
        String regex = "(\\$*[ob0-9]+\\$)+([ob0-9]+[\\$!]*)+";
        //System.out.println(content);
        Pattern pattern = Pattern.compile(regex);
        StringBuilder cellPositionCode = new StringBuilder();


        Scanner scan = new Scanner(fileContent);
        while(scan.hasNext()){
            String content = scan.nextLine();
            Matcher match = pattern.matcher(content);
            if(match.find()){
                cellPositionCode.append(match.group());
                //System.out.println(match.group());
            }
        }

        //System.out.println(cellPositionCode);
        return cellPositionCode.toString();
    }

    public String expand(String input) {

        String regex = "([0-9]+)([$bo])";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(input);
        StringBuffer result = new StringBuffer();
        while (match.find())
        {
            //holder på tegnet vi skal ekspandere
            String tmp = "";
            // i group 1 finnes et tall som angir antallet av tegnet i group 2
            for (int i = 0; i < Integer.parseInt(match.group(1)); i++) {
                tmp += match.group(2);
            }

            match.appendReplacement(result, Matcher.quoteReplacement(tmp));
        }
        match.appendTail(result);
        return result.toString();
    }

    public String getMatchGroup(String input, String regex, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(input);
        match.find();
        return match.group(group);
    }

    public boolean[][] createArray(String input, int x, int y) {
        boolean[][] result = new boolean[160][100];
        int xIndex = (160-x)/2;
        int yIndex = (100-y)/2;
        char[] charArray = input.toCharArray();
        for(char charOutput : charArray) {
            if (charOutput == '$') {
                xIndex = (160-x)/2;
                yIndex++;
            }
            else if (charOutput == 'o') {
                //System.out.println(xIndex + ", " + yIndex);
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
     * Method used to generate the error message box.
     * @param HeaderText    The text displayed in the error message box.
     * @param ioe           The exception being handled.
     */
    private void showErrorMessage(String HeaderText, IOException ioe) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(HeaderText);

        if (ioe.toString().contains("MalformedURLException")) {
            alert.setContentText("The URL entered was not valid.");
        } else if (ioe.toString().contains("FileNotFoundException")) {
            alert.setContentText("The file could not be found.");
        } else if (ioe.toString().contains("NoSuchFileException")) {
            alert.setContentText("The file could not be found.");
        } else {
            alert.setContentText("Caught IOException: " + ioe);
        }
        //System.err.format("IOException: %s%n", ioe);
        alert.showAndWait();
    }
}