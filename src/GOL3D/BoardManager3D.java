package GOL3D;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.FileChooser;
import model.FileHandling;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The BoardManager3D creates a 3D board of Box-objects, for running the Game of Life on.
 * Created by Elise Haram Vannes on 03.04.2017.
 */
class BoardManager3D{

    private int speed;
    private final Board3D board3D;
    private final FileHandling fileHandling = new FileHandling();
    private final Group group;
    private List<List<Box>> boardBoxes;
    private boolean isRunning = false;
    private long time = System.nanoTime();
    private final int boardSize = 50;
    private final int cellSize = 50;
    private int boxX = 0;
    private int boxZ = 0;
    private PhongMaterial blueMaterial;
    private PhongMaterial purpleMaterial;
    private boolean[][] fileArray;
    private final Charset charset = Charset.forName("US-ASCII");

    /**
     * The constructor initializing the animation of Game of Life with the 3D board.
     * @param board3D     the board
     * @param group       the group-node that contains the box-objects in the 3D board
     */
    public BoardManager3D(Board3D board3D, Group group) {
        this.board3D = board3D;
        this.group = group;
        createMaterials();
        createBoxes();
        changeBoard();
        AnimationTimer animationTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - time) > getSpeed()) {
                    board3D.nextGenerationConcurrent();
                    changeBoard();

                    time = System.nanoTime();
                }
            }
        };
        animationTimer.start();
    }

    /**
     * Changes the color and size of the boxes in the 3D board,
     * according to the boolean values of the board.
     */
    void changeBoard(){

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {

                if(board3D.board.get(i).get(j)) {
                    boardBoxes.get(i).get(j).setMaterial(purpleMaterial);
                    boardBoxes.get(i).get(j).setHeight(cellSize + 50);
                } else {
                    boardBoxes.get(i).get(j).setMaterial(blueMaterial);
                    boardBoxes.get(i).get(j).setHeight(cellSize);
                }
            }
        }
    }

    /**
     * Creates the boxes that make up the board, and places them at the given coordinates.
     */
    private void createBoxes(){

        boardBoxes = new ArrayList<>();

        for(int i = 0; i < boardSize; i++) {
            boardBoxes.add(i, new ArrayList<>(boardSize));
        }

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                Box box = new Box(cellSize,cellSize,cellSize);
                boardBoxes.get(i).add(j,box);

                box.setTranslateX(boxX);
                int boxY = 0;
                box.setTranslateY(boxY);
                box.setTranslateZ(boxZ);
                group.getChildren().add(box);
                boxX += 53;
            }
            boxX = 0;
            boxZ += 53;
        }
    }

    /**
     * Creates a blue and a purple PhongMaterial, which is used to color the boxes.
     */
    private void createMaterials(){
        blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.SKYBLUE);
        blueMaterial.setSpecularColor(Color.SNOW);

        purpleMaterial = new PhongMaterial();
        purpleMaterial.setDiffuseColor(Color.MEDIUMSLATEBLUE);
        purpleMaterial.setSpecularColor(Color.SKYBLUE);
    }

    /**
     * Removes all the boxes from the group node, causing the board to disappear from the view.
     */
    void removeBoxes(){
        group.getChildren().clear();
    }

    /**
     * Starts the animation of the board
     */
    public void start() {
        isRunning = true;
    }

    /**
     * Pauses the game by stopping the animation.
     */
    public void pauseGame(){
        isRunning = false;
    }

    /**
     * Resumes the game by starting the animation again.
     */
    void resumeGame(){
        isRunning = true;
    }

    /**
     * Exits the game.
     */
    public void exitGame(){
        System.exit(0);
    }

    /**
     * Lets the user select a rle pattern from disk.
     */
    public void selectPatternFromDisk() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = null;

        try {
            fileChooser.setTitle("Open RLE file from disk");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("RLE file", "*.rle"));
            selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                Path inFile = selectedFile.toPath();
                BufferedReader reader = Files.newBufferedReader(inFile, charset);
                fileArray = fileHandling.getPatternFromFile(reader);
            } else {
                throw new FileNotFoundException("Cancel was pressed - File");
            }

        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the pattern file", ioe);
        }

        if (selectedFile != null) {
            board3D.setInputInBoard(board3D.createArrayListFromArray(fileArray));
            changeBoard();
        }
    }

    /**
     * Lets the user select a rle pattern from URL.
     */
    public void selectPatternFromURL() {
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
                    fileArray = fileHandling.getPatternFromFile(reader);

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
            board3D.setInputInBoard(board3D.createArrayListFromArray(fileArray));
            changeBoard();
        }
    }

    /**
     * Returns whether the animation is running or not.
     * @return      <code>true</code> if the animation is running.
     */
    boolean getIsRunning(){
        return isRunning;
    }

    /**
     * Sets the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    void setSpeed(int value) {
        speed = value;
    }

    /**
     * Returns the speed of the animation.
     * @return  the speed of the animation
     */
    private int getSpeed(){
        return speed;
    }

    /**
     * Clears the board.
     */
    public void clearBoard(){
        isRunning = false;
        board3D.clearBoard();
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