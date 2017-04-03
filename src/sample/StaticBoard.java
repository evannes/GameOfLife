package sample;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */
public class StaticBoard extends Board {
    //The canvas is 800 defaultWidth 500 px so in order to create square cells the array must maintain a similar ratio
    public boolean[][] staticBoardArray;
    public boolean[][] clone;


    public StaticBoard(int width, int height) {
        super(width, height);
        staticBoardArray = new boolean[defaultWidth][defaultHeight];
    }

    @Override
    public void initStartBoard(){
        staticBoardArray = new boolean[defaultWidth][defaultHeight];
    }

    @Override
    public void setValue(int x, int y, boolean value) {
        staticBoardArray[x][y] = value;
    }

    @Override
    public boolean getValue(int x, int y) {
        return staticBoardArray[x][y];
    }

    @Override
    public void toggleValue(int x, int y) {
        staticBoardArray[x][y] = !staticBoardArray[x][y];
    }

    @Override
    public int getWidth() {
        return staticBoardArray.length;
    }

    @Override
    public int getHeight() {
        return staticBoardArray[0].length;
    }

    @Override
    public void createClone() {
        clone = new boolean[getWidth()][getHeight()];
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                clone[i][j] = getValue(i, j);
            }
        }
    }

    @Override
    public void toggleBoards() {
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                setValue(i, j, clone[i][j]);
            }
        }
    }

    @Override
    public void setCloneValue(int x, int y, boolean value) {
        clone[x][y] = value;
    }

    @Override
    public void clearBoard() {
        for(int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                staticBoardArray[i][j] = false;
            }
        }
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


    private void transferPatternToBoard(boolean[][] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[0].length; j++) {
                staticBoardArray[i][j] = array[i][j];
            }
        }
    }

}
