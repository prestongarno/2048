package game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by preston on 2/7/17.
 */
public class Manager implements NumberSlider
{
    private int winningValue;
    private int rows, columns;
    private static final Random random = new Random();
    private RowLinkedList[] rowsLists;
    private ColumnLinkedList[] columnsLists;

    public Manager(int rows, int columns){
        this.winningValue = 2048;
        this.rows = rows;
        this.columns = columns;
        //create the linked lists
        this.rowsLists = new RowLinkedList[rows];
        for (int i = 0; i < rowsLists.length; i++) {
            rowsLists[i] = new RowLinkedList();
        }
        this.columnsLists = new ColumnLinkedList[columns];
        for (int i = 0; i < columnsLists.length; i++) {
            columnsLists[i] = new ColumnLinkedList();
        }
        //insert a random value to start the game
        //this.placeRandomValue();
    }

    @Override
    public void resizeBoard(int height, int width, int winningValue) {
        this.setWinningValue(winningValue);
        ColumnLinkedList[] cls = new ColumnLinkedList[width];
        for (int i = 0; i < cls.length; i++) {
            if(i < this.columnsLists.length){
                cls[i] = columnsLists[i];
            } else {
                cls[i] = new ColumnLinkedList();
            }
        }
        RowLinkedList[] rls = new RowLinkedList[height];
        for (int i = 0; i < rls.length; i++) {
            if(i < this.rowsLists.length){
                rls[i] = rowsLists[i];
            } else {
                rls[i] = new RowLinkedList();
            }
        }

        this.rowsLists = rls;
        this.columnsLists = cls;
    }

    private void setWinningValue(int winningValue)
    {
        for (int i = 2; i < winningValue; i = i*2) {
            if(i == winningValue) {
               this.winningValue = winningValue;
            }
            throw new IllegalArgumentException("Winning value must be something within 2!");
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void setValues(int[][] ref) {
    }

    @Override
    public Cell placeRandomValue() {
        IllegalArgumentException ig = new IllegalArgumentException("to start while loop");
        Cell randomCell = new Cell(0,0,0);
        while (ig != null){
            randomCell.row = random.nextInt(rows);
            randomCell.column = random.nextInt(columns);
            int value = random.nextInt(2);
            //random value weighted 2/3 - 1/3 in favor of 2
            if(value == 2){
                value = 4;
            } else {
                value = 2;
            }
            randomCell.value = value;

            try{
                this.add(randomCell);
                ig = null;
            } catch (IllegalArgumentException i){
                ig = i;
            }

        }
        return randomCell;
    }

    @Override
    public boolean slide(SlideDirection dir) {
        switch (dir)
        {
            case UP:
                for(ColumnLinkedList cl : this.columnsLists){
                    //cl.shiftToHead(this.rowsLists);
                    this.shiftColumnToHead(cl.getHead(), cl);
                }
                break;
            case DOWN:
                for(ColumnLinkedList cl : this.columnsLists){
                    //cl.shiftToTail(this.rowsLists);
                }
                break;
            case LEFT:
                for(RowLinkedList rl : this.rowsLists){
                    //rl.shiftToHead(this.columnsLists);
                    this.shiftRowToHead(rl.getHead(), rl);
                }
                break;
            case RIGHT:
                for(RowLinkedList rl : this.rowsLists){
                    //r/l.shiftToTail(this.columnsLists);
                }
                break;
        }
        return true;
    }

    @Override
    public ArrayList<Cell> getNonEmptyTiles() {
        ArrayList<Cell> ar = new ArrayList<>();
        for(ColumnLinkedList cl : this.columnsLists){
            Cell c = cl.getHead();
            ar.add(c);
            if(c!= null){
                while (c.getcBelow() != null){
                    ar.add(c.getcBelow());
                    c = c.getcBelow();
                }
            }
        }
        return null;
    }

    @Override
    public GameStatus getStatus() {
        return null;
    }

    @Override
    public void undo() {

    }

    public void add(Cell... toBeInserted)
    {
        for(Cell cell : toBeInserted){
            RowLinkedList row = this.rowsLists[cell.row];
            ColumnLinkedList column = this.columnsLists[cell.column];
            row.insert(cell);
            column.insert(cell);
        }
    }

    public void prettyPrintGameBoard() {
        System.out.print("\nCol [");
        for (int i = 0; i < this.columns; i++) {
            System.out.print(" C" + i);
        }
        System.out.print(" ]");
        for (int i = 0; i < this.rowsLists.length; i++) {
            System.out.print("\nR"+i+": [ ");
            RowLinkedList rl = this.rowsLists[i];
            Cell temp = rl.getHead();
            if (temp == null) {
                for (int j = 0; j < this.columns; j++) {
                    System.out.print(" - ");
                }
                System.out.print("]");
            } else {
                int columnCounter = 0;
                while (temp != null && columnCounter < this.columns) {
                    for (int j = columnCounter; j < temp.column; j++) {
                        System.out.print(" - ");
                        columnCounter += 1;
                    }
                    System.out.print(" " + temp.value + " ");
                    columnCounter += 1;
                    temp = temp.getcRight();
                }
                if(columnCounter < this.columns){
                    for (int j = columnCounter; j < this.columns; j++) {
                        System.out.print(" - ");
                    }
                }
                System.out.print(" ]");
            }
        }
        System.out.println("\n>------------------------------------------<");
    }

    /********************************************************/
    //Shift the rows/Columns methods

    private void shiftColumnToHead(Cell current, ColumnLinkedList columnLinkedList)
    {
        /********************* BASE CASE*****************/
        if(current == null){
            return;
        }
        /**************** CHECK IF THE CELL HAS A NEXT TO COMBINE*********/
        //if it has a next, it's not a head or a tail
        if(current.getcBelow() != null && current.value == current.getcBelow().value){
            current.value = current.value * 2;
            Cell nextNext = current.getcBelow().getcBelow();
            this.rowsLists[current.getcBelow().row].remove(current.getcBelow());
            if(nextNext != null){
                current.getcBelow().getcBelow().setcAbove(current);
                current.setcRight(nextNext);
            } else {
                current.setcBelow(null);
                columnLinkedList.setTail(current);
            }
        } else if(current.getcRight() == null){ /******* if no next, it must be a tail ********/
            current.setcRight(null);
            columnLinkedList.setTail(current);
        }

        /*******************************************/
        //shift the column it's in here
        int oldRow = current.row;
        if(current.getcAbove() != null){
            current.row = current.getcAbove().row + 1;
        } else {
            current.row = 0;
            columnLinkedList.setHead(current);
        }
        //update the appropriate column if it moved
        if(oldRow != current.row){
            this.rowsLists[oldRow].remove(current);
            this.rowsLists[current.row].insert(current);
        }

        shiftColumnToHead(current.getcBelow(), columnLinkedList);
    }

    private void shiftColumnToTail(Cell current){
    }

    private void shiftRowToHead(Cell current, RowLinkedList rowLinkedList)
    {
        /********************* BASE CASE*****************/
        if(current == null){
            return;
        }
        /**************** CHECK IF THE CELL HAS A NEXT TO COMBINE*********/
        //if it has a next, it's not a head or a tail
        if(current.getcRight() != null && current.value == current.getcRight().value){
            current.value = current.value * 2;
            Cell nextNext = current.getcRight().getcRight();
            this.columnsLists[current.getcRight().column].remove(current.getcRight());
            rowLinkedList.remove(current.getcRight());
            if(nextNext != null){
                current.getcRight().getcRight().setcLeft(current);
                current.setcRight(nextNext);
            } else {
                current.setcRight(null);
                rowLinkedList.setTail(current);
            }
        } else if(current.getcRight() == null){ /******* if no next, it must be a tail ********/
            current.setcRight(null);
            rowLinkedList.setTail(current);
        }

        /*******************************************/
        //shift the column it's in here
        int oldColumn = current.column;
        if(current.getcLeft() != null){
            current.column = current.getcLeft().column + 1;
        } else {
            current.column = 0;
            rowLinkedList.setHead(current);
        }
        //update the appropriate column if it moved
        if(oldColumn != current.column){
            this.columnsLists[oldColumn].remove(current);
            this.columnsLists[current.column].insert(current);
        }

        shiftRowToHead(current.getcRight(), rowLinkedList);
    }

    public RowLinkedList getRowList(int index){
        return this.rowsLists[index];
    }

    public ColumnLinkedList getColumn(int index){
        return this.columnsLists[index];
    }

}
