package tests;

import GraphGame.Cell;
import GraphGame.Direction;
import GraphGame.NumberGame;

/**
 * ========================================================================
 * 2048 - by Preston Garno on 2/19/17
 * =========================================================================
 */
public final class TestBoard {

    public static Cell c00 = new Cell(0,0,2);
    public static Cell c01 = new Cell(0,1,2);
    public static Cell c10 = new Cell(1,0,2);

    public static Cell c11 = new Cell(1,1,2);
    public static Cell c02 = new Cell(0,2,2);
    public static Cell c12 = new Cell(1,2,2);

    public static Cell c20 = new Cell(2,0,2);
    public static Cell c21 = new Cell(2,1,2);
    public static Cell c22 = new Cell(2,2,2);

    public static Cell getDummyC11WithEdges(){
        Cell copyC11 = Cell.createDummy(c11);
        for (Cell c : getAll()) {
            if(!copyC11.equals(c))
                copyC11.EDGES.put(Direction.isTo(copyC11, c), Cell.createDummy(c));
        }
        return copyC11;
    }


    public static void resetStaticCells(){
        c00 = new Cell(0,0,2);
        c01 = new Cell(0,1,2);
        c10 = new Cell(1,0,2);

        c11 = new Cell(1,1,2);
        c02 = new Cell(0,2,2);
        c12 = new Cell(1,2,2);

        c20 = new Cell(2,0,2);
        c21 = new Cell(2,1,2);
        c22 = new Cell(2,2,2);
    }

    public static Cell[] getAll(){
        return new Cell[]{c00,c01,c02,c10,c11,c12,c20,c21,c22};
    }

    /**
     * Manually creates a board and resets this class' static Cell
     * object to their correct relationship to each other
     * This is to make Unit testing multiple situations, etc easy,
     * since if an exception is thrown, just call this method and
     * the board, along with all of the cells, will be reset
     * @return A new NumberGame instance, with c00 as the start
     */
    public static NumberGame createSolid3x3Board(){

        NumberGame gb = new NumberGame(10,10);

        resetStaticCells();

        c00.EDGES.put(Direction.isTo(c00, c01), c01);
        c00.EDGES.put(Direction.isTo(c00,c10), c10);
        c00.EDGES.put(Direction.isTo(c00,c11), c11);

        c01.EDGES.put(Direction.isTo(c01,c00), c00);
        c01.EDGES.put(Direction.isTo(c01,c10), c10);
        c01.EDGES.put(Direction.isTo(c01,c11), c11);
        c01.EDGES.put(Direction.isTo(c01,c02), c02);
        c01.EDGES.put(Direction.isTo(c01,c12), c12);

        c02.EDGES.put(Direction.isTo(c02,c01), c01);
        c02.EDGES.put(Direction.isTo(c02,c11), c11);
        c02.EDGES.put(Direction.isTo(c02,c12), c12);
        
        c10.EDGES.put(Direction.isTo(c10,c00), c00);
        c10.EDGES.put(Direction.isTo(c10,c01), c01);
        c10.EDGES.put(Direction.isTo(c10,c11), c11);
        c10.EDGES.put(Direction.isTo(c10,c21), c21);
        c10.EDGES.put(Direction.isTo(c10,c20), c20);

        c11.EDGES.put(Direction.isTo(c11,c00), c00);
        c11.EDGES.put(Direction.isTo(c11,c01), c01);
        c11.EDGES.put(Direction.isTo(c11,c02), c02);
        c11.EDGES.put(Direction.isTo(c11,c10), c10);
        c11.EDGES.put(Direction.isTo(c11,c12), c12);
        c11.EDGES.put(Direction.isTo(c11,c20), c20);
        c11.EDGES.put(Direction.isTo(c11,c21), c21);
        c11.EDGES.put(Direction.isTo(c22,c11), c11);

        c12.EDGES.put(Direction.isTo(c12,c01), c01);
        c12.EDGES.put(Direction.isTo(c12,c02), c01);
        c12.EDGES.put(Direction.isTo(c12,c11), c11);
        c12.EDGES.put(Direction.isTo(c12,c21), c21);
        c12.EDGES.put(Direction.isTo(c12,c22), c22);

        c21.EDGES.put(Direction.isTo(c21,c10), c10);
        c21.EDGES.put(Direction.isTo(c21,c11), c11);
        c21.EDGES.put(Direction.isTo(c21,c12), c12);
        c21.EDGES.put(Direction.isTo(c21,c20), c20);
        c21.EDGES.put(Direction.isTo(c21,c22), c22);

        c22.EDGES.put(Direction.isTo(c22,c12), c12);
        c22.EDGES.put(Direction.isTo(c22,c21), c21);
        c22.EDGES.put(Direction.isTo(c22,c11), c11);

        c20.EDGES.put(Direction.isTo(c20,c10), c10);
        c20.EDGES.put(Direction.isTo(c20,c21), c21);
        c20.EDGES.put(Direction.isTo(c20,c11), c11);

        gb.start = c00;
        return gb;
    }

    public static NumberGame create3x3BoardWithoutC11(){

        NumberGame gb = new NumberGame(10,10);

        resetStaticCells();

        c00.EDGES.put(Direction.isTo(c00, c01), c01);
        c00.EDGES.put(Direction.isTo(c00,c10), c10);
        c00.EDGES.put(Direction.isTo(c00,c11), c11);

        c01.EDGES.put(Direction.isTo(c01,c00), c00);
        c01.EDGES.put(Direction.isTo(c01,c10), c10);
        c01.EDGES.put(Direction.isTo(c01,c11), c11);
        c01.EDGES.put(Direction.isTo(c01,c02), c02);
        c01.EDGES.put(Direction.isTo(c01,c12), c12);

        c02.EDGES.put(Direction.isTo(c02,c01), c01);
        c02.EDGES.put(Direction.isTo(c02,c11), c11);
        c02.EDGES.put(Direction.isTo(c02,c12), c12);

        c10.EDGES.put(Direction.isTo(c10,c00), c00);
        c10.EDGES.put(Direction.isTo(c10,c01), c01);
        c10.EDGES.put(Direction.isTo(c10,c11), c11);
        c10.EDGES.put(Direction.isTo(c10,c21), c21);
        c10.EDGES.put(Direction.isTo(c10,c20), c20);

        c12.EDGES.put(Direction.isTo(c12,c01), c01);
        c12.EDGES.put(Direction.isTo(c12,c02), c01);
        c12.EDGES.put(Direction.isTo(c12,c11), c11);
        c12.EDGES.put(Direction.isTo(c12,c21), c21);
        c12.EDGES.put(Direction.isTo(c12,c22), c22);

        c21.EDGES.put(Direction.isTo(c21,c10), c10);
        c21.EDGES.put(Direction.isTo(c21,c11), c11);
        c21.EDGES.put(Direction.isTo(c21,c12), c12);
        c21.EDGES.put(Direction.isTo(c21,c20), c20);
        c21.EDGES.put(Direction.isTo(c21,c22), c22);

        c22.EDGES.put(Direction.isTo(c22,c12), c12);
        c22.EDGES.put(Direction.isTo(c22,c21), c21);
        c22.EDGES.put(Direction.isTo(c22,c11), c11);

        c20.EDGES.put(Direction.isTo(c20,c10), c10);
        c20.EDGES.put(Direction.isTo(c20,c21), c21);
        c20.EDGES.put(Direction.isTo(c20,c11), c11);

        gb.start = c00;
        return gb;
    }



    public static NumberGame createSquareBoard(int x, int y) {
        NumberGame board = new NumberGame(x, y);
        Cell current;

        for (int a = 0; a < board.getNumRows(); a++) {
            for (int b = 0; b < board.getNumColumns(); b++) {
                current = new Cell(a,b,4);
                board.addCell(current);
            }
        }

        return board;
    }

}
