package sample;


import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public class DynamicBoard extends Board {
    private int original_x_size;
    private int original_y_size;
    public List<List<Boolean>> dynamicBoardArray = new ArrayList<List<Boolean>>(160);
    AnimationTimer drawTimer;

    /**
     * Constructs and initiates the visible playing board.
     */
    public DynamicBoard(BoardGraphics boardGraphics) {
        super(boardGraphics);
        initStartBoard();
        rules.setBoard(dynamicBoardArray);
        boardGraphics.drawDynamic();
        drawTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - tid) > boardGraphics.getSpeed()) {
                    rules.nextListGeneration();
                    boardGraphics.drawDynamic();

                    tid = System.nanoTime();
                }

                if (isClearing){
                    isClearing = false;
                    boardGraphics.drawDynamic();
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
        rules.setBoard(dynamicBoardArray);
        this.dynamicBoardArray = board;
    }

    /**
     * The method initializing the board with all values set to false
     */
    public void initStartBoard(){
        for(int i = 0; i < x; i++) {
            dynamicBoardArray.add(i, new ArrayList<Boolean>(y));
        }

        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                dynamicBoardArray.get(i).add(j,false);
            }
        }
    }

    @Override
    public void defaultStartBoard(){
        dynamicBoardArray.get(0).set(2,true);
        dynamicBoardArray.get(1).set(2,true);
        dynamicBoardArray.get(2).set(2,true);
        dynamicBoardArray.get(2).set(1,true);
        dynamicBoardArray.get(1).set(0,true);
    }

    @Override
    public void newGame() {
        clearBoard();
        rules.setBoard(dynamicBoardArray);
        defaultStartBoard();
        isRunning = true;
    }

    @Override
    public void clearBoard(){
        isRunning = false;

        for(int i = 0; i < dynamicBoardArray.size(); i++) {
            for(int j = 0; j < dynamicBoardArray.get(0).size(); j++) {
                dynamicBoardArray.get(i).set(j,false);
            }
        }
        rules.setBoard(dynamicBoardArray);
        isClearing = true;
    }

    @Override
    public String toString(){
        String boardStringOutput = "";
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                if (dynamicBoardArray.get(i).get(j)) {
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
        selectPatternLogic(array);
    }

    public void selectPatternFromURL() {
        boolean[][] array = fileHandling.readPatternFromURL();
        selectPatternLogic(array);
    }

    private void selectPatternLogic(boolean[][] array) {
        try {
            List<List<Boolean>> listArray = fileHandling.createArrayListFromArray(array);
            setDynamicBoardArray(listArray);

            rules.setBoard(dynamicBoardArray);
            boardGraphics.drawDynamic();
        } catch (NullPointerException cancelException) {
        }
    }

    private void setDynamicBoardArray(List<List<Boolean>> listArray) {
        int inputX = listArray.size();
        int inputY = listArray.get(0).size();
        original_x_size = dynamicBoardArray.size();
        original_y_size = dynamicBoardArray.get(0).size();
        int diffX = inputX - original_x_size;
        int diffY = inputY - original_y_size;
        int width_loop_count;
        int height_loop_count;
        double factor;

        System.out.println(inputY);
        System.out.println(original_y_size);
        System.out.println(inputX);
        System.out.println(original_x_size);

        //check is the input array is bigger than dynamicBoardArray on both the x and y coordinates
        if( inputX > original_x_size && inputY > original_y_size){
            //check which of the factors is the largest
            if(inputX/x > inputY/original_y_size ){
                factor = (double)inputX/original_x_size;
                height_loop_count = (int)(factor*original_y_size)-original_y_size ;
                System.out.println("height loop: "+ height_loop_count);
                System.out.println("diffX: " + diffX);
                enlarge(diffX, height_loop_count);
            } else {
                factor = (double)inputY/original_y_size ;
                System.out.println(factor);
                width_loop_count = (int)(factor*original_x_size)-original_x_size;
                System.out.println("width loop: "+ width_loop_count);
                System.out.println("y = " + original_y_size);
                System.out.println("diffY: " + diffY);
                enlarge(width_loop_count, diffY);
            }
        //check if the input array is larger on the y coordinate
        } else if(inputY > original_y_size) {
            factor = (double)inputY/y;
            width_loop_count = (int)(factor*original_x_size)-original_x_size;
            enlarge(width_loop_count, diffY);
        //check if the input array is larger on the x coordinate
        } else if(inputX > original_x_size) {
            factor = (double)inputX/original_x_size;
            height_loop_count = (int)(factor*original_y_size)-original_y_size ;
            enlarge(diffX, height_loop_count);

        }

        //place the input array in the middle of dynamicBoardArray and transfer all values
        System.out.println("dynamic size: "+ dynamicBoardArray.size() +"  " +listArray.size());
        System.out.println("dynamic get0: "+ dynamicBoardArray.get(0).size() +"  "+ listArray.get(0).size());
        int xStartIndex = (dynamicBoardArray.size() - listArray.size())/2;
        System.out.println("startindex X: " + xStartIndex);
        int yStartIndex = (dynamicBoardArray.get(0).size() - listArray.get(0).size())/2;
        System.out.println("startindex y: " + yStartIndex);
        int xIndex = 0;

        /* ///INTSTREAM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        IntStream.range(0,10).forEach(e -> System.out.println(e));
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
        dynamicBoardArray.forEach(e-> e.forEach()));

        */

        for (int i = xStartIndex; i < listArray.size()+xStartIndex; i++){
            int yIndex = 0;
            for(int j = yStartIndex; j < listArray.get(0).size()+yStartIndex; j++){
                Boolean value = listArray.get(xIndex).get(yIndex);
                dynamicBoardArray.get(i).set(j, value);
                yIndex++;
            }
            xIndex++;
        }

    }

    private void enlarge(int width_factor, int height_factor) {
        for(int f = 0; f < height_factor; f++) {
            for (int i = 0; i < original_x_size; i++) {
                dynamicBoardArray.get(i).add(Boolean.FALSE);
            }
            original_y_size++;
            System.out.println(dynamicBoardArray.size()+" "+ dynamicBoardArray.get(0).size());
        }

        for(int f = 0; f < width_factor; f++) {

            dynamicBoardArray.add(new ArrayList());
            for (int i = 0; i < original_y_size; i++) {
                dynamicBoardArray.get(original_x_size).add(i, Boolean.FALSE);
            }
            original_x_size++;
            System.out.println(dynamicBoardArray.size()+" "+ dynamicBoardArray.get(0).size());
        }

    }


}
