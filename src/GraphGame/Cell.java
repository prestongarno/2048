package GraphGame;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.*;

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
        this.edges = new ArrayList<>(8);
        if (edges[0] != null) {
            Collections.addAll(this.edges, edges);
        }
    }

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
    //===================/**the edges*/================================//
    private final List<Edge> edges;
    //============================================================//
    public List<Edge> methodThatShouldOnlyBeCalledWhenUsingUnitTestsGetEdges(){return this.edges;}

    /**
     * Adds the edge to the current cell
     * @return the previous edge that was here, or the parameter Cell
     * if the current one is closer to this cell, or null
     */
    public Cell addEdge(Cell c)
    {
        Direction d = Edge.calculateRelationShip(this, c);
        Edge currentEdge = this.getEdge(d);
        Cell curr = (currentEdge == null) ? null : currentEdge.get();
        if(Cell.getCloserTo(curr, c, this) == c){
            edges.remove(currentEdge);
            edges.add(new Edge(c,this));
            return curr;
        } else {
            throw new IllegalArgumentException("Should'nt try to add an edge that shouldn't be added!!!");
        }
    }

    public static void updateAdjacents(Cell original, Cell toUpdate){
        if(toUpdate == null)
            return;
        Cell[] edges = toUpdate.getEdges();
        for(int i = 0; i < edges.length; i++){
            Cell cell = edges[i];
            Cell adjacent = cell.get(original.isTo(cell));
            if(original != cell && getCloserTo(original, adjacent, cell) == original && !cell.hasEdge(original)){
                original.addEdge(cell);
                updateAdjacents(original, cell.addEdge(original));
            }
        }
    }

    public Edge getCloserEdge(Edge e1, Edge e2){
        if(e1 == null | e2 == null)
            return (e1 == null) ? e2 : e1;
        return (e1.distanceToParent < e2.distanceToParent) ? e1 : e2;
    }

    public boolean hasAdjacents(){
        return edges.get(0) != null;
    }

    /**
     * Removes an edge by Cell reference
     * @param c the cell to remove
     * @return the Cell that was removed
     */
    @NotNull
    public Edge removeEdge(@NotNull Cell c)
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

    /**
     * Get the cell that is closest to this cell in a direction
     * @param direction the direction of the adgacent cell to get
     * @return the cell or null if it doesn't reference a cell in that direction
     */
    @Nullable
    public Cell get(Direction direction){
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

    /**
     * Find out if a cell exists in a direction
     * @param direction the direction to check
     * @return true if it exists, false otherwise
     */
    public boolean hasEdge(Direction direction){
        for(Edge e : edges){
            if(e != null && e.dir == direction){
                return true;
            }
        }
        return false;
    }

    /**
     * private helper method --> No other cell should directly access another Cell's edges ideally
     * @param e edge to check for
     * @return true if exists, false otherwise
     */
    private boolean hasEdge(Edge e){
        return edges.contains(e);
    }

    private Edge getEdge(Direction direction){
        for(Edge e : edges){
            if(e != null && e.dir == direction){
                return e;
            }
        }
        return null;
    }

    public Cell getEdgeCell(Direction d){
        Edge e = this.getEdge(d);
        return (e != null) ? e.get() : null;
    }

    /**
     * Find out where the cell stands in relation to another cell
     * @param other the anchor cell
     * @return the direction this cell is to the parameter cell
     */
    public Direction isTo(Cell other)
    {
        return Edge.calculateRelationShip(other, this);
    }

    /**
     * Get all edges that exist in multiple specified directions
     * @param constraints the direction of edges to get
     * @return All edges that exist in parameter directions
     */
    public Cell[] getEdges(@Nullable Direction... constraints){
        if(this.edges == null || this.edges.isEmpty()){
            return new Cell[0];
        } else {
            if(constraints == null || constraints.length == 0){
                constraints = Direction.values();
            }

            Cell[] cells = new Cell[constraints.length];
            int amountCounter = 0;
            for (Direction constraint : constraints) {
                Edge edgee = this.getEdge(constraint);
                if (edgee != null) {
                    cells[amountCounter++] = edgee.get();
                }
            }
            return (amountCounter+1 == constraints.length) ? cells : Arrays.copyOf(cells, amountCounter);
        }
    }

    public Cell[] getAllNonNullEdges(){
    Cell[] cArr = new Cell[this.edges.size()];
        for (int i = 0; i < this.edges.size(); i++) {
            Edge edge = this.edges.get(i);
            if(edge != null){
                cArr[i] = edge.get();
            } else {
                cArr[i] = null;
            }
        }
        return cArr;
    }

    /**
     * Distance to a point
     * @param row    value of row
     * @param column value of column
     * @return the distance to the point,
     */
    public int distanceTo(int row, int column) {
        return Math.abs(this.row - row) + Math.abs(this.column - column);
    }

    /**
     * Distance to another cell
     * @param cell other cell
     * @return int > 0
     */
    public int distanceTo(Cell cell) {
        return Math.abs(this.row - cell.row) + Math.abs(this.column - cell.column);
    }

    public static Cell getCloserTo(Cell c1, Cell c2, Cell target){
        if(c1 == null | c2 == null)
            return (c1 == null) ? c2 : c1;
        return (c1.distanceTo(target) < c2.distanceTo(target)) ? c1 : c2;
    }

    /**
     * Compares the distance to 0,0 (origin) of the board
     * @param other cell to compare
     * @return true if this Cell is closer to the origin
     */
    public boolean closerToOrigin(Cell other) {
        if(other == null){
            return true;
        }
        int thisTotal = this.row + this.column;
        int thatTotal = other.row + other.column;

        if (thisTotal == thatTotal) {
            return this.row < other.row;
        } else return thisTotal < thatTotal;
    }

    /**
     * Edge to reference other cells
     * NOTE -> parent reference is weak, manually remove bi-directional relationship from somewhere else
     */
    public static final class Edge {

        /**The direction that this edge is in*/
        public final Direction dir;
        /**The cell that exists at this edge*/
        private final Cell cell;
        /**Weak reference to the parent*/
        private final WeakReference<Cell> parent;
        /**Distance from the parent that this cell is*/
        // TODO: 2/17/17 when shifting cells, make this non-final so update distance can be called for updating adjacent
        public final int distanceToParent;

        public Edge(@NotNull Cell edge, @NotNull Cell parent){
            this.distanceToParent = edge.distanceTo(parent.row, parent.column);
            this.cell = edge;
            this.parent = new WeakReference<Cell>(parent);
            dir = calculateRelationShip(parent, edge);
        }

        /**
         * @return the Cell at this edge
         */
        public Cell get(){
            return this.cell;
        }

        /**
         * @return the parent of this cell
         */
        @Nullable
        public Cell getParent(){
            return this.parent.get();
        }

        /**
         * @param parent the original cell
         * @param other the other cell
         * @return the Direction that other is from parent
         */
        public static Direction calculateRelationShip(Cell parent, Cell other){
            if(other.row > parent.row && other.column > parent.column){return Direction.BTM_RIGHT;}
            else if(other.row > parent.row && other.column < parent.column){return Direction.BTM_LEFT;}
            else if(other.row < parent.row && other.column < parent.column){return Direction.TOP_LEFT;}
            else if(other.row < parent.row && other.column > parent.column){return Direction.TOP_RIGHT;}
            else if(other.row > parent.row){return Direction.BELOW;}
            else if(other.row < parent.row){return Direction.ABOVE;}
            else if(other.column < parent.column){return Direction.LEFT;}
            else {return Direction.RIGHT;}
        }
    }
}

