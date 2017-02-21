package tests;

import static org.junit.Assert.*;
import static tests.TestBoard.*;
import static tests.TestBoard.create3x3BoardWithoutC11;

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
        board.addCell(2,2);
        assertEquals(c, board.getStart());
    }

    @Test
    public void testBoard2CellsPointCorrectly(){
        BOARD.addCell(c12);
        BOARD.addCell(c22);
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
        b.addCell(c12);
        b.addCell(c21);
        b.addCell(c22);
        assertTrue(c12.get(Direction.BELOW) == c22);
        assertTrue(c22.get(Direction.ABOVE) == c12);
        assertTrue(c21.get(Direction.TOP_RIGHT) == c12);
        assertTrue(c12.get(Direction.BTM_LEFT) == c21);
        assertTrue(c21.get(Direction.RIGHT) == c22);
    }

    @Test
    public void testInsertOutOfOrder(){
        GraphBoard b = new GraphBoard(8,8);
        b.addCell(c21);
        b.addCell(c22);
        b.addCell(c12);
        b.printGraphicalBoard(b.getStart());
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
        b.addCell(c21);
        b.addCell(c22);
        b.addCell(c12);
        b.addCell(c00);
        b.addCell(c02);
        b.addCell(c11);
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
        for(Cell c : TestBoard.getAll()){
            gg.addCell(c);
        }
        gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void makeThisFail(){
        GraphBoard gg = new GraphBoard(500,500);
        Random r = new Random();
        int x, y, v;
        Cell c;
        ArrayList<Cell> cells = new ArrayList<>(50);
        for (int i = 0; i < 1000; i++) {
            x = r.nextInt(500);
            y = r.nextInt(500);
            v = r.nextInt(5);
            if(v>=0 && v<=2){
                v=2;
            } else
                v=4;
            c = new Cell(x,y,v);
            cells.add(gg.addCell(c));
            if(c.edges.size() > 8){
                throw new IllegalArgumentException("something's wrong");
            }
        }
        System.out.println("\nCell count: " + cells.size());
        for(Cell mCells : cells){
            if(mCells!= null){
                //System.out.println(mCells);
                //assertTrue(!mCells.hasEdge(mCells));
                if(mCells.hasEdge(mCells)){
                    System.out.println("Bad - " + mCells);
                }
            }
        }
        //gg.printGraphicalBoard(gg.getStart());
    }

    @Test
    public void testA20x20Board() throws Exception {
        for (int i = 0; i < 10; i++) {
            GraphBoard gg = new GraphBoard(20,20);
            Random r = new Random();
            int x, y, v;
            Cell c;
            ArrayList<Cell> cells = new ArrayList<>(20);
            for (int a = 0; a < 10; a++) {
                 cells = new ArrayList<>(20);
                a = r.nextInt(20);
                y = r.nextInt(20);
                v = r.nextInt(5);
                if (v >= 0 && v <= 2) {
                    v = 2;
                } else
                    v = 4;
                c = new Cell(a, y, v);
                cells.add(gg.addCell(c));
            }
            for (Cell cz :
                    cells) {
                System.out.println(cz);
            }
            System.out.println(">>");
        }
    }

    @Test
    public void testGetAdjacents(){

        GraphBoard gg = create3x3BoardWithoutC11();

        Cell expected = TestBoard.getDummyC11WithEdges();

        HashMap<Direction, Cell> hh;
        Cell c11 = new Cell(1,1,3);
        ArrayList<Cell> cells = new ArrayList<>(8);

        for(Cell c : TestBoard.getAll()) {
            if (!c.equals(c11)) {
                hh = new HashMap<>(8);
                System.out.println("\n/*====================================/");
                System.out.println("Test: get adjacents FOR C[1,1] from: " + c);
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
        //asserts that the target is returned
        assertEquals(target, gg.directWalk(target, gg.start));
        //asserts that the starting corner doesn't reference the target
        assertFalse(gg.getStart().hasEdge(target));
        //assert that the target now points to the closest edge
        assertTrue(target.hasEdge(c22));
    }

    @Test
    public void walkTo1000cellsAwayInTheory() throws Exception {
        GraphBoard gg = TestBoard.create3x3BoardWithoutC11();
        Cell target = new Cell(1000, 1000, 999999);
        assertEquals(target, gg.directWalk(target, gg.start));
    }

    @Test (expected = NullPointerException.class)
    public void testDirectWalkOnEmptyBoard() throws Exception {
        Cell cell = new Cell (4,2,2);
        GraphBoard gb = new GraphBoard(8,8);
        Cell returned = gb.directWalk(cell, gb.getStart());
    }

    @Test
    public void testDirectWalkOnSingleCellBoard() throws Exception {
        Cell cell = new Cell (4,2,2);
        Cell startCell = new Cell(1,1,4);
        GraphBoard gb = new GraphBoard(8,8);
        gb.setStart(startCell);
        Cell returned = gb.directWalk(cell, gb.getStart());
        assertTrue(cell.hasEdge(startCell));
    }

    @Test
    public void testInsertC31On3x3() throws Exception {
        Cell c31 = new Cell(3,1,4);
        GraphBoard gg = TestBoard.createSolid3x3Board();
        //make sure the directWalk() function returns c31
        assertEquals(c31, gg.directWalk(c31, gg.getStart()));
        //make sure c31 now has the closest edge
        //relative to its location on the physical board
        assertTrue(c31.hasEdge(c21));
        //make sure that the closest edge points at c31
        assertTrue(c21.hasEdge(c31));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsertingNegativeColumnCellOn3x3Board() throws Exception {
        Cell c31 = new Cell(3,-1,4);
        GraphBoard gg = TestBoard.create3x3BoardWithoutC11();
        gg.addCell(c31);
    }

    @Test
    public void testReferencingWhenInsertingExistingCell() throws Exception {
        Cell c22Dummy = Cell.createDummy(c22);
        GraphBoard gg = TestBoard.createSolid3x3Board();
        //When inserting a cell that exists already, directWalk() does not return a
        //reference to the directWalk() target, but to the one that already exists
        assertTrue(gg.directWalk(c22Dummy, gg.getStart()) == c22);
        System.out.println(c22Dummy);
        System.out.println(c22);
    }

    @Test
    public void testGetCellByXY() throws Exception {
        assertTrue(c22 == TestBoard.create3x3BoardWithoutC11().getCell(2,2));
    }

    @Test
    public void testGetAllSolid3x3Board() throws Exception {
        GraphBoard gb = TestBoard.createSolid3x3Board();
        for (Cell c : TestBoard.getAll()) {
            assertTrue(c == gb.getCell(c.row, c.column));
            System.out.println("Found: " + c);
        }
    }

    @Test
    public void testAddCellInBottomRightCorner() throws Exception {
        GraphBoard gb = TestBoard.createSolid3x3Board();
        assertNull(gb.getCell(3,3));
        Cell newCell = gb.addCell(3,3);
        System.out.println(newCell);
        System.out.println(TestBoard.c22);
        gb.printGraphicalBoard(gb.getStart());
    }
}
