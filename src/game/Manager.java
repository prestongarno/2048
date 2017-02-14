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
        // TODO: 2/14/17 throw an exception in the linked list impl's if a cell being inserted already exists at that location
        IllegalArgumentException ig = new IllegalArgumentException("placeHolder");
        Cell randomCell = new Cell(0,0,0);
        while (ig != null){
            randomCell.row = random.nextInt(rows);
            randomCell.column = random.nextInt(columns);
            int value;
            if(System.currentTimeMillis() % 2 == 0)
            {
                value = 2;
            } else {
                value = 4;
            }
            randomCell.value = value;

            try{
                this.insertCell(randomCell);
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
                    cl.shiftToHead(this.rowsLists);
                }
                break;
            case DOWN:
                for(ColumnLinkedList cl : this.columnsLists){
                    cl.shiftToTail(this.rowsLists);
                }
                break;
            case LEFT:
                for(RowLinkedList rl : this.rowsLists){
                    rl.shiftToHead(this.columnsLists);
                }
                break;
            case RIGHT:
                for(RowLinkedList rl : this.rowsLists){
                    rl.shiftToTail(this.columnsLists);
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
                while (c.getColumnNext() != null){
                    ar.add(c.getColumnNext());
                    c = c.getColumnNext();
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

    public void insertCell(Cell toBeInserted)
    {
        RowLinkedList row = this.rowsLists[toBeInserted.row];
        ColumnLinkedList column = this.columnsLists[toBeInserted.column];
        row.insert(toBeInserted);
        column.insert(toBeInserted);
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
                    temp = temp.getRowNext();
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
}
