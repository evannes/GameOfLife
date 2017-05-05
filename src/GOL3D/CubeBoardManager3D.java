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
 * The CubeBoardManager3D creates a cube from Box-objects,
 * for running the Game of Life on. It also handles the interaction
 * between the cube in the view and the logic in other classes.
 * Created by Elise Haram Vannes on 05.04.2017.
 */
class CubeBoardManager3D {

    private final CubeBoard3D cubeBoard3D;
    private final FileHandling fileHandling = new FileHandling();
    private final Group group;
    private int speed = 250000000;
    private boolean isRunning = false;
    private long time = System.nanoTime();
    private final int boardSize = 30;
    private final int cellSize = 50;
    private PhongMaterial blueMaterial;
    private PhongMaterial purpleMaterial;

    private List<List<Box>> boxBoard1;
    private List<List<Box>> boxBoard2;
    private List<List<Box>> boxBoard3;
    private List<List<Box>> boxBoard4;
    private List<List<Box>> boxBoard5;
    private List<List<Box>> boxBoard6;
    private List<List<Box>>[] boxArrays;

    private boolean[][] fileArray;
    private final Charset charset = Charset.forName("US-ASCII");

    /**
     * The constructor initializing the animation of Game of Life,
     * and calls on the necessary methods for initializing the cube itself.
     * The cube is made up of six different boards. When a pattern moves
     * from one board to the next, the cube moves a little bit. This is
     * because the live cells gets changed their width, height or depth,
     * and this interferes with the placement of the cube. It has been
     * chosen to still do it so, because having the live cells larger than
     * the dead cells makes the cube more visually interesting.
     *
     * @param cubeBoard3D the board
     * @param group       the group that will contain all the boxes of the cube
     */

    public CubeBoardManager3D(CubeBoard3D cubeBoard3D, Group group) {
        this.cubeBoard3D = cubeBoard3D;
        this.group = group;
        createMaterials();
        initBoxArrays();
        initArrayOfBoxarrays();
        changeBoards();
        AnimationTimer animationTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - time) > getSpeed()) {
                    cubeBoard3D.nextGenerations();
                    cubeBoard3D.switchBoards();
                    changeBoards();
                    time = System.nanoTime();
                }
            }
        };
        animationTimer.start();
    }

    /**
     * Creates all the arrays of boxes with the specific start coordinates
     * for each separate board of the cube.
     */
    private void initBoxArrays() {
        boxBoard1 = createBoxes(0, -53, 0, 53, 0, 53, true, false);
        boxBoard2 = createBoxes(0, 0, -53, 53, 53, 0, false, true);
        boxBoard3 = createBoxes(0, 1590, 0, 53, 0, 53, true, false);
        boxBoard4 = createBoxes(0, 0, 1590, 53, 53, 0, false, true);
        boxBoard5 = createBoxes(1590, 0, 0, 0, 53, 53, false, true);
        boxBoard6 = createBoxes(-53, 0, 0, 0, 53, 53, false, false);
    }

    /**
     * Changes all the boards of the cube, this is used after each next generation.
     */
    void changeBoards() {
        for (int i = 0; i < 6; i++) {
            changeBoard(i);
        }
    }

    /**
     * Changes a single board according to the values of the corresponding
     * board of boolean arrays.
     * @param indexBoard  index of the board to be changed
     */
    private void changeBoard(int indexBoard) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Box currentBox = boxArrays[indexBoard].get(i).get(j);

                if (cubeBoard3D.getBoardArrays()[indexBoard].get(i).get(j)) {
                    currentBox.setMaterial(purpleMaterial);
                    if (indexBoard == 5 || indexBoard == 4) {
                        currentBox.setWidth(cellSize + 50);
                    } else if (indexBoard == 1 || indexBoard == 3) {
                        currentBox.setDepth(cellSize + 50);
                    } else {
                        currentBox.setHeight(cellSize + 50);
                    }
                } else {
                    currentBox.setMaterial(blueMaterial);
                    if (indexBoard == 5 || indexBoard == 4) {
                        currentBox.setWidth(cellSize);
                    } else if (indexBoard == 1 || indexBoard == 3) {
                        currentBox.setDepth(cellSize);
                    } else {
                        currentBox.setHeight(cellSize);
                    }
                }
            }
        }
    }

    /**
     * Creates the boxes that makes up the cube. The increment- and reset-values
     * are used for determining which directions in the 3D space the board is to be placed.
     * The boards are placed according to the x, y and z-coordinates.
     * @param boxX          initial translateX-value
     * @param boxY          initial translateY-value
     * @param boxZ          initial translateZ-value
     * @param incrementX    <code>true</code> if boxX is to be incremented
     * @param decrementY    <code>true</code> if boxY is to be decremented
     * @param incrementZ    <code>true</code> if boxZ is to be incremented
     * @param resetX        <code>true</code> if boxX is to be incremented within the outer for-loop
     * @param resetY        <code>true</code> if boxY is to be incremented within the outer for-loop
     * @return              returns a list containing the boxes that was created
     */
    private List<List<Box>> createBoxes(int boxX, int boxY, int boxZ,
                                       int incrementX, int decrementY, int incrementZ,
                                       boolean resetX, boolean resetY) {

        List<List<Box>> boardBoxes = new ArrayList<>();

        for (int i = 0; i < boardSize; i++) {
            boardBoxes.add(i, new ArrayList<>(boardSize));
            for (int j = 0; j < boardSize; j++) {
                Box box = new Box(cellSize, cellSize, cellSize);
                boardBoxes.get(i).add(box);

                box.setTranslateX(boxX);
                box.setTranslateY(boxY);
                box.setTranslateZ(boxZ);
                group.getChildren().add(box);

                if (resetX) {
                    boxX += incrementX;
                } else if (resetY) {
                    boxY += decrementY;
                } else {
                    boxZ += incrementZ;
                }
            }

            boxX += incrementX;
            boxY += decrementY;
            boxZ += incrementZ;

            if (resetX) {
                boxX = 0;
            } else if (resetY) {
                boxY = 0;
            } else {
                boxZ = 0;
            }
        }
        return boardBoxes;
    }

    /**
     * Initializes an array that contains all the arrays of boxes.
     */
    private void initArrayOfBoxarrays(){
        boxArrays = new ArrayList[6];
        boxArrays[0] = boxBoard1;
        boxArrays[1] = boxBoard2;
        boxArrays[2] = boxBoard3;
        boxArrays[3] = boxBoard4;
        boxArrays[4] = boxBoard5;
        boxArrays[5] = boxBoard6;
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
            cubeBoard3D.setInputInBoard(cubeBoard3D.createArrayListFromArray(fileArray),1);
            changeBoard(1);
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
            cubeBoard3D.setInputInBoard(cubeBoard3D.createArrayListFromArray(fileArray),1);
            changeBoard(1);
        }
    }

    /**
     * Creates a blue and a purple PhongMaterial, which is used for
     * coloring the boxes.
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
     * Removes all the boxes the cube consists of from the group
     * node they were in, causing them to disappear from the view.
     */
    void removeBoxes() {
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
    public void pauseGame() {
        isRunning = false;
    }

    /**
     * Resumes the game by starting the animation again.
     */
    void resumeGame() {
        isRunning = true;
    }

    /**
     * Returns whether the animation is running or not.
     *
     * @return <code>true</code> if the animation
     * is running.
     */
    boolean getIsRunning() {
        return isRunning;
    }

    /**
     * Sets the speed of the animation.
     *
     * @param value the value used to set the speed of the animation
     */
    void setSpeed(int value) {
        speed = value;
    }

    /**
     * Returns the speed of the animation.
     *
     * @return the speed of the animation
     */
    private int getSpeed() {
        return speed;
    }

    /**
     * Clears all the boards of the cube.
     */
    void clearBoards() {
        isRunning = false;
        cubeBoard3D.clearBoards();
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