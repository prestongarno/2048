package tests;

import GraphGame.Cell;
import GraphGame.GraphBoard;

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

    public static Cell[] getAll(){
        return new Cell[]{c00,c01,c02,c10,c11,c12,c20,c21,c22};
    }

    /**
     * Manually creates a board and resets this class' static Cell
     * object to their correct relationship to each other
     * This is to make Unit testing multiple situations, etc easy,
     * since if an exception is thrown, just call this method and
     * the board, along with all of the cells, will be reset
     * @return A new GraphBoard instance, with TestBoard.c00 as the start
     */
    public static GraphBoard createSolid3x3Board(){

        GraphBoard gg = new GraphBoard(10,10);

        c00 = new Cell(0,0,2);
        c01 = new Cell(0,1,2);
        c10 = new Cell(1,0,2);

        c11 = new Cell(1,1,2);
        c02 = new Cell(0,2,2);
        c12 = new Cell(1,2,2);

        c20 = new Cell(2,0,2);
        c21 = new Cell(2,1,2);
        c22 = new Cell(2,2,2);

        c00.edges.add(new Cell.Edge(c01,c00));
        c00.edges.add(new Cell.Edge(c10,c00));
        c00.edges.add(new Cell.Edge(c11,c00));

        c01.edges.add(new Cell.Edge(c00,c01));
        c01.edges.add(new Cell.Edge(c10,c01));
        c01.edges.add(new Cell.Edge(c11,c01));
        c01.edges.add(new Cell.Edge(c02,c01));
        c01.edges.add(new Cell.Edge(c12,c01));

        c02.edges.add(new Cell.Edge(c01,c02));
        c02.edges.add(new Cell.Edge(c11,c02));
        c02.edges.add(new Cell.Edge(c12,c02));

        c10.edges.add(new Cell.Edge(c00,c10));
        c10.edges.add(new Cell.Edge(c01,c10));
        c10.edges.add(new Cell.Edge(c11,c10));
        c10.edges.add(new Cell.Edge(c21,c10));
        c10.edges.add(new Cell.Edge(c20,c10));

        c11.edges.add(new Cell.Edge(c00,c11));
        c11.edges.add(new Cell.Edge(c01,c11));
        c11.edges.add(new Cell.Edge(c02,c11));
        c11.edges.add(new Cell.Edge(c10,c11));
        c11.edges.add(new Cell.Edge(c12,c11));
        c11.edges.add(new Cell.Edge(c20,c11));
        c11.edges.add(new Cell.Edge(c21,c11));
        c11.edges.add(new Cell.Edge(c22,c11));

        c12.edges.add(new Cell.Edge(c01,c12));
        c12.edges.add(new Cell.Edge(c02,c12));
        c12.edges.add(new Cell.Edge(c11,c12));
        c12.edges.add(new Cell.Edge(c21,c12));
        c12.edges.add(new Cell.Edge(c22,c12));

        c21.edges.add(new Cell.Edge(c10,c21));
        c21.edges.add(new Cell.Edge(c11,c21));
        c21.edges.add(new Cell.Edge(c12,c21));
        c21.edges.add(new Cell.Edge(c20,c21));
        c21.edges.add(new Cell.Edge(c22,c21));

        c22.edges.add(new Cell.Edge(c12,c22));
        c22.edges.add(new Cell.Edge(c21,c22));
        c22.edges.add(new Cell.Edge(c11,c22));

        c20.edges.add(new Cell.Edge(c10,c20));
        c20.edges.add(new Cell.Edge(c21,c20));
        c20.edges.add(new Cell.Edge(c11,c20));

        gg.start = c00;
        return gg;
    }

    public static GraphBoard create3x3BoardWithoutC11(){
        c00 = new Cell(0,0,2);
        c01 = new Cell(0,1,2);
        c10 = new Cell(1,0,2);

        c02 = new Cell(0,2,2);
        c12 = new Cell(1,2,2);

        c20 = new Cell(2,0,2);
        c21 = new Cell(2,1,2);
        c22 = new Cell(2,2,2);

        c00.edges.add(new Cell.Edge(c01,c00));
        c00.edges.add(new Cell.Edge(c10,c00));
        c00.edges.add(new Cell.Edge(c12,c00));

        c01.edges.add(new Cell.Edge(c00,c01));
        c01.edges.add(new Cell.Edge(c10,c01));
        c01.edges.add(new Cell.Edge(c21,c01));
        c01.edges.add(new Cell.Edge(c02,c01));
        c01.edges.add(new Cell.Edge(c12,c01));

        c02.edges.add(new Cell.Edge(c01,c02));
        c02.edges.add(new Cell.Edge(c10,c02));
        c02.edges.add(new Cell.Edge(c12,c02));

        c10.edges.add(new Cell.Edge(c00,c10));
        c10.edges.add(new Cell.Edge(c01,c10));
        c10.edges.add(new Cell.Edge(c12,c10));
        c10.edges.add(new Cell.Edge(c21,c10));
        c10.edges.add(new Cell.Edge(c20,c10));

        c12.edges.add(new Cell.Edge(c01,c12));
        c12.edges.add(new Cell.Edge(c02,c12));
        c12.edges.add(new Cell.Edge(c10,c12));
        c12.edges.add(new Cell.Edge(c21,c12));
        c12.edges.add(new Cell.Edge(c22,c12));

        c21.edges.add(new Cell.Edge(c10,c21));
        c21.edges.add(new Cell.Edge(c10,c21));
        c21.edges.add(new Cell.Edge(c12,c21));
        c21.edges.add(new Cell.Edge(c20,c21));
        c21.edges.add(new Cell.Edge(c22,c21));

        c22.edges.add(new Cell.Edge(c12,c22));
        c22.edges.add(new Cell.Edge(c21,c22));
        c22.edges.add(new Cell.Edge(c10,c22));

        c20.edges.add(new Cell.Edge(c10,c20));
        c20.edges.add(new Cell.Edge(c21,c20));
        c20.edges.add(new Cell.Edge(c12,c20));

        GraphBoard gg = new GraphBoard(3,3);
        gg.start = c00;
        return gg;
    }

}
