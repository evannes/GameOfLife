package sample;

import javafx.animation.AnimationTimer;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class StaticBoard extends Board{
    //The canvas is 800 x 500 px so in order to create square cells the array must maintain a similar ratio
    public boolean[][] staticBoardArray = new boolean[x][y];
    AnimationTimer drawTimer;

    /**
     * Constructs and initiates the visible playing board.
     */
    public StaticBoard() {
        rules.setBoard(staticBoardArray);
        boardGraphics.drawStatic();
        drawTimer = new AnimationTimer() {
            public void handle(long now) {
                if (isRunning && (now - tid) > boardGraphics.getSpeed()) {
                    boardGraphics.drawStatic();
                    rules.nextGeneration();
                    tid = System.nanoTime();
                }

                if (isClearing){
                    isClearing = false;
                    boardGraphics.drawStatic();
                }
            }
        };

        drawTimer.start();
    }

    /**
     * Constructs and initiates the playing board used for unit testing.
     * @param board     the board used instead of the default board
     */
    public StaticBoard(boolean[][] board) {
        rules.setBoard(board);
        this.staticBoardArray = board;
    }

    @Override
    public void defaultStartBoard(){
        staticBoardArray[0][2] = true;
        staticBoardArray[1][2] = true;
        staticBoardArray[2][2] = true;
        staticBoardArray[2][1] = true;
        staticBoardArray[1][0] = true;
    }

    @Override
    public void newGame() {
        clearBoard();
        rules.setBoard(staticBoardArray);
        defaultStartBoard();
        isRunning = true;
    }

    @Override
    public void clearBoard(){
        isRunning = false;

        /*for(int i = 0; i < staticBoardArray.length; i++) {
            for(int j = 0; j < staticBoardArray[0].length; j++) {*/
        for(int i = 0; i < this.staticBoardArray.length; i++) {
            for(int j = 0; j < this.staticBoardArray[0].length; j++) {
                staticBoardArray[i][j] = false;
            }
        }
        rules.setBoard(staticBoardArray);
        isClearing = true;
    }

    @Override
    public String toString(){
        String boardStringOutput = "";
        for(int i = 0; i < staticBoardArray.length; i++) {
            for(int j = 0; j < staticBoardArray[0].length; j++) {
                if (staticBoardArray[i][j]) {
                    boardStringOutput += "1";
                    } else {
                    boardStringOutput += "0";
                    }
                }
            }
        return boardStringOutput;
    }

    public void selectPatternFromDisk(){
        boolean[][] array = fileHandling.readPatternFromDisk();
        transferPatternToBoard(array);
    }
    public void selectPatternFromURL(){
        boolean[][] array = fileHandling.readPatternFromURL();
        transferPatternToBoard(array);
    }

    private void transferPatternToBoard(boolean[][] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[0].length; j++) {
                staticBoardArray[i][j] = array[i][j];
            }
        }
    }

}
