package GraphGame;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import static GraphGame.Direction.*;

/**
 * Created by preston on 2/7/17.
 */
public class Cell implements Comparable<Cell> {

    //============================================================//
    /**
     * ===================/**the EDGES/===========================
     */
    public ConcurrentHashMap<Direction, Cell> EDGES;
    //============================================================//

    public int row, column, value;

    /**
     * Integer can be used to track amount of
     * steps a cell takes on it's way to insert/delete/slide
     */
    public int pathCounter;

    public Cell() {
        this(0, 0, 0);
    }

    public Cell(int r, int c, int v) {
        row = r;
        column = c;
        value = v;
        this.EDGES = new ConcurrentHashMap<>();
    }

    /**
     * Adds the edge to the current cell
     *
     * @return the parameter cell if:
     *                  - it currently has a closer edge
     *                  - it has an edge that .equals() parameter
     * or the previous edge that was replaced by this call
     */
    @NotNull
    public Cell addEdge(Cell c) {
        Direction d = Direction.isTo(this, c);
        Cell currentEdge = EDGES.get(d);
        if(currentEdge == null){
            EDGES.put(d, c);
            currentEdge = c;
        } else if (Cell.getCloserTo(currentEdge, c, this) == c) {
            EDGES.replace(Direction.isTo(this, c), c);
            currentEdge = c;
        }
        return currentEdge;
    }

    /**
     * Removes an edge by Cell reference
     *
     * @param c the cell to remove
     * @return the Cell that was removed
     */
    public void removeEdge(@NotNull Cell c) {
        for (Cell e : EDGES.values()) {
            if (e == c) {
                EDGES.values().remove(e);
            }
        }
    }

    /**
     * @return true if this cell has adjacents
     */
    public boolean hasAdjacents() {
        return EDGES == null;
    }

    /**
     * Get the cell that is closest to this cell in a direction
     *
     * @param direction the direction of the adgacent cell to get
     * @return the cell or null if it doesn't reference a cell in that direction
     */
    @Nullable
    public Cell get(Direction direction) {
        return this.EDGES.get(direction);
    }

    /**
     * @param c cell to check for
     * @return true if param Cell is adjacent to this one
     */
    public boolean hasEdge(Cell c) {
        Direction d = Direction.isTo(this, c);
        return this.EDGES.get(d) != null;
    }

    /**
     * Find out if a cell exists in a direction
     *
     * @param direction the direction to check
     * @return true if it exists, false otherwise
     */
    public boolean hasEdge(Direction direction) {
        return this.EDGES.get(direction) != null;
    }

    /**
     * Find out where the cell stands in relation to another cell
     *
     * @param other the anchor cell
     * @return the direction this cell is to the parameter cell,
     *              returns NULL if cell's row & column equal this one's
     */
    @Nullable
    public Direction isTo(Cell other) {
        return (other.equals(this)) ? null : Direction.isTo(other, this);
    }

    /**
     * Get all EDGES that exist in multiple specified directions
     *
     * @param constraints the direction of EDGES to get
     * @return All EDGES that exist in parameter directions
     */
    public Cell[] getEdges(@Nullable Direction... constraints) {
        if (this.EDGES == null || this.EDGES.isEmpty()) {
            return new Cell[0];
        } else {
            if (constraints == null || constraints.length == 0) {
                return this.EDGES.values().toArray(new Cell[EDGES.values().size()]);
            }

            Cell[] cells = new Cell[constraints.length];
            int amountCounter = 0;
            for (Direction constraint : constraints) {
                Cell edgee = this.get(constraint);
                if (edgee != null) {
                    cells[amountCounter++] = edgee;
                }
            }
            return (amountCounter == constraints.length) ? cells : Arrays.copyOf(cells, amountCounter);
        }
    }

    /**
     * Gets an edge cell closer than this cell
     *
     * @param target the cell to compare to
     * @return another cell that's closer to target than this, or this
     */
    public Cell getEdgeTo(Cell target) {
        Cell closest = this;
        for (Cell c : EDGES.values()) {
            closest = getCloserTo(c, closest, target);
        }
        return closest;
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
     * @param edges array of EDGES
     * @return the closest edge
     */
    public static Cell getClosest(Cell... edges) {
        Cell closest;
        closest = edges[0];
        for (Cell c : edges) {
            if (closest == null | c == null)
                closest = (closest == null) ? c : closest;
            else closest = (closest.distanceTo(c) < c.distanceTo(closest)) ? closest : c;
        }
        return closest;
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
        sb.append("Cell[ ");
        sb.append(row);
        sb.append(" ");
        sb.append(column);
        sb.append(" ]");
        if (this.EDGES.size() > 0) {
            if(this.row < 9 && this.column < 9)
                sb.append("\t");
            sb.append("\tA=");
            sb.append(EDGES.size());
            sb.append("{");
            for (Cell c : EDGES.values()) {
                sb.append(c.toShortString());
            }
            sb.append("}");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = EDGES != null ? EDGES.hashCode() : 0;
        result = 31 * result + row;
        result = 31 * result + column;
        result = 31 * result + value;
        return result;
    }

    public static Cell getCloserTo(Cell c1, Cell c2, Cell target) {
        if (c1 == null | c2 == null)
            return (c1 == null) ? c2 : c1;
        return (c1.distanceTo(target) <= c2.distanceTo(target)) ? c1 : c2;
    }

    /**
     * @return a new cell object with only the same row, column, and value
     */
    @Contract("_ -> !null")
    public static Cell createDummy(Cell cell) {
        return (cell == null) ? null : new Cell(cell.row, cell.column, cell.value);
    }

    /**
     * Create a deep copy of this cell
     * Includes copies of all of this cell's EDGES,
     * Does NOT extend beyond that
     * (used for unit tests before graph complete)
     *
     * @return a new Cell with same
     * number of EDGES and edge values as this
     */
    @Contract("_ -> !null")
    public static Cell createDummyWithEdges(Cell c) {
        Cell copy = new Cell(c.row, c.column, c.value);
        ArrayList<Cell> e = new ArrayList<>(8);
        for (Cell ccells : c.EDGES.values()) {
            copy.addEdge(createDummy(ccells));
        }
        return copy;
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

    /**
     * notifies only this cell's adjacent cells to
     * remove their pointer to this cell
     */
    public void unlink() {
        for(Cell e : EDGES.values()){
            e.removeEdge(this);
        }
    }

    public Cell getClosestEdge() {
        Cell c = null;
        for (Cell curr : this.getEdges()) {
            c = (Cell.getCloserTo(c, curr, this));
        }
        return c;
    }

    /**
     * @param target the cell relative to constrain search
     * @return this, or an edge to this cell that is closer
     * to the target but also lies in the same direction to target
     */
    public Cell getCloserConstrained(Cell target)
    {
        Cell closest = this;
        Direction toTarget = this.isTo(target);
        for(Cell c : EDGES.values()){
            if(c.isTo(target) == toTarget && Cell.getCloserTo(closest, c, target) == c){
                closest = c;
            }
        }
        return closest;
    }

    public void setCoord(Direction d, int value)
    {
        if(d == ABOVE || d == BELOW){
            this.row = value;
        } else if(d == LEFT || d == RIGHT){
            this.column = value;
        }
    }

    public void update(){
        Collection<Cell> cells = this.EDGES.values();
        for(Cell c : cells){
            this.EDGES = updateAdjacents(this, c, this.EDGES);
        }
    }

    public static ConcurrentHashMap<Direction, Cell> updateAdjacents(
            Cell orig,
            Cell adjacent,
            ConcurrentHashMap<Direction, Cell> map){

        if(adjacent == null){
            return map;
        }
        //System.out.println("Adj search : " + orig + " curr = " + adjacent);
        //Get the current closest value
        Direction to = adjacent.isTo(orig);
        Cell curr = map.get(to);
        if(curr == null){
            map.put(to, adjacent);
        }

        // enters block if the current closest cell
        // is further from orig than the adjacent cell
        if(curr != adjacent && Cell.getCloserTo(curr, adjacent, orig) == adjacent){

            map.replace(to, adjacent);
            //add new cell to the cell's EDGES
            Cell toUpdate = adjacent.addEdge(orig);

            //will recurse  if this cell has an edge cell that is closer to
            // the added cell, which also lies in the same direction relative to added cell
            Cell[] edgeCells = adjacent.getEdges();
            for (int i = 0; i <edgeCells.length; i++) {

                Cell cConst = edgeCells[i].getCloserConstrained(orig);
                if(cConst!=orig && cConst != adjacent) {
                    return updateAdjacents(orig, cConst, map);
                }
            }
        }
        return map;
    }

    public String toShortString()
    {
        return " [" + row + "," + column + "] ";
    }
}

