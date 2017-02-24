package GraphGame;


import GraphGame.concurrent.Manager;
import GraphGame.interfaces.GraphAction;
import GraphGame.interfaces.Functional;
import GraphGame.interfaces.Slide;
import GraphGame.interfaces.UpdateListener;
import game.GameStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static GraphGame.Direction.*;
import static GraphGame.interfaces.Functional.*;

/***********************************
 * Created by preston on 2/15/17.
 ***********************************/
public class NumberGame implements UpdateListener, NumberSlider {

    private int numRows;
    private int numColumns;
    private int size;

    public Cell start;

    public NumberGame(int numRows, int numColumns) {
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

    public void setStart(Cell c)
    {
        if(start == null || c.closerToOrigin(start)){
            this.start = c;
        } else {
            throw new IllegalArgumentException("Not closer than current start!");
        }
    }

    /***********************************
     * <pre>
     *  Better walk function:<br>
     *  1) A cell's EDGES do not always have this cell as an edge in the opposite direction<br>
     *  2) However, it is impossible for a cell to not have an edge that
     *      does NOT contain the cell in its list of EDGES<br>
     *  3) this function will locate the cell that will:<br>
     *          a) point at the target<br>
     *          b) the target points at the cell<br>
     *              </pre>
     * N.B. this method will return the cell at the target location,
     * but it will be another REFERENCE value<br>
     * to the one that exists there already if one existed before
     * @param target the target cell
     * @param current the start cell
     * @return the cell at the location
     *<br>**********************************/
    public Cell directWalk(Cell target, Cell current) {

        if (current.isTo(target) == null){
            return current;
        } else {
            target.addEdge(current);
            return directWalk(target, current.addEdge(target));
        }
    }

    /***********************************
     * Insert a cell into the board
     * @param cell the cell to insert
     * @return The parameter cell that was inserted, or null if the spot is occupied
     * <br>***********************************/
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
            // FIXME: 2/20/17 handle duplicate cells
            return null;
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

    /***********************************<br>
     * Insert a cell into the board at
     * @param x location
     * @param y location
     * @return The parameter cell that was inserted, or null if the spot is occupied
     * <br>***********************************/
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

    /***********************************
     * Method that locates adjacents of a cell
     * Also points the adjacent cells at the target cell
     *
     * @param orig cell to be inserted
     * @param adjacent the current closest known adjacent
     * @return a set of the closest cells to be inserted
     ***********************************/
    public static HashMap<Direction, Cell> getAdjacentsForNewCell(
            Cell orig,
            Cell adjacent,
            HashMap<Direction, Cell> map){

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
                    map = getAdjacentsForNewCell(orig, cConst, map);
                }
            }
        }
        return map;
    }

    /**********************************
     * Get a cell at an x,y point
     * @param x value
     * @param y value
     ***********************************/
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
     * Method performs an action on the every cell on the board
     * // TODO: 2/20/17 add direction parameter to make this work for the slide() methods
     * @param graphAction the func interface to execute
     */
    public void doStuff(Cell startingPoint, GraphAction graphAction)
    {
        while(startingPoint != null){
            startingPoint = sweepHorizontal(startingPoint, graphAction);
        }
    }

    /**
     * Iterates through the "row" of cells
     * @param start start cell of the row
     * @param graphAction the action to perform on every cell in this row
     * @return the start of the next row
     */
    private static Cell sweepHorizontal(Cell start, GraphAction graphAction)
    {

        Cell current = start;
        Cell nextStart = null;

        while(current != null)
        {
            graphAction.executeOn(current);

            Cell[] below = current.getEdges(BTM_LEFT, BTM_RIGHT, BELOW);
            for(Cell c : below){
                nextStart = (c.closerToOrigin(nextStart)) ? c : nextStart;
            }
            current = current.get(RIGHT);
        }

        return nextStart;
    }

    public void slide(Direction d, Slide slide){
        Cell current = this.start;
        int startValue = 0;

        if(d == RIGHT){
            current = getTopRightCorner();
            startValue = this.getNumColumns();
        } else if (d == BELOW){
            current = getBottomLeftCorner();
            startValue = this.getNumRows();
        }

        while(current != null){
            Cell finalCurrent = current;
            int finalStartValue = startValue;
            Manager.getInstance().postSlide(() -> slide.slide(finalCurrent, d, finalStartValue, this), current, d.opposite(), startValue, this);
            current = getNext(d, current);
        }
    }

    public void walkEdge(Direction d, GraphAction g){
        Cell current = this.start;
        int startValue = 0;

        if(d == RIGHT){
            current = getTopRightCorner();
            startValue = this.getNumColumns();
        } else if (d == BELOW){
            current = getBottomLeftCorner();
            startValue = this.getNumRows();
        }

        while(current != null){
            Manager.getInstance().post(g, current);
            current = getNext(d, current);
        }
    }

    private Cell getNext(Direction d, Cell current) {
        Cell[] cells;
        Cell next = null;
        if(d == ABOVE || d == BELOW){
            cells = current.getEdges(TOP_RIGHT, RIGHT, BTM_RIGHT);
            for(Cell c : cells){
                if(next == null || c.column < next.column)
                    next = c;
            }
        }
        else if(d == LEFT || d == RIGHT){
            cells = current.getEdges(BTM_LEFT, BELOW, BTM_RIGHT);
            for(Cell c : cells){
                if(next == null || c.row < next.column)
                    next = c;
            }
        }
        return next;
    }

    public Cell getTopRightCorner(){
        Cell current = this.getStart();
        while(current != null){
            Cell[] nextes = current.getEdges(RIGHT, TOP_RIGHT, BTM_RIGHT);
            if(nextes.length > 0){
                for(Cell c : nextes){
                    if(c.column > current.column){
                        current = c;
                    }
                }
            } else break;
        }
        return current;
    }

    public Cell getBottomLeftCorner(){
        Cell current = this.getStart();
        while(current != null){
            for(Cell c : current.getEdges(BELOW, BTM_LEFT, BTM_RIGHT)){
                if(c.column > current.column){
                    current = c;
                }
            }
        }
        return current;
    }

    /***********************************
     * Print the game board
     ***********************************/
    public void printGraphicalBoard() {
        System.out.println("\n");
        this.doStuff(this.start, Functional.printGraphicalBoard);
    }

    /***********************************
     * Print the game board
     ***********************************/
    public void printCellsWithMatrices() {
        this.doStuff(this.start, Functional.printCell);
    }

    @Override
    public void notifyStart() {
        slide(RIGHT, UPDATE);
    }

    @Override
    public void resizeBoard(int height, int width, int winningValue) {

    }

    @Override
    public void reset() {
        start = null;
    }

    @Override
    public void setValues(int[][] ref) {
        start = null;
        for (int i = 0; i < ref.length; i++) {
            for (int j = 0; j < ref[i].length; j++) {
                int value = ref[i][j];
                if(value != 0)
                    this.addCell(i,j);
            }
        }
    }

    @Override
    public Cell placeRandomValue() {
        return null;
    }

    @Override
    public boolean slide(SlideDirection dir) {
        return false;
    }

    @Override
    public boolean slide(SlideDirection dir) {
        return false;
    }

    @Override
    public boolean slide(SlideDirection dir) {
        return false;
    }

    @Override
    public ArrayList<Cell> getNonEmptyTiles() {
        return null;
    }

    @Override
    public GameStatus getStatus() {
        return null;
    }

    @Override
    public void undo() {

    }
}
