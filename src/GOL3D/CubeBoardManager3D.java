package GOL3D;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.List;

import sample.FileHandling;

/**
 * Created by Elise Haram Vannes on 05.04.2017.
 */
public class CubeBoardManager3D {

    private AnimationTimer animationTimer;
    private int speed = 250000000;
    private Board3D board3D;
    private CubeBoard3D cubeBoard3D;
    private FileHandling fileHandling = new FileHandling();
    private Group group;
    private List<List<Box>> boardBoxes;
    private Box currentBox;
    private boolean isRunning = false;
    private boolean isClearing = false;
    private long time = System.nanoTime();
    private int boardSize = 30;
    private int cellSize = 50;
    private int boxX = 0;
    private int boxY = 0;
    private int boxZ = 0;
    private List<List<Box>> boxBoard1;
    private List<List<Box>> boxBoard2;
    private List<List<Box>> boxBoard3;
    private List<List<Box>> boxBoard4;
    private List<List<Box>> boxBoard5;
    private List<List<Box>> boxBoard6;
    private PhongMaterial blueMaterial;
    private PhongMaterial greenMaterial;
    private PhongMaterial purpleMaterial;
    private PhongMaterial pinkMaterial;

    //private static CubeBoardManager3D ourInstance = new CubeBoardManager3D();

    //public static CubeBoardManager3D getInstance() {
    //    return ourInstance;
    //}
    //
    // inneholder boxer.. changeBoard, setPurpleMaterial, createBoxes

    /**
     * The constructor initializing the animation of Game of Life.
     *
     * @param cubeBoard3D the board
     */

    public CubeBoardManager3D(CubeBoard3D cubeBoard3D, Group group) {
        this.cubeBoard3D = cubeBoard3D;
        this.group = group;
        initBoxArrays();
        changeBoards();
        animationTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - time) > getSpeed()) {
                    cubeBoard3D.nextGenerations();
                    changeBoards();
                    time = System.nanoTime();
                }
                if (isClearing) {
                    isClearing = false;
                    changeBoards();
                }
            }
        };
        animationTimer.start();
    }

    public void initBoxArrays() {
        // 3D-vector lagrer x,y og z-transforms
        boxBoard1 = createBoxes(boxBoard1, 0, -53, 0, 53, 0, 53, true, false);
        boxBoard2 = createBoxes(boxBoard2, 0, 0, -53, 53, 53, 0, false, true);
        boxBoard3 = createBoxes(boxBoard3, 0, 1590, 0, 53, 0, 53, true, false);
        boxBoard4 = createBoxes(boxBoard4, 0, 0, 1590, 53, 53, 0, false, true);
        boxBoard5 = createBoxes(boxBoard5, 1590, 0, 0, 0, 53, 53, false, false);
        boxBoard6 = createBoxes(boxBoard6, -53, 0, 0, 0, 53, 53, false, false);
    }

    public void changeBoards() {
        for (int i = 0; i < 6; i++) {
            changeBoard(i);
        }
    }

    public void changeBoard(int numBoard) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                currentBox = getBoxBoards(numBoard + 1).get(i).get(j);
                if (cubeBoard3D.getBoardArrays()[numBoard].get(i).get(j)) {
                    setPurpleMaterial(currentBox);
                    if (numBoard == 5 || numBoard == 4) {
                        currentBox.setWidth(cellSize + 50);
                    } else if (numBoard == 1 || numBoard == 3) {
                        currentBox.setDepth(cellSize + 50);
                    } else {
                        currentBox.setHeight(cellSize + 50);
                    }
                } else {
                    setBlueMaterial(currentBox);
                    if (numBoard == 5 || numBoard == 4) {
                        currentBox.setWidth(cellSize);
                    } else if (numBoard == 1 || numBoard == 3) {
                        currentBox.setDepth(cellSize);
                    } else {
                        currentBox.setHeight(cellSize);
                    }
                }
            }
        }
    }

    public List<List<Box>> getBoxBoards(int boxBoard) {
        switch (boxBoard) {
            case 1:
                return boxBoard1;
            case 2:
                return boxBoard2;
            case 3:
                return boxBoard3;
            case 4:
                return boxBoard4;
            case 5:
                return boxBoard5;
            case 6:
                return boxBoard6;
        }
        return null;
    }

    public void changeBoard2() {
        for (int i = 0; i < cubeBoard3D.getBoard2().size(); i++) {
            for (int j = 0; j < cubeBoard3D.getBoard2().size(); j++) {
                currentBox = boxBoard2.get(i).get(j);
                if (cubeBoard3D.getBoard2().get(i).get(j)) {
                    setPurpleMaterial(currentBox);
                    currentBox.setWidth(cellSize + 50);
                } else {
                    setBlueMaterial(currentBox);
                    currentBox.setWidth(cellSize);
                }
            }
        }
    }

    public void changeBoard1() {
        for (int i = 0; i < cubeBoard3D.getBoard1().size(); i++) {
            for (int j = 0; j < cubeBoard3D.getBoard1().size(); j++) {
                currentBox = boxBoard1.get(i).get(j);
                if (cubeBoard3D.getBoard1().get(i).get(j)) {
                    setPurpleMaterial(currentBox);
                    currentBox.setHeight(cellSize + 50);
                } else {
                    setBlueMaterial(currentBox);
                    currentBox.setHeight(cellSize);
                }
            }
        }
    }

    public List<List<Box>> createBoxes(List<List<Box>> boardBoxes, int boxX, int boxY, int boxZ,
                                       int incrementX, int decrementY, int incrementZ,
                                       boolean resetX, boolean resetY) {

        boardBoxes = new ArrayList<List<Box>>();

        for (int i = 0; i < boardSize; i++) {
            boardBoxes.add(i, new ArrayList<Box>(boardSize));
        }

        /* vurdere å bruke foreach istedenfor for-løkke der det er mulig
        boardBoxes.forEach(row -> {
            Box myBox = new Box(cellSize, cellSize, cellSize);
            row.add(myBox);
        });

        for (List row: boardBoxes) {

        }*/

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Box myBox = new Box(cellSize, cellSize, cellSize);
                boardBoxes.get(i).add(myBox);

                if (j % 2 == 0) {
                    setBlueMaterial(myBox);
                } else {
                    setPurpleMaterial(myBox);
                }

                myBox.setTranslateX(boxX);
                myBox.setTranslateY(boxY);
                myBox.setTranslateZ(boxZ);
                group.getChildren().add(myBox);

                if (resetX) {
                    boxX += incrementX;//53;
                } else if (resetY) {
                    boxY += decrementY;
                } else {
                    boxZ += incrementZ;
                }
            }

            boxX += incrementX;
            boxY += decrementY;
            boxZ += incrementZ;//53;

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
     * The method allowing the user to select a rle pattern from disk.
     */
    public void selectPatternFromDisk() {
        boolean[][] array = fileHandling.readPatternFromDisk();
        selectPatternLogic(array);
        changeBoard2();
    }

    /**
     * The method allowing the user to select a rle pattern from URL.
     */
    public void selectPatternFromURL() {
        boolean[][] array = fileHandling.readPatternFromURL();
        selectPatternLogic(array);
        changeBoard2();
    }

    public void selectPatternLogic(boolean[][] array) {
        try {
            ////////lag en if-else som sjekker om instansen er Dynamic eller Static
            if (board3D instanceof Board3D) {
                ((Board3D) board3D).setInputInBoard(((Board3D) board3D).createArrayListFromArray(array));
                changeBoard2();
            }
        } catch (NullPointerException cancelException) {
        }
    }

    public void setBlueMaterial(Box box) {
        blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.SKYBLUE);
        blueMaterial.setSpecularColor(Color.SNOW);
        box.setMaterial(blueMaterial);
    }

    public void setPurpleMaterial(Box box) {
        purpleMaterial = new PhongMaterial();
        purpleMaterial.setDiffuseColor(Color.MEDIUMSLATEBLUE);
        purpleMaterial.setSpecularColor(Color.SKYBLUE);
        box.setMaterial(purpleMaterial);
    }

    public void setGreenMaterial(Box box) {
        greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.FORESTGREEN);
        greenMaterial.setSpecularColor(Color.LIGHTGREEN);
        box.setMaterial(greenMaterial);
    }

    public void setPinkMaterial(Box box) {
        pinkMaterial = new PhongMaterial();
        pinkMaterial.setDiffuseColor(Color.web("#FFE9B8"));
        pinkMaterial.setSpecularColor(Color.web("#FFFBF2"));
        box.setMaterial(pinkMaterial);
    }

    public void removeBoxes() {
        group.getChildren().clear();

    }

    /**
     * The method starting the animation of the board
     */
    public void start() {
        isRunning = true;
    }

    /**
     * The method pausing the game by stopping the animation.
     */
    public void pauseGame() {
        isRunning = false;
    }

    /**
     * The method resuming the game by starting the animation again.
     */
    public void resumeGame() {
        isRunning = true;
    }

    /**
     * The method return whether the animation is running or not.
     *
     * @return <code>true</code> if the animation
     * is running.
     */
    public boolean getIsRunning() {
        return isRunning;
    }

    /**
     * The method setting the speed of the animation.
     *
     * @param value the value used to set the speed of the animation
     */
    protected void setSpeed(int value) {
        speed = value;
    }

    /**
     * The method returning the speed of the animation.
     *
     * @return the speed of the animation
     */
    protected int getSpeed() {
        return speed;
    }

    /**
     * The method clearing the board.
     */
    public void clearBoard() {
        isRunning = false;
        board3D.clearBoard();
        isClearing = true;
    }
}
