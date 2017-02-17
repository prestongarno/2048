package GraphGame;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
    public final ArrayList<Edge> edges;
    //============================================================//
    public ArrayList<Edge> methodThatShouldOnlyBeCalledWhenUsingUnitTestsGetEdges(){return this.edges;}
    /**
     * Adds the edge to the current cell
     * @return the previous edge that was here, or null
     */
    public Edge addEdge(Cell c)
    {
        if(this.hasEdge(c))
            throw new IllegalArgumentException("Edge already exists!");
        Edge oldEdge = null;
        Direction direction = Edge.calculateRelationShip(this, c);
        if(this.hasEdge(direction)){
            oldEdge = this.getEdge(direction);
        }
        this.edges.add(new Edge(c, this));
        return oldEdge;
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

    public Edge getEdge(Direction direction){
        for(Edge e : edges){
            if(e != null && e.dir == direction){
                return e;
            }
        }
        return null;
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
     * @return
     */
    public Cell[] getEdges(Direction... constraints){
        List<Edge> ll = new LinkedList<>();
        for(Direction d : constraints){
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

    /**
     * Compares the distance to 0,0 (origin) of the board
     * @param other cell to compare
     * @return true if this Cell is closer to the origin
     */
    public boolean closerToOrigin(Cell other) {
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
            this.calculateRelationShip();
            this.dir = calculateRelationShip();
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

        /** Calculates where this edge stands on the parent.get()
         * @return where this edge stands on the parent.get()
         */
        private Direction calculateRelationShip(){
            if(this.parent.get() != null) {
                if (cell.row > parent.get().row && cell.column > parent.get().column) {
                    return Direction.BTM_RIGHT;
                } else if (cell.row > parent.get().row && cell.column < parent.get().column) {
                    return Direction.BTM_LEFT;
                } else if (cell.row < parent.get().row && cell.column < parent.get().column) {
                    return Direction.TOP_LEFT;
                } else if (cell.row < parent.get().row && cell.column > parent.get().column) {
                    return Direction.TOP_RIGHT;
                } else if (cell.row > parent.get().row) {
                    return Direction.BELOW;
                } else if (cell.row < parent.get().row) {
                    return Direction.ABOVE;
                } else if (cell.column < parent.get().column) {
                    return Direction.LEFT;
                } else {
                    return Direction.RIGHT;
                }
            } else {
                return null;
            }
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

