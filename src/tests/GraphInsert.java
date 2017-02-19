package tests;

import static org.junit.Assert.*;
import static tests.TestBoard.*;
import static tests.TestBoard.create3x3BoardWithoutC11;
import static tests.TestBoard.getAll;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.GraphBoard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by preston on 2/17/17.
 */
public class GraphInsert {

    static final GraphBoard BOARD = new GraphBoard(8,8);
    /*static final Cell c00 = new Cell(0,0,1);
    static final Cell c01 = new Cell(0,1,1);
    static final Cell c10 = new Cell(1,0,1);

    static final Cell c11 = new Cell(1,1,1);
    static final Cell c02 = new Cell(0,2,1);
    static final Cell c12 = new Cell(1,2,1);

    static final Cell c20 = new Cell(2,0,1);
    static final Cell c21 = new Cell(2,1,1);
    static final Cell c22 = new Cell(2,2,1);*/

    @Test
    public void testAddFirstCell(){
        GraphBoard board = new GraphBoard(8,8);
        Cell c = new Cell(2,2,2);
        board.insertCell(c);
        assertEquals(c, board.getStart());
    }

    @Test
    public void testBoard2CellsPointCorrectly(){
        BOARD.insertCell(c12);
        BOARD.insertCell(c22);
        assertTrue(c12.hasEdge(c22));
    }

    @Test
    public void testCloserEdgeMethod(){
        Cell.Edge e1 = new Cell.Edge(c10,c00);
        Cell.Edge e2 = new Cell.Edge(c11,c00);
        assertEquals(e1, c00.getClosest(e1,e2));
    }

    @Test
    public void test3CellsPointCorrectly(){
        GraphBoard b = new GraphBoard(8,8);
        b.insertCell(c12);
        b.insertCell(c21);
        b.insertCell(c22);
        assertTrue(c12.get(Direction.BELOW) == c22);
        assertTrue(c22.get(Direction.ABOVE) == c12);
        assertTrue(c21.get(Direction.TOP_RIGHT) == c12);
        assertTrue(c12.get(Direction.BTM_LEFT) == c21);
        assertTrue(c21.get(Direction.RIGHT) == c22);
    }

    @Test
    public void testInsertOutOfOrder(){
        GraphBoard b = new GraphBoard(8,8);
        b.insertCell(c21);
        b.insertCell(c22);
        b.insertCell(c12);
        assertEquals(c12, b.getStart());
        assertTrue(c12.get(Direction.BELOW) == c22);
        assertTrue(c22.get(Direction.ABOVE) == c12);
        assertTrue(c21.get(Direction.TOP_RIGHT) == c12);
        assertTrue(c12.get(Direction.BTM_LEFT) == c21);
        assertTrue(c21.get(Direction.RIGHT) == c22);
    }

    @Test
    public void testInsertMore(){
        GraphBoard b = new GraphBoard(8,8);
        b.insertCell(c21);
        b.insertCell(c22);
        b.insertCell(c12);
        b.insertCell(c00);
        b.insertCell(c02);
        b.insertCell(c11);
        b.printGraphicalBoard(b.getStart());
        assertTrue(c12.get(Direction.BELOW) == c22);
        assertTrue(c22.get(Direction.ABOVE) == c12);
        assertTrue(c21.get(Direction.TOP_RIGHT) == c12);
        assertTrue(c12.get(Direction.BTM_LEFT) == c21);
        assertTrue(c21.get(Direction.RIGHT) == c22);
    }

    @Test
    public void testThreeByThreeBoard(){
        GraphBoard gg = new GraphBoard(3,3);
        Cell[] threeByThree = {c21,c01,c12,c01,c10,c11,c20,c00,c22};
        for(Cell c : threeByThree){
            gg.insertCell(c);
        }
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void makeThisFail(){
        GraphBoard gg = new GraphBoard(-500,-86);
        Random r = new Random();
        int x, y, v;
        Cell c;
        for (int i = 0; i < 20; i++) {
            x = r.nextInt(20);
            y = r.nextInt(20);
            v = r.nextInt(5);
            while(v==0 || v == 3){
                 v = r.nextInt(5);
            }
            c = new Cell(x,y,v);
            gg.insertCell(c);
            gg.printGraphicalBoard(gg.getStart());
        }
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void testGetAdjacents(){

        GraphBoard gg = create3x3BoardWithoutC11();

        Cell expected = Cell.createDummyWithEdges(c11);

        HashMap<Direction, Cell> hh;
        Cell c11 = new Cell(1,1,3);
        ArrayList<Cell> cells = new ArrayList<>(8);

        for(Cell c : getAll()) {
            if (!c.equals(c11)) {
                hh = new HashMap<>(8);
                System.out.println("\n/*====================================/");
                System.out.println("Testing adjacents from: " + c);
                System.out.println("/*====================================/");
                hh = gg.getAdjacentsForNewCell(c11, c, hh);
                assertTrue(test11EdgesAgainstReturned(expected, c11, hh));
            }
        }
    }

    private boolean test11EdgesAgainstReturned(Cell expected, Cell inserted, HashMap<Direction, Cell> ham){

        Iterator<Cell> hamIt = ham.values().iterator();

        while(hamIt.hasNext()){
            inserted.addEdge(hamIt.next());
            hamIt.remove();
        }

        ArrayList<Cell> cells = new ArrayList<>(8);
        for(Cell.Edge e : expected.edges){
            cells.add(e.get());
        }
        for(Cell.Edge e : inserted.edges){
            if(!cells.contains(e.get()))
                return false;
        }
        return true;
    }

    @Test
    public void testNewWalkFunction(){
        GraphBoard gg = TestBoard.createSolid3x3Board();
        Cell target = new Cell(3, 3, 100000);
        assertEquals(c22, gg.directWalk(target, gg.start));
        assertFalse(gg.getStart().hasEdge(target));
    }

    @Test
    public void walkTo1000cellsAwayInTheory() throws Exception {
        GraphBoard gg = TestBoard.create3x3BoardWithoutC11();
        //// TODO: 2/19/17 This method fails, because the directWalk()
        // func returns as soon as its possibilities are exhausted with regards to the
        //target cell.  After this case is reached, check all of this cell's edges to see if
        //it has a cell in a diff direction that is closer
        assertEquals(c22, gg.directWalk(new Cell(1000,1000,999999), gg.start));
    }
}
