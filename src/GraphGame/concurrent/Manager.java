package GraphGame.concurrent;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.interfaces.GraphAction;
import GraphGame.interfaces.Walk;
import GraphGame.interfaces.WalkListener;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*** ========================================================================
 * 2048 - by Preston Garno on 2/22/17
 * =========================================================================*/
public class Manager {

    /**  the executor  */
    private ExecutorService executor;

    /***  the Singleton instance  */
    private static Manager instance;

    /*********************************
     * Initializes the thread manager instance
    *********************************/
    private Manager(){
        this.executor = Executors.newFixedThreadPool(8);
    }

    /*********************************
     * @return the manager instance
    *********************************/
    public static Manager getInstance()
    {
        if(instance == null)
            instance = new Manager();
        return instance;
    }

    /*********************************
     * runs an action on a cell
     * 		@param g the action
     * 		@param cell the cell to perform it on
    *********************************/
    public void post(GraphAction g, Cell cell) {
        executor.execute(() -> g.executeOn(cell));
    }

    /*********************************
     * run a slide action to the manager
     * 		@param walk the walking action
     * 		@param cell the cell to start on
     * 		@param d the direction to go
     * 		@param ul the walklistener
    *********************************/
    public void postWalk(Walk walk, Cell cell, Direction d, WalkListener ul) {
        WalkRunner wr = new WalkRunner();
        executor.execute(wr.set(walk, cell, d, ul));
    }

    /*********************************
     * The runner that holds the vars and runnnable, and nulls them on complete
    *********************************/
    private static final class WalkRunner{

        /*********************************
         * Create a new runner
		*********************************/
        WalkRunner(){
            mRunnable = new MyRunnable();
        }

        /*********************************
         * The Runnable
		*********************************/
        private final Runnable mRunnable;

        /** represents whether the thread is running */
        boolean isRunning = false;
        /** the action */
        Walk walk;
        /** the cell to start the slide */
        Cell startCell;
        /** the direction to walk */
        Direction direction;
        /** the listener for completion */
        WalkListener walkListener;

        /*********************************
         * clear references
		*********************************/
        private void clear(){
            this.walk = null;
            this.startCell = null;
            this.direction = null;
            this.walkListener = null;
        }

        /*********************************
         * Set the references and get the Runnable
         * 		@param walk the action
         * 		@param startCell the cell to start the slide
         * 		@param direction the direction
         * 		@param walkListener the listener
         * @return the Runnable
		*********************************/
        Runnable set(Walk walk, Cell startCell, Direction direction, @Nullable WalkListener walkListener){
            this.walk = walk;
            this.startCell = startCell;
            this.direction = direction;
            this.walkListener = walkListener;
            return mRunnable;
        }

        /*********************************
         * The Runnable class
		*********************************/
        private final class MyRunnable implements Runnable{

            /** the usual, releases latch on the completion of the slide/walk*/
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
