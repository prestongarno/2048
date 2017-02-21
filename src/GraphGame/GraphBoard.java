package GraphGame;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by preston on 2/15/17.
 */
public class GraphBoard {

    private int numRows;
    private int numColumns;
    private int size;

    public Cell start;

    public GraphBoard(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
    }

    // TODO: 2/21/17 make a method doStuffToCells() that takes a lambda as param to easily do whatever to the entire board
    /**
     * Useless method I might need later
     *
     * @param original the cell that was inserted/changed
     * @param toUpdate this cell must contain original cell as an edge
     */
    public static void update(Cell original, Cell toUpdate) {
        /*if (toUpdate == null)
            return;
        Cell adjacent = toUpdate.get(original.isTo(toUpdate));
        if (original != toUpdate && Cell.getCloserTo(original, adjacent, toUpdate) == original
                && !toUpdate.hasEdge(original)){
            original.addEdge(toUpdate);
            update(original, toUpdate.addEdge(original));
        }*/
    }

    /**
     * Method that locates adjacents
     * of a cell that is new to the area
     *
     * Also points the adjacent cells at the target cell
     * And updates the other Cells that got upset by that move
     * Until the board is updated technically
     *
     * @param orig cell to be inserted
     * @param adjacent the current closest known adjacent
     * @return a list of the closest cells to be inserted
     */
    public HashMap<Direction, Cell> getAdjacentsForNewCell(
            Cell orig,
            Cell adjacent,
            HashMap<Direction, Cell> map){

        if(adjacent == null){
            return map;
        }
        //Get the current closest value
        Direction to = adjacent.isTo(orig);
        Cell curr = map.get(to);
        if(curr == null){
            map.put(to, adjacent);
        }

        if(curr != adjacent && Cell.getCloserTo(curr, adjacent, orig) == adjacent){

            map.replace(to, adjacent);
            Cell toUpdate = adjacent.addEdge(orig);

            //if(toUpdate !=)
            //update(adjacent, toUpdate);
            Cell[] edgeCells = adjacent.getEdges();
            for (int i = 0; i <edgeCells.length; i++) {
                //get the direction this cell stands to the cell being inserted
                //Direction dd = edgeCells[i].isTo(orig);
                Cell cConst = edgeCells[i].getCloserConstrained(orig);
                if(cConst!=orig) {
                    map = getAdjacentsForNewCell(orig, cConst, map);
                }
            }
        }
        return map;
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

    public void setStart(Cell c)
    {
        if(start == null || c.closerToOrigin(start)){
            this.start = c;
        } else {
            throw new IllegalArgumentException("Not closer than current start!");
        }
    }

    /**
     * <pre>
     *  Better walk function. Reasoning:<br>
     *  1) A cell's edges do not always have this cell as an edge in the opposite direction<br>
     *  2) However, it is impossible for a cell to not have an edge that
     *      does NOT contain the cell in its list of edges<br>
     *  3) this function will locate the cell that will:<br>
     *          a) point at the target<br>
     *          b) the target points at the cell<br>
     *              </pre>
     * N.B. this method will return the cell at the target location, but it will be another REFERENCE value<br>
     * to the one that exists there already if one existed before
     * @param target the target cell
     * @param current the start cell
     * @return the cell at the location
     */
    public Cell directWalk(Cell target, Cell current) {
        //System.out.println(current + " --> " + target);
        if (current.isTo(target) == null){
            return current;
        } else {
            //Cell next = current.addEdge(target);
            target.addEdge(current);
            return directWalk(target, current.addEdge(target));
        }
    }

    /**
     * Insert a cell into the board
     * @param cell the cell to insert
     * @return The parameter cell that was inserted, or null if the spot is occupied
     */
    public Cell addCell(Cell cell) {
        if(cell.row < 0 | cell.column < 0 | cell.row> numRows | cell.column> numColumns)
            throw new IllegalArgumentException("Illegal location!");

        if(start == null){
            this.start = cell;
            return start;
        }

        Cell current = directWalk(cell, this.start);

        if(cell.closerToOrigin(this.start)){
            this.start = cell;
        }

        if(current != cell) {
            return null;
            //means it's an occupied
            //cell = current; //meaningless hack to get around this for now
        }

        HashMap<Direction, Cell> nap = new HashMap<>(8);
        nap = getAdjacentsForNewCell(cell, cell.getClosestEdge(), nap);
        Iterator<Cell> ii = nap.values().iterator();
        while(ii.hasNext()){
            cell.addEdge(ii.next());
            ii.remove();
        }
        return cell;
    }

    public Cell addCell(int x, int y){
        if(x < 0 | y < 0 | x > numRows | y > numColumns)
            throw new IllegalArgumentException("Illegal location!");

        Cell cell = new Cell(x,y,2);

        if(start == null){
            this.start = cell;
            return start;
        }

        return this.addCell(cell);
    }

    /**
     * Get a cell at an x,y point
     * @param x value
     * @param y value
     */
    @Nullable
    public Cell getCell(int x, int y)
    {
        Cell dummyCell = new Cell(x,y,-5000);
        Cell currentCell = directWalk(dummyCell, getStart());
        //remove cells that point to dummy from the walk
        dummyCell.unlink();
        if(dummyCell == currentCell){
            return null;
        }
        return currentCell;
    }

    /**
     * Print the game board
     * @param current the cell to highlight
     */
    public void printGraphicalBoard(Cell current) {
        if (current == null){
            System.out.println("<>--------------------");
            return;
        }
        System.out.print("[ ");
        Cell c = current;
        int blankTracker = -1;

        while (c != null){
            while (++blankTracker < c.column){
                System.out.print("-");
            }
            blankTracker = c.column;
            System.out.print(c.value);
            c = c.get(Direction.RIGHT);
        }
        while (++blankTracker < this.getNumColumns()){
            System.out.print("-");
        }
        System.out.print("]\n");
        Cell[] bigC = current.getEdges(Direction.BTM_LEFT,
                Direction.BELOW, Direction.BTM_RIGHT);
        if(bigC.length == 0 && current.hasEdge(Direction.RIGHT)){
            while(current.hasEdge(Direction.RIGHT) && bigC.length == 0){
                current = current.getEdgeCell(Direction.RIGHT);
                bigC = current.getEdges(Direction.BTM_LEFT,
                        Direction.BELOW, Direction.BTM_RIGHT);
            }
        }
        if(bigC.length > 0)
            printGraphicalBoard(bigC[0]);
    }
}
