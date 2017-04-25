package GOL3D;

import javafx.event.ActionEvent;
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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * This is the controller class for the 3D Game of Life window.
 * Created by Elise Haram Vannes on 03.04.2017.
 */
public class view3DController implements Initializable {

    @FXML
    SubScene subscene;

    @FXML
    GridPane gridPane;

    @FXML
    Slider rotateView;

    @FXML
    Slider rotateViewVertical;

    @FXML
    Button pauseButton;

    @FXML
    private Slider changeSpeed;

    private Board3D board;
    private BoardManager3D boardManager3D;
    private CubeBoardManager3D cubeBoardManager3D;
    private CubeBoard3D cubeBoard3D;
    private Group group;
    private Camera camera;
    private boolean cubeExists = false;
    private boolean boardExists = false;

    /**
     * Lets the user to select a pattern from disk
     */
    public void selectPatternFromDisk() {
        if(boardExists) {
            boardManager3D.selectPatternFromDisk();
        }else if(cubeExists){
            cubeBoardManager3D.selectPatternFromDisk();
        }
    }

    /**
     * Lets the user to select a pattern from a URL
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
            boardManager3D.changeBoard();
        }else if(cubeExists){
            cubeBoardManager3D.clearBoards();
        }
    }

    /**
     * Starts the animation of the game.
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
    public void exitGame(ActionEvent event) {
        //((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

        //boardManager3D.exitGame();
    }

    /**
     * Initializes 3D board, and removes any current boards.
     */
    public void initBoard(){
        if(group != null && cubeExists) {
            cubeBoardManager3D.removeBoxes();
            cubeExists = false;
            boardExists = true;
        }
        if(boardExists) {
            boardManager3D.removeBoxes();
        }
        createBoard();
        pauseButton.setText("Resume");
    }

    /**
     * Creates a new board, and sets new camera angles accordingly.
     */
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

    /**
     * Creates a new cube board, and sets new camera angles accordingly.
     */
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
                    group.setRotationAxis(new Point3D(750,750,750));
                    group.setRotate((double)newValue);
                });
        // dei roterer ikkje sammen, avbryter kvarandre isteden
        rotateViewVertical.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    group.setRotationAxis(new Point3D(0,750,0));
                    group.setRotate((double)newValue);
                });

        changeSpeed.valueProperty().addListener(
                (observable, oldValue, value) ->
                {
                    boardManager3D.setSpeed((int)((double)value * 10000000));
                    boardManager3D.changeBoard();
                });
    }
}