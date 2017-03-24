package sample;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Canvas canvas;

    //StaticBoard board;
    //Rules staticRules = new Rules();
    //FileHandling fileHandling = new FileHandling();
    DynamicFileHandling dynamicFileHandling = new DynamicFileHandling();
    DynamicBoard board;
    Rules rules;

    @FXML
    private Slider changeCellSize;

    @FXML
    private Slider changeSpeed;

    @FXML
    private CheckBox randomColors;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Button pauseButton;

    public void selectPatternFromDisk() {
        board.selectPatternFromDisk();
        //boolean array[][] = fileHandling.readPatternFromDisk();
        //board.staticRules.setBoard(array);
        //List<List<Boolean>> array = dynamicFileHandling.readPatternFromDisk();
        //board.rules.setBoard(array);
        //board.draw(canvas);
    }

    public void selectPatternFromURL() {
        board.selectPatternFromURL();
        //boolean array[][] = fileHandling.readPatternFromURL();
        //board.staticRules.setBoard(array);
        //List<List<Boolean>> array = dynamicFileHandling.readPatternFromURL();
        //board.rules.setBoard(array);
       // board.draw(canvas);
    }

    /**
     * Assigns color to the cells.
     */
    public void setCellColor(){
        board.setCellColor(colorPicker);
    }

    /**
     * Assigns color to the grid.
     */
    public void setGridColor(){
        board.setGridColor(colorPicker);
    }

    /**
     * Assigns color to the boards background.
     */
    public void setBoardColor(){
        board.setBoardColor(colorPicker);
    }

    /**
     * Assigns random colors to the cells.
     */
    public void setRandomColors(){
        board.setDrawRandomColors(randomColors.isSelected());
    }

    /**
     * Starts a new game from the beginning.
     */
    public void newGame(){
        pauseButton.setText("Pause");
        board.newGame();
    }

    /**
     * Clears the board.
     */
    public void clearBoard(){
        board.clearBoard();
    }

    public void start() {

        pauseButton.setText("Pause");
        board.start();
    }

    /**
     * Pauses the game.
     */
    public void pauseGame(){
        if(board.getIsRunning()) {
            board.pauseGame();
            pauseButton.setText("Resume");
        } else {
            board.resumeGame();
            pauseButton.setText("Pause");
        }
    }

    /**
     * Exits the game.
     */
    public void exitGame(){
        board.exitGame();
    }

    //protected boolean[][] boardGrid = new boolean[160][100];

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
<<<<<<< Updated upstream
        //board = new StaticBoard(canvas);
=======
        // board = new Board(canvas);
>>>>>>> Stashed changes
        board = new DynamicBoard(canvas);

        board.userDrawCell(canvas);

        changeSpeed.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                board.setSpeed((int)((double)value * 10000000));
            });

        changeCellSize.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                board.setDrawScale((double)value);
            });

        board.setSpeed((int)(changeSpeed.getValue() * 10000000));
    }

}
