package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.Board;
import model.DynamicBoard;
import model.FileHandling;
import model.StaticBoard;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * The BoardManager binds the logic from the model classes together with the view.
 * It contains all the methods related to the canvas,
 * including the <code>draw</code> method that draws the cells to the game board.
 * The cells, the grid and the background as all drawn to separate canvases to improve performance.
 * It was eventually decided to implement a function for the user to remove the grid
 * (setting its color equal to the board color), as the representation
 * doesn't look particularly good when a large dynamic board is drawn.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @since   1.0
 */
public class BoardManager {
    private boolean drawRandomColors;
    private double drawScale;
    private double gridSize;
    private double startingPointX;
    private double startingPointY;
    private int speed;
    Color cellColor = Color.LIGHTSEAGREEN;
    Color gridColor = Color.GRAY;
    Color boardColor = Color.WHITE;
    private final Canvas canvas;
    private final Canvas bgCanvas;
    private final Canvas gridCanvas;
    private final Board board;
    private final FileHandling fileHandling = new FileHandling();
    boolean isRunning = false;
    private long time = System.nanoTime();
    private int scaleFactorX = 80;
    private int scaleFactorY = 50;
    private final double canvasIncrease = 1.5;
    private int scaledX;
    private int scaledY;
    private boolean gridIsOn = true;
    private final int canvasDefaultHeight;
    private final int canvasDefaultWidth;
    private boolean[][] fileArray;
    private final Charset charset = Charset.forName("US-ASCII");

    /**
     * The constructor initializing the animation of Game of Life.
     * @param canvas         the canvas to draw alive cells to
     * @param bgCanvas       the canvas to draw the background on
     * @param gridCanvas     the canvas to draw the grid
     * @param board          the board to draw on the canvas
     */
    BoardManager(Canvas canvas, Canvas bgCanvas, Canvas gridCanvas, Board board) {
        this.canvas = canvas;
        this.bgCanvas = bgCanvas;
        this.gridCanvas = gridCanvas;
        this.board = board;
        canvasDefaultHeight = 500;
        canvasDefaultWidth = 800;
        drawBackground();
        drawGrid();

        AnimationTimer drawTimer = new AnimationTimer() {
            public void handle(long now) {
            if (isRunning && (now - time) > getSpeed()) {
                board.nextGenerationConcurrent();
                draw();
                time = System.nanoTime();
            }
            }
        };
        drawTimer.start();
    }

    /**
     * Draws the grid of the board.
     */
    private void drawGrid() {
        GraphicsContext gc = gridCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(gridColor);
        gc.setLineWidth(gridSize);
        double cellWidth = (canvas.getWidth() * drawScale) / board.getWidth();
        double cellHeight = (canvas.getHeight() * drawScale) / board.getHeight();
        int width = board.getWidth();
        int height = board.getHeight();
        int canvasWidth = (int)gridCanvas.getWidth();
        int canvasHeight = (int)gridCanvas.getHeight();

        for (int x = 0; x < width; x++) {
            double cellX = (cellHeight * x) - startingPointX;

            gc.strokeLine(cellX, 0,cellX, canvasHeight);
        }

        for (int y = 0; y < height; y++) {
            double cellY = (cellWidth * y) - startingPointY;

            gc.strokeLine(0, cellY, canvasWidth, cellY);

        }
    }

    /**
     * Draws the background of the board.
     */
    private void drawBackground() {
        GraphicsContext gc = bgCanvas.getGraphicsContext2D();
        gc.setFill(boardColor);
        gc.fillRect(0,0, bgCanvas.getWidth(), bgCanvas.getHeight());
    }

    /**
     * The method drawing the active cells to the board.
     */
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        // find the starting points so the zoom will go towards the middle
        startingPointX = (canvas.getWidth() / 2 - scaledX) * drawScale - canvas.getWidth() / 2;
        startingPointY = (canvas.getHeight() / 2 - scaledY) * drawScale - canvas.getHeight() / 2;
        int width = board.getWidth();
        int height = board.getHeight();
        double cellWidth = (canvas.getWidth() * drawScale) / width;
        double cellHeight = (canvas.getHeight() * drawScale) / height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                if (!board.getValue(x,y))
                    continue;

                if (board.getValue(x, y) && drawRandomColors) {
                    gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                }
                else {
                    gc.setFill(cellColor);
                }
                double cellX = (cellHeight * x) - startingPointX;
                double cellY = (cellWidth * y) - startingPointY;

                gc.fillRect(cellX, cellY, cellWidth, cellHeight);
            }
        }
    }

    /**
     * The method which lets the user set or remove cells manually from the board.
     */
    void userDrawCell(){
        gridCanvas.setOnMouseClicked(e -> {
            double cellWidth = (canvas.getWidth() * drawScale + gridSize) / board.getWidth();
            double cellHeight = (canvas.getHeight() * drawScale + gridSize) / board.getHeight();
            double coordinateX = e.getX()+startingPointX;
            double coordinateY = e.getY()+startingPointY;
            int arrayX = (int)Math.floor(coordinateX/cellWidth);
            int arrayY = (int)Math.floor(coordinateY/cellHeight);

            // check if we are inside the board
            if (arrayX > -1 && arrayY > -1 && arrayX < board.getWidth() && arrayY < board.getHeight()) {
                board.toggleValue(arrayX, arrayY);
                draw();
            }
        });
    }

    /**
     * Increases the canvas size to fit a maximized stage.
     */
    void maxCanvasSize() {
        if (canvas.getHeight() == canvasDefaultHeight* canvasIncrease)
            return;

        int width = (int)(canvasDefaultWidth * canvasIncrease);
        int height = (int)(canvasDefaultHeight * canvasIncrease);

        canvas.setHeight(height);
        canvas.setWidth(width);
        bgCanvas.setHeight(height);
        bgCanvas.setWidth(width);
        gridCanvas.setHeight(height);
        gridCanvas.setWidth(width);
        scaleFactorX *= canvasIncrease;
        scaleFactorY *= canvasIncrease;
        scaledX *= canvasIncrease;
        scaledY *= canvasIncrease;
        drawBackground();
        draw();
        drawGrid();
    }

    /**
     * Decreases the canvas size to fit the normal sized stage.
     */
    void normalCanvasSize() {
        if (canvas.getHeight() == canvasDefaultHeight)
            return;

        canvas.setHeight(canvasDefaultHeight);
        canvas.setWidth(canvasDefaultWidth);
        bgCanvas.setHeight(canvasDefaultHeight);
        bgCanvas.setWidth(canvasDefaultWidth);
        gridCanvas.setHeight(canvasDefaultHeight);
        gridCanvas.setWidth(canvasDefaultWidth);
        scaleFactorX /= canvasIncrease;
        scaleFactorY /= canvasIncrease;
        scaledX /= canvasIncrease;
        scaledY /= canvasIncrease;
        drawBackground();
        draw();
        drawGrid();
    }

    /**
     * Moves the canvas by using the w,a,s,d keys on the keyboard.
     * It will not be possible to move up or left inside the board when it reaches the position 0x0.
     * @param keyEvent      the KeyEvent
     */
    void moveCanvas(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.S) {
            scaledY -= (int)(scaleFactorY / drawScale);
        }

        if (keyEvent.getCode() == KeyCode.W) {
            scaledY += (int)(scaleFactorY / drawScale);
            int maxScale = (int)(((canvas.getHeight() / 2) * drawScale - canvas.getHeight() / 2) / drawScale);

            // to lock the canvas in the position 0x0 or lower
            if (scaledY > maxScale)
                scaledY = maxScale;
        }

        if (keyEvent.getCode() == KeyCode.D){
            scaledX -= (int)(scaleFactorX / drawScale);
        }

        if (keyEvent.getCode() == KeyCode.A) {
            scaledX += (int)(scaleFactorX / drawScale);
            int maxScale = (int)(((canvas.getWidth() / 2) * drawScale - canvas.getWidth() / 2) / drawScale);

            // to lock the canvas in the position 0x0 or lower
            if (scaledX > maxScale)
                scaledX = maxScale;
        }
        draw();
        drawGrid();
    }

    /**
     * The method scaling the board.
     * The higher the value passed in the larger the board will become.
     * @param value     the value used to change the size of the board.
     */
    void scaleBoard(double value) {
        drawScale = value;
        gridSize = 0.1 * drawScale;
        draw();
        drawGrid();
    }

    /**
     * Starting the animation of the board.
     */
    public void start() {
        isRunning = true;
    }

    /**
     * Starting the game over again with the preset pattern.
     */
    void newGame() {
        clearBoard();
        scaledX = 0;
        scaledY = 0;
        selectLocalPattern();
    }

    /**
     * Reads a local file.
     */
    private void selectLocalPattern() {
        Path inFile = Paths.get(
                "src/model/patterns/halfmax.rle").toAbsolutePath();
        try {
            BufferedReader reader = Files.newBufferedReader(inFile, charset);
            fileArray = fileHandling.getPatternFromFile(reader);
        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the pattern file", ioe);
        }

        if (inFile != null) {
            selectPatternLogic(fileArray);
        }
    }

    /**
     * Lets the user select a pattern from disk of an rle file.
     */
    void selectPatternFromDisk() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = null;

        try {
            fileChooser.setTitle("Open RLE file from disk");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("RLE file", "*.rle"));
            selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                Path inFile = selectedFile.toPath();
                BufferedReader reader = Files.newBufferedReader(inFile, charset);
                fileArray = fileHandling.getPatternFromFile(reader);
            } else {
                throw new FileNotFoundException("Cancel was pressed - File");
            }

        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the pattern file", ioe);
        }

        if (selectedFile != null) {
            selectPatternLogic(fileArray);
        }
    }

    /**
     * Let the user select a pattern from an URL link to a rle file.
     */
    void selectPatternFromURL() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter URL");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the URL below:");
        String enteredURL = "";

        try {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                enteredURL = result.get();
                if (enteredURL.endsWith(".rle")) {
                    URL url = new URL(enteredURL);
                    URLConnection conn = url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    fileArray = fileHandling.getPatternFromFile(reader);

                } else {
                    throw new Exception("You tried to use a different file format. \n" +
                            "Only .rle files are allowed.");
                }
            } else {
                throw new NullPointerException("Cancel was pressed");
            }
        } catch (IOException ioe) {
            showErrorMessage("There was an error getting the file", ioe);
        } catch (NullPointerException npe) {
            dialog.close();
        } catch (Exception e) {
            showErrorMessage("Only .rle files can be submitted", e);
        }

        if (!enteredURL.isEmpty()) {
            selectPatternLogic(fileArray);
        }
    }

    /**
     * Pausing the game by stopping the animation.
     */
    void pauseGame(){
        isRunning = false;
    }

    /**
     * Resuming the game by starting the animation again.
     */
    void resumeGame(){
        isRunning = true;
    }

    /**
     * Exiting the game.
     */
    void exitGame(){
        System.exit(0);
    }

    /**
     * Selects the correct method for setting the pattern into the current board.
     * @param array the pattern array to be put into the current board
     */
    private void selectPatternLogic(boolean[][] array) {
        try {
            if(board instanceof DynamicBoard) {
                ((DynamicBoard) board).setInputInBoard(((DynamicBoard) board).createArrayListFromArray(array));
                draw();
                drawGrid();
            } else {
                ((StaticBoard) board).transferPatternToBoard(array);
                draw();
                drawGrid();
            }
            // This is added to avoid errors when pressing cancel.
            // It is intended to be empty, as nothing should happen.
        } catch (NullPointerException cancelException) {
        }
    }

    /**
     * Returns whether the animation is running or not.
     * @return      <code>true</code> if the animation
     *              is running.
     */
    boolean getIsRunning(){
        return isRunning;
    }

    /**
     * Clears the board.
     */
   public void clearBoard(){
        isRunning = false;
        board.clearBoard();
        draw();
    }

    /**
     * Sets the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    void setSpeed(int value) {
        speed = value;
        draw();
    }

    /**
     * Returns the speed of the animation.
     * @return  the speed of the animation
     */
    private int getSpeed(){
        return speed;
    }

    /**
     * Sets color to the alive cells.
     * @param colorPicker       the input color to set on the cell
     */
    void setCellColor(ColorPicker colorPicker){
        cellColor = colorPicker.getValue();
        draw();
    }

    /**
     * Sets color to the grid.
     * @param colorPicker       the input color to set on the grid
     */
    void setGridColor(ColorPicker colorPicker) {
        gridColor = colorPicker.getValue();
        drawGrid();
    }

    /**
     * Sets color to the boards background.
     * @param colorPicker       the input color to set on the boards background
     */
    void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
        drawBackground();

        if (!gridIsOn) {
            gridColor = boardColor;
        }
        drawBackground();
    }


    /**
     * Sets random colors to the cells.
     * @param value     <code>true</code> if drawRandomColors is to be turned on
     */
    void setDrawRandomColors(boolean value) {
        drawRandomColors = value;
    }

    /**
     * Hides the grid. It works by making the
     * grid color the same as the board color.
     */
    void switchOffGrid() {
        gridIsOn = false;
        gridColor = boardColor;
        drawGrid();
    }

    /**
     * Shows the grid again. It sets the grid color
     * equal to the <code>ColorPicker</code> related to grid color.
     * @param colorPickerGrid   The <code>ColorPicker</code> related
     *                          to grid color.
     */
    void switchOnGrid(ColorPicker colorPickerGrid) {
        gridIsOn = true;
        gridColor = colorPickerGrid.getValue();
        drawGrid();
    }

    /**
     * Generates the error message box.
     * @param HeaderText    The text to be shown depending on the type of error produced.
     * @param ioe           The type of exception being handled.
     */
    private void showErrorMessage(String HeaderText, Exception ioe) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(HeaderText);

        if (ioe.toString().contains("UnknownHostException")) {
            alert.setContentText("The URL entered was not valid (UnknownHostException).");
        } else if (ioe.toString().contains("MalformedURLException")) {
            alert.setContentText("The URL entered was not valid (MalformedURLException).");
            alert.showAndWait();
        } else if (ioe.toString().contains("Cancel")) {
            return;
        } else if (ioe.toString().contains("FileNotFoundException")) {
            alert.setContentText("The file could not be found (FileNotFoundException).");
            alert.showAndWait();
        } else if (ioe.toString().contains("NoSuchFileException")) {
            alert.setContentText("The file could not be found (NoSuchFileException).");
            alert.showAndWait();
        } else {
            alert.setContentText("Error: " + ioe);
            alert.showAndWait();
        }
    }
}
