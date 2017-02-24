package GraphGame.concurrent;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.interfaces.GraphAction;
import GraphGame.interfaces.UpdateListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ========================================================================
 * 2048 - by Preston Garno on 2/22/17
 * =========================================================================
 */
public class Manager {

    private ExecutorService executor;

    private static Manager instance;

    private Manager(){
        this.executor = Executors.newFixedThreadPool(8);
    }

    public static Manager getInstance()
    {
        if(instance == null)
            instance = new Manager();
        return instance;
    }

    public void post(GraphAction g, Cell cell) {
        executor.execute(() -> g.executeOn(cell));
    }

    public void postSlide(Runnable r, Cell cell, Direction d, int startValue, UpdateListener ul) {
        executor.execute(r);
    }
}
