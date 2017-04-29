package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Board;
import model.DynamicBoard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the main controller class for the Game of Life window.
 * It controls the various methods related to the base game,
 * such as new game and pause, as well as the color choices related to
 * cells, grid and board background.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @since   1.0
 */
public class Controller implements Initializable{

    @FXML
    private Canvas canvas;
    private Board board;
    private BoardManager boardManager;

    @FXML
    private Slider changeCellSize;

    @FXML
    private Slider changeSpeed;

    @FXML
    private CheckBox randomColors;

    @FXML
    private Button pauseButton;

    @FXML
    private MenuItem selectRules;

    @FXML
    private MenuItem version3DButton;

    @FXML
    private MenuItem viewStatistics;

    @FXML
    private CheckBox gridOnOff;

    @FXML
    private ColorPicker colorPickerCell;

    @FXML
    private ColorPicker colorPickerGrid;

    @FXML
    private ColorPicker colorPickerBoard;

    @FXML
    private Button fullscreen;

    @FXML
    private Button normalscreen;

    /**
     * Increases the stage to maximized size and increases the size of the canvas.
     */
    public void fullScreen(){
        boardManager.maxCanvasSize();
        Stage stage = (Stage) fullscreen.getScene().getWindow();
        stage.setMaximized(true);
    }

    /**
     * Decreases the stage to normale size and resize the canvas.
     */
    public void normalScreen() {
        boardManager.normalCanvasSize();
        Stage stage = (Stage) normalscreen.getScene().getWindow();
        stage.setMaximized(false);
    }

    /**
     * Moves the canvas by pressing the keys w, a, s, and d
     * @param keyEvent      the KeyEvent
     */
    public void moveCanvas(KeyEvent keyEvent) {
        boardManager.moveCanvas(keyEvent);
    }

    /**
     * Lets the user to select a pattern from disk
     */
    public void selectPatternFromDisk() {
        boardManager.selectPatternFromDisk();
    }

    /**
     * Lets the user to select a pattern from a URL
     */
    public void selectPatternFromURL() {
        boardManager.selectPatternFromURL();
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
     * Starts the animation of the game.
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

    /**
     * Creates the <code>Stage</code> for the rule selection window.
     */
    public void selectRules() {
        try {
            if (boardManager.isRunning) {
                pauseGame();
            }
            Stage ruleWindowStage = new Stage();
            ruleWindowStage.initModality(Modality.WINDOW_MODAL);
            ruleWindowStage.initOwner(pauseButton.getScene().getWindow());

            FXMLLoader ruleWindowLoader = new FXMLLoader(getClass().getResource("../view/ruleWindow.fxml"));
            BorderPane ruleWindowBorderPane = ruleWindowLoader.load();

            Scene ruleWindowScene = new Scene(ruleWindowBorderPane, 600, 400);

            ruleWindowStage.setScene(ruleWindowScene);
            ruleWindowStage.setTitle("Rule selection");
            ruleWindowStage.getIcons().add(new Image("icons/gol_icon.png"));
            ruleWindowStage.showAndWait();
            pauseGame();
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        }
    }

    /**
     * Creates the <code>Stage</code> for the 3D game window.
     */
    public void start3DGame(){
        try {
            Stage window3DGame = new Stage();

            FXMLLoader gol3DLoader = new FXMLLoader(getClass().getResource("../GOL3D/view3D.fxml"));
            GridPane gridPane = gol3DLoader.load();

            Scene gol3DScene = new Scene(gridPane, 1200, 650);

            window3DGame.setScene(gol3DScene);
            window3DGame.setTitle("Game of Life 3D");
            window3DGame.getIcons().add(new Image("icons/gol_icon.png"));
            window3DGame.showAndWait();
            pauseGame();
        } catch(IOException e){
            // Alert-boks som forklarer at det mangler en fil?
            System.out.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Creates the <code>Stage</code> for the statistics window.
     */
    public void viewStatistics() {
        try {
            if (boardManager.isRunning) {
                pauseGame();
            }

            try {
                DynamicBoard clonedBoard = (DynamicBoard) board.clone();

                Stage statisticsWindowStage = new Stage();
                statisticsWindowStage.initModality(Modality.WINDOW_MODAL);
                statisticsWindowStage.initOwner(pauseButton.getScene().getWindow());

                FXMLLoader statisticsWindowLoader = new FXMLLoader(getClass().getResource("../view/statisticsWindow.fxml"));

                BorderPane statisticsWindowBorderPane = statisticsWindowLoader.load();

                StatisticsController swController = statisticsWindowLoader.getController();
                swController.setClonedBoard(clonedBoard);

                Scene statisticsWindowScene = new Scene(statisticsWindowBorderPane, 1200, 600);

                statisticsWindowStage.setScene(statisticsWindowScene);
                statisticsWindowStage.setTitle("View game statistics");
                statisticsWindowStage.getIcons().add(new Image("icons/gol_icon.png"));
                statisticsWindowStage.showAndWait();
            } catch (CloneNotSupportedException cnse) {
                return;
            }
            pauseGame();
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getCause());
        }
    }

    /**
     * Called to initialize a controller after its root element has been completely processed.
     * @param fxmlFileLocation  The location used to resolve relative paths for the root object,
     *                          or null if the location is not known.
     * @param resources         The resources used to localize the root object,
     *                          or null if the root object was not localized.
     * @see                     Initializable
     */
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {



        board = new DynamicBoard(160, 100);

        boardManager = new BoardManager(canvas, board);

        boardManager.userDrawCell();

        changeSpeed.valueProperty().addListener(
                (observable, oldValue, value) ->
                {
                    boardManager.setSpeed((int)((double)value * 10000000));
                    System.out.println(value);
                    boardManager.draw();
                });

        changeCellSize.valueProperty().addListener(
                (observable, oldValue, value) ->
                {
                    boardManager.scaleBoard((double)value);
                });

        //boardManager.setSpeed((int)(changeSpeed.getValue() * 10000000));

        gridOnOff.setOnAction(event -> {
            if (gridOnOff.isSelected()) {
                boardManager.switchOffGrid();
            } else {
                boardManager.switchOnGrid(colorPickerGrid);
            }
        });

        colorPickerCell.setValue(boardManager.cellColor);
        colorPickerGrid.setValue(boardManager.gridColor);
        colorPickerBoard.setValue(boardManager.boardColor);

        colorPickerCell.setOnAction(event -> {
            boardManager.setCellColor(colorPickerCell);
        });

        colorPickerGrid.setOnAction(event -> {
            boardManager.setGridColor(colorPickerGrid);
            if (gridOnOff.isSelected()) {
                gridOnOff.setSelected(false);
            }
        });

        colorPickerBoard.setOnAction(event -> {
            boardManager.setBoardColor(colorPickerBoard);
        });
    }

}
