package model;

/**
 * Rules is the class containing the two methods used to either:
 * <ul>
 * <li>See if a cell should be spawned, or
 * <li>See if a cell should survive to the next generation
 * </ul>
 * <p>
 * When the custom ruleset extension task was implemented,
 * the class was expanded with the switch statements related to
 * rulesets found on
 * <a href="http://conwaylife.com/wiki/Rules#Well-known_Life-like_cellular_automata">LifeWiki</a>.
 * The rule names are also taken from LifeWiki.
 *
 * @author  Miina Lervik
 * @author  Elise Vannes
 * @author  Alexander Kingdon
 * @version %I%, %G%
 * @since   1.0
 */
public class Rules {
    static String ruleSet = "Default (Life)";

    /**
     * The method checking if a cell should be born.
     * @param counter   the amount of alive neighbors to the cell
     * @return          <code>true</code> if the cell has the exact number
     *                  of neighbors required to stay alive defined from the rule set in use
     */
    public boolean shouldSpawnActiveCell(int counter) {
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
    }

    /**
     * The method checking if an alive cell should stay alive or die.
     * @param counter   the amount of alive neighboring cells
     * @return          <code>true</code> if the amount of neighboring
     *                  cells is the same as required from the rule set in use
     */
    public static boolean shouldStayAlive(int counter) {
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


