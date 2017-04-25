package model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

/**
 * Created by Alex on 25.04.2017.
 */
public class StatisticsService extends Service<Void> {

    private int iterations;

    private DynamicBoard gifBoard;
    private List<Integer> generationsOver98;

    public StatisticsService(DynamicBoard gifBoard, List<Integer> generationsOver98, int iterations) {
        this.iterations = iterations;
        this.gifBoard = gifBoard;
        this.generationsOver98 = generationsOver98;
    }

    @Override
    public Task<Void> createTask() {
        return new StatisticsGIF(gifBoard, generationsOver98, iterations);
    }
}
