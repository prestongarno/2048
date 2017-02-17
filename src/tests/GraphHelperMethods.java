package tests;

import GraphGame.Cell;
import GraphGame.Cell.*;
import GraphGame.Direction;
import GraphGame.GraphBoard;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * This class will prove that all helper methods used in this
 * application is completely sound
 */
public class GraphHelperMethods {

    static final GraphBoard gb = new GraphBoard(6,6);

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
        assertTrue(c.distanceTo(0,0) != c1.distanceTo(0,0));
    }

    @Test
    public void testClearAllEdges(){

    }

    @Test
    public void testRelationshipEnumWorks(){
        Cell c = new Cell(0,0,0);
        Cell c1 = new Cell(0,1,0);
        assertEquals(Direction.RIGHT, Edge.calculateRelationShip(c, c1));
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
        assertEquals(Direction.BTM_RIGHT, Edge.calculateRelationShip(c, c1));
        assertEquals(Direction.TOP_LEFT, Edge.calculateRelationShip(c1, c));
    }

    @Test
    public void abstractBoardCreationFactoryImplementor(){
        GraphBoard gg = createBoard();
        gg.printBoard(gg.start);
    }

    @Test
    public void abstractBoardCreationFactoryImplementor2(){
        GraphBoard gg = createBoard();
        gg.printBoard(gg.start);
        Cell target = new Cell(3, 3, 2);
        (gg.walkTo(target)).addEdge(target);
        gg.printBoard(gg.start);
    }

    @Test
    public void abstractBoardCreationFactoryImplementor3(){
        GraphBoard gg = createBoard();
        gg.printBoard(gg.start);
        Cell target = new Cell(100, 100, 2);
        (gg.walkTo(target)).addEdge(target);
        gg.printBoard(gg.start);
    }

    @Test
    public void abstractBoardCreationFactoryImplementor4(){
        GraphBoard gg = createBoard();
        gg.printBoard(gg.start);
        Cell target = new Cell(2, 5, 2);
        (gg.walk(target, gg.start)).addEdge(target);
        gg.printBoard(gg.start);
    }

    @Test
    public void abstractBoardCreationFactoryImplementor5(){
        GraphBoard gg = createBoard();
        gg.printBoard(gg.start);
        Cell target = new Cell(2, 3, 2);
        (gg.walk(target, gg.start)).addEdge(target);
        gg.printBoard(gg.start);
    }
    @Test
    public void abstractBoardCreationFactoryImplementor6(){
        GraphBoard gg = createBoard();
        gg.printBoard(gg.start);
        int row = 3;
        int column = 0;
        int value = 2;
        for (int i = 0; i < 200; i++) {
            Cell target = new Cell(row, column, value);
            (gg.walk(target, gg.start)).addEdge(target);
            Iterator<Edge> it = target.methodThatShouldOnlyBeCalledWhenUsingUnitTestsGetEdges().iterator();
            while(it.hasNext()){
                Edge e = it.next();
                if(e != null && !e.get().hasEdge(target)){
                    e.get().addEdge(target);
                }
            }
            gg.printBoard(gg.start);
            row += 2;
            column += 2;
            value += 2;
        }
    }

    private GraphBoard createBoard(){
        GraphBoard gg = new GraphBoard(10,10);
        Cell c00 = new Cell(0,0,2);
        Cell c01 = new Cell(0,1,3);
        Cell c10 = new Cell(1,0,26);

        Cell c11 = new Cell(1,1,20);
        Cell c02 = new Cell(0,2,1);
        Cell c12 = new Cell(1,2,9);

        Cell c20 = new Cell(2,0,2);
        Cell c21 = new Cell(2,1,5);
        Cell c22 = new Cell(2,2,2);

        c00.edges.add(new Edge(c01,c00));
        c00.edges.add(new Edge(c10,c00));
        c00.edges.add(new Edge(c11,c00));
        
        c01.edges.add(new Edge(c00,c01));
        c01.edges.add(new Edge(c10,c01));
        c01.edges.add(new Edge(c11,c01));
        c01.edges.add(new Edge(c02,c01));
        c01.edges.add(new Edge(c12,c01));

        c10.edges.add(new Edge(c01,c10));
        c10.edges.add(new Edge(c12,c10));
        c10.edges.add(new Edge(c11,c10));

        c10.edges.add(new Edge(c00,c10));
        c10.edges.add(new Edge(c01,c10));
        c10.edges.add(new Edge(c11,c10));
        c10.edges.add(new Edge(c21,c10));
        c10.edges.add(new Edge(c20,c10));

        c11.edges.add(new Edge(c00,c11));
        c11.edges.add(new Edge(c01,c11));
        c11.edges.add(new Edge(c02,c11));
        c11.edges.add(new Edge(c10,c11));
        c11.edges.add(new Edge(c12,c11));
        c11.edges.add(new Edge(c20,c11));
        c11.edges.add(new Edge(c21,c11));
        c11.edges.add(new Edge(c22,c11));

        c12.edges.add(new Edge(c01,c12));
        c12.edges.add(new Edge(c02,c12));
        c12.edges.add(new Edge(c11,c12));
        c12.edges.add(new Edge(c21,c12));
        c12.edges.add(new Edge(c22,c12));

        c21.edges.add(new Edge(c10,c21));
        c21.edges.add(new Edge(c11,c21));
        c21.edges.add(new Edge(c12,c21));
        c21.edges.add(new Edge(c20,c21));
        c21.edges.add(new Edge(c22,c21));

        c22.edges.add(new Edge(c12,c22));
        c22.edges.add(new Edge(c21,c22));
        c22.edges.add(new Edge(c11,c22));

        c20.edges.add(new Edge(c10,c20));
        c20.edges.add(new Edge(c21,c20));
        c20.edges.add(new Edge(c11,c20));

        gg.start = c00;
        return gg;
    }
}
