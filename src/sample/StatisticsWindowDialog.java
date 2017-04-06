package sample;


import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Created by Alex on 06.04.2017.
 */
public class StatisticsWindowDialog {

    private int specifiedIteration;

    public int createDialogWindow() {
        TextInputDialog iterationDialog = new TextInputDialog(null);
        iterationDialog.setTitle("Specify iteration");
        iterationDialog.setHeaderText(null);
        iterationDialog.setContentText("Specify the iteration to compare with:");
        Optional<String> result = iterationDialog.showAndWait();
        result.ifPresent(number -> specifiedIteration = Integer.parseInt(number));
        return specifiedIteration;
    }
}
