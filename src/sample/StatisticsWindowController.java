package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Alex on 05.04.2017.
 */
public class StatisticsWindowController implements Initializable {

    @FXML
    private StatisticsWindow statisticsWindow;

    public void setClonedBoard(DynamicBoard clonedBoard) {
        statisticsWindow.setClonedBoard(clonedBoard);
    }

    public void cancelButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        statisticsWindow = new StatisticsWindow();
    }
}
