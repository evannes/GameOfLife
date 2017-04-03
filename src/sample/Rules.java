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
            case "Default (Life)" : return counter == 3;
            case "Seeds" : return counter == 2;
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
            case "Default (Life)" : return counter == 2 || counter == 3;
            case "Seeds" : return false;
            default : return counter == 2 || counter == 3;
        }
    }


}


