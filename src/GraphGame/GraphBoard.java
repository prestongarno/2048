package GraphGame;

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

    /**
     * Magically updates the entire board
     *
     * @param original the cell that was inserted/changed
     * @param toUpdate this cell must contain original cell as an edge
     */
    public static void update(Cell original, Cell toUpdate) {
        if (toUpdate == null)
            return;
        Cell adjacent = toUpdate.get(original.isTo(toUpdate));
        if (original != toUpdate && Cell.getCloserTo(original, adjacent, toUpdate) == original
                && !toUpdate.hasEdge(original)){
            original.addEdge(toUpdate);
            update(original, toUpdate.addEdge(original));
        }
    }

    /**
     * Breadth - first search to locate adjacents
     * of a cell that needs to be inserted
     * @param orig cell to be inserted
     * @param adjacent the current closest known adjacent
     * @return a list of the closest cells to be inserted
     */
    public HashMap<Direction, Cell> getAdjacentsForNewCell(Cell orig, Cell adjacent, HashMap<Direction, Cell> map){
        //base case --> cell null
        Direction to = adjacent.isTo(orig);
        Cell curr = map.get(to);
        //System.out.println(to + "->" + curr);
        if(curr == null ){
            map.put(to, adjacent);
        }
        if(curr == null || (!(curr == adjacent) && Cell.getCloserTo(adjacent, curr, orig) == adjacent)){
            //System.out.println("NEW ADJACENT FOUND  =>  " + adjacent);
            map.replace(to, adjacent);
            Cell[] edgeCells = adjacent.getEdges();
            for (int i = 0; i <edgeCells.length; i++) {
                if(edgeCells[i] != orig)
                    map = getAdjacentsForNewCell(orig, edgeCells[i], map);
            }
            Cell toUpdate = adjacent.addEdge(orig);
            update(adjacent, toUpdate);
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

    private Cell setStart(Cell c) {
        if (start==null)
            return this.start = c;
        if(!c.equals(this.start)){
            c.addEdge(start);
            update(c, start);
            this.start = c;
            return c;
        }
        throw new IllegalArgumentException("Not closer than current start!");
    }

    /**
     * Magically walks to a cell
     * @param target the cell to walk to
     * @param current the current Cell
     * @return an adjacent cell, or the cell if it exists
     */
    public Cell walk(Cell target, Cell current){
        if(current == null)
            return null;
        Cell[] branches = current.getEdges(Direction.BTM_RIGHT, Direction.RIGHT, Direction.BELOW);
        if(branches != null && branches.length != 0){
            Cell next = current;
            for (Cell c : branches) {
                next = Cell.getCloserTo(c,next, target);
            }
            return (next.equals(current)) ? current : walk(target, next);
        } else {
            return current;
        }
    }

    /**
     * New and improved walk function
     * @return an adjacent cell if not on the board, or a cell if it exists
     */
    public Cell directWalk(Cell target, Cell start){
        //base case - param is null
        if(start == null)
            return null;
        Cell next = directWalk(target, start.addEdge(target));
        return (next == null) ? start : next;
    }

    /**
     * Insert a cell into the board
     * @param c the cell to insert
     * @return The parameter cell that was inserted, or null if the spot is occupied
     */
    public Cell insertCell(Cell c) {
        Cell closest = walk(c, start);
        if(start == null) {
            this.start = c;
            return c;
        }
        if(closest != c){
            HashMap<Direction, Cell> nap = new HashMap<>(8);
            nap = getAdjacentsForNewCell(c, closest, nap);
            Iterator<Cell> ii = nap.values().iterator();
            while(ii.hasNext()){
                c.addEdge(ii.next());
                ii.remove();
            }
            if(c.closerToOrigin(start)){
                this.start = c;
            }
            return c;
        } else return null;
    }

    /**
     * Print the game board
     * @param current the cell to highlight
     */
    public void printGraphicalBoard(Cell current){
        if(current == null)
            return;
        System.out.print("[ ");
        Cell c = current;
        int blankTracker = 0;
        while (c != null){
            while (blankTracker++ < c.column){
                System.out.print("0-");
            }
            System.out.print(/*" " + */c.value + "-");
            blankTracker = c.column;
            c = c.get(Direction.RIGHT);
        }
        System.out.print("]\n");
        Cell[] bigC = current.getEdges(Direction.BTM_LEFT,
                Direction.BELOW, Direction.BTM_RIGHT);
        if(bigC != null && bigC.length > 0){
            printGraphicalBoard(bigC[0]);
        } else {
            System.out.println("<>--------------------");
        }
    }
}
