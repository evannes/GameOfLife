package statistics;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.DynamicBoard;
import statistics.StatisticsGIF;

import java.util.List;

/**
 * Creates a <code>Service</code> to be used in conjunction with
 * {@link StatisticsGIF} which is using the <code>Task</code> class.
 * This allows the gif creation to be run in a separate thread
 * so that the interface remains responsive during creation.
 *
 * @author  Alexander Kingdon
 * @since   1.0
 */
public class StatisticsService extends Service<Void> {

    private int iterations;
    private DynamicBoard gifBoard;
    private List<Integer> generationsOver98;

    /**
     * Sets parameters to be passed to {@link StatisticsGIF}.
     * @param gifBoard          Board to be used to generate statistics
     * @param generationsOver98 List of generations with a similarity
     *                          measure equal to or greater than 98
     * @param iterations        Number of generations for statistical data
     */
    public StatisticsService(DynamicBoard gifBoard, List<Integer> generationsOver98, int iterations) {
        this.iterations = iterations;
        this.gifBoard = gifBoard;
        this.generationsOver98 = generationsOver98;
    }

    /**
     * Creates the task to make the gif.
     * @return  A {@link StatisticsGIF} instance
     */
    @Override
    public Task<Void> createTask() {
        return new StatisticsGIF(gifBoard, generationsOver98, iterations);
    }
}
