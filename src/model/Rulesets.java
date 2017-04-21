package model;


import javafx.scene.control.ListView;

/**
 * Created by Alexander Kingdon on 03.04.2017.
 */
public class Rulesets {

    private ListView ruleList;
    private String ruleIdentifier;

    public Rulesets(ListView ruleList) {
        this.ruleList = ruleList;
    }

    public String getRuleDescription() {
        ruleIdentifier = (String) ruleList.getSelectionModel().getSelectedItem();
        String ruleDescription;
        switch (ruleIdentifier) {
            case "Replicator":
                ruleDescription = "B1357/S1357 \n- A cell is born when it has one, three, five or seven neighbors." +
                        "\n- A cell will survive when it has one, three, five or seven neighbors. \n\n" +
                        "LifeWiki description: \n\nA rule in which every pattern is a replicator.";
                break;
            case "Fredkin":
                ruleDescription = "B1357/S02468 \n- A cell is born when it has one, three, five or seven neighbors." +
                        "\n- A cell will survive when it has zero, two, four, six or eight neighbors. \n\n" +
                        "LifeWiki description: \n\nA rule in which, like Replicator, every pattern is a replicator.";
                break;
            case "Seeds":
                ruleDescription = "B2/S \n- A cell is born when it has two neighbors. \n" +
                        "- A cell will never survive to the next generation. \n\n" +
                        "LifeWiki description: \n\nAn exploding rule in which every cell dies in every generation." +
                        " \n" +
                        "It has many simple orthogonal spaceships, \n" +
                        "though it is in general difficult to create patterns that don't explode.";
                break;
            case "Live Free or Die":
                ruleDescription = "B2/S0 \n- A cell is born when it has two neighbors.\n" +
                        "- A cell will survive only when it has zero neighbors. \n\n" +
                        "LifeWiki description: \n\nAn exploding rule in which only cells with no neighbors survive. " +
                        "It has many spaceships, puffers, and oscillators, " +
                        "some of infinitely extensible size and period.";
                break;
            case "Life without death":
                ruleDescription = "B3/S012345678 \n- A cell is born when it has three neighbors.\n" +
                        "- A cell will survive when it has any number of neighbors, including zero. \n\n" +
                        "LifeWiki description: \n\nAn expanding rule that produces complex flakes. \n" +
                        "It also has important ladder patterns.";
                break;
            case "Flock":
                ruleDescription = "B3/S12 \n- A cell is born when it has three neighbors.\n" +
                        "- A cell will survive when it has one or two neighbors. \n\n" +
                        "LifeWiki description: \n\n A rule which decays into small still lifes and oscillators";
                break;
            case "Mazectric":
                ruleDescription = "B3/S1234 \n- A cell is born when it has three neighbors.\n" +
                        "- A cell will survive when it has one, two, three or four neighbors. \n\n" +
                        "LifeWiki description: \n\nAn expanding rule that crystalizes to form maze-like designs \n" +
                        "that tend to be straighter (ie. have longer \"halls\") than the standard maze rule.";
                break;
            case "Maze":
                ruleDescription = "B3/S12345 \n- A cell is born when it has three neighbors.\n" +
                        "- A cell will survive when it has one, two, three, four or five neighbors. \n\n" +
                        "LifeWiki description: \n\nAn expanding rule that crystalizes to form maze-like designs.";
                break;
            case "Default (Conway's Life)":
                ruleDescription = "B3/S23 \n- A cell is born when it has three neighbors. \n" +
                        "- A cell will survive when it has two or three neighbors. \n\n" +
                        "LifeWiki description: \n\nA chaotic rule that is by far the most well-known and " +
                        "well-studied. \n" +
                        "It exhibits highly complex behavior.";
                break;
            case "2x2":
                ruleDescription = "B36/S125 \n- A cell is born when it has three or six neighbors. \n" +
                        "- A cell will survive when it has one, two or five neighbors. \n\n" +
                        "LifeWiki description: \n\nA chaotic rule with many simple still lifes, " +
                        "oscillators and spaceships. \nIts name comes from the fact that it sends patterns made up " +
                        "of 2x2 blocks to patterns made up of 2x2 blocks.";
                break;
            case "HighLife":
                ruleDescription = "B36/S23 \n- A cell is born when it has three or six neighbors. \n" +
                        "- A cell will survive when it has two or three neighbors. \n\n" +
                        "LifeWiki description: \n\nA chaotic rule very similar to Conway's Life " +
                        "that is of interest \nbecause it has a simple replicator.";
                break;
            case "Move":
                ruleDescription = "B368/S245 \n- A cell is born when it has three, six or eight neighnors. \n" +
                        "- A cell will survive when it has two, four or five neighbors. \n\n" +
                        "LifeWiki description: \n\nA rule in which random patterns tend to stabilize " +
                        "extremely quickly. \nHas a very common slow-moving spaceship and slow-moving puffer.";
                break;
            case "Day & Night":
                ruleDescription = "B3678/S34678 \n- A cell is born when it has three, six, seven or eight neighbors." +
                        "\n- A cell will survive when it has three, four, six, seven or eight neighbors. \n\n" +
                        "LifeWiki description: \n\nA stable rule that is symmetric under on-off reversal. " +
                        "\nMany patterns exhibiting highly complex behavior have been found for it.";
                break;
            default:
                ruleDescription = "B3/S23 \n- A cell is born when it has three neighbors. \n" +
                        "- A cell will survive when it has two or three neighbors. \n\n" +
                        "LifeWiki description: \n\nA chaotic rule that is by far the most well-known and " +
                        "well-studied. \n" +
                        "It exhibits highly complex behavior.";
                break;
        }
        return ruleDescription;
    }

    public void setRules() {
        if (!ruleIdentifier.equals("")) {
            Rules.ruleSet = ruleIdentifier;
        } else {
            Rules.ruleSet = "Default (Conway's Life)";
        }
    }
}
