package sample;


import javafx.scene.chart.XYChart;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Created by Alex on 06.04.2017.
 */
public class StatisticsView {

    private int specifiedIteration;

    public int createDialogWindow() {
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

    public XYChart.Series<Number, Number> populateLivingCells(int[][] stats) {
        XYChart.Series<Number, Number> livingCellsSeries = new XYChart.Series<>();
        livingCellsSeries.setName("Living cells");
        for (int i = 0; i < stats[0].length; i ++) {
            livingCellsSeries.getData().add(new XYChart.Data<>(i, stats[0][i]));
        }

        livingCellsSeries.getData().remove((stats[0].length) - 1);

        return livingCellsSeries;
    }

    public XYChart.Series<Number, Number> populateChangeInLivingCells(int[][] stats) {
        XYChart.Series<Number, Number> changeInLivingCellsSeries = new XYChart.Series<>();
        changeInLivingCellsSeries.setName("Change in living cells");
        for (int i = 0; i < stats[1].length; i ++) {
            changeInLivingCellsSeries.getData().add(new XYChart.Data<>(i, stats[1][i]));
        }

        changeInLivingCellsSeries.getData().remove(stats[1].length - 1);

        return changeInLivingCellsSeries;
    }

    public XYChart.Series<Number, Number> populateSimilarityMeasure(int[][] stats) {
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
