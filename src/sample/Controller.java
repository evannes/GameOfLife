package sample;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Canvas canvas;
    DynamicBoard board;
    BoardManager boardManager;

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
        boardManager.selectPatternFromDisk();
    }

    /**
     * The method allowing the user to select a pattern from a URL
     */
    public void selectPatternFromURL() {
        boardManager.selectPatternFromURL();
    }

    /**
     * Assigns color to the cells.
     */
    public void setCellColor(){
        boardManager.setCellColor(colorPicker);
        boardManager.draw();
    }

    /**
     * Assigns color to the grid.
     */
    public void setGridColor(){
        boardManager.setGridColor(colorPicker);
        boardManager.draw();
    }

    /**
     * Assigns color to the boards background.
     */
    public void setBoardColor(){
        boardManager.setBoardColor(colorPicker);
        boardManager.draw();
    }

    /**
     * Assigns random colors to the cells.
     */
    public void setRandomColors(){
        boardManager.setDrawRandomColors(randomColors.isSelected());
    }

    /**
     * Starts a new game from the beginning.
     */
    public void newGame(){
        pauseButton.setText("Pause");
        boardManager.newGame();
    }

    /**
     * Clears the board.
     */
    public void clearBoard(){
        boardManager.clearBoard();
    }

    /**
     * The method starting the animation of the game.
     */
    public void start() {
        pauseButton.setText("Pause");
        boardManager.start();
    }

    /**
     * Pauses the game.
     */
    public void pauseGame(){
        if(boardManager.getIsRunning()) {
            boardManager.pauseGame();
            pauseButton.setText("Resume");
        } else {
            boardManager.resumeGame();
            pauseButton.setText("Pause");
        }
    }

    /**
     * Exits the game.
     */
    public void exitGame(){
        boardManager.exitGame();
    }

    public void selectRules() {
        boardManager.ruleWindow();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //board = new StaticBoard(canvas);
        board = new DynamicBoard(160, 100);
        boardManager = new BoardManager(canvas, board);

        boardManager.userDrawCell();

        changeSpeed.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                boardManager.setSpeed((int)((double)value * 10000000));
                boardManager.draw();
            });

        changeCellSize.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                boardManager.setDrawScale((double)value);
                boardManager.draw();
            });

        boardManager.setSpeed((int)(changeSpeed.getValue() * 10000000));
        //boardManager.drawDynamic();
    }

}
