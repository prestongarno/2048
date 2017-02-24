package GraphGame.concurrent;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.NumberGame;
import GraphGame.interfaces.GraphAction;
import GraphGame.interfaces.Walk;
import GraphGame.interfaces.WalkListener;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ========================================================================
 * 2048 - by Preston Garno on 2/22/17
 * =========================================================================
 */
public class Manager {

    private ExecutorService executor;
    private WalkRunner[] runners;

    private static Manager instance;

    private Manager(){
        this.executor = Executors.newFixedThreadPool(8);
        runners = new WalkRunner[15];
        for (int i = 0; i < runners.length; i++) {
            runners[i] = new WalkRunner();
        }
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

    public void postWalk(Walk walk, Cell cell, Direction d, WalkListener ul) {
        WalkRunner wr = new WalkRunner();
        /*for (int i = 0; i < runners.length; i++) {
            if(!runners[i].isRunning){
                wr = runners[i];
                break;
            }
        }
        if(wr == null)
            wr = new WalkRunner();*/

        executor.execute(wr.set(walk, cell, d, ul));
    }

    private static final class WalkRunner{

        WalkRunner(){
            mRunnable = new MyRunnable();
        }

        private final Runnable mRunnable;

        boolean isRunning = false;
        Walk walk;
        Cell startCell;
        Direction direction;
        WalkListener walkListener;

        private void clear(){
            this.walk = null;
            this.startCell = null;
            this.direction = null;
            this.walkListener = null;
        }

        Runnable set(Walk walk, Cell startCell, Direction direction, @Nullable WalkListener walkListener){
            this.walk = walk;
            this.startCell = startCell;
            this.direction = direction;
            this.walkListener = walkListener;
            return mRunnable;
        }

        private final class MyRunnable implements Runnable{
            @Override
            public void run() {
                isRunning = true;
                try{
                    walk.walk(startCell, direction);
                } catch (NullPointerException nullPointer) {
                    System.out.print("Null pointer --> " + walk + " start Cell = " + startCell + " direction: " + direction);
                }
                if(walkListener != null) {
                    walkListener.getLatch().countDown();

                }
                clear();
                isRunning = false;
            }
        }
    }
}
