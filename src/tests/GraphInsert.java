package tests;

import static org.junit.Assert.*;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.GraphBoard;
import org.junit.Test;

/**
 * Created by preston on 2/17/17.
 */
public class GraphInsert {

    static final GraphBoard BOARD = new GraphBoard(8,8);
    static final Cell c00 = new Cell(0,0,2);
    static final Cell c01 = new Cell(0,1,3);
    static final Cell c10 = new Cell(1,0,26);

    static final Cell c11 = new Cell(1,1,20);
    static final Cell c02 = new Cell(0,2,1);
    static final Cell c12 = new Cell(1,2,9);

    static final Cell c20 = new Cell(2,0,2);
    static final Cell c21 = new Cell(2,1,5);
    static final Cell c22 = new Cell(2,2,2);

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
        assertEquals(e1, c00.getCloserEdge(e1,e2));
    }

    @Test
    public void test3CellsPointCorrectly(){
        GraphBoard BOARD = new GraphBoard(8,8);
        BOARD.insertCell(c12);
        BOARD.insertCell(c21);
        BOARD.insertCell(c22);

        assertTrue(c12.get(Direction.BELOW) == c22);
        assertTrue(c22.get(Direction.ABOVE) == c12);
        assertTrue(c21.get(Direction.TOP_RIGHT) == c12);
        assertTrue(c12.get(Direction.BTM_LEFT) == c21);
        assertTrue(c21.get(Direction.RIGHT) == c22);
    }

}
