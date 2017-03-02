package GraphGame;


import GraphGame.concurrent.Manager;
import GraphGame.interfaces.GraphAction;
import GraphGame.interfaces.impl;
import GraphGame.interfaces.Walk;
import GraphGame.interfaces.WalkListener;
import game.GameStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static GraphGame.Direction.*;


/***********************************************************************************************
 * Created by preston on 2/15/17.
 ***********************************************************************************************/
 public class NumberGame implements WalkListener, NumberSlider {

    /**  latch to make the main thread wait (hacky)  */
    private final CountDownLatch latch;

    /**Static so I don't have to pass a
     * max value on the slide() to other threads*/
    private static int numColumns;
    /**Static so I don't have to pass a
     * max value on the slide() to other threads*/
    private static int numRows;
    private int winningValue;

    public Cell start;

    /***********************************************************************************************
     * Create a game board with a certain size
     * @param numColumns the number of columns
     * @param numRows the number of rows
     ***********************************************************************************************/
    public NumberGame(int numRows, int numColumns) {
        NumberGame.numRows = numRows;
        NumberGame.numColumns = numColumns;
        this.latch = new CountDownLatch(1);
    }

    /****************************
     * 		@return number of Rows
    ****************************/
    public static int getNumRows() {
        return numRows;
    }

    /****************************
     * 		@return number of columns
     ****************************/
    public static int getNumColumns() {
        return numColumns;
    }

    /****************************
     * 		@return the closest cell to the origin
     ****************************/
    public Cell getStart() {
        return start;
    }

    /***********************************************************************************************
     * set the start cell
     * @param c the closest cell to the origin
     ***********************************************************************************************/
    public void setStart(Cell c)
    {
        if(start == null || c.closerToOrigin(start)){
            this.start = c;
        } else {
            throw new IllegalArgumentException("Not closer than current start!");
        }
    }

    /***********************************************************************************************
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
     * 		@return the cell at the location
     *<br>**********************************************************************************************/
    public Cell directWalk(Cell target, Cell current) {
        while(current.isTo(target) != null){
            target.addEdge(current);
            current = current.addEdge(target);
        }
        return current;
    }

    /***********************************************************************************************<br>
     * Insert a cell into the board at
     * @param x location
     * @param y location
     * 		@return The parameter cell that was inserted, or null if the spot is occupied
     * 		@throws IllegalArgumentException if the cell has negative values or out of bounds values
     * <br>***********************************************************************************************/
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

    /***********************************************************************************************
     * Insert a cell into the board
     * @param cell the cell to insert
     * 		@return The parameter cell that was inserted, or null if the spot is occupied
     * <br>***********************************************************************************************/
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

    /***********************************************************************************************
     * Method that locates adjacents of a cell
     * Also points the adjacent cells at the target cell
     *
     * @param orig cell to be inserted
     * @param adjacent the current closest known adjacent
     * 		@return a set of the closest cells to be inserted
     ***********************************************************************************************/
    public HashMap<Direction, Cell> getAdjacentsForNewCell(
            Cell orig,
            Cell adjacent,
            HashMap<Direction, Cell> map) {

        if (adjacent == null) {
            return map;
        }

        Direction to = adjacent.isTo(orig);
        Cell curr = map.get(to);
        if (curr == null) {
            map.put(to, adjacent);
        }

        if (curr != adjacent && Cell.getCloserTo(curr, adjacent, orig) == adjacent) {

            map.replace(to, adjacent);
            Cell toUpdate = adjacent.addEdge(orig);

            Cell[] edgeCells = adjacent.getEdges();
            for (int i = 0; i < edgeCells.length; i++) {
                Cell cConst = edgeCells[i].getCloserConstrained(orig);
                if (cConst != orig) {
                    map = getAdjacentsForNewCell(orig, cConst, map);
                }
            }
        }
        return map;
    }

    /**********************************************************************************************
    * get an adjacent cell in a specific direction
     * @param target the cell of interest
     * @param current the current cell in in 'direction' relative to the cell
     * @param direction the direction to get the adjacent cell
     *                  --not working
    ***********************************************************************************************/
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

        if(next == null)
            return null;

        Cell evenCloser = next.getCloserConstrained(target);
        if(evenCloser == next){
            (next.addEdge(target)).update();
            return current;
        } else if(next.hasAdjacents()){
            while(next != evenCloser && evenCloser != target){
                next = evenCloser;
                evenCloser = evenCloser.getCloserConstrained(target);
            }
            (next.addEdge(target)).update();
            return current;
        } else {
            return null;
        }
    }


   /*****************************
     * Get a cell at an x,y point
     * @param x value
     * @param y value
     ***************************/
    @Nullable
    public Cell getCell(int x, int y)
    {
        Cell dummyCell = new Cell(x,y,-5000);
        Cell currentCell = directWalk(dummyCell, getStart());
        if(dummyCell == currentCell){
            return null;
        }
        return currentCell;
    }

    /**********************************************************************************************
     * Method performs an action on the every cell on the board
     * @param startingPoint use this.getStart() to walk the board correctly
     * @param graphAction the action to execute on each cell
     ************************************************************************************************/
    public void sweepBoard(Cell startingPoint, GraphAction graphAction)
    {
        while(startingPoint != null){
            startingPoint = sweepHorizontal(startingPoint, graphAction);
        }
    }


     /**********************************************************************************************
     * Iterates through the "row" of cells
     * @param start start cell of the row
     * @param graphAction the action to perform on every cell in this row
     * 		@return the start of the next row
     ************************************************************************************************/
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

    /**********************************************************************************************
     * Walk a row or column
     * @param d the direction to walk
     * @param walk the action to perform on th row
     *             @throws InterruptedException if something goes wrong when main thread waits
    ***********************************************************************************************/
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
                walk.walk(finalCurrent, d);
                //Manager.getInstance().postWalk(walk, finalCurrent, d, this);
        }
    }

    /**********************************************************************************************
     * Walk a row or column
     * @param d the direction to walk
     * @param current the current cell
     *                		@return the last cell
     ***********************************************************************************************/
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
    /**********************************************************************************************
     * 		@return the top right corner of the board
    ***********************************************************************************************/
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
    /**********************************************************************************************
     * 		@return the bottom left corner of the board
    ***********************************************************************************************/
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

    /***********************************************************************************************
     * Print the game board
     ***********************************************************************************************/
    public void printGraphicalBoard() {
        System.out.println("\n");
        this.sweepBoard(this.start, impl.printGraphicalBoard);
    }

    /***********************************************************************************************
     * Print the game board
     ***********************************************************************************************/
    public void printCellsWithMatrices() {
        this.sweepBoard(this.start, impl.printCell);
    }

    /**********************************************************************************************
     * called when the slide method is complete
     * @param from the direction that the slide/walk was done
     * @param which the action that was done
    ***********************************************************************************************/
    @Override
    public void onComplete(Direction from, Walk which) {
        if(which.equals(impl.getSlide())){
            resetStart();
            try{
                walk(from, impl.getUpdate());
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }else {
            this.printCellsWithMatrices();
        }
    }

    /**********************************************************************************************
    * Get the latch
     * 		@return the latch for the game board
    ***********************************************************************************************/
    @Override
    public CountDownLatch getLatch() {
        return this.latch;
    }

    /**********************************************************************************************
    * update the start cell
    ***********************************************************************************************/
    private void resetStart(){
        Cell lastClosest = start;
        Cell closest = start;

        if(this.start != null){
            while(closest.hasAdjacents()){

                for (Cell c :
                        closest.EDGES.values()) {
                    if (c.closerToOrigin(closest)){
                        closest = c;
                    }
                }

                if(closest == lastClosest){
                    this.start = closest;
                    return;
                } else
                    lastClosest = closest;
            }
        }
        this.start = closest;
    }

    /**********************************************************************************************
     * resize the board
     * @param height the height of the board
     * @param width the width of the board
     * @param winningValue the winning value of the game
    ***********************************************************************************************/
    @Override
    public void resizeBoard(int height, int width, int winningValue) {
        if(height < 0 | width < 0)
            throw new IllegalArgumentException("No negative board sizes!");

        int i;
        for (i = 2; i <= winningValue; i *= 2) {}
        if(i < winningValue)
            throw new IllegalArgumentException("Winning value must be a multiple of two!");

        NumberGame.numColumns = width;
        NumberGame.numRows = height;
        this.winningValue = winningValue;
    }

    /***********************************
     * reset the game board
    ***********************************/
    @Override
    public void reset() {
        start = null;
    }

    /**********************************************************************************************
     * set the game board to the values of the
     * given array
     * @param ref te values of the game board
     ***********************************************************************************************/
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

    /**********************************************************************************************
     * place a tile on the board in a random location
     ***********************************************************************************************/
    @Override
    public Cell placeRandomValue() {
        return null;
    }

    /**********************************************************************************************
     * Walk all the tiles in the board in the requested direction
     * @param dir move direction of the tiles
     *
     * @return true when the board changes
     ***********************************************************************************************/
    @Override
    public boolean slide(SlideDirection dir) {
        Direction myImpl = Direction.mapToslideDirection(dir);

        try {
            this.walk(myImpl, impl.getSlide());
            resetStart();
            this.walk(myImpl, impl.getUpdate());
            this.walk(myImpl, impl.getUpdate());
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**********************************************************************************************
     * @return an arraylist of Cells. Each cell holds the (row,column) and value of a tile
     ***********************************************************************************************/
    @Override
    public ArrayList<Cell> getNonEmptyTiles() {
        return null;
    }

    /**********************************************************************************************
     * Return the current state of the game
     * @return one of the possible values of GameStatus enum
     ***********************************************************************************************/
    @Override
    public GameStatus getStatus() {
        return null;
    }

    /**********************************************************************************************
     * Undo the most recent action, i.e. restore the board to its previous
     * state. Calling this method multiple times will ultimately restore
     * the gam to the very first initial state of the board holding two
     * random values. Further attempt to undo beyond this state will throw
     * an IllegalStateException.
     *
     * @throws IllegalStateException when undo is not possible
     ***********************************************************************************************/
    @Override
    public void undo() {

    }

    /**********************************************************************************************
     * restore a move
     * @param move the move that was done
     ***********************************************************************************************/
    public void restore(UndoStack.Movement move) {

    }
}
