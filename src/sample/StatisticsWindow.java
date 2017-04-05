package sample;

import java.util.List;

/**
 * Created by Alex on 05.04.2017.
 */
public class StatisticsWindow {

    private DynamicBoard clonedBoard;

    public void setClonedBoard(DynamicBoard clonedBoard) {
        this.clonedBoard = clonedBoard;
        int i = 0;
        while (i <= 10) {
            clonedBoard.nextGeneration();
            i++;
        }
    }

    public int[][] getStatistics(Board clonedBoard, int iterations) {
        int[][] gameStatistics = new int[3][iterations+1];
        return  gameStatistics;
    }


}
