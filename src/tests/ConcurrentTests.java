package tests;

import GraphGame.Direction;
import GraphGame.NumberGame;
import GraphGame.concurrent.Manager;
import org.junit.Test;

/**
 * ========================================================================
 * $(PROJECT_NAME) - by Preston Garno on 2/23/17
 * =========================================================================
 */
public class ConcurrentTests {

    @Test
    public void testPrintConcurrent(){
        Manager m = Manager.getInstance();
        NumberGame gb = TestBoard.createSquareBoard(20, 20);
        gb.printGraphicalBoard();
        gb.slide(Direction.RIGHT);
        System.out.println("b");
        gb.printCellsWithMatrices();
        gb.printGraphicalBoard();
    }

}
