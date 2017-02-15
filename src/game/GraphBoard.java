package game;

/**
 * Created by preston on 2/15/17.
 */
public class GraphBoard {

    int numRows;
    int numColumns;

    private Cell start;
    private Cell end;

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

    public Cell getEnd() {
        return end;
    }

    private void setStart(Cell start) {
        this.start = start;
    }

    private void setEnd(Cell end) {
        this.end = end;
    }

    public void add(Cell cell){
        //if this is empty or param is closer than current start, set as start
        if(this.start == null || cell.isCloserToOrigin(this.start)){
            setStart(cell);
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
    public Cell.EdgeHolder walkTo(int x, int y, Cell current)
    {
        // Base case -> reached the end of the graph or the current cell's row & column are greater than the target?
        if(current == this.end || !current.isCloserToOrigin(new Cell(x,y,0))){
            return current.edge;
        } else {
            return this.walkTo(x,y,current.getClosestEdgeTo(x,y));
        }
    }

    public void remove(Cell cell){

    }

    private void calculateDistance(Cell from, Cell to) {

    }
}
