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

    public static Cell getDummyC11WithEdges(){
        Cell copyC11 = Cell.createDummy(c11);
        for (Cell c : getAll()) {
            if(!copyC11.equals(c))
                copyC11.edges.add(new Cell.Edge(Cell.createDummy(c),copyC11));
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
     * @return A new GraphBoard instance, with TestBoard.c00 as the start
     */
    public static GraphBoard createSolid3x3Board(){

        GraphBoard gb = new GraphBoard(10,10);

        resetStaticCells();

        TestBoard.c00.edges.add(new Cell.Edge(TestBoard.c01,TestBoard.c00));
        TestBoard.c00.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c00));
        TestBoard.c00.edges.add(new Cell.Edge(TestBoard.c11,TestBoard.c00));

        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c00,TestBoard.c01));
        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c01));
        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c11,TestBoard.c01));
        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c02,TestBoard.c01));
        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c01));

        TestBoard.c02.edges.add(new Cell.Edge(TestBoard.c01,TestBoard.c02));
        TestBoard.c02.edges.add(new Cell.Edge(TestBoard.c11,TestBoard.c02));
        TestBoard.c02.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c02));

        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c00,TestBoard.c10));
        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c01,TestBoard.c10));
        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c11,TestBoard.c10));
        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c10));
        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c20,TestBoard.c10));

        TestBoard.c11.edges.add(new Cell.Edge(TestBoard.c00,TestBoard.c11));
        TestBoard.c11.edges.add(new Cell.Edge(TestBoard.c01,TestBoard.c11));
        TestBoard.c11.edges.add(new Cell.Edge(TestBoard.c02,TestBoard.c11));
        TestBoard.c11.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c11));
        TestBoard.c11.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c11));
        TestBoard.c11.edges.add(new Cell.Edge(TestBoard.c20,TestBoard.c11));
        TestBoard.c11.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c11));
        TestBoard.c11.edges.add(new Cell.Edge(TestBoard.c22,TestBoard.c11));

        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c01,TestBoard.c12));
        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c02,TestBoard.c12));
        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c11,TestBoard.c12));
        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c12));
        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c22,TestBoard.c12));

        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c21));
        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c11,TestBoard.c21));
        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c21));
        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c20,TestBoard.c21));
        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c22,TestBoard.c21));

        TestBoard.c22.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c22));
        TestBoard.c22.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c22));
        TestBoard.c22.edges.add(new Cell.Edge(TestBoard.c11,TestBoard.c22));

        TestBoard.c20.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c20));
        TestBoard.c20.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c20));
        TestBoard.c20.edges.add(new Cell.Edge(TestBoard.c11,TestBoard.c20));

        gb.start = TestBoard.c00;
        return gb;
    }

    public static GraphBoard create3x3BoardWithoutC11(){

        GraphBoard gb = new GraphBoard(10,10);

        resetStaticCells();

        TestBoard.c00.edges.add(new Cell.Edge( TestBoard.c01, TestBoard.c00));
        TestBoard.c00.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c00));
        TestBoard.c00.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c00));

        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c00,TestBoard.c01));
        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c01));
        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c01));
        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c02,TestBoard.c01));
        TestBoard.c01.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c01));

        TestBoard.c02.edges.add(new Cell.Edge(TestBoard.c01,TestBoard.c02));
        TestBoard.c02.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c02));
        TestBoard.c02.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c02));

        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c00,TestBoard.c10));
        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c01,TestBoard.c10));
        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c10));
        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c10));
        TestBoard.c10.edges.add(new Cell.Edge(TestBoard.c20,TestBoard.c10));

        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c01,TestBoard.c12));
        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c02,TestBoard.c12));
        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c12));
        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c12));
        TestBoard.c12.edges.add(new Cell.Edge(TestBoard.c22,TestBoard.c12));

        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c21));
        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c21));
        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c21));
        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c20,TestBoard.c21));
        TestBoard.c21.edges.add(new Cell.Edge(TestBoard.c22,TestBoard.c21));

        TestBoard.c22.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c22));
        TestBoard.c22.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c22));
        TestBoard.c22.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c22));

        TestBoard.c20.edges.add(new Cell.Edge(TestBoard.c10,TestBoard.c20));
        TestBoard.c20.edges.add(new Cell.Edge(TestBoard.c21,TestBoard.c20));
        TestBoard.c20.edges.add(new Cell.Edge(TestBoard.c12,TestBoard.c20));

        gb.start = TestBoard.c00;
        return gb;
    }

}
