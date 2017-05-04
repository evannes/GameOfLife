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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
    Slider rotateDiagonally;

    @FXML
    Slider rotateHorizontal;

    @FXML
    Slider rotateVertical;

    @FXML
    Button pauseButton;

    @FXML
    private Slider changeSpeed;

    private BoardManager3D boardManager3D;
    private CubeBoardManager3D cubeBoardManager3D;
    private Group group;
    private Camera camera;
    private boolean cubeExists = false;
    private boolean boardExists = false;
    private int speed;

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
            cubeBoardManager3D.changeBoards();
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
     * Exits the 3D game and returns to the original game.
     * Found on <a href="https://stackoverflow.com/questions/25037724/">Stack Overflow</a>.
     * Also checks which board is running to stop the correct
     * game from running in the background.
     * @param event The event from the button being clicked.
     */
    public void exitGame(ActionEvent event) {
        if(cubeExists && cubeBoardManager3D.getIsRunning()){
            cubeBoardManager3D.pauseGame();
        }
        if(boardExists && boardManager3D.getIsRunning()){
            boardManager3D.pauseGame();
        }

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    /**
     * Initializes the 3D board, and removes any current boards.
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
     * Creates a new 3D board, and sets new camera angles accordingly.
     */
    private void createBoard(){
        Board3D board = new Board3D();
        cubeExists = false;
        boardExists = true;

        group.setStyle("-fx-background-color:#000000");
        group.setRotationAxis(new Point3D(750,750,750));

        boardManager3D = new BoardManager3D(board,group);
        boardManager3D.setSpeed(speed);

        camera.setRotationAxis(new Point3D(10,10,10));
        camera.setTranslateX(1350);
        camera.setTranslateY(-1000);
        camera.setTranslateZ(-2000);
        camera.setRotate(-32);
        subscene.setCamera(camera);
    }

    /**
     * Creates a new cube board, and sets new camera angles accordingly.
     */
    public void createCube(){
        boardManager3D.removeBoxes();
        boardExists = false;
        cubeExists = true;

        CubeBoard3D cubeBoard3D = new CubeBoard3D();
        cubeBoardManager3D = new CubeBoardManager3D(cubeBoard3D,group);
        cubeBoardManager3D.setSpeed(speed);
        pauseButton.setText("Resume");

        camera.setTranslateX(1300);
        camera.setTranslateY(-800);
        camera.setTranslateZ(-3000);
        camera.setRotate(325);
        subscene.setCamera(camera);
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        group = new Group();
        camera = new PerspectiveCamera();

        createBoard();

        speed = ((int) ((double) 9 * 10000000));
        boardManager3D.setSpeed(speed);

        subscene.setCamera(camera);
        subscene.setFill(Color.BLACK);
        subscene.setRoot(group);

        // rotates the group containing Box objects diagonally
        rotateDiagonally.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    group.setRotationAxis(new Point3D(750,750,750));
                    group.setRotate((double)newValue);
                });

        // rotates the group containing Box objects horizontally
        rotateHorizontal.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    group.setRotationAxis(new Point3D(0,750,0));
                    group.setRotate((double)newValue);
                });

        // rotates the group containing Box objects vertically
        rotateVertical.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    group.setRotationAxis(new Point3D(750,0,0));
                    group.setRotate((double)newValue);
                });

        changeSpeed.valueProperty().addListener(
                (observable, oldValue, value) ->
                {
                    if(boardExists) {
                        speed = ((int) ((double) value * 10000000));
                        boardManager3D.setSpeed(speed);
                        boardManager3D.changeBoard();
                    }
                    if(cubeExists){
                        speed = ((int) ((double) value * 10000000));
                        cubeBoardManager3D.setSpeed(speed);
                        cubeBoardManager3D.changeBoards();
                    }
                });
    }
}