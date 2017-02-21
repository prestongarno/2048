package tests;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.GraphBoard;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;
import static tests.TestBoard.*;

/**
 * Created by preston on 2/17/17.
 */
public class GraphInsert {

    /**
     * Add cells to come up with a perfect 30x30 board
     * Check all cells references for inconsistencies with
     */
    @Test
    public void targetAnyIncorrectAdcajentRefs() throws Exception {
        GraphBoard board = new GraphBoard(12,12);
        Cell current;
        Cell dummy;

        for (int x = 0; x < board.getNumRows(); x++) {
            for (int y = 0; y < board.getNumColumns(); y++) {
                current = new Cell(x,y,2);
                board.addCell(current);
            }
        }

        //test for incorrect edges
        board.doStuff(board.start, testForCorrectEdges);
    }

    final GraphBoard.GraphAction testForCorrectEdges = cell -> {
        ArrayList<Cell> adjacents = GraphInsert.createDummyAdjacents(cell, 12);
        System.out.println("Checking Cell: " + cell);
        for(Cell c : adjacents){
            Cell actual = cell.get(c.isTo(cell));
            //System.out.println(cell + " - > checking for: " + actual);
            assertTrue("Cell[" +c.row + "," + c.column + "] - > checking for: " + actual, c.equals(actual));
        }
    };

    /**
     * Creates a list of cells that would be adjacent in a perfectly at-rest board
     * @param current the cell to get adjacents
     * @return
     */
    public static ArrayList<Cell> createDummyAdjacents(Cell current, int limit){
        Cell above = new Cell(current.row-1, current.column,2);
        Cell below = new Cell(current.row+1, current.column,2);
        Cell left = new Cell(current.row, current.column - 1,2);
        Cell right = new Cell(current.row, current.column+1,2);
        Cell btmLeft = new Cell(current.row+1, current.column-1,2);
        Cell btmRight = new Cell(current.row+1, current.column+1,2);
        Cell topRight = new Cell(current.row-1, current.column+1,2);
        Cell topLeft = new Cell(current.row-1, current.column-11,2);
        Cell[] vals = {above, below, left, right, btmLeft, btmRight, topLeft, topRight};
        ArrayList<Cell> cells = new ArrayList<>(8);
        for(Cell c : vals){
            if(!(c.row < 0 | c.column < 0 | c.row >= limit | c.column >= limit)){
                cells.add(c);
            }
        }
        return cells;
    }

    @Test
    public void testAddFirstCell(){
        GraphBoard board = new GraphBoard(8,8);
        Cell c = new Cell(2,2,2);
        board.addCell(2,2);
        assertEquals(c, board.getStart());
    }

    @Test
    public void testBoard2CellsPointCorrectly(){
        /*BOARD.addCell(c12);
        BOARD.addCell(c22);
        assertTrue(c12.hasEdge(c22));*/
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
    public void breakMyBoard(){
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
        gg.printGraphicalBoard(gg.getStart());
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
        gg.printGraphicalBoard(gg.getStart());
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

    @Test
    public void printUsingSweep() throws Exception {
        GraphBoard gb = TestBoard.createSolid3x3Board();
        gb.addCell(3,3);
        gb.addCell(2,9);
        gb.addCell(0,10);
        gb.doStuff(gb.getStart(), GraphBoard.printGraphicalBoard);
    }
}
