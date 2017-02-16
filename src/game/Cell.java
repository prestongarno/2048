package game;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by preston on 2/7/17.
 */
public class Cell implements Comparable<Cell> {
    public int row, column, value;

    public Cell()
    {
        this(0,0,0, (Cell) null);
    }

    public Cell (int r, int c, int v)
    {
        this(r,c,v, (Cell) null);
    }

    public Cell(Cell... edges){
        this(0,0,0, edges);
    }

    public Cell(int r, int c, int v, Cell... edges){
        row = r;
        column = c;
        value = v;
        this.edges = new LinkedList<>();
        if(edges != null){
            for(Cell cell : edges){
                this.edges.addFirst(cell);
            }
        }
    }

    /* must override equals to ensure List::contains() works
     * correctly
     */
    @Override
    public int compareTo (@NotNull Cell other) {
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

        return row == cell.row && column == cell.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "Cell [" + row + "," + column + "]";
    }

    public static final int MAX_NUM_EDGES = 8;

    private LinkedList<Cell> edges;

    /**
     * adds an edge to this cell
     * @param edge to add
     */
    public void addEdge(Cell edge){
        this.edges.addFirst(edge);
        if(!hasEdge(Cell.this)){
            addEdge(Cell.this);
        }
    }

    private Cell Right, Left, Below, Above;
    private Cell TopRight, TopLeft, BottomRight, BottomLeft;

    public Cell[] getAll(){
        return new Cell[]{Right, Left, Below, Above, TopRight, TopLeft, BottomRight, BottomLeft};
    }

    @Nullable
    public Cell[] getNonNullEdges(){
        Cell[] all = getAll();

        int firstNull = 0;
        for (int i = 0; i < all.length; i++) {
            if(all[i] != null && all[firstNull] != all[i]){
                all[firstNull] = all[i];
                firstNull++;
            }
        }
        return (firstNull > 0) ? Arrays.copyOfRange(all, 0, firstNull) : null;
    }

    public void clearAllEdges(){
        Cell[] nn = getNonNullEdges();

        if(nn != null){
            for(Cell c : nn){
                c.remove(Cell.this);
                c = null;
            }
        }
    }

    /**
     * Removes the UNDIRECTIONAL relationship
     * between these two cells, if it exists
     * @param c the cell to remove
     * @return true if Cell c was an edge on this cell
     */
    public boolean remove(Cell c){
        Cell[] nn = getNonNullEdges();
        if(nn == null)
            return false;

        for(Cell edge : nn){
            if(equals(c)) {
                edge = null;
                c.remove(Cell.this);
                return true;
            }
        }
        return false;
    }

    public void forceClearAllEdges(){

    }

    private boolean hasEdge(Cell cell) {
        return (this.edges.contains(cell));
    }

    // Helper methods for the GraphBoard
    /**
     * Compares the distance to 0,0 (origin) of the board
     * @param other
     * @return
     */
    public boolean isCloserToOrigin(Cell other)
    {
        int thisTotal = this.row + this.column;
        int thatTotal = other.row + other.column;

        if (thisTotal == thatTotal) {
            return this.row < other.row;
        } else return thisTotal < thatTotal;
    }

    public boolean hasAdjacents(){
        return !(Right == null && Left == null && Below == null && Above == null
                && TopLeft == null && TopRight == null && BottomLeft == null && BottomRight == null);
    }

    public Cell getClosestEdgeTo(int x, int y) {
        if(!hasAdjacents()){
            return null;
        }

        Cell currentClosest;
        currentClosest = Right;
        for(Cell c : getAll()){
            if(c.distanceTo(x,y) < currentClosest.distanceTo(x,y)){
                currentClosest = c;
            }
        }
        return currentClosest;
    }

    /**
     * Distance to a point specified by parameters
     * @param row value of row
     * @param column value of column
     * @return the distance to the point,
     */
    public int distanceTo(int row, int column) {
        return Math.abs(this.row - row) + Math.abs(this.column - column);
    }
    private int distanceTo(Cell cell) {
        return Math.abs(this.row - cell.row) + Math.abs(this.column - cell.column);
    }





    /*********************************************************************************
    /*********************************************************************************
    /*********************************************************************************
    /*********************************************************************************
    /*********************************************************************************
    /*********************************************************************************
    /*********************************************************************************
     * DO NOT USE! Overly complicated double linked list code
     */
    public Cell cRight, cLeft, cBelow, cAbove;
    public Cell cTopRight, cTopLeft, cBottomRight, cBottomLeft;

    public Cell getcRight() {
        return cRight;
    }

    public void setcRight(Cell cRight) {
        this.cRight = cRight;
    }

    public Cell getcLeft() {
        return cLeft;
    }

    public void setcLeft(Cell cLeft) {
        this.cLeft = cLeft;
    }

    public Cell getcBelow() {
        return cBelow;
    }

    public void setcBelow(Cell cBelow) {
        this.cBelow = cBelow;
    }

    public Cell getcAbove() {
        return cAbove;
    }

    public void setcAbove(Cell cAbove) {
        this.cAbove = cAbove;
    }
    //===========================================================================================
}

