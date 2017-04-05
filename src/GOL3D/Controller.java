package GOL3D;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    SubScene subscene;

    @FXML
    GridPane gridPane;

    @FXML
    Slider rotateView;

    @FXML
    Button pauseButton;

    Board3D board;
    BoardManager3D boardManager3D;
    CubeBoard3D cubeBoard3D;
    Group group;
    Camera camera;

    /**
     * The method allowing the user to select a pattern from disk
     */
    public void selectPatternFromDisk() {
        boardManager3D.selectPatternFromDisk();
    }

    /**
     * The method allowing the user to select a pattern from a URL
     */
    public void selectPatternFromURL() {
        boardManager3D.selectPatternFromURL();
    }

    /**
     * Starts a new game from the beginning.
     */
    public void newGame(){
        pauseButton.setText("Pause");
        boardManager3D.newGame();
    }

    /**
     * Clears the board.
     */
    public void clearBoard(){
        boardManager3D.clearBoard();
    }

    /**
     * The method starting the animation of the game.
     */
    public void start() {
        pauseButton.setText("Pause");
        boardManager3D.start();
    }

    /**
     * Pauses the game.
     */
    public void pauseGame(){
        if(boardManager3D.getIsRunning()) {
            boardManager3D.pauseGame();
            pauseButton.setText("Resume");
        } else {
            boardManager3D.resumeGame();
            pauseButton.setText("Pause");
        }
    }

    /**
     * Exits the game.
     */
    public void exitGame(){
        boardManager3D.exitGame();
    }

    boolean cubeExists = false;
    boolean boardExists = false;

    public void initBoard(){
        if(group != null && cubeExists) {
            cubeBoard3D.removeBoxes();
            cubeExists = false;
            boardExists = true;
        }
        // blir det laget 2D-brett oppå hverandre?
        if(boardExists) {
            boardManager3D.removeBoxes();
        }
        createBoard();
        pauseButton.setText("Resume");
    }

    public void createBoard(){
        board = new Board3D();

        group.setStyle("-fx-background-color:#000000");
        boardManager3D = new BoardManager3D(board,group);
        camera.setRotationAxis(new Point3D(10,10,10));
        group.setRotationAxis(new Point3D(750,750,750));
        camera.setTranslateX(500);
        camera.setTranslateY(-600);
        camera.setTranslateZ(-1000);
        camera.setRotate(-35);
        subscene.setCamera(camera);
    }

    public void initCube(){
        boardManager3D.removeBoxes();
        boardExists = false;
        cubeExists = true;

        camera.setTranslateX(1300);
        camera.setTranslateY(-800);
        camera.setTranslateZ(-3000);
        camera.setRotate(325);
        subscene.setCamera(camera);
        subscene.setFill(Color.BLACK);

        cubeBoard3D = new CubeBoard3D(group);
        pauseButton.setText("Resume");
        // ha ekstra box på hjørnene
    }

    public GridPane getGridPane(){
        return gridPane;
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        group = new Group();
        camera = new PerspectiveCamera();

        createBoard();
        boardExists = true;

        subscene.setCamera(camera);
        subscene.setFill(Color.BLACK);
        subscene.setRoot(group);

        rotateView.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    group.setRotate((double)newValue);
                });
    }
}