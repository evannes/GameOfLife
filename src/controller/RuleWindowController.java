package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Rulesets;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class for the rule selection window.
 * It is accessed from the main game window using the button
 * named "Select rules".
 *
 * @author  Alexander Kingdon
 * @version %I%, %G%
 * @since   1.0
 */
public class RuleWindowController implements Initializable {

    @FXML
    private Rulesets rulesets;
    public ListView ruleList;
    public Label ruleDescriptionText;
    public Button ruleWindowCancel;
    public Button ruleWindowOK;

    public void showRuleDescription() {
        ruleDescriptionText.setText(rulesets.getRuleDescription());
    }

    public void okButton(ActionEvent event) {
        rulesets.setRules();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void cancelButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        rulesets = new Rulesets(ruleList);
    }
}
