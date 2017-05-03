package GOL3D;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.List;

import model.*;

/**
 * The BoardManager3D creates a 3D board of Box-objects, for running the Game of Life on.
 * Created by Elise Haram Vannes on 03.04.2017.
 */
public class BoardManager3D{

    private int speed = 250000000;
    private Board3D board3D;
    private FileHandling fileHandling = new FileHandling();
    private Group group;
    private List<List<Box>> boardBoxes;
    private boolean isRunning = false;
    private long time = System.nanoTime();
    private int boardSize = 50;
    private int cellSize = 50;
    private int boxX = 0;
    private int boxY = 0;
    private int boxZ = 0;
    private PhongMaterial blueMaterial;
    private PhongMaterial purpleMaterial;

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
    protected void changeBoard(){

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

        boardBoxes = new ArrayList<List<Box>>();

        for(int i = 0; i < boardSize; i++) {
            boardBoxes.add(i, new ArrayList<Box>(boardSize));
        }

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                Box box = new Box(cellSize,cellSize,cellSize);
                boardBoxes.get(i).add(j,box);

                box.setTranslateX(boxX);
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
    protected void removeBoxes(){
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
    protected void resumeGame(){
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
        boolean[][] array = fileHandling.readPatternFromDisk();
        board3D.setInputInBoard(board3D.createArrayListFromArray(array));
        changeBoard();
    }
    /**
     * Lets the user select a rle pattern from URL.
     */
    public void selectPatternFromURL() {
        boolean[][] array = fileHandling.readPatternFromURL();
        board3D.setInputInBoard(board3D.createArrayListFromArray(array));
        changeBoard();
    }

    /**
     * Returns whether the animation is running or not.
     * @return      <code>true</code> if the animation is running.
     */
    protected boolean getIsRunning(){
        return isRunning;
    }

    /**
     * Sets the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    protected void setSpeed(int value) {
        speed = value;
    }

    /**
     * Returns the speed of the animation.
     * @return  the speed of the animation
     */
    protected int getSpeed(){
        return speed;
    }

    /**
     * Clears the board.
     */
    public void clearBoard(){
        isRunning = false;
        board3D.clearBoard();
    }
}