package tests;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.NumberGame;
import org.junit.Test;

import static org.junit.Assert.*;
import static tests.TestBoard.*;

/**
 * This class will prove that all helper methods used in this
 * application is completely sound
 */
@SuppressWarnings("Duplicates")
public class GraphHelperMethods {

    static final NumberGame gb = new NumberGame(6,6);

    @Test
    public void testOriginCompareUnequal()
    {
        Cell c1 = new Cell(4,4,4);
        Cell c2 = new Cell(2,2,4);
        assertFalse(c1.closerToOrigin(c2));
    }

    @Test
    public void testOriginCompareSameRow()
    {
        Cell c1 = new Cell(4,4,4);
        Cell c2 = new Cell(4,2,4);
        assertFalse(c1.closerToOrigin(c2));
    }

    @Test
    public void testOriginCompareUnequalSameColumn()
    {
        Cell c1 = new Cell(4,2,4);
        Cell c2 = new Cell(2,2,4);
        assertFalse(c1.closerToOrigin(c2));
    }

    @Test
    public void testOriginCompareSameSumRowColumn()
    {
        Cell c1 = new Cell(2,4,4);
        Cell c2 = new Cell(4,2,4);
        assertTrue(c1.closerToOrigin(c2));
    }

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
        assertTrue(c.distanceTo(0,0) != c1.distanceTo(0,0));
    }

    @Test
    public void testClearAllEdges(){

    }

    @Test
    public void testRelationshipEnumWorks(){
        Cell c = new Cell(0,0,0);
        Cell c1 = new Cell(0,1,0);
        assertEquals(Direction.RIGHT, Direction.isTo(c, c1));
    }

    @Test
    public void testRelationshipEnumWorks2(){
        Cell c = new Cell(0,0,0);
        Cell c1 = new Cell(0,1,0);
        assertEquals(Direction.RIGHT, c1.isTo(c));
    }

    @Test
    public void testRelationshipEnumWorks3(){
        Cell c = new Cell(0,0,0);
        Cell c1 = new Cell(1,1,0);
        assertEquals(Direction.BTM_RIGHT, Direction.isTo(c, c1));
        assertEquals(Direction.TOP_LEFT, Direction.isTo(c1, c));
    }

    @Test
    public void abstractBoardCreationFactoryImplementor(){
        NumberGame gg = TestBoard.create3x3BoardWithoutC11();
        gg.printGraphicalBoard();
    }

    /*@Test
    public void abstractBoardCreationFactoryImplementor2(){
        NumberGame gg = TestBoard.createSolid3x3Board();
        gg.printGraphicalBoard(gg.getStart());
        Cell target = new Cell(3, 3, 2);
        (gg.walk(target, gg.getStart())).addEdge(target);
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void abstractBoardCreationFactoryImplementor3(){
        NumberGame gg = TestBoard.createSolid3x3Board();
        gg.printGraphicalBoard(gg.getStart());
        Cell target = new Cell(100, 100, 2);
        (gg.walk(target, gg.getStart())).addEdge(target);
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void abstractBoardCreationFactoryImplementor4(){
        NumberGame gg = TestBoard.createSolid3x3Board();
        gg.printGraphicalBoard(gg.getStart());
        Cell target = new Cell(2, 5, 2);
        (gg.walk(target, gg.getStart())).addEdge(target);
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void abstractBoardCreationFactoryImplementor5(){
        NumberGame gg = TestBoard.createSolid3x3Board();
        gg.printGraphicalBoard(gg.getStart());
        Cell target = new Cell(2, 3, 2);
        (gg.walk(target, gg.getStart())).addEdge(target);
        gg.printGraphicalBoard(gg.getStart());
    }
    @Test
    public void abstractBoardCreationFactoryImplementor6(){
        NumberGame gg = TestBoard.createSolid3x3Board();
        gg.printGraphicalBoard(gg.getStart());
        int row = 3;
        int column = 0;
        int value = 2;
        for (int i = 0; i < 200; i++) {
            Cell target = new Cell(row, column, value);
            (gg.walk(target, gg.getStart())).addEdge(target);
            Iterator<Edge> it = target.EDGES.iterator();
            while(it.hasNext()){
                Edge e = it.next();
                if(e != null && !e.get().hasEdge(target)){
                    e.get().addEdge(target);
                }
            }
            gg.printGraphicalBoard(gg.getStart());
            row += 2;
            column += 2;
            value += 2;
        }
    }

    @Test
    public void testGetCloserToOnSelf() throws Exception {
        Cell c = new Cell(100,100,-256);
        System.out.println(Cell.getCloserTo(c,c,c));
        System.out.println(c.getEdgeTo(c));
        System.out.println(c.closerToOrigin(c));
        System.out.println(c.distanceTo(c));
        System.out.println(c.isTo(c));
    }*/

    @Test
    public void testGetCloserTo() throws Exception {
        assertTrue(Cell.getCloserTo(c12, c21,c00) == c12);
        assertEquals(c20, Cell.getCloserTo(c20, c22,c01));
        assertEquals(c22, Cell.getCloserTo(c22, c20,c01));
    }

    @Test
    public void testAddEdgeMethod() throws Exception {
        Cell c = new Cell(0,0,2);
        Cell next = new Cell(0,1,2);

        String[] s = new String[0];
        for (String ddd : s){
            System.out.println(ddd);
        }
    }
}
