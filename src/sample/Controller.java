package sample;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    GraphicsContext gc;

    Board board = new Board(canvas);

    @FXML
    private Slider changeCellSize;

    @FXML
    private CheckBox randomColors;

    @FXML
    private ColorPicker colorPicker;

    public void setCellColor(){
        board.setCellColor(colorPicker);
    }

    public void setGridColor(){
        board.setGridColor(colorPicker);
    }

    public void setBoardColor(){
        board.setBoardColor(colorPicker);
    }
    /*
    public void setRandomColors(){
        board.setRandomColors();
    }*/

    public void draw(){
        board.draw(gc,randomColors);
    }

    public void changeCellSize() {
        board.setCellSize(gc, changeCellSize);
    }

    public void clearBoard(){
        board.clearBoard();
    }

    public void pauseGame(){

        board.pauseGame();
    }

    public void exitGame(){
        board.exitGame();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
    }
}
