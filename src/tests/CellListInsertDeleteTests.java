package tests;

import game.Cell;
import game.ColumnLinkedList;
import game.Manager;
import game.RowLinkedList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by preston on 2/15/17.
 */
public class CellListInsertDeleteTests {
    @Test
    public void testHeadAndTailSetCorrectlyRow(){
        RowLinkedList ll = new RowLinkedList();
        Cell c = new Cell(0,4,2);
        Cell c01 = new Cell(0,2,2);
        ll.insert(c);
        ll.insert(c01);
        assertEquals(ll.getHead(), c01);
        assertEquals(ll.getTail(), c);
    }

    @Test
    public void testHeadAndTailSetCorrectlyColumn(){
        ColumnLinkedList ll = new ColumnLinkedList();
        Cell c = new Cell(2,4,2);
        Cell c01 = new Cell(4,4,2);
        ll.insert(c01);
        ll.insert(c);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c01);
    }

    @Test
    public void listReferencingTestInsert123r(){
        RowLinkedList ll = new RowLinkedList();
        Cell c = new Cell(0,1,2);
        Cell c01 = new Cell(0,2,2);
        Cell c02 = new Cell(0,3,2);
        ll.insert(c);
        ll.insert(c01);
        ll.insert(c02);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c01, c.getcRight());
        assertEquals(c, c01.getcLeft());
        assertEquals(c02, c01.getcRight());
        assertEquals(c01, c02.getcLeft());
        assertNull(c.getcLeft());
        assertNull(c02.getcRight());
    }

    @Test
    public void listReferencingTestInsert123c(){
        ColumnLinkedList ll = new ColumnLinkedList();
        Cell c = new Cell(1,0,2);
        Cell c01 = new Cell(2,0,2);
        Cell c02 = new Cell(3,0,2);
        ll.insert(c);
        ll.insert(c01);
        ll.insert(c02);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c01, c.getcBelow());
        assertEquals(c, c01.getcAbove());
        assertEquals(c02, c01.getcBelow());
        assertEquals(c01, c02.getcAbove());
        assertNull(c.getcAbove());
        assertNull(c02.getcBelow());
    }

    @Test
    public void listReferencingTestInsert213r(){
        RowLinkedList ll = new RowLinkedList();
        Cell c = new Cell(0,1,2);
        Cell c01 = new Cell(0,2,2);
        Cell c02 = new Cell(0,3,2);
        ll.insert(c01);
        ll.insert(c);
        ll.insert(c02);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c01, c.getcRight());
        assertEquals(c, c01.getcLeft());
        assertEquals(c02, c01.getcRight());
        assertEquals(c01, c02.getcLeft());
        assertNull(c.getcLeft());
        assertNull(c02.getcRight());
    }

    @Test
    public void listReferencingTestInsert213c(){
        ColumnLinkedList ll = new ColumnLinkedList();
        Cell c = new Cell(1,0,2);
        Cell c01 = new Cell(2,0,2);
        Cell c02 = new Cell(3,0,2);
        ll.insert(c01);
        ll.insert(c);
        ll.insert(c02);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c01, c.getcBelow());
        assertEquals(c, c01.getcAbove());
        assertEquals(c02, c01.getcBelow());
        assertEquals(c01, c02.getcAbove());
        assertNull(c.getcAbove());
        assertNull(c02.getcBelow());
    }

    @Test
    public void listReferencingTestInsert231r(){
        RowLinkedList ll = new RowLinkedList();
        Cell c = new Cell(0,1,2);
        Cell c01 = new Cell(0,2,2);
        Cell c02 = new Cell(0,3,2);
        ll.insert(c01);
        ll.insert(c02);
        ll.insert(c);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c01, c.getcRight());
        assertEquals(c, c01.getcLeft());
        assertEquals(c02, c01.getcRight());
        assertEquals(c01, c02.getcLeft());
        assertNull(c.getcLeft());
        assertNull(c02.getcRight());
    }

    @Test
    public void listReferencingTestInsert231c(){
        ColumnLinkedList ll = new ColumnLinkedList();
        Cell c = new Cell(1,0,2);
        Cell c01 = new Cell(2,0,2);
        Cell c02 = new Cell(3,0,2);
        ll.insert(c01);
        ll.insert(c02);
        ll.insert(c);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c01, c.getcBelow());
        assertEquals(c, c01.getcAbove());
        assertEquals(c02, c01.getcBelow());
        assertEquals(c01, c02.getcAbove());
        assertNull(c.getcAbove());
        assertNull(c02.getcBelow());
    }

    @Test
    public void listReferencingTestInsert321r(){
        RowLinkedList ll = new RowLinkedList();
        Cell c = new Cell(0,1,2);
        Cell c01 = new Cell(0,2,2);
        Cell c02 = new Cell(0,3,2);
        ll.insert(c02);
        ll.insert(c01);
        ll.insert(c);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c01, c.getcRight());
        assertEquals(c, c01.getcLeft());
        assertEquals(c02, c01.getcRight());
        assertEquals(c01, c02.getcLeft());
        assertNull(c.getcLeft());
        assertNull(c02.getcRight());
    }

    @Test
    public void listReferencingTestInsert321c(){
        ColumnLinkedList ll = new ColumnLinkedList();
        Cell c = new Cell(1,0,2);
        Cell c01 = new Cell(2,0,2);
        Cell c02 = new Cell(3,0,2);
        ll.insert(c02);
        ll.insert(c01);
        ll.insert(c);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c01, c.getcBelow());
        assertEquals(c, c01.getcAbove());
        assertEquals(c02, c01.getcBelow());
        assertEquals(c01, c02.getcAbove());
        assertNull(c.getcAbove());
        assertNull(c02.getcBelow());
    }

    @Test
    public void deletionTest1r(){
        RowLinkedList ll = new RowLinkedList();
        Cell c = new Cell(0,1,2);
        Cell c01 = new Cell(0,2,2);
        Cell c02 = new Cell(0,3,2);
        ll.insert(c02);
        ll.insert(c01);
        ll.insert(c);
        ll.remove(c01);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c02, c.getcRight());
        assertEquals(c, c02.getcLeft());
        assertEquals(c02, c.getcRight());
        assertNull(c.getcLeft());
        assertNull(c02.getcRight());
    }

    @Test
    public void deletionTest1c(){
        ColumnLinkedList ll = new ColumnLinkedList();
        Cell c = new Cell(1,0,2);
        Cell c01 = new Cell(2,0,2);
        Cell c02 = new Cell(3,0,2);
        ll.insert(c02);
        ll.insert(c01);
        ll.insert(c);
        ll.remove(c01);
        assertEquals(ll.getHead(), c);
        assertEquals(ll.getTail(), c02);
        assertEquals(c02, c.getcBelow());
        assertEquals(c, c02.getcAbove());
        assertEquals(c02, c.getcBelow());
        assertNull(c.getcAbove());
        assertNull(c02.getcBelow());
    }

    @Test
    public void deletionTest2r(){
        RowLinkedList ll = new RowLinkedList();
        Cell c = new Cell(0,1,2);
        Cell c01 = new Cell(0,2,2);
        Cell c02 = new Cell(0,3,2);
        ll.insert(c02);
        ll.insert(c01);
        ll.insert(c);
        ll.prettyPrint();
        ll.remove(c);
        ll.prettyPrint();
        assertEquals(ll.getHead(), c01);
        assertEquals(ll.getTail(), c02);
        assertEquals(c02, c01.getcRight());
        assertEquals(c01, c02.getcLeft());
        assertEquals(c02, c01.getcRight());
        assertNull(c.getcRight());
        assertNull(c02.getcRight());
        assertEquals(c01, ll.getHead());
    }

    @Test
    public void deletionTest2c(){
        ColumnLinkedList ll = new ColumnLinkedList();
        Cell c = new Cell(1,0,2);
        Cell c01 = new Cell(2,0,2);
        Cell c02 = new Cell(3,0,2);
        ll.insert(c02);
        ll.insert(c01);
        ll.insert(c);
        ll.remove(c);
        assertEquals(ll.getHead(), c01);
        assertEquals(ll.getTail(), c02);
        assertEquals(c02, c01.getcBelow());
        assertEquals(c01, c02.getcAbove());
        assertEquals(c02, c01.getcBelow());
        assertNull(c01.getcAbove());
        assertNull(c02.getcBelow());
        assertEquals(c01, ll.getHead());
    }

    @Test
    public void deletionTest3r(){
        RowLinkedList ll = new RowLinkedList();
        Cell c = new Cell(0,1,2);
        Cell c01 = new Cell(0,2,2);
        Cell c02 = new Cell(0,3,2);
        ll.insert(c02);
        ll.insert(c01);
        ll.insert(c);
        ll.remove(c02);
        assertEquals(c, ll.getHead());
        assertEquals(ll.getTail(), c01);
        assertEquals(c01, c.getcRight());
        assertEquals(c, c01.getcLeft());
        assertNull(c.getcLeft());
        assertNull(c01.getcRight());
    }

    @Test
    public void deletionTest3c(){
        ColumnLinkedList ll = new ColumnLinkedList();
        Cell c = new Cell(1,0,2);
        Cell c01 = new Cell(2,0,2);
        Cell c02 = new Cell(3,0,2);
        ll.insert(c02);
        ll.insert(c01);
        ll.insert(c);
        ll.remove(c02);
        assertEquals(c, ll.getHead());
        assertEquals(ll.getTail(), c01);
        assertEquals(c01, c.getcBelow());
        assertEquals(c, c01.getcAbove());
        assertNull(c.getcAbove());
        assertNull(c01.getcBelow());
    }
}
