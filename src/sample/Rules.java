package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miinael on 15.02.2017.
 */
public class Rules {
    private static Rules rules = null;
    protected static String ruleSet = "Default (Life)";


    public static Rules getInstance() {
        if(rules == null) {
            rules = new Rules();
        }
        return rules;
    }




    /**
     * The method checking if a cell should be born.
     * @param counter   the amount of alive neighbors to the cell
     * @return          <code>true</code> if the cell has exactly 3 neighbors
     */
    protected boolean shouldSpawnActiveCell(int counter) {
        switch (ruleSet) {
            case "Replicator": return counter == 1 || counter == 3 || counter == 5 || counter == 7;
            case "Fredkin": return counter == 1 || counter == 3 || counter == 5 || counter == 7;
            case "Seeds": return counter == 2;
            case "Life Free or Die": return counter == 2;
            case "Life without death": return counter == 3;
            case "Flock": return counter == 3;
            case "Mazectric": return counter == 3;
            case "Maze": return counter == 3;
            case "Default (Conway's Life)": return counter == 3;
            case "2x2": return counter == 3 || counter == 6;
            case "HighLife": return counter == 3 || counter == 6;
            case "Move": return counter == 3 || counter == 6 || counter == 8;
            case "Day & Night": return counter == 3 || counter == 6 || counter == 7 || counter == 8;
            default : return counter == 3;
        }
        //return counter == 3;
    }

    /**
     * The method checking if an alive cell should stay alive or die.
     * @param counter   the amount of alive neighboring cells
     * @return          <code>true</code> if the amount of neighboring
     *                  cells is 2 or 3
     */
    protected static boolean shouldStayAlive(int counter) {
        switch (ruleSet) {
            case "Replicator": return counter == 1 || counter == 3 || counter == 5 || counter == 7;
            case "Fredkin": return counter == 0 || counter == 2 || counter == 4 || counter == 6 || counter == 8;
            case "Seeds": return false;
            case "Live Free or Die": return counter == 0;
            case "Life without death": return true;
            case "Flock": return counter == 1 || counter == 2;
            case "Mazectric": return counter == 1 || counter == 2 || counter == 3 || counter == 4;
            case "Maze": return counter == 1 || counter == 2 || counter == 3 || counter == 4 || counter == 5;
            case "Default (Conway's Life)": return counter == 2 || counter == 3;
            case "2x2": return counter == 1 || counter == 2 || counter == 5;
            case "HighLife": return counter == 2 || counter == 3;
            case "Move": return counter == 2 || counter == 4 || counter == 5;
            case "Day & Night": return counter == 3 || counter == 4 || counter == 6 || counter == 7 || counter == 8;
            default : return counter == 2 || counter == 3;
        }
    }


}


