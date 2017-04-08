package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alex on 05.04.2017.
 */
public class StatisticsController implements Initializable {

    @FXML
    private StatisticsLogic statisticsLogic;
    private StatisticsView statisticsView;
    private int iterations;
    private int specifiedNumber;

    @FXML
    private TextField iterationValue;

    @FXML
    private LineChart<Number, Number> statisticsChart;

    protected void setClonedBoard(DynamicBoard clonedBoard) {
        statisticsLogic.setClonedBoard(clonedBoard);
    }

    public void getStatistics() {
        if (iterations != 0) {
            statisticsLogic.setIterations(iterations);
        }
        int[][] stats = statisticsLogic.getStatistics();
        XYChart.Series<Number, Number> livingCellsSeries = statisticsView.populateLivingCells(stats);
        XYChart.Series<Number, Number> changeInLivingCellsSeries = statisticsView.populateChangeInLivingCells(stats);
        XYChart.Series<Number, Number> similarityMeasureSeries = statisticsView.populateSimilarityMeasure(stats);

        statisticsChart.getData().addAll(livingCellsSeries, changeInLivingCellsSeries, similarityMeasureSeries);
    }

    public void getSpecifiedStatistics() {
        statisticsView = new StatisticsView();
        specifiedNumber = statisticsView.createDialogWindow();
        if (specifiedNumber > iterations) {
            specifiedNumber = iterations;
        }
        statisticsLogic.setSimilaritySpecifiedNumber(specifiedNumber);
        getStatistics();
    }

    public void cancelButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        statisticsLogic = new StatisticsLogic();
        statisticsView = new StatisticsView();

        iterationValue.textProperty().addListener((observable, oldValue, value) -> {
            iterations = Integer.parseInt(value);
        });

        iterations = Integer.parseInt(iterationValue.getText());
    }
}
