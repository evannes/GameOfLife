package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alex on 03.04.2017.
 */
public class RuleWindowController implements Initializable {

    @FXML
    RuleWindow ruleWindow;
    public ListView ruleList;
    public Label ruleDescriptionText;
    public Button ruleWindowCancel;
    public Button ruleWindowOK;

    public void showRuleDescription() {
        ruleDescriptionText.setText(ruleWindow.getRuleDescription());
    }

    public void okButton(ActionEvent event) {
        ruleWindow.setRules();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void cancelButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        ruleWindow = new RuleWindow(ruleList);
    }
}
