package tests;

import game.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestSlide {

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
        m.add(testCell);
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
        m.add(testCell);
        m.add(testCell2);
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
        m.add(testCell);
        m.add(testCell2);
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
        m.add(new Cell(0,1,2));
        m.add(new Cell(0,2,8));
        m.add(new Cell(0,3,4));
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
    }

    @Test
    public void testSlideLeftWith3DiffValuesPutInBackwards(){
        Manager m = new Manager(6,6);
        m.add(new Cell(0,4,2));
        m.add(new Cell(0,3,8));
        m.add(new Cell(0,1,4));
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
    }

    //failing  -> setting all to 8
    @Test
    public void testCombine2WithextraSameValue(){
        Manager m = new Manager(6,6);
        Cell c00 = new Cell(0, 3, 4);
        Cell c01 = new Cell(0, 4, 4);
        Cell c02 = new Cell(0, 5, 4);
        m.add(c00);
        m.add(c01);
        m.add(c02);
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();;
        assertEquals(8, c00.value);
        assertEquals(4, c02.value);
    }

    @Test
    public void testCombine2WithextraSameValue2(){
        Manager m = new Manager(6,6);
        m.add(new Cell(0,4,2));
        m.add(new Cell(0,3,2));
        m.add(new Cell(0,1,2));
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
    public void testPlacingRandomValuesToNAndSlideLeftAndThenUp()
    {
        Manager m = new Manager(8,8);
        for (int i = 0; i < 8; i++) {
            m.placeRandomValue();
        }
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        m.prettyPrintGameBoard();
        m.slide(SlideDirection.UP);
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

    @Test
    public void testMassiveBoard(){
        Manager m = new Manager(5000,5000);
        m.placeRandomValue();
        m.placeRandomValue();
        m.placeRandomValue();
        m.placeRandomValue();
        m.placeRandomValue();
        ArrayList<Cell> ar = m.getNonEmptyTiles();
        for(Cell c : ar){
            System.out.println(c);
        }
        //m.prettyPrintGameBoard();
        m.slide(SlideDirection.LEFT);
        //m.prettyPrintGameBoard();
        ar = m.getNonEmptyTiles();
        for(Cell c : ar){
            System.out.println(c);
        }
    }
}
