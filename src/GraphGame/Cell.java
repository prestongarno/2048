package GraphGame;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by preston on 2/7/17.
 */
public class Cell implements Comparable<Cell> {
    public int row, column, value;

    public Cell() {
        this(0, 0, 0, (Edge) null);
    }

    public Cell(int r, int c, int v) {
        this(r, c, v, (Edge) null);
    }

    public Cell(Edge... edges) {
        this(0, 0, 0, edges);
    }

    public Cell(int r, int c, int v, Edge... edges) {
        row = r;
        column = c;
        value = v;
        this.edges = new ArrayList<>(0);
        if (edges != null) {
            for (Edge e : edges) {
                this.edges.add(e);
            }
        }
    }

    /* must override equals to ensure List::contains() works
     * correctly
     */
    @Override
    public int compareTo(@NotNull Cell other) {
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
    public String toString() {
        return "Cell(v" + this.value + ") [" + row + "," + column + "]";
    }

    //============================================================//
    //===================the almighty edges=======================//
    public final ArrayList<Edge> edges;

    //============================================================//
    /**
     * Adds the edge to the current cell
     * @return the previous edge that was here, or null
     */
    public Edge addEdge(Cell c)
    {
        if(this.hasEdge(c))
            throw new IllegalArgumentException("Edge already exists!");
        Edge oldEdge = null;
        Edge.Direction direction = Edge.calcRelationshipToParent(c, this);
        if(this.hasEdge(direction)){
            oldEdge = this.getEdge(direction);
        }
        this.edges.add(new Edge(c, this));
        return oldEdge;
    }

    public Edge removeEdge(Cell c)
    {
        if(!this.hasEdge(c)){
            throw new IllegalArgumentException("No such edge!");
        }
        for(Edge e : edges){
            if(e.cell.equals(c)){
                edges.remove(e);
                return e;
            }
        }

        return null;
    }

    public Cell get(Edge.Direction direction){
        Edge edgey = this.getEdge(direction);
        return (edgey != null) ? edgey.cell : null;
    }

    public boolean hasEdge(Cell c){
        for(Edge e : edges){
            if(e!= null && e.cell.equals(c)){
                return true;
            }
        }
        return false;
    }

    public boolean hasEdge(Edge.Direction direction){
        for(Edge e : edges){
            if(e != null && e.dir == direction){
                return true;
            }
        }
        return false;
    }

    private boolean hasEdge(Edge e){
        return edges.contains(e);
    }

    public Edge getEdge(Edge.Direction direction){
        for(Edge e : edges){
            if(e != null && e.dir == direction){
                return e;
            }
        }
        return null;
    }

    //all-star method
    public Edge.Direction isTo(Cell other)
    {
        return Edge.calcRelationshipToParent(this, other);
    }

    public Cell[] getEdges(Edge.Direction... constraints){
        List<Edge> ll = new LinkedList<>();
        for(Edge.Direction d : constraints){
            Edge edgee = this.getEdge(d);
            if(edgee != null){
                ll.add(this.getEdge(d));
            }
        }
        Cell[] c = new Cell[ll.size()];
        int count = 0;
        for(Edge e : ll){
            c[count] = e.cell;
            count++;
        }
        return c;
    }

    /**
     * Distance to a point specified by parameters
     *
     * @param row    value of row
     * @param column value of column
     * @return the distance to the point,
     */
    public int distanceTo(int row, int column) {
        return Math.abs(this.row - row) + Math.abs(this.column - column);
    }

    public int distanceTo(Cell cell) {
        return Math.abs(this.row - cell.row) + Math.abs(this.column - cell.column);
    }

    /* Compares the distance to 0,0 (origin) of the board
        * @param other
        * @return
    */
    public boolean closerToOrigin(Cell other) {
        int thisTotal = this.row + this.column;
        int thatTotal = other.row + other.column;

        if (thisTotal == thatTotal) {
            return this.row < other.row;
        } else return thisTotal < thatTotal;
    }
}

