package tests;

import GraphGame.Cell;
import GraphGame.Cell.*;
import GraphGame.Direction;
import GraphGame.GraphBoard;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * This class will prove that all helper methods used in this
 * application is completely sound
 */
@SuppressWarnings("Duplicates")
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
        assertEquals(Direction.RIGHT, Edge.isTo(c, c1));
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
        assertEquals(Direction.BTM_RIGHT, Edge.isTo(c, c1));
        assertEquals(Direction.TOP_LEFT, Edge.isTo(c1, c));
    }

    @Test
    public void abstractBoardCreationFactoryImplementor(){
        GraphBoard gg = createBoard();
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void abstractBoardCreationFactoryImplementor2(){
        GraphBoard gg = createBoard();
        gg.printGraphicalBoard(gg.getStart());
        Cell target = new Cell(3, 3, 2);
        (gg.walk(target, gg.getStart())).addEdge(target);
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void abstractBoardCreationFactoryImplementor3(){
        GraphBoard gg = createBoard();
        gg.printGraphicalBoard(gg.getStart());
        Cell target = new Cell(100, 100, 2);
        (gg.walk(target, gg.getStart())).addEdge(target);
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void abstractBoardCreationFactoryImplementor4(){
        GraphBoard gg = createBoard();
        gg.printGraphicalBoard(gg.getStart());
        Cell target = new Cell(2, 5, 2);
        (gg.walk(target, gg.getStart())).addEdge(target);
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void abstractBoardCreationFactoryImplementor5(){
        GraphBoard gg = createBoard();
        gg.printGraphicalBoard(gg.getStart());
        Cell target = new Cell(2, 3, 2);
        (gg.walk(target, gg.getStart())).addEdge(target);
        gg.printGraphicalBoard(gg.getStart());
    }
    @Test
    public void abstractBoardCreationFactoryImplementor6(){
        GraphBoard gg = createBoard();
        gg.printGraphicalBoard(gg.getStart());
        int row = 3;
        int column = 0;
        int value = 2;
        for (int i = 0; i < 200; i++) {
            Cell target = new Cell(row, column, value);
            (gg.walk(target, gg.getStart())).addEdge(target);
            Iterator<Edge> it = target.edges.iterator();
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

        c02.edges.add(new Edge(c01,c02));
        c02.edges.add(new Edge(c11,c02));
        c02.edges.add(new Edge(c12,c02));

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
        Cell[] threeByThree = {c00,c01,c02,c10,c11,c12,c20,c21, c22};
        for(Cell c : threeByThree){
            System.out.println(c);
        }
        return gg;
    }

    @Test
    public void testGetAdjacents(){
        Cell c00 = new Cell(0,0,2);
        Cell c01 = new Cell(0,1,3);
        Cell c10 = new Cell(1,0,26);

        Cell c02 = new Cell(0,2,1);
        Cell c12 = new Cell(1,2,9);

        Cell c20 = new Cell(2,0,2);
        Cell c21 = new Cell(2,1,5);
        Cell c22 = new Cell(2,2,2);

        c00.edges.add(new Edge(c01,c00));
        c00.edges.add(new Edge(c10,c00));
        c00.edges.add(new Edge(c12,c00));

        c01.edges.add(new Edge(c00,c01));
        c01.edges.add(new Edge(c10,c01));
        c01.edges.add(new Edge(c21,c01));
        c01.edges.add(new Edge(c02,c01));
        c01.edges.add(new Edge(c12,c01));

        c02.edges.add(new Edge(c01,c02));
        c02.edges.add(new Edge(c10,c02));
        c02.edges.add(new Edge(c12,c02));

        c10.edges.add(new Edge(c00,c10));
        c10.edges.add(new Edge(c01,c10));
        c10.edges.add(new Edge(c12,c10));
        c10.edges.add(new Edge(c21,c10));
        c10.edges.add(new Edge(c20,c10));

        c12.edges.add(new Edge(c01,c12));
        c12.edges.add(new Edge(c02,c12));
        c12.edges.add(new Edge(c10,c12));
        c12.edges.add(new Edge(c21,c12));
        c12.edges.add(new Edge(c22,c12));

        c21.edges.add(new Edge(c10,c21));
        c21.edges.add(new Edge(c10,c21));
        c21.edges.add(new Edge(c12,c21));
        c21.edges.add(new Edge(c20,c21));
        c21.edges.add(new Edge(c22,c21));

        c22.edges.add(new Edge(c12,c22));
        c22.edges.add(new Edge(c21,c22));
        c22.edges.add(new Edge(c10,c22));

        c20.edges.add(new Edge(c10,c20));
        c20.edges.add(new Edge(c21,c20));
        c20.edges.add(new Edge(c12,c20));

        GraphBoard gg = new GraphBoard(3,3);
        gg.start = c00;
        Cell[] threeByThree = {c00,c01,c02,c10,/*c11,*/c12,c20,c21, c22};
        for(Cell c : threeByThree){
            System.out.println(c);
        }

        Cell expected = new Cell(1,1,2);
        expected.edges.add(new Edge(c00,expected));
        expected.edges.add(new Edge(c01,expected));
        expected.edges.add(new Edge(c02,expected));
        expected.edges.add(new Edge(c10,expected));
        expected.edges.add(new Edge(c12,expected));
        expected.edges.add(new Edge(c20,expected));
        expected.edges.add(new Edge(c21,expected));
        expected.edges.add(new Edge(c22,expected));

        HashMap<Direction, Cell> hh;
        Cell c11 = new Cell(1,1,3);
        ArrayList<Cell> cells = new ArrayList<>(8);

        for(Cell c : threeByThree){
            hh = new HashMap<>(8);
            System.out.println("\n/*====================================/");
            System.out.println("Testing adjacents from: " + c);
            System.out.println("/*====================================/");
            hh = gg.getAdjacentsForNewCell(c11, c, hh);
            assertTrue(test11EdgesAgainstReturned(expected, c11, hh));
        }

    }
    
    private boolean test11EdgesAgainstReturned(Cell expected, Cell inserted, HashMap<Direction, Cell> ham){

        Iterator<Cell> hamIt = ham.values().iterator();

        while(hamIt.hasNext()){
            inserted.addEdge(hamIt.next());
            hamIt.remove();
        }

        ArrayList<Cell> cells = new ArrayList<>(8);
        for(Edge e : expected.edges){
            cells.add(e.get());
        }
        for(Edge e : inserted.edges){
            if(!cells.contains(e.get()))
                return false;
        }
        return true;
    }
}
