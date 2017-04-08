package sample;


import javafx.scene.chart.XYChart;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Created by Alexander Kingdon on 06.04.2017.
 */
public class StatisticsView {

    private int specifiedIteration;

    /**
     * This method creates a dialog window where the user can input a specified iteration.
     * The value is used to create the similarity measure in {@link StatisticsLogic#getStatistics()}.
     * @return  The specified iteration to compare similarity with.
     */
    protected int createDialogWindow() {
        TextInputDialog iterationDialog = new TextInputDialog(null);
        iterationDialog.setTitle("Specify iteration");
        iterationDialog.setHeaderText(null);
        iterationDialog.setContentText("Specify the iteration to compare with:");
        Optional<String> result = iterationDialog.showAndWait();
        result.ifPresent(number -> specifiedIteration = Integer.parseInt(number));
        return specifiedIteration;
    }

    /*public void createIterationTooLargeWindow() {
        Alert iterationTooLarge = new Alert(Alert.AlertType.ERROR);
        iterationTooLarge.setTitle("Error");
        iterationTooLarge.setHeaderText("Iteration chosen was too large.");
        iterationTooLarge.setContentText("You must specify an iteration equal to or smaller " +
                "than the number of iterations. Please try again");
        iterationTooLarge.showAndWait();
    }*/

    /**
     * This method populates the series used in the line chart to show living cells.
     * @param stats The statistics array produced by {@link StatisticsLogic#getStatistics()}.
     * @return  Fully populated series ready to be applied to the line chart.
     */
    protected XYChart.Series<Number, Number> populateLivingCells(int[][] stats) {
        XYChart.Series<Number, Number> livingCellsSeries = new XYChart.Series<>();
        livingCellsSeries.setName("Living cells");
        for (int i = 0; i < stats[0].length; i ++) {
            livingCellsSeries.getData().add(new XYChart.Data<>(i, stats[0][i]));
        }

        livingCellsSeries.getData().remove((stats[0].length) - 1);

        return livingCellsSeries;
    }

    /**
     * This method populates the series used in the line chart to show change in living cells.
     * @param stats The statistics array produced by {@link StatisticsLogic#getStatistics()}.
     * @return  Fully populated series ready to be applied to the line chart.
     */
    protected XYChart.Series<Number, Number> populateChangeInLivingCells(int[][] stats) {
        XYChart.Series<Number, Number> changeInLivingCellsSeries = new XYChart.Series<>();
        changeInLivingCellsSeries.setName("Change in living cells");
        for (int i = 0; i < stats[1].length; i ++) {
            changeInLivingCellsSeries.getData().add(new XYChart.Data<>(i, stats[1][i]));
        }

        changeInLivingCellsSeries.getData().remove(stats[1].length - 1);

        return changeInLivingCellsSeries;
    }

    /**
     * This method populates the series used in the line chart to show the similarity measure.
     * @param stats The statistics array produced by {@link StatisticsLogic#getStatistics()}.
     * @return  Fully populated series ready to be applied to the line chart.
     */
    protected XYChart.Series<Number, Number> populateSimilarityMeasure(int[][] stats) {
        XYChart.Series<Number, Number> similarityMeasureSeries = new XYChart.Series<>();
        similarityMeasureSeries.setName("Similarity measure");
        for (int i = 0; i < stats[2].length; i ++) {
            similarityMeasureSeries.getData().add(new XYChart.Data<>(i, stats[2][i]));
        }
        if (specifiedIteration != 0) {
            similarityMeasureSeries.getData().remove(specifiedIteration);
            similarityMeasureSeries.getData().remove(stats[2].length - 2);
            return similarityMeasureSeries;
        }

        similarityMeasureSeries.getData().remove(stats[2].length - 1);

        return similarityMeasureSeries;
    }
}
