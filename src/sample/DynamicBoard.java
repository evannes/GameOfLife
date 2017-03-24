package sample;


import com.sun.deploy.util.ArrayUtil;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public class DynamicBoard extends Board {

    public Rules rules = new Rules();
    public FileHandling fileHandling = new FileHandling();
    int originalX;
    int originalY;

    private List<List<Boolean>> dynamicBoard = new ArrayList<List<Boolean>>(160);
    AnimationTimer drawTimer;


    /**
     * Constructs and initiates the visible playing board.
     * @param canvas        the canvas the board is to be painted on
     */
    public DynamicBoard(Canvas canvas) {
        super(canvas);
        initStartBoard();
        rules.setBoard(dynamicBoard);
        draw(canvas);
        drawTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - tid) > speed) {
                    draw(canvas);
                    rules.nextListGeneration();
                    tid = System.nanoTime();
                }

                if (isClearing){
                    isClearing = false;
                    draw(canvas);
                }
            }
        };

        drawTimer.start();
    }

    /**
     * Constructs and initiates the playing board used for unit testing.
     * @param board     the board used instead of the default board
     */
    public DynamicBoard(List<List<Boolean>> board) {
        rules.setBoard(dynamicBoard);
        this.dynamicBoard = board;
    }


    public void initStartBoard(){
        for(int i = 0; i < x; i++) {
            dynamicBoard.add(i, new ArrayList<Boolean>(y));
        }

        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                dynamicBoard.get(i).add(j,false);
            }
        }
    }

    /**
     * The method applying a default pattern of cells to the board.
     */
    @Override
    public void defaultStartBoard(){
        dynamicBoard.get(0).set(2,true);
        dynamicBoard.get(1).set(2,true);
        dynamicBoard.get(2).set(2,true);
        dynamicBoard.get(2).set(1,true);
        dynamicBoard.get(1).set(0,true);
    }

    /**
     * The method starting the game over again with the preset pattern.
     */
    @Override
    public void newGame() {
        clearBoard();
        rules.setBoard(dynamicBoard);
        defaultStartBoard();
        isRunning = true;
    }

    /**
     * The method drawing the board with alive cells, background.
     * and grid. The method will draw the board according to the array applied in the <code>Rules</code> class.
     * @param canvas    the canvas to be drawn on.
     */
    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        dynamicBoard = rules.getListBoard();
        gc.setFill(gridColor);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        cellWidth = (canvas.getWidth()*drawScale - gridSize) / dynamicBoard.size();
        cellHeight = (canvas.getHeight()*drawScale - gridSize) / dynamicBoard.get(0).size();

        x = dynamicBoard.size();
        y = dynamicBoard.get(0).size();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                if(dynamicBoard.get(i).get(j) == true && drawRandomColors) {
                    gc.setFill(new Color(Math.random(),Math.random(),Math.random(),1));
                }
                else if (dynamicBoard.get(i).get(j) == true) {
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
     * @param canvas        the canvas to get the coordinates from
     */
    @Override
    public void userDrawCell(Canvas canvas){
        canvas.setOnMouseClicked(e -> {
            cellWidth = (canvas.getWidth()*drawScale + gridSize) / x;
            cellHeight = (canvas.getHeight()*drawScale + gridSize) / y;
            int korX = (int)e.getX();
            int korY = (int)e.getY();
            int arrayX = (int)Math.floor(korX/(int)cellWidth);
            int arrayY = (int)Math.floor(korY/(int)cellHeight);

            if(dynamicBoard.get(arrayX).get(arrayY) == false) {
                dynamicBoard.get(arrayX).set(arrayY,true);
            } else {
                dynamicBoard.get(arrayX).set(arrayY,false);
            }
            rules.setBoard(dynamicBoard);
            draw(canvas);
        });
    }

    /**
     * The method clearing the board.
     */
    @Override
    public void clearBoard(){
        isRunning = false;

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                dynamicBoard.get(i).set(j,false);
            }
        }
        rules.setBoard(dynamicBoard);
        isClearing = true;
    }

    /**
     * Method used to unit test {@link Rules#nextListGeneration()}.
     * @return  The board array in an easy to read String format
     */
    @Override
    public String toString(){
        String boardStringOutput = "";
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                if (dynamicBoard.get(i).get(j)) {
                    boardStringOutput += "1";
                } else {
                    boardStringOutput += "0";
                }
            }
        }
        return boardStringOutput;
    }

    public void selectPatternFromDisk() {
        boolean[][] array = fileHandling.readPatternFromDisk();
        List<List<Boolean>> listArray = fileHandling.createArrayListFromArray(array);
        setDynamicBoard(listArray);
        rules.setBoard(dynamicBoard);
        draw(canvas);
    }

    public void selectPatternFromURL() {
        boolean[][] array = fileHandling.readPatternFromURL();
        List<List<Boolean>> listArray = fileHandling.createArrayListFromArray(array);
        setDynamicBoard(listArray);

        rules.setBoard(dynamicBoard);
        draw(canvas);
    }

    private void setDynamicBoard(List<List<Boolean>> listArray) {
        //se om listArray er større enn dynamicBoard
        //sjekk høyde først, så bredde
        int inputX = listArray.size();
        int inputY = listArray.get(0).size();
        originalX = dynamicBoard.size();
        originalY = dynamicBoard.get(0).size();
        int diffX = inputX - originalX;
        int diffY = inputY - originalY;

        if( inputX > originalX && inputY > originalY){
            //finn ut hvilken differanse som er størst
            int largestDiff = diffX >= diffY ? diffX : diffY;

            //gjør den større med antall til den største differansen
            for(int k = 0; k < largestDiff; k++){
                enlarge();
            }

        } else if(inputY > originalY) {
            for(int k = 0; k < diffY; k++){
                enlarge();
            }

        } else if(inputX > originalX) {
            for(int k = 0; k < diffX; k++){
                enlarge();
            }
        }

        //midtstille og overføre til brett
        int xStartIndex = (dynamicBoard.size() - listArray.size())/2;
        int yStartIndex = (dynamicBoard.get(0).size() - listArray.get(0).size())/2;
        int xIndex = 0;

        /* ///INTSTREAM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        IntStream.range(0,10).forEach(e -> System.out.println(e));
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
        dynamicBoard.forEach(e-> e.forEach()));

        */

        for (int i = xStartIndex; i < listArray.size()+xStartIndex; i++){
            int yIndex = 0;
            for(int j = yStartIndex; j < listArray.get(0).size()+yStartIndex; j++){
                Boolean value = listArray.get(xIndex).get(yIndex);
                dynamicBoard.get(i).set(j, value);
                yIndex++;
            }
            xIndex++;
        }




    }

    //gjør brettet større
    private void enlarge() {
        for(int i = 0; i < originalX; i++){
            dynamicBoard.get(i).add( Boolean.FALSE);
        }
        originalY++;
        dynamicBoard.add(new ArrayList());
        for(int i = 0; i < originalY; i++){
            dynamicBoard.get(originalX).add(i, Boolean.FALSE);
        }
        originalX++;

        System.out.println(dynamicBoard.size()+" "+ dynamicBoard.get(0).size());
    }


}
