package tests;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.NumberGame;
import GraphGame.NumberSlider;
import GraphGame.concurrent.Manager;
import static GraphGame.interfaces.Functional.*;

import GraphGame.interfaces.GraphAction;
import GraphGame.interfaces.*;
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
        gb.slide(Direction.RIGHT, SLIDE);
        System.out.println("b");
        gb.printCellsWithMatrices();
        gb.printGraphicalBoard();
    }

    @Test
    public void slideRow()
    {
        NumberGame g = TestBoard.createSquareBoard(10,10);
        Cell c = g.getStart();
        g.printGraphicalBoard();
        SLIDE.slide(g.start, Direction.RIGHT, 0, g);
        g.notifyStart();
        g.printGraphicalBoard();
        //g.printCellsWithMatrices();
    }

}
