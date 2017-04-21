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
 * Created by Alexander Kingdon on 03.04.2017.
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
