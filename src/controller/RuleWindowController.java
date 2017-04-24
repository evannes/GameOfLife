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
 * @since   1.0
 */
public class RuleWindowController implements Initializable {

    @FXML
    private Rulesets rulesets;
    public ListView ruleList;
    public Label ruleDescriptionText;
    public Button ruleWindowCancel;
    public Button ruleWindowOK;

    /**
     * Setter method used to show the rule description text.
     */
    public void showRuleDescription() {
        ruleDescriptionText.setText(rulesets.getRuleDescription());
    }

    /**
     * Used to enable the OK button functionality,
     * letting the user return to the game. Found on
     * <a href="https://stackoverflow.com/questions/25037724/">Stack Overflow</a>.
     * @param event The event being handled - user clicked OK
     */
    public void okButton(ActionEvent event) {
        rulesets.setRules();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    /**
     * Used to enable the cancel button functionality,
     * letting the user return to the game. Found on
     * <a href="https://stackoverflow.com/questions/25037724/">Stack Overflow</a>.
     * @param event The event being handled - user clicked cancel
     */
    public void cancelButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    /**
     * Called to initialize a controller after its root element has been completely processed.
     * @param fxmlFileLocation  The location used to resolve relative paths for the root object,
     *                          or null if the location is not known.
     * @param resources         The resources used to localize the root object,
     *                          or null if the root object was not localized.
     * @see                     Initializable
     */
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        rulesets = new Rulesets(ruleList);
    }
}
