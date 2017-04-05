package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class Controller implements Initializable{

    @FXML
    private Canvas canvas;
    Board board;
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

    @FXML
    private Button selectRules;

    @FXML
    private Button viewStatistics;

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
        try {
            if (boardManager.isRunning) {
                pauseGame();
            }
            Stage ruleWindowStage = new Stage();
            ruleWindowStage.initModality(Modality.WINDOW_MODAL);
            ruleWindowStage.initOwner(selectRules.getScene().getWindow());

            FXMLLoader ruleWindowLoader = new FXMLLoader(getClass().getResource("ruleWindow.fxml"));
            BorderPane ruleWindowBorderPane = ruleWindowLoader.load();

            Scene ruleWindowScene = new Scene(ruleWindowBorderPane, 600, 400);

            ruleWindowStage.setScene(ruleWindowScene);
            ruleWindowStage.setTitle("Rule selection");
            ruleWindowStage.showAndWait();
            pauseGame();
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        }
    }

    public void viewStatistics() {
        try {
            if (boardManager.isRunning) {
                pauseGame();
            }

            try {
                DynamicBoard clonedBoard = (DynamicBoard) board.clone();

                System.out.println("Like brett før sw: " + clonedBoard.toString().equals(board.toString()));

                Stage statisticsWindowStage = new Stage();
                statisticsWindowStage.initModality(Modality.WINDOW_MODAL);
                statisticsWindowStage.initOwner(viewStatistics.getScene().getWindow());

                FXMLLoader statisticsWindowLoader = new FXMLLoader(getClass().getResource("statisticsWindow.fxml"));

                BorderPane statisticsWindowBorderPane = statisticsWindowLoader.load();

                StatisticsWindowController swController = statisticsWindowLoader.getController();
                swController.setClonedBoard(clonedBoard);

                Scene statisticsWindowScene = new Scene(statisticsWindowBorderPane, 800, 600);

                statisticsWindowStage.setScene(statisticsWindowScene);
                statisticsWindowStage.setTitle("View game statistics");
                statisticsWindowStage.showAndWait();
                System.out.println("ulike etter kjøring: " + !clonedBoard.toString().equals(board.toString()));
            } catch (CloneNotSupportedException cnse) {
                return;
            }
            pauseGame();
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getCause());
        }
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //board = new StaticBoard(160, 100);
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
    }

}
