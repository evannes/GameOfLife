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

    public void setCellColor(){
        board.setCellColor(colorPicker);
    }

    public void setGridColor(){
        board.setGridColor(colorPicker);
    }

    public void setBoardColor(){
        board.setBoardColor(colorPicker);
    }

    public void setRandomColors(){
        board.setDrawRandomColors(randomColors.isSelected());
    }

    public void start(){
        board.start();
    }

    public void clearBoard(){
        board.clearBoard();
    }

    public void pauseGame(){
        if(board.getIsRunning()) {
            board.pauseGame();
            pauseButton.setText("Resume");
        } else {
            board.resumeGame();
            pauseButton.setText("Pause");
        }
    }

    public void exitGame(){
        board.exitGame();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        board = new Board((int)changeCellSize.getValue(), canvas);

        changeSpeed.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                board.setSpeed((int)((double)value * 10000000));
            });

        changeCellSize.valueProperty().addListener(
            (observable, oldValue, value) ->
            {
                board.setCellSize((int)(double)value);
            });

        board.setSpeed((int)(changeSpeed.getValue() * 10000000));
    }
}
