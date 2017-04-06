package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alex on 05.04.2017.
 */
public class StatisticsWindowController implements Initializable {

    @FXML
    private StatisticsWindow statisticsWindow;
    private StatisticsWindowDialog statisticsWindowDialog;
    private int iterations;
    private int specifiedNumber;

    @FXML
    private TextField iterationValue;

    public void setClonedBoard(DynamicBoard clonedBoard) {
        statisticsWindow.setClonedBoard(clonedBoard);
    }

    public void getStatistics() {
        System.out.println(iterationValue.getText());
        if (iterations != 0) {
            statisticsWindow.setIterations(iterations);
        }
        statisticsWindow.getStatistics();
    }

    public void getSpecifiedStatistics() {
        statisticsWindowDialog = new StatisticsWindowDialog();
        specifiedNumber = statisticsWindowDialog.createDialogWindow();
        statisticsWindow.setSimilaritySpecifiedNumber(specifiedNumber);
        getStatistics();
    }

    public void cancelButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        statisticsWindow = new StatisticsWindow();

        iterationValue.textProperty().addListener((observable, oldValue, value) -> {
            iterations = Integer.parseInt(value);
        });
    }
}
