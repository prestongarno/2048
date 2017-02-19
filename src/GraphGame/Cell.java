package GraphGame;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Created by preston on 2/7/17.
 */
public class Cell implements Comparable<Cell> {

    //============================================================//
    /**===================/**the edges/===========================*/
    public final List<Edge> edges;
    //============================================================//

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

    /**
     * Adds the edge to the current cell
     *
     * @return the previous edge that was here, or null
     * if the current one is closer to this cell/no cell existed here before
     */
    public Cell addEdge(Cell c) {
        Direction d = Edge.isTo(this, c);
        Edge currentEdge = this.getEdge(d);
        Cell curr = (currentEdge == null) ? null : currentEdge.get();
        if (Cell.getCloserTo(curr, c, this) == c) {
            edges.remove(currentEdge);
            edges.add(new Edge(c, this));
            return curr;
        } else {
            return null;
        }
    }

    /**
     * Removes an edge by Cell reference
     *
     * @param c the cell to remove
     * @return the Cell that was removed
     */
    @Nullable
    public Edge removeEdge(@NotNull Cell c) {
        if (!this.hasEdge(c)) {
            throw new IllegalArgumentException("No such edge!");
        }
        for (Edge e : edges) {
            if (e.cell.equals(c)) {
                edges.remove(e);
                return e;
            }
        }
        return null;
    }

    /**
     * @return true if this cell has adjacents
     */
    public boolean hasAdjacents() {
        return edges == null;
    }

    /**
     * Get the cell that is closest to this cell in a direction
     *
     * @param direction the direction of the adgacent cell to get
     * @return the cell or null if it doesn't reference a cell in that direction
     */
    @Nullable
    public Cell get(Direction direction) {
        Edge edgey = this.getEdge(direction);
        return (edgey != null) ? edgey.cell : null;
    }

    /**
     * @param c cell to check for
     * @return true if param Cell is adjacent to this one
     */
    public boolean hasEdge(Cell c) {
        for (Edge e : edges) {
            if (e != null && e.cell.equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find out if a cell exists in a direction
     *
     * @param direction the direction to check
     * @return true if it exists, false otherwise
     */
    public boolean hasEdge(Direction direction) {
        for (Edge e : edges) {
            if (e != null && e.dir == direction) {
                return true;
            }
        }
        return false;
    }

    /**
     * private helper method
     *
     * @param e edge to check for
     * @return true if exists, false otherwise
     */
    private boolean hasEdge(Edge e) {
        return edges.contains(e);
    }

    /**
     * @param direction a direction
     * @return the edge that lies in this direction, or null
     */
    @Nullable
    private Edge getEdge(Direction direction) {
        for (Edge e : edges) {
            if (e != null && e.dir == direction) {
                return e;
            }
        }
        return null;
    }

    /**
     * @param d a direction
     * @return the edge that lies in this direction, or null
     */
    public Cell getEdgeCell(Direction d) {
        Edge e = this.getEdge(d);
        return (e != null) ? e.get() : null;
    }

    /**
     * Find out where the cell stands in relation to another cell
     *
     * @param other the anchor cell
     * @return the direction this cell is to the parameter cell
     */
    public Direction isTo(Cell other) {
        return Edge.isTo(other, this);
    }

    /**
     * Get all edges that exist in multiple specified directions
     *
     * @param constraints the direction of edges to get
     * @return All edges that exist in parameter directions
     */
    public Cell[] getEdges(@Nullable Direction... constraints) {
        if (this.edges == null || this.edges.isEmpty()) {
            return new Cell[0];
        } else {
            if (constraints == null || constraints.length == 0) {
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
            return (amountCounter + 1 == constraints.length) ? cells : Arrays.copyOf(cells, amountCounter);
        }
    }

    /**
     * Distance to a point
     *
     * @param row    value of row
     * @param column value of column
     * @return the distance to the point,
     */
    public int distanceTo(int row, int column) {
        return Math.abs(this.row - row) + Math.abs(this.column - column);
    }

    /**
     * Distance to another cell
     *
     * @param cell other cell
     * @return int > 0
     */
    public int distanceTo(Cell cell) {
        return Math.abs(this.row - cell.row) + Math.abs(this.column - cell.column);
    }

    /**
     * Compares the distance to 0,0 (origin) of the board
     *
     * @param other cell to compare
     * @return true if this Cell is closer to the origin
     */
    public boolean closerToOrigin(Cell other) {
        if (other == null) {
            return true;
        }
        int thisTotal = this.row + this.column;
        int thatTotal = other.row + other.column;

        if (thisTotal == thatTotal) {
            return this.row < other.row;
        } else return thisTotal < thatTotal;
    }

    /**
     * @param edges array of edges
     * @return the closest edge
     */
    public static Edge getClosest(Edge... edges) {
        Edge closest;
        closest = edges[0];
        for (Edge e : edges) {
            if (closest == null | e == null)
                closest = (closest == null) ? e : closest;
            else closest = (closest.distanceToParent < e.distanceToParent) ? closest : e;
        }
        return closest;
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
        StringBuilder sb = new StringBuilder();
        sb.append("C[");
        sb.append(row);
        sb.append(",");
        sb.append(column);
        sb.append("]");
        if(this.edges.size() > 0)
        {
            sb.append(" a{");
            for(Edge e : this.edges){
                sb.append("| " + e.cell.row + "," + e.cell.column + " |");
            }
            sb.append("} ");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = edges != null ? edges.hashCode() : 0;
        result = 31 * result + row;
        result = 31 * result + column;
        result = 31 * result + value;
        return result;
    }

    public static Cell getCloserTo(Cell c1, Cell c2, Cell target) {
        if (c1 == null | c2 == null)
            return (c1 == null) ? c2 : c1;
        return (c1.distanceTo(target) < c2.distanceTo(target)) ? c1 : c2;
    }

    public static final class Edge {

        /**
         * The direction that this edge is in
         */
        public final Direction dir;
        /**
         * The cell that exists at this edge
         */
        private final Cell cell;
        /**
         *  to the parent
         */
        private final Cell parent;
        /**
         * Distance from the parent that this cell is
         */
        public final int distanceToParent;

        /**
         * Edge to reference other cells
         */
        public Edge(@NotNull Cell edge, @NotNull Cell parent) {
            this.distanceToParent = edge.distanceTo(parent.row, parent.column);
            this.cell = edge;
            this.parent = parent;
            dir = isTo(parent, edge);
        }

        /**
         * @return the Cell at this edge
         */
        public Cell get() {
            return this.cell;
        }

        /**
         * @return the parent of this cell
         */
        @Nullable
        public Cell getParent() {
            return this.parent;
        }

        /**
         * @param parent the original cell
         * @param other  the other cell
         * @return the Direction that other is from parent
         */
        public static Direction isTo(Cell parent, Cell other) {
            if (other.row > parent.row && other.column > parent.column) {
                return Direction.BTM_RIGHT;
            } else if (other.row > parent.row && other.column < parent.column) {
                return Direction.BTM_LEFT;
            } else if (other.row < parent.row && other.column < parent.column) {
                return Direction.TOP_LEFT;
            } else if (other.row < parent.row && other.column > parent.column) {
                return Direction.TOP_RIGHT;
            } else if (other.row > parent.row) {
                return Direction.BELOW;
            } else if (other.row < parent.row) {
                return Direction.ABOVE;
            } else if (other.column < parent.column) {
                return Direction.LEFT;
            } else {
                return Direction.RIGHT;
            }
        }
    }
}

