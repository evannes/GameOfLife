package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miinael on 28.03.2017.
 */
public class BoardGraphics {
    private boolean drawRandomColors;
    private double drawScale = 1;
    private double gridSize = 0.1;
    private double cellWidth;
    private double cellHeight;
    private int speed;
    private Color cellColor = Color.LIGHTSEAGREEN;
    private Color gridColor = Color.BLACK;
    private Color boardColor = Color.WHITE;
    private Canvas canvas;
    Rules rules = Rules.getInstance();

    public List<List<Boolean>> dynamicBoardArray = new ArrayList<List<Boolean>>(160);
    private boolean[][] staticBoardArray;
    private int boardSizeX;
    private int boardSizeY;

    public BoardGraphics(Canvas canvas){
        this.canvas = canvas;
    }



    /**
     * The method drawing the board with alive cells, background.
     * and grid. The method will draw the board according to the array applied in the <code>Rules</code> class.
     */
    public void drawDynamic() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.dynamicBoardArray = rules.getListBoard();
        gc.setFill(gridColor);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        cellWidth = (canvas.getWidth()*drawScale - gridSize) / dynamicBoardArray.size();
        cellHeight = (canvas.getHeight()*drawScale - gridSize) / dynamicBoardArray.get(0).size();

        boardSizeX = dynamicBoardArray.size();
        boardSizeY = dynamicBoardArray.get(0).size();

        for (int i = 0; i < boardSizeX; i++) {
            for (int j = 0; j < boardSizeY; j++) {

                if(dynamicBoardArray.get(i).get(j) == true && drawRandomColors) {
                    gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                }
                else if (dynamicBoardArray.get(i).get(j) == true) {
                    gc.setFill(cellColor);
                }
                else {
                    gc.setFill(boardColor);
                }

                double cellX = cellHeight * i;
                double cellY = cellWidth * j;

                gc.fillRect(cellX + gridSize, cellY + gridSize, cellWidth - gridSize, cellHeight - gridSize);
            }
        }
    }

    /**
     * The method drawing the board with alive cells, background.
     * and grid. The method will draw the board according to the array applied in the <code>staticRules</code> class.
     */
    public void drawStatic() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        staticBoardArray = rules.getStaticBoard();
        gc.setFill(gridColor);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        cellWidth = (canvas.getWidth()*drawScale - gridSize) / staticBoardArray.length;
        cellHeight = (canvas.getHeight()*drawScale - gridSize) / staticBoardArray[0].length;

        for (int i = 0; i < staticBoardArray.length; i++) {
            for (int j = 0; j < staticBoardArray[0].length; j++) {

                if(staticBoardArray[i][j] == true && drawRandomColors) {
                    gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                }
                else if (staticBoardArray[i][j] == true) {
                    gc.setFill(cellColor);
                }
                else {
                    gc.setFill(boardColor);
                }

                double cellX = cellHeight * i;
                double cellY = cellWidth * j;

                gc.fillRect(cellX + gridSize, cellY + gridSize, cellWidth - gridSize, cellHeight - gridSize);
            }
        }
    }

    /**
     * The method which lets the user set or remove cells manually from the board.
     */
    public void userDrawCell(){
        canvas.setOnMouseClicked(e -> {
            double cellWidth = ((canvas.getWidth()*drawScale) + gridSize) / dynamicBoardArray.size();
            double cellHeight = ((canvas.getHeight()*drawScale) + gridSize) / dynamicBoardArray.get(0).size();
            int korX = (int)e.getX();
            int korY = (int)e.getY();
            int arrayX = (int)Math.floor(korX/cellWidth);
            int arrayY = (int)Math.floor(korY/cellHeight);

            if(dynamicBoardArray.get(arrayX).get(arrayY) == false) {
                dynamicBoardArray.get(arrayX).set(arrayY,true);
            } else {
                dynamicBoardArray.get(arrayX).set(arrayY,false);
            }
            rules.setBoard(dynamicBoardArray);
            drawDynamic();
        });
    }

    /**
     * The method which lets the user set or remove cells manually from the board.
     */
    public void userDrawCellStatic(){
        canvas.setOnMouseClicked(e -> {
            cellWidth = (canvas.getWidth()*drawScale + gridSize) / staticBoardArray.length;
            cellHeight = (canvas.getHeight()*drawScale + gridSize) / staticBoardArray[0].length;
            int korX = (int)e.getX();
            int korY = (int)e.getY();
            int arrayX = (int)Math.floor(korX/(int)cellWidth);
            int arrayY = (int)Math.floor(korY/(int)cellHeight);

            if(staticBoardArray[arrayX][arrayY] == false) {
                staticBoardArray[arrayX][arrayY] = true;
            } else {
                staticBoardArray[arrayX][arrayY] = false;
            }
            rules.setBoard(staticBoardArray);
            drawStatic();
        });
    }

    /**
     * The method scaling the board.
     * The higher the value passed in the larger the board will become.
     * @param value     the value used to change the size of the board.
     */
    protected void setDrawScale(double value) {
        drawScale = value;
        gridSize = 0.1 * value;
    }


    /**
     * The method setting the speed of the animation.
     * @param value     the value used to set the speed of the animation
     */
    protected void setSpeed(int value) {
        speed = value;
    }

    protected int getSpeed(){
        return speed;
    }

    /**
     * The method setting color to the alive cells.
     * @param colorPicker       the input color to set on the cell
     */
    public void setCellColor(ColorPicker colorPicker){
        cellColor = colorPicker.getValue();
    }

    /**
     * The method setting color to the grid.
     * @param colorPicker       the input color to set on the grid
     */
    public void setGridColor(ColorPicker colorPicker) {
        gridColor = colorPicker.getValue();
    }

    /**
     * The method setting color to the boards background.
     * @param colorPicker       the input color to set on the boards background
     */
    public void setBoardColor(ColorPicker colorPicker) {
        boardColor = colorPicker.getValue();
    }


    /**
     * The method used for setting random colors to the cells.
     * @param value     <code>true</code> if drawRandomColors is to be turned on
     */
    public void setDrawRandomColors(boolean value) {
        drawRandomColors = value;
    }
}
