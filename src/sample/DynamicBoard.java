package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miina Lervik
 * @author Elise Vannes
 * @author Alexander Kingdon
 */

public class DynamicBoard extends Board {
    private int minWidth;
    private int minHeight;

    public DynamicBoard() {
        minWidth = 160;
        minHeight = 100;
        grid = new DynamicGrid(x, y);
    }

    /**
     * Constructs and initiates the playing board used for unit testing.
     * @param board     the board used instead of the default board
     */
    public DynamicBoard(DynamicGrid grid) {
        this.grid = grid;
    }

    /**
     * The method creating an ArrayList our of an array.
     * @param array     the two-dimensional array to be converted
     * @return          a two-dimensional ArrayList with the same content as the input array
     */
    public List<List<Boolean>> createArrayListFromArray(boolean[][] array) {
        List<List<Boolean>> listArray = new ArrayList<>();
        for(int i = 0; i < array.length; i++){
            listArray.add(new ArrayList<>());
            for(int j = 0; j < array[0].length; j++){
                Boolean b = array[i][j];
                listArray.get(i).add(b);
            }

        }
        return listArray;
    }

    public void set_input_in_board(List<List<Boolean>> inputArray) {
        resize(inputArray.size(), inputArray.get(0).size());
        grid.clear();

        int xStartIndex = (int)Math.floor((grid.getWidth() - inputArray.size())/2);
        int yStartIndex = (int)Math.floor((grid.getHeight() - inputArray.get(0).size())/2);

        for (int x = 0; x < inputArray.size(); x++) {
            for (int y = 0; y < inputArray.get(0).size(); y++) {
                grid.setValue(x + xStartIndex, y + yStartIndex, inputArray.get(x).get(y));
            }
        }
    }

    private void resize(int width, int height) {
        float widthFactor = 1.0f / grid.getWidth() * width;
        float heightFactor = 1.0f / grid.getHeight() * height;

        if (widthFactor > heightFactor) {
            // bredde har større faktor enn høyde
            height = (int)Math.ceil((float)height / heightFactor * widthFactor);
        }
        else {
            // Høyde har større faktor enn bredde
            width = (int)Math.ceil((float)width / widthFactor * heightFactor);
        }

        width = Math.max(width, minWidth);
        height = Math.max(height, minHeight);
        ((DynamicGrid)grid).resize(width, height);
    }

    ////////////////////////   KUN FOR Å TESTE!
    public void print(List<List<Boolean>> array) {
        for(int i = 0; i < array.size(); i++) {
            for(int j = 0; j < array.get(0).size(); j++) {
                System.out.print(array.get(i).get(j) == true ? "■" : "□");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
    }


}
