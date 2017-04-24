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

    /**
     * Used to tell {@link StatisticsGIF} to begin creating an animated gif based on data from
     * {@link StatisticsLogic}.
     * @throws Exception    Any exception thrown by {@link lieng.GIFWriter}
     */
    public void createRandomGIF() throws Exception {
        getGIFStatistics();
        statisticsGIF.writeGif(
                statisticsLogic.getGifBoard(), statisticsLogic.getSimilaritiesOver98(), iterations);
    }

    /**
     * Used to enable the cancel button functionality. Found on
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
