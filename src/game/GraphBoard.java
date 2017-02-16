package game;

import java.util.Iterator;

/**
 * Created by preston on 2/15/17.
 */
public class GraphBoard {

    private int numRows;
    private int numColumns;

    private Cell start;

    public GraphBoard(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public Cell getStart() {
        return start;
    }

    private void setStart(Cell start) {
        this.start = start;
    }

    public void add(Cell cell){
        //if this is empty or param is closer than current start, set as start
        if(this.start == null || cell.isCloserToOrigin(this.start)){
            setStart(cell);
            /*for(Cell c : cell.getNonNullEdges()){
                c = null;
            }*/
        } else {
            //get an adjacent cell to the one to be inserted
            Cell adjacent = this.findAdjacent(cell.row, cell.column, this.getStart());
            //update the graph by updating surrounding cell's adjacents
            this.updateEdgesOfGraph(cell, adjacent);
        }
    }

    /**
     * Recursively will walk through the game board and
     * eventually returns all of the "edge" cells (adjacents) at
     * the point defined by
     * @param x row value of point
     * @param y column value of the point
     * @return Adjacent cells to the given point
     */
    public Cell findAdjacent(int x, int y, Cell current)
    {
        boolean isEndOfGraph = true;
        Cell[] nonNullEdges = current.getNonNullEdges();

        if(nonNullEdges != null){
            for(Cell c : nonNullEdges){
                if(current.isCloserToOrigin(c)){
                    isEndOfGraph = false;
                    break;
                }
            }
        } else {
            return current;
        }

        // Base case -> reached the end of the graph or the current cell's row & column are greater than the target?
        if(isEndOfGraph || !current.isCloserToOrigin(new Cell(x,y,0))){
            return current;
        } else {
            return this.findAdjacent(x,y,current.getClosestEdgeTo(x,y));
        }
    }

    /**
     * Updates the edges of cells adjacent to the cell to be inserted
     * Uses the initial adjacent cell to find all other adjacent cells
     * to the one that is going to be inserted
     * @param insert
     * @param adjacent
     */
    public void updateEdgesOfGraph(Cell insert, Cell adjacent){
        //// TODO: 2/15/17 HINT --> look at deepening algorithm (prevent infinite loops),
        // depth first, and short cycles (graph theory) to do this
        /*if(insert.row == adjacent.row){

        } else if (insert.column == adjacent.column){

        } else if(adjacent.isAboveLeft(insert)){

        } else if(adjacent.isAboveRight(insert)){

        } else if(adjacent.isBelowLeft(insert)){

        }else if (adjacent.isBelowRight(insert)){

        } else {
            throw new IllegalArgumentException("parameter adjacent cell " +
                    "does not stand in any relation to the cell being inserted!");
        }*/


    }

    public void remove(Cell cell){

    }
}
