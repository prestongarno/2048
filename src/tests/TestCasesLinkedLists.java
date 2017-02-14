package tests;

import game.Cell;
import game.ColumnLinkedList;
import game.Manager;
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
}
