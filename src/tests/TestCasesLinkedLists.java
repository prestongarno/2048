package tests;

import com.sun.rowset.internal.Row;
import game.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestCasesLinkedLists {

    static final Manager m = new Manager(4,4);

    @Test
    public void testPlaceRandomValue(){
        Cell c = m.placeRandomValue();
        assertNotNull(c);
    }

    @Test
    public void testSimpleOneTileShiftRow(){
        Cell testCell = new Cell(0,2,4);
        ColumnLinkedList.rowsRef[0].setHead(testCell);
        ColumnLinkedList.rowsRef[0].shiftToHead();
        System.out.println(testCell);
        assertEquals(0, testCell.column);
    }

    @Test
    public void testSimpleOneTileShiftRow2(){
        Manager m = new Manager(4,4);
        Cell testCell = new Cell(0,2,4);
        Cell secondTestCell = new Cell(0,3,2);
        ColumnLinkedList.rowsRef[0].setHead(testCell);
        ColumnLinkedList.rowsRef[0].insert(secondTestCell);
        ColumnLinkedList.rowsRef[0].shiftToHead();
        System.out.println(testCell + " -> " + secondTestCell);
        assertEquals(1, secondTestCell.column);
    }

    @Test
    public void test2PresetsInsertRandomShift(){
        System.out.println(m.placeRandomValue());
        System.out.println(m.placeRandomValue());
        System.out.println(m.placeRandomValue());
        System.out.println(m.placeRandomValue());
        m.slide(SlideDirection.LEFT);
    }

    @Test
    public void testLinkedListCorrectHeadAndTail(){

    }

    @Test
    public void insertOnBothAxesCheckHeadSetCorrectly(){
        Manager m = new Manager(4,4);
        Cell cell1 = new Cell(2,2,2048);
        RowLinkedList.columnsRef[2].insert(cell1);
        ColumnLinkedList.rowsRef[2].insert(cell1);
        assertEquals(RowLinkedList.columnsRef[2].getHead(), cell1);
        assertEquals(ColumnLinkedList.rowsRef[2].getHead(), cell1);
    }

    @Test
    public void insertMultipleOnBothAxesCheckTailSetCorrectly(){
        Manager m = new Manager(4,4);
        Cell cell1 = new Cell(2,2,2048);
        Cell cell2 = new Cell(2,3,4);
        RowLinkedList.columnsRef[cell1.column].insert(cell1);
        ColumnLinkedList.rowsRef[cell1.row].insert(cell1);
        RowLinkedList.columnsRef[cell2.column].insert(cell2);
        ColumnLinkedList.rowsRef[cell2.row].insert(cell2);
        assertEquals(cell1, RowLinkedList.columnsRef[2].getHead());
        assertEquals(cell1, ColumnLinkedList.rowsRef[2].getHead());
        assertEquals(cell2, ColumnLinkedList.rowsRef[2].getTail());
        //assertEquals(cell2, RowLinkedList.columnsRef[2].getHead());
        m.slide(SlideDirection.LEFT);
/*        assertEquals(0, cell1.column);
        assertEquals(1, cell2.column);*/
    }
}
