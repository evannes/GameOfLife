package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alexander Kingdon on 05.04.2017.
 */
public class StatisticsController implements Initializable {

    @FXML
    private StatisticsLogic statisticsLogic;
    private StatisticsView statisticsView;
    private StatisticsGIF statisticsGIF;
    private int iterations;
    private int similaritySpecifiedNumber;

    @FXML
    private TextField iterationValue;

    @FXML
    private LineChart<Number, Number> statisticsChart;

    @FXML
    private Label comparingGenerationLabel;

    protected void setClonedBoard(DynamicBoard clonedBoard) {
        statisticsLogic.setClonedBoard(clonedBoard);
    }

    /**
     * This method binds together the logic and view methods producing the statistics.
     */
    public void getStatistics() {
        if (iterations != 0) {
            statisticsLogic.setIterations(iterations);
        }
        int[][] stats = statisticsLogic.getStatistics();
        if (!statisticsLogic.getCreateGIF()) {
            XYChart.Series<Number, Number> livingCellsSeries = statisticsView.populateLivingCells(stats);
            XYChart.Series<Number, Number> changeInLivingCellsSeries = statisticsView.populateChangeInLivingCells(stats);
            XYChart.Series<Number, Number> similarityMeasureSeries = statisticsView.populateSimilarityMeasure(
                    stats, statisticsLogic.getHighestSimilarityNumber(), statisticsLogic.getSameOccurrencesHelper());

            statisticsChart.getData().addAll(livingCellsSeries, changeInLivingCellsSeries, similarityMeasureSeries);
        }
    }

    /**
     * This method opens up a dialog window letting the user specify an iteration to compare similarity with.
     * It also tells {@link StatisticsView} to create labels showing the information gathered.
     */
    public void getSpecifiedStatistics() {
        statisticsView = new StatisticsView();
        similaritySpecifiedNumber = statisticsView.createDialogWindow();
        if (similaritySpecifiedNumber > iterations) {
            similaritySpecifiedNumber = iterations;
        }
        statisticsLogic.setSimilaritySpecifiedNumber(similaritySpecifiedNumber);
        getStatistics();
        comparingGenerationLabel.setText(statisticsView.setComparingGenerationLabelText());
    }

    private void getGIFStatistics() {
        similaritySpecifiedNumber = statisticsView.createDialogWindow();
        if (similaritySpecifiedNumber > iterations) {
            similaritySpecifiedNumber = iterations;
        }
        statisticsLogic.setSimilaritySpecifiedNumber(similaritySpecifiedNumber);
        statisticsLogic.setCreateGIF();
        getStatistics();
    }

    public void createGIF() throws Exception {
        getGIFStatistics();
        statisticsGIF.writeGif(statisticsLogic.getGifBoard(), iterations, statisticsLogic.getSimilarOccurrencesHelper());
    }

    /**
     * Listener method for the cancel button.
     * @param event The event being triggered when the button is clicked.
     */
    public void cancelButton(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        statisticsLogic = new StatisticsLogic();
        statisticsView = new StatisticsView();
        statisticsGIF = new StatisticsGIF();

        iterationValue.textProperty().addListener((observable, oldValue, value) -> {
            if (Integer.parseInt(value) != 0) {
                iterations = Integer.parseInt(value);
            }
        });

        iterations = Integer.parseInt(iterationValue.getText());
    }
}
