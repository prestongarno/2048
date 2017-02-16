package tests;

import game.*;
import org.junit.Test;

import static org.junit.Assert.*;


public class GraphBoardTest {

    static final GraphBoard gb = new GraphBoard(6,6);

    @Test
    public void testOriginCompareUnequal()
    {
        Cell c1 = new Cell(4,4,4);
        Cell c2 = new Cell(2,2,4);
        assertFalse(c1.isCloserToOrigin(c2));
    }

    @Test
    public void testOriginCompareSameRow()
    {
        Cell c1 = new Cell(4,4,4);
        Cell c2 = new Cell(4,2,4);
        assertFalse(c1.isCloserToOrigin(c2));
    }

    @Test
    public void testOriginCompareUnequalSameColumn()
    {
        Cell c1 = new Cell(4,2,4);
        Cell c2 = new Cell(2,2,4);
        assertFalse(c1.isCloserToOrigin(c2));
    }

    @Test
    public void testOriginCompareSameSumRowColumn()
    {
        Cell c1 = new Cell(2,4,4);
        Cell c2 = new Cell(4,2,4);
        assertTrue(c1.isCloserToOrigin(c2));
    }

    public void testHasAdjacentsNull(){
        assertFalse((new Cell().hasAdjacents()));
    }

/*    public void testHasAdjacentsNonNull(){
        Cell c = new Cell();
        c.setAbove(new Cell());
        assertTrue(c.hasAdjacents());
    }*/

/*    @Test
    public void testGetNonNullEdges(){
        Cell c = new Cell();
        c.setAbove(new Cell());
        c.setBelow(new Cell());
        assertEquals(2, c.edge.getNonNullEdges().length);
    }*/

    @Test
    public void testDistanceTo(){
        Cell c = new Cell(1,0,0);
        Cell c1 = new Cell(1,1,0);
        assertTrue(c.distanceTo(0,0) < c1.distanceTo(0,0));
    }

    @Test
    public void testDistanceToEqual(){
        Cell c = new Cell(1,0,0);
        Cell c1 = new Cell(1,1,0);
        assertTrue(c.distanceTo(0,0) == c1.distanceTo(0,0));
    }

    @Test
    public void testClearAllEdges(){

    }
}
