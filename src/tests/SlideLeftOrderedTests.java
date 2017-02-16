package tests;

import game.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by preston on 2/15/17.
 */
public class SlideLeftOrderedTests {

    static final Manager manager = new Manager(8,8);

    @Test
    public void testSlideNonCombined(){
        Cell c0 = new Cell(3,3,2);
        int[] c0Backup = {c0.row, c0.column, c0.value};
        Cell c1 = new Cell(3,5,4);
        int[] c1Backup = {c1.row, c1.column, c1.value};
        manager.add(c0, c1);
        manager.prettyPrintGameBoard();
        manager.slide(SlideDirection.LEFT);
        manager.prettyPrintGameBoard();
        assertEquals(3,c0.row);
        assertEquals(3,c1.row);
        assertEquals(0, c0.column);
        assertEquals(1, c1.column);
        RowLinkedList rl = manager.getRowList(c0.row);
        ColumnLinkedList clCell0 = manager.getColumn(c0.column);
        ColumnLinkedList clCell1 = manager.getColumn(c1.column);
        //make sure the old column is empty for c0 and c1
        assertNull(manager.getColumn(c0Backup[1]).getHead());
        assertNull(manager.getColumn(c1Backup[1]).getHead());
        //make sure the new columns is updated correctly
        assertEquals(c0, manager.getColumn(c0.column).getHead());
        assertNull(manager.getColumn(c0.column).getTail());
        assertEquals(c1, manager.getColumn(c1.column).getHead());
        assertNull(manager.getColumn(c1.column).getTail());
    }

    public void testSlideCombined(){
        Cell c0 = new Cell(3,3,8);
        Cell c1 = new Cell(3,5,8);
        manager.add(c0, c1);
        manager.prettyPrintGameBoard();
        manager.slide(SlideDirection.LEFT);
        manager.prettyPrintGameBoard();
        assertEquals(16, c0.value);
        assertEquals(0, c1.value);
        RowLinkedList rl = manager.getRowList(c0.row);
        ColumnLinkedList clCell0 = manager.getColumn(c0.column);
        ColumnLinkedList clCell1 = manager.getColumn(c1.column);
    }

}
