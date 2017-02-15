package tests;

import com.sun.rowset.internal.Row;
import game.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestCasesLinkedLists {

    @Test
    public void testPlaceRandomValue(){
        Manager m = new Manager(4,4);
        Cell c = m.placeRandomValue();
        m.prettyPrintGameBoard();
        assertNotNull(c);
    }

    @Test
    public void testPlaceRandomValueAndShiftLeft(){
        Manager m = new Manager(4,4);
        Cell c = m.placeRandomValue();
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
        assertNotNull(c);
    }

    @Test
    public void testSimpleOneTileShiftRow(){
        Manager m = new Manager(8,8);
        Cell testCell = new Cell(0,2,4);
        m.insertCell(testCell);
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
        assertEquals(0, testCell.column);
    }

    @Test
    public void testTwoTileShiftRow(){
        Manager m = new Manager(8,8);
        Cell testCell = new Cell(0,2,4);
        Cell testCell2 = new Cell(0,4,8);
        m.insertCell(testCell);
        m.insertCell(testCell2);
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
        assertEquals(0, testCell.column);
        assertEquals(1, testCell2.column);
    }

    @Test
    public void testTwoTilePlaceRandomAndShiftLeft(){
        Manager m = new Manager(8,8);
        Cell testCell = new Cell(0,2,4);
        Cell testCell2 = new Cell(0,4,8);
        m.insertCell(testCell);
        m.insertCell(testCell2);
        m.placeRandomValue();
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
        assertEquals(0, testCell.column);
        assertEquals(1, testCell2.column);
    }

    @Test
    public void test2PresetsInsertRandomShift(){
        Manager m = new Manager(4,4);
        System.out.println(m.placeRandomValue());
        System.out.println(m.placeRandomValue());
        System.out.println(m.placeRandomValue());
        System.out.println(m.placeRandomValue());
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
    }

    @Test
    public void testSlideLeftWith3DiffValues(){
        Manager m = new Manager(6,6);
        m.insertCell(new Cell(0,1,2));
        m.insertCell(new Cell(0,2,8));
        m.insertCell(new Cell(0,3,4));
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
    }

    @Test
    public void testSlideLeftWith3DiffValuesPutInBackwards(){
        Manager m = new Manager(6,6);
        m.insertCell(new Cell(0,4,2));
        m.insertCell(new Cell(0,3,8));
        m.insertCell(new Cell(0,1,4));
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
    }

    //failing  -> setting all to 8
    @Test
    public void testCombine2WithextraSameValue(){
        Manager m = new Manager(6,6);
        m.insertCell(new Cell(0,4,4));
        m.insertCell(new Cell(0,3,4));
        m.insertCell(new Cell(0,1,4));
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
    }

    //failing  -> setting all to 8
    @Test
    public void testCombine2WithextraSameValue2(){
        Manager m = new Manager(6,6);
        m.insertCell(new Cell(0,4,2));
        m.insertCell(new Cell(0,3,2));
        m.insertCell(new Cell(0,1,2));
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
    }

    @Test
    public void testPlacingRandomValuesToNAndSlideLeft()
    {
        Manager m = new Manager(8,8);
        for (int i = 0; i < 8; i++) {
            m.placeRandomValue();
        }
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
    }

    @Test
    public void firstTestSlideUp()
    {
        Manager m = new Manager(8,8);
        for (int i = 0; i < 10; i++) {
            m.placeRandomValue();
        }
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.UP);
        m.prettyPrintGameBoard();
    }
}
