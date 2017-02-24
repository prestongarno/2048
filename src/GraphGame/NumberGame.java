package GraphGame;


import GraphGame.concurrent.Manager;
import GraphGame.interfaces.GraphAction;
import GraphGame.interfaces.impl;
import GraphGame.interfaces.Walk;
import GraphGame.interfaces.WalkListener;
import game.GameStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static GraphGame.Direction.*;
import static GraphGame.interfaces.impl.SLIDE;

/***********************************
 * Created by preston on 2/15/17.
 ***********************************/
public class NumberGame implements WalkListener, NumberSlider {

    //hack to wait for slide threads
    private final CountDownLatch latch;

    /**Static so I don't have to pass a
     * max value on the slide() to other threads*/
    private static int numColumns;
    /**Static so I don't have to pass a
     * max value on the slide() to other threads*/
    private static int numRows;
    private int size;

    public Cell start;

    public NumberGame(int numRows, int numColumns) {
        NumberGame.numRows = numRows;
        NumberGame.numColumns = numColumns;
        this.latch = new CountDownLatch(1);
    }

    public static int getNumRows() {
        return numRows;
    }

    public static int getNumColumns() {
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

        while(current.isTo(target) != null){
            target.addEdge(current);
            current = current.addEdge(target);
        }
        return current;
        /*if (current.isTo(target) == null){
            return current;
        } else {
            target.addEdge(current);
            return directWalk(target, current.addEdge(target));
        }*/
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
        System.out.println("Adding: " + cell);
        //ConcurrentHashMap<Direction, Cell> nap = new ConcurrentHashMap<>(8);
        //cell.EDGES = (getAdjacentsForNewCell(cell, cell.getClosestEdge(), cell.EDGES));
        //cell.update();
        Iterator<Cell> it;
        Cell next;
        for(Direction d : Direction.values()){
            next = getAdjacent(cell, cell.getClosestEdge(), d);
            if(next != null){
                cell.addEdge(next);
            }
        }
        Manager.getInstance().post(Cell::update, cell);
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
    public static ConcurrentHashMap<Direction, Cell> getAdjacentsForNewCell(
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
            for (Cell edgeCell : edgeCells) {

                Cell cConst = edgeCell.getCloserConstrained(orig);
                if (cConst != orig && cConst != adjacent) {
                    map = getAdjacentsForNewCell(orig, cConst, map);
                }
            }
        }
        return map;
    }

    public static Cell getAdjacent(
            Cell target,
            Cell current,
            Direction direction){

        Cell next = null;
        for (Cell c : current.EDGES.values()) {
            if (c.isTo(target) == direction) {
                next = c;
            }
        }
        if(next == null){
            next = current.getEdgeTo(target);
            if(next != null && next != target)
                getAdjacent(target, next, direction);
            else return null;
        }

        Direction nextDir = next.isTo(target);
        if(next.getCloserConstrained(target) == next && nextDir == direction){
            (next.addEdge(target)).update();
            return current;
        } else if(nextDir == direction || next.hasAdjacents()){
            next = next.getCloserConstrained(target);
            while(next != current && current != target){
                current = next;
                next = next.getCloserConstrained(target);
            }
            (next.addEdge(target)).update();
            return current;
        } else {
            return null;
        }
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
     * @param startingPoint use this.getStart() to walk the board correctly
     * @param graphAction the action to execute on each cell
     */
    public void sweepBoard(Cell startingPoint, GraphAction graphAction)
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

    public synchronized void walk(Direction d, Walk walk) throws InterruptedException {
        Cell current = this.start;

        if(d == RIGHT){
            current = getTopRightCorner();
        } else if (d == BELOW){
            current = getBottomLeftCorner();
        }

        Cell finalCurrent;
        while(current != null){
            finalCurrent = current;
            current = getNext(d, current);
            if(current == null){
                Manager.getInstance().postWalk(walk, finalCurrent, d, this);
                latch.await();
                onComplete(d, walk);
            } else {
                Manager.getInstance().postWalk(walk, finalCurrent, d, null);
            }
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
        this.sweepBoard(this.start, impl.printGraphicalBoard);
    }

    /***********************************
     * Print the game board
     ***********************************/
    public void printCellsWithMatrices() {
        this.sweepBoard(this.start, impl.printCell);
    }

    @Override
    public void onComplete(Direction from, Walk which) {
        if(which == SLIDE){
            resetStart();
            try{
                walk(from, impl.UPDATE);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        } else {
            this.printGraphicalBoard();
        }
    }

    @Override
    public CountDownLatch getLatch() {
        return this.latch;
    }

    private void resetStart(){
        Cell lastClosest = start;
        Cell closest = start;
        if(this.start != null){
            while(closest.hasAdjacents()){
                System.out.println("FUUUUUUUUUUUUUUUUUUUUUUCK");
                for (Cell c :
                        closest.EDGES.values()) {
                    if (c.closerToOrigin(closest)){
                        closest = c;
                        System.out.println("to --> " + c.toShortString());
                    }
                }
                if(closest == lastClosest){
                    this.start = closest;
                    return;
                } else
                    lastClosest = closest;
            }
        }
        System.out.println(this.start);
        this.start = closest;
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
