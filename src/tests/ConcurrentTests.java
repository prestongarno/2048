package tests;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.NumberGame;
import GraphGame.concurrent.Manager;
import static GraphGame.interfaces.impl.*;

import org.junit.Test;

/**
 * ========================================================================
 * $(2048) - by Preston Garno on 2/23/17
 * =========================================================================
 */
public class ConcurrentTests {

    @Test
    public void testPrintConcurrent() throws Exception {
        //Manager m = Manager.getInstance();
        NumberGame gb = TestBoard.createSquareBoard(20, 20);
        //gb.printGraphicalBoard();
        gb.walk(Direction.RIGHT, SLIDE);
        //NumberGame.latch.await();
        //gb.printCellsWithMatrices();
        //gb.printGraphicalBoard();
    }

    /**
     * Orig test to get the walk() method working
     */
    @Test
    public void slideRow()
    {
        /*NumberGame g = TestBoard.createSquareBoard(10,10);
        Cell c = g.getStart();
        //g.printGraphicalBoard();
        Cell c00 = g.getCell(0,0);
        Cell c10 = g.getCell(1,0);
        Cell c20 = g.getCell(2,0);
        //System.out.println(c00);
        SLIDE.walk(g.start, Direction.RIGHT, 0, g);
        SLIDE.walk(c10, Direction.RIGHT, 0, g);
        //System.out.println(c00);
        UPDATE.walk(c00, Direction.RIGHT, 0, g);
        UPDATE.walk(c10, Direction.RIGHT, 0, g);
        UPDATE.walk(c20, Direction.RIGHT, 0, g);
        g.onComplete();
        g.printGraphicalBoard();
        g.printCellsWithMatrices();*/
    }

    @Test
    public void testInsertOnlyThenUpdate() throws Exception {
        NumberGame ng = new NumberGame(30,30);
        for (int i = 10; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                ng.addCell(new Cell(i,j,4));
            }
        }
        ng.printCellsWithMatrices();
        ng.printGraphicalBoard();
        /*Cell c1 = new Cell (4,4,4);
        Cell c2 = new Cell (4,5,4);
        Cell c3 = new Cell (5,4,4);
        Cell c4 = new Cell (4,6,4);
        Cell c5= new Cell (5,5,4);
        Cell c6 = new Cell (3,4,4);
        ng.addCell(c1);
        ng.addCell(c2);
        ng.addCell(c3);
        ng.addCell(c4);
        ng.addCell(c5);
        ng.addCell(c6);
        ng.printGraphicalBoard();
        ng.printCellsWithMatrices();*/
    }

    @Test
    public void testSlideEntireBoard() throws Exception {
        NumberGame ng = TestBoard.createSquareBoard(10,10);
        //ng.printGraphicalBoard();
        ng.walk(Direction.LEFT, SLIDE);
        /*try {
            Thread.sleep(5000);
            } catch (InterruptedException i){

        }*/
        ng.printCellsWithMatrices();
        ng.printGraphicalBoard();
    }

    @Test
    public void testIterate400ArraySome() throws Exception {
        int[][] fakeBoard = new int[1000][1000];

        int itCount = 0;

        for (int x = 0; x <fakeBoard.length; x++) {
            //System.out.println("\n"
            //);
            for (int y = 0; y < fakeBoard[1].length; y++) {
                fakeBoard[x][y] = 4;
                itCount ++;
                //System.out.print(4);
            }
        }

        for (int x = 0; x <fakeBoard.length; x++) {
            for (int y = 0; y < fakeBoard[1].length; y++) {
                fakeBoard[x][y] -= 2;
                itCount++;
                //System.out.print(2);
            }
        }

        for (int x = 0; x <fakeBoard.length; x++) {
            for (int y = 0; y < fakeBoard[1].length; y++) {
                fakeBoard[x][y] -= 2;
                itCount++;
            }
        }
        System.out.println("Total count = " + itCount);
    }
}
