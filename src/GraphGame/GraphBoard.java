package GraphGame;

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
        /*if(this.start!= null){
            start.addEdge(this.start);
            Cell.updateAdjacents(start, this.start);
        }*/
        this.start = start;
    }

    public Cell walkTo(Cell target){
        Cell closest = this.start;
        Cell[] branches = this.start.getEdges(Direction.BTM_RIGHT, Direction.RIGHT, Direction.BELOW);
        for(Cell c: branches) {
            c = walk(target, c);
        }

        for(Cell e : branches){
            closest = Cell.getCloserTo(e,closest,target);
        }
        return closest;
    }

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

    public void insertCell(Cell c) {
        Cell closest = walk(c, start);
        if(closest != null){
            c.addEdge(closest);
            Cell upsetCell = closest.addEdge(c);
            Cell.updateAdjacents(c, closest);
        } else if (c.closerToOrigin(start)){
            this.start = c;
        }
    }

    public void printGraphicalBoard(Cell current){
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
            c = c.get(Direction.RIGHT);
        }
        System.out.print(" ]\n");
        Cell[] bigC = current.getEdges(Direction.BTM_LEFT, Direction.BELOW, Direction.BTM_RIGHT);
        if(bigC != null && bigC.length > 0){
            printGraphicalBoard(bigC[0]);
        } else {
            System.out.println("<>---------------------------------------------<>");
        }
    }
}
