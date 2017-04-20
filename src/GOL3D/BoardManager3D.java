package GOL3D;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.List;

import sample.*;

/**
 * Created by Elise Haram Vannes on 03.04.2017.
 */
public class BoardManager3D{

    // inneholder boxer.. changeBoard, setPurpleMaterial, createBoxes

    private AnimationTimer animationTimer;
    private int speed = 250000000;
    private Board3D board3D;
    private CubeBoard3D cubeBoard3D;
    private FileHandling fileHandling = new FileHandling();
    private Group group;
    private List<List<Box>> boardBoxes;
    private boolean isRunning = false;
    private boolean isClearing = false;
    private long time = System.nanoTime();
    private int boardSize = 30;
    private int cellSize = 50;
    private int boxX = 0;
    private int boxY = 0;
    private int boxZ = 0;
    private PhongMaterial blueMaterial;
    private PhongMaterial greenMaterial;
    private PhongMaterial purpleMaterial;
    private PhongMaterial pinkMaterial;

    /**
     * The constructor initializing the animation of Game of Life.
     * @param board3D     the board
     */
    public BoardManager3D(Board3D board3D, Group group) {
        this.board3D = board3D;
        this.group = group;
        createBoxes();
        changeBoard();
        animationTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - time) > getSpeed()) {
                    board3D.nextGeneration();
                    changeBoard();

                    time = System.nanoTime();
                }
                //isClearing sier ifra at vi har clearet boardet og skal tegne det en gang.
                if (isClearing){
                    isClearing = false;
                    changeBoard();
                }
            }
        };
        animationTimer.start();
    }

    public void changeBoard(){

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {

                Box currentBox = boardBoxes.get(i).get(j);

                if(board3D.board.get(i).get(j) == true) {
                    setPurpleMaterial(currentBox);
                    currentBox.setHeight(cellSize + 50);
                    //currentBox.setTranslateY(boxY-25);
                } else {
                    setBlueMaterial(currentBox);
                    currentBox.setHeight(cellSize);
                    //currentBox.setTranslateY(boxY);
                }
            }
            //boxY = 0;
        }
    }

    public void createBoxes(){

        boardBoxes = new ArrayList<List<Box>>();

        for(int i = 0; i < boardSize; i++) {
            boardBoxes.add(i, new ArrayList<Box>(boardSize));
        }

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                Box box = new Box(cellSize,cellSize,cellSize);
                boardBoxes.get(i).add(j,box);

                if(j%2==0){
                    setBlueMaterial(box);
                } else {
                    setPurpleMaterial(box);
                }

                box.setTranslateX(boxX);
                box.setTranslateY(boxY);
                box.setTranslateZ(boxZ);
                group.getChildren().add(box);
                boxX += 53;
            }
            boxX = 0;
            boxZ += 53;
        }
        board3D.initStartBoard();
    }

    public void setBlueMaterial(Box box){
        blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.SKYBLUE);
        blueMaterial.setSpecularColor(Color.SNOW);
        box.setMaterial(blueMaterial);
    }

    public void setPurpleMaterial(Box box){
        purpleMaterial = new PhongMaterial();
        purpleMaterial.setDiffuseColor(Color.MEDIUMSLATEBLUE);
        purpleMaterial.setSpecularColor(Color.SKYBLUE);
        box.setMaterial(purpleMaterial);
    }

    public void setGreenMaterial(Box box){
        greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.FORESTGREEN);
        greenMaterial.setSpecularColor(Color.LIGHTGREEN);
        box.setMaterial(greenMaterial);
    }

    public void setPinkMaterial(Box box){
        pinkMaterial = new PhongMaterial();
        pinkMaterial.setDiffuseColor(Color.web("#FFE9B8"));
        pinkMaterial.setSpecularColor(Color.web("#FFFBF2"));
        box.setMaterial(pinkMaterial);
    }

    public void removeBoxes(){
        group.getChildren().clear();

    }

    /**
     * The method starting the animation of the board
     */
    public void start() {
        isRunning = true;
    }

    /**
     * The method starting the game over again with the preset pattern.
     */
    public void newGame() {
        clearBoard();
        board3D.defaultStartBoard();
        isRunning = true;
    }

    /**
     * The method pausing the game by stopping the animation.
     */
    public void pauseGame(){
        isRunning = false;
    }

    /**
     * The method resuming the game by starting the animation again.
     */
    public void resumeGame(){
        isRunning = true;
    }

    /**
     * The method exiting the game.
     */
    public void exitGame(){
        System.exit(0);
    }

    /**
     * The method allowing the user to select a rle pattern from disk.
     */
    public void selectPatternFromDisk() {
        boolean[][] array = fileHandling.readPatternFromDisk();
        selectPatternLogic(array);
        changeBoard();
    }
    /**
     * The method allowing the user to select a rle pattern from URL.
     */
    public void selectPatternFromURL() {
        boolean[][] array = fileHandling.readPatternFromURL();
        selectPatternLogic(array);
        changeBoard();
    }

    public void selectPatternLogic(boolean[][] array) {
        try {
            ////////lag en if-else som sjekker om instansen er Dynamic eller Static
            if(board3D instanceof Board3D){
                ((Board3D) board3D).setInputInBoard(((Board3D) board3D).createArrayListFromArray(array));
                changeBoard();
            }
        } catch (NullPointerException cancelException) {
        }
    }

    /**
     * The method return whether the animation is running or not.
     * @return      <code>true</code> if the animation
     *              is running.
     */
    public boolean getIsRunning(){
        return isRunning;
    }

    /**
     * The method setting the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    protected void setSpeed(int value) {
        speed = value;
    }

    /**
     * The method returning the speed of the animation.
     * @return  the speed of the animation
     */
    protected int getSpeed(){
        return speed;
    }

    /**
     * The method clearing the board.
     */
    public void clearBoard(){
        isRunning = false;
        board3D.clearBoard();
        isClearing = true;
    }
}