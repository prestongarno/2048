package game;

/**
 * Created by preston on 2/7/17.
 */
public class Cell implements Comparable<Cell> {
    public int row, column, value;

    public Cell()
    {
        this(0,0,0);
    }
    public Cell (int r, int c, int v)
    {
        row = r;
        column = c;
        value = v;
    }

    /* must override equals to ensure List::contains() works
     * correctly
     */
    @Override
    public int compareTo (Cell other) {
        if (this.row < other.row) return -1;
        if (this.row > other.row) return +1;

        /* break the tie using column */
        if (this.column < other.column) return -1;
        if (this.column > other.column) return +1;

        return this.value - other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (row != cell.row) return false;
        return column == cell.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "Cell " + row + ", " + column;
    }

    /*********************************************************************************
     * Overly complicated double linked list code
     */
    private Cell rowNext, rowPrevious, columnNext, columnPrevious;

    public Cell getRowNext() {
        return rowNext;
    }

    public void setRowNext(Cell rowNext) {
        this.rowNext = rowNext;
    }

    public Cell getRowPrevious() {
        return rowPrevious;
    }

    public void setRowPrevious(Cell rowPrevious) {
        this.rowPrevious = rowPrevious;
    }

    public Cell getColumnNext() {
        return columnNext;
    }

    public void setColumnNext(Cell columnNext) {
        this.columnNext = columnNext;
    }

    public Cell getColumnPrevious() {
        return columnPrevious;
    }

    public void setColumnPrevious(Cell columnPrevious) {
        this.columnPrevious = columnPrevious;
    }
}

