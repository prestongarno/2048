package GraphGame;

import GraphGame.Edge.Direction.*;
import sun.print.DialogOwner;

/**
 * Created by preston on 2/15/17.
 */
public class GraphBoard {

    private int numRows;
    private int numColumns;

    public Cell start;

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

    public Cell walkTo(Cell target){
        Cell closest = this.start;
        Cell[] branches = this.start.getEdges(Edge.Direction.BTM_RIGHT, Edge.Direction.RIGHT, Edge.Direction.BELOW);
        for(Cell c: branches) {
            c = walk(target, c);
        }

        for(Cell e : branches){
            closest = this.getCloserToTarget(e,closest,target);
        }
        return closest;
    }

    public Cell walk(Cell target, Cell current){
        Cell[] branches = current.getEdges(Edge.Direction.BTM_RIGHT, Edge.Direction.RIGHT, Edge.Direction.BELOW);
        if(branches.length != 0){
            Cell next = current;
            for (Cell c : branches) {
                next = this.getCloserToTarget(c,next, target);
            }
            return (next.equals(current)) ? current : walk(target, next);
        } else {
            return current;
        }
    }

    public Cell getCloserToTarget(Cell c1, Cell c2, Cell target){
        return (c1.distanceTo(target) < c2.distanceTo(target)) ? c1 : c2;
    }

    public void printBoard(Cell current){
        System.out.print("[ ");
        Cell c = current;
        int blankTracker = 0;
        while (c != null){
            while (blankTracker < c.column){
                System.out.print(" - ");
                blankTracker++;
            }
            System.out.print(" " + c.value + " ");
            blankTracker = c.column;
            c = c.get(Edge.Direction.RIGHT);
        }
        System.out.print(" ]\n");
        Cell[] bigC = current.getEdges(Edge.Direction.BTM_LEFT, Edge.Direction.BELOW, Edge.Direction.BTM_RIGHT);
        if(bigC != null && bigC.length > 0){
            printBoard(bigC[0]);
        } else {
            System.out.println("<>---------------------------------------------<>");
        }
    }
}
