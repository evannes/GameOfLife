package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DynamicBoard;
import model.StatisticsGIF;
import model.StatisticsLogic;
import view.StatisticsManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class for the rule selection window.
 * It is accessed from the main game window using the button
 * named "View statistics".
 *
 * @author  Alexander Kingdon
 * @version %I%, %G%
 * @since   1.0
 */
public class StatisticsController implements Initializable {

    @FXML
    private StatisticsLogic statisticsLogic;
    private StatisticsManager statisticsView;
    private StatisticsGIF statisticsGIF;
    private int iterations;

    @FXML
    private TextField iterationValue;

    @FXML
    private LineChart<Number, Number> statisticsChart;

    @FXML
    private Label comparingGenerationLabel;

    /**
     * Initialzer method for the cloned board to be used for gathering statistical data.
     * @param clonedBoard   The cloned board supplied by {@link Controller}.
     */
    void setClonedBoard(DynamicBoard clonedBoard) {
        statisticsLogic.setClonedBoard(clonedBoard);
    }

    /**
     * This method binds together the logic and view methods producing the statistics.
     */
    public void getStatistics() {
        if (iterations > 0 && iterations <= 100) {
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
     * It also tells {@link StatisticsManager} to create labels showing the information gathered.
     */
    public void getSpecifiedStatistics() {
        statisticsView = new StatisticsManager();
        int similaritySpecifiedNumber = statisticsView.createDialogWindow();
        if (similaritySpecifiedNumber > iterations) {
            similaritySpecifiedNumber = iterations;
        }
        statisticsLogic.setSimilaritySpecifiedNumber(similaritySpecifiedNumber);
        getStatistics();
        comparingGenerationLabel.setText(statisticsView.setComparingGenerationLabelText());
        statisticsLogic.setSimilaritySpecifiedNumber(0);
    }

    private void getGIFStatistics() {
        statisticsLogic.setCreateGIF();
        getStatistics();
        statisticsLogic.unsetCreateGIF();
    }

    public void createRandomGIF() throws Exception {
        getGIFStatistics();
        statisticsGIF.writeGif(
                statisticsLogic.getGifBoard(), statisticsLogic.getSimilaritiesOver98(), iterations);
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
        statisticsView = new StatisticsManager();
        statisticsGIF = new StatisticsGIF();

        iterationValue.textProperty().addListener((observable, oldValue, value) -> {
            if (Integer.parseInt(value) > 0 && Integer.parseInt(value) <= 100) {
                iterations = Integer.parseInt(value);
            } else if (Integer.parseInt(value) > 100){
                iterations = 100;
            } else {
                iterations = 30;
            }
        });

        iterations = Integer.parseInt(iterationValue.getText());
    }
}
