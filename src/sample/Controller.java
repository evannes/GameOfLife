package sample;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Canvas canvas;

    Board board;

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
     * Assigns color to the cells.
     */
    public void setCellColor(){
        board.setCellColor(colorPicker);
        board.draw(canvas);
    }

    /**
     * Assigns color to the grid.
     */
    public void setGridColor(){
        board.setGridColor(colorPicker);
        board.draw(canvas);
    }

    /**
     * Assigns color to the boards background.
     */
    public void setBoardColor(){
        board.setBoardColor(colorPicker);
        board.draw(canvas);
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


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        board = new Board(canvas);

        board.userDrawCell(canvas);

        changeSpeed.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                board.setSpeed((int)((double)value * 10000000));
                board.draw(canvas);
            });

        changeCellSize.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                board.setDrawScale((double)value);
                board.draw(canvas);
            });

        board.setSpeed((int)(changeSpeed.getValue() * 10000000));
    }
}
