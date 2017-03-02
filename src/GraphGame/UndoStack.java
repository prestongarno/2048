package GraphGame;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/*** ========================================================================
 * $(2048) - by Preston Garno on 3/2/17
 * =========================================================================*/


public class UndoStack {


    /** the undo stack instance */
    private static UndoStack instance;

    /** the list of moves */
    private final List<Movement> MOVE_LIST;

    /** current move count */
    private int currentMoveCount;

    /** list that keeps track of amount of moves per slide */
    private final List<Integer> MOVE_INDEX;


    /***********************************************
     * private constructor
     ***********************************************/

    private UndoStack() {
        MOVE_LIST = Collections.synchronizedList(new ArrayList<Movement>(10));
        MOVE_INDEX = Collections.synchronizedList(new ArrayList<Integer>(10));
        currentMoveCount = 0;
    }

    /***********************************************
     * @return the instance of the undostack
     ***********************************************/

    private static UndoStack getInstance() {
        if(instance == null)
            instance = new UndoStack();
        return instance;
    }

    /***********************************************
     * pushes a move onto the stack
     * @param movement the movement to push
     ***********************************************/

    public static void pushMove(@NotNull Movement movement){
        synchronized (getInstance().MOVE_LIST){
            getInstance().MOVE_LIST.add(0, movement);
        }
        getInstance().currentMoveCount += 1;
    }

    /***********************************************
     * Called when a slide() is complete
     ***********************************************/

    public static void onSlideDone(){
        getInstance().MOVE_INDEX.add(0, getInstance().currentMoveCount);
        getInstance().resetCount();
    }

    private void resetCount() {
        this.currentMoveCount = 0;
    }

    /***********************************************
     * Restores the last known game state
     * @param game the game board
     ***********************************************/

    public static void popStack(NumberGame game)
    {
       int lastMoveCount = getInstance().MOVE_INDEX.get(0);

       Iterator<Movement> movementIterator = getInstance().MOVE_LIST.iterator();
       int i = 0;
       while(movementIterator.hasNext() && i < lastMoveCount){
           game.restore(movementIterator.next());
           movementIterator.remove();
           i++;
       }
      getInstance().MOVE_INDEX.remove(0);
    }

    public static class Movement {

        enum MoveType{MOVE, DELETE;}

        final MoveType type;

        final int fromX;
        int toX;

        final int fromY;
        int toY;

        final int value;

        /***********************************************
         * Public constructor for a cell movement
         * @param fromX @param toX @param fromY @param toY @param value
         ***********************************************/

        public Movement(int fromX, int toX, int fromY, int toY, int value) {
            this.fromX = fromX;
            this.toX = toX;
            this.fromY = fromY;
            this.toY = toY;
            this.value = value;

            this.type = MoveType.MOVE;
        }

        /***********************************************
         * Public constructor for a deletion
         * @param fromX @param toX @param value
         ***********************************************/

        public Movement(int fromX, int fromY, int value) {
            this.fromX = fromX;
            this.fromY = fromY;
            this.value = value;

            this.type = MoveType.DELETE;
        }
    }

}
