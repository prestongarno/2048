package tests;

import static org.junit.Assert.*;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.GraphBoard;
import org.junit.Test;

import java.util.Random;

/**
 * Created by preston on 2/17/17.
 */
public class GraphInsert {

    static final GraphBoard BOARD = new GraphBoard(8,8);
    static final Cell c00 = new Cell(0,0,1);
    static final Cell c01 = new Cell(0,1,1);
    static final Cell c10 = new Cell(1,0,1);

    static final Cell c11 = new Cell(1,1,1);
    static final Cell c02 = new Cell(0,2,1);
    static final Cell c12 = new Cell(1,2,1);

    static final Cell c20 = new Cell(2,0,1);
    static final Cell c21 = new Cell(2,1,1);
    static final Cell c22 = new Cell(2,2,1);

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

}
