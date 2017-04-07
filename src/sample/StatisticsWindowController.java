package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

    @FXML
    private LineChart<Number, Number> statisticsChart;

    @FXML
    private NumberAxis xAxis, yAxis;

    public void setClonedBoard(DynamicBoard clonedBoard) {
        statisticsWindow.setClonedBoard(clonedBoard);
    }

    public void getStatistics() {
        if (iterations != 0) {
            statisticsWindow.setIterations(iterations);
        }
        int[][] stats = statisticsWindow.getStatistics();
        XYChart.Series<Number, Number> livingCellsSeries = statisticsWindowDialog.populateLivingCells(stats);
        XYChart.Series<Number, Number> changeInLivingCellsSeries = statisticsWindowDialog.populateChangeInLivingCells(stats);
        XYChart.Series<Number, Number> similarityMeasureSeries = statisticsWindowDialog.populateSimilarityMeasure(stats);
        statisticsChart.getData().addAll(livingCellsSeries, changeInLivingCellsSeries, similarityMeasureSeries);

    }

    public void getSpecifiedStatistics() {
        statisticsWindowDialog = new StatisticsWindowDialog();
        specifiedNumber = statisticsWindowDialog.createDialogWindow();
        System.out.println("Number: " + specifiedNumber);
        if (specifiedNumber > iterations) {
            specifiedNumber = iterations;
        }
        System.out.println("Number3: " + specifiedNumber);
        statisticsWindow.setSimilaritySpecifiedNumber(specifiedNumber);
        getStatistics();
    }

    //public void populateStatisticsChart() {
    //    statisticsWindowDialog.populateStatisticsChart()
    //}

    public void cancelButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        statisticsWindow = new StatisticsWindow();
        statisticsWindowDialog = new StatisticsWindowDialog();

        iterationValue.textProperty().addListener((observable, oldValue, value) -> {
            iterations = Integer.parseInt(value);
        });

        iterations = Integer.parseInt(iterationValue.getText());
    }
}
