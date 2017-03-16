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

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class FileHandling {

    Charset charset = Charset.forName("US-ASCII");

    public void readGameBoard(Reader r) throws IOException {

    }

    /**
     * Method for reading pattern files from disk.
     */
    public void readPatternFromDisk() {
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

                System.out.println(patternString);
            }

        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the pattern file", ioe);
        }
    }

    /**
     * Method for reading pattern files from URL.
     */
    public void readPatternFromURL() {
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

                System.out.println(patternString);
            }
        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the file", ioe);
        }
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