package GOL3D;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.List;

import model.FileHandling;

/**
 * The CubeBoardManager3D creates a cube from Box-objects,
 * for running the Game of Life on.
 * Created by Elise Haram Vannes on 05.04.2017.
 */
public class CubeBoardManager3D {

    private CubeBoard3D cubeBoard3D;
    private FileHandling fileHandling = new FileHandling();
    private Group group;
    private int speed = 250000000;
    private boolean isRunning = false;
    private long time = System.nanoTime();
    private int boardSize = 30;
    private int cellSize = 50;
    private PhongMaterial blueMaterial;
    private PhongMaterial purpleMaterial;

    private List<List<Box>> boxBoard1;
    private List<List<Box>> boxBoard2;
    private List<List<Box>> boxBoard3;
    private List<List<Box>> boxBoard4;
    private List<List<Box>> boxBoard5;
    private List<List<Box>> boxBoard6;
    private List<List<Box>>[] boxArrays;

    /**
     * The constructor initializing the animation of Game of Life.
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
     * Creates all the arrays of boxes.
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
     * Changes all the boards of the cube, is used after each next generation.
     */
    protected void changeBoards() {
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
                if (cubeBoard3D.getBoardArrays()[indexBoard].get(i).get(j)) {
                    boxArrays[indexBoard].get(i).get(j).setMaterial(purpleMaterial);
                    if (indexBoard == 5 || indexBoard == 4) {
                        boxArrays[indexBoard].get(i).get(j).setWidth(cellSize + 50);
                    } else if (indexBoard == 1 || indexBoard == 3) {
                        boxArrays[indexBoard].get(i).get(j).setDepth(cellSize + 50);
                    } else {
                        boxArrays[indexBoard].get(i).get(j).setHeight(cellSize + 50);
                    }
                } else {
                    boxArrays[indexBoard].get(i).get(j).setMaterial(blueMaterial);
                    if (indexBoard == 5 || indexBoard == 4) {
                        boxArrays[indexBoard].get(i).get(j).setWidth(cellSize);
                    } else if (indexBoard == 1 || indexBoard == 3) {
                        boxArrays[indexBoard].get(i).get(j).setDepth(cellSize);
                    } else {
                        boxArrays[indexBoard].get(i).get(j).setHeight(cellSize);
                    }
                }
            }
        }
    }

    /**
     * Creates the boxes that makes up the cube.
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

        List<List<Box>> boardBoxes = new ArrayList<List<Box>>();

        for (int i = 0; i < boardSize; i++) {
            boardBoxes.add(i, new ArrayList<Box>(boardSize));
        }

        for (int i = 0; i < boardSize; i++) {
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
        boolean[][] array = fileHandling.readPatternFromDisk();
        cubeBoard3D.setInputInBoard(cubeBoard3D.createArrayListFromArray(array),1);
        changeBoard(1);
    }

    /**
     * Lets the user select a rle pattern from URL.
     */
    public void selectPatternFromURL() {
        boolean[][] array = fileHandling.readPatternFromURL();
        cubeBoard3D.setInputInBoard(cubeBoard3D.createArrayListFromArray(array),1);
        changeBoard(1);
    }

    /**
     * Creates a blue and a purple PhongMaterial, which is used for the boxes.
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
     * Removes all the boxes the cube consists of from the group,
     * causing them to disappear from the view.
     */
    protected void removeBoxes() {
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
    protected void resumeGame() {
        isRunning = true;
    }

    /**
     * Returns whether the animation is running or not.
     *
     * @return <code>true</code> if the animation
     * is running.
     */
    protected boolean getIsRunning() {
        return isRunning;
    }

    /**
     * Sets the speed of the animation.
     *
     * @param value the value used to set the speed of the animation
     */
    protected void setSpeed(int value) {
        speed = value;
    }

    /**
     * Returns the speed of the animation.
     *
     * @return the speed of the animation
     */
    protected int getSpeed() {
        return speed;
    }

    /**
     * Clears all the boards.
     */
    protected void clearBoards() {
        isRunning = false;
        cubeBoard3D.clearBoards();
    }
}