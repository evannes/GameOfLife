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

    private Board3D board;
    private BoardManager3D boardManager3D;
    private CubeBoardManager3D cubeBoardManager3D;
    private CubeBoard3D cubeBoard3D;
    private Group group;
    private Camera camera;
    private boolean cubeExists = false;
    private boolean boardExists = false;

    /**
     * The method allowing the user to select a pattern from disk
     */
    public void selectPatternFromDisk() {
        if(boardExists) {
            boardManager3D.selectPatternFromDisk();
        }else if(cubeExists){
            cubeBoardManager3D.selectPatternFromDisk();
        }
    }

    /**
     * The method allowing the user to select a pattern from a URL
     */
    public void selectPatternFromURL() {
        if(boardExists) {
            boardManager3D.selectPatternFromURL();
        }else if(cubeExists){
            cubeBoardManager3D.selectPatternFromURL();
        }
    }

    /**
     * Clears the board.
     */
    public void clearBoard(){
        if(boardExists) {
            boardManager3D.clearBoard();
        }else if(cubeExists){
            cubeBoardManager3D.clearBoard();
        }
    }

    /**
     * The method starting the animation of the game.
     */
    public void start() {
        pauseButton.setText("Pause");
        if(boardExists) {
            boardManager3D.start();
        } else if(cubeExists){
            cubeBoardManager3D.start();
        }
    }

    /**
     * Pauses the game.
     */
    public void pauseGame(){
        // rotete????
        if(boardExists) {
            if (boardManager3D.getIsRunning()) {
                boardManager3D.pauseGame();
                pauseButton.setText("Resume");
            } else {
                boardManager3D.resumeGame();
                pauseButton.setText("Pause");
            }
        } else if(cubeExists){
            if (cubeBoardManager3D.getIsRunning()) {
                cubeBoardManager3D.pauseGame();
                pauseButton.setText("Resume");
            } else {
                cubeBoardManager3D.resumeGame();
                pauseButton.setText("Pause");
            }
        }
    }

    /**
     * Exits the game.
     */
    public void exitGame(){
        boardManager3D.exitGame();
    }

    public void initBoard(){
        if(group != null && cubeExists) {
            cubeBoardManager3D.removeBoxes();
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

        cubeBoard3D = new CubeBoard3D();
        cubeBoardManager3D = new CubeBoardManager3D(cubeBoard3D,group);
        pauseButton.setText("Resume");
        // ha ekstra box på hjørnene
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