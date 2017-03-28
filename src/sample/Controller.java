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
    DynamicBoard board;
    BoardGraphics boardGraphics;

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

    /**
     * The method allowing the user to select a pattern from disk
     */
    public void selectPatternFromDisk() {
        board.selectPatternFromDisk();
    }

    /**
     * The method allowing the user to select a pattern from a URL
     */
    public void selectPatternFromURL() {
        board.selectPatternFromURL();
    }

    /**
     * Assigns color to the cells.
     */
    public void setCellColor(){
        boardGraphics.setCellColor(colorPicker);
        boardGraphics.drawDynamic();
    }

    /**
     * Assigns color to the grid.
     */
    public void setGridColor(){
        boardGraphics.setGridColor(colorPicker);
        boardGraphics.drawDynamic();
    }

    /**
     * Assigns color to the boards background.
     */
    public void setBoardColor(){
        boardGraphics.setBoardColor(colorPicker);
        boardGraphics.drawDynamic();
    }

    /**
     * Assigns random colors to the cells.
     */
    public void setRandomColors(){
        boardGraphics.setDrawRandomColors(randomColors.isSelected());
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

    /**
     * The method starting the animation of the game.
     */
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


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //board = new StaticBoard(canvas);
        boardGraphics = new BoardGraphics(canvas);
        board = new DynamicBoard(boardGraphics);
        boardGraphics.userDrawCell();

        changeSpeed.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                boardGraphics.setSpeed((int)((double)value * 10000000));
                boardGraphics.drawDynamic();
            });

        changeCellSize.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                boardGraphics.setDrawScale((double)value);
                boardGraphics.drawDynamic();
            });

        boardGraphics.setSpeed((int)(changeSpeed.getValue() * 10000000));
        //boardGraphics.drawDynamic();
    }

}
