package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by preston on 2/7/17.
 */
public class Manager implements NumberSlider
{
    private int winningValue;
    private int rows, columns;
    private static final Random random = new Random();
    private RowLinkedList[] rowsRowLinkedLists;
    private ColumnLinkedList[] columnLinkedLists;

    public Manager(int rows, int columns){
        this.winningValue = 2048;
        this.rows = rows;
        this.columns = columns;
        //create the linked lists
        this.rowsRowLinkedLists = new RowLinkedList[rows];
        for (int i = 0; i < rowsRowLinkedLists.length; i++) {
            rowsRowLinkedLists[i] = new RowLinkedList();
        }
        this.columnLinkedLists = new ColumnLinkedList[columns];
        for (int i = 0; i < columnLinkedLists.length; i++) {
            columnLinkedLists[i] = new ColumnLinkedList();
        }
        RowLinkedList.columnsRef = columnLinkedLists;
        ColumnLinkedList.rowsRef = rowsRowLinkedLists;
        //insert a random value to start the game
        this.placeRandomValue();
    }

    @Override
    public void resizeBoard(int height, int width, int winningValue) {
        this.setWinningValue(winningValue);
        ColumnLinkedList[] cls = new ColumnLinkedList[width];
        for (int i = 0; i < cls.length; i++) {
            if(i < this.columnLinkedLists.length){
                cls[i] = columnLinkedLists[i];
            } else {
                cls[i] = new ColumnLinkedList();
            }
        }
        RowLinkedList[] rls = new RowLinkedList[height];
        for (int i = 0; i < rls.length; i++) {
            if(i < this.rowsRowLinkedLists.length){
                rls[i] = rowsRowLinkedLists[i];
            } else {
                rls[i] = new RowLinkedList();
            }
        }

        this.rowsRowLinkedLists = rls;
        this.columnLinkedLists = cls;
        RowLinkedList.columnsRef = columnLinkedLists;
        ColumnLinkedList.rowsRef = rowsRowLinkedLists;
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
                ColumnLinkedList.rowsRef[randomCell.row].insert(randomCell);
                RowLinkedList.columnsRef[randomCell.column].insert(randomCell);
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
                for(ColumnLinkedList cl : this.columnLinkedLists){
                    cl.shiftToHead();
                }
                break;
            case DOWN:
                for(ColumnLinkedList cl : this.columnLinkedLists){
                    cl.shiftToTail();
                }
                break;
            case LEFT:
                for(RowLinkedList rl : this.rowsRowLinkedLists){
                    rl.shiftToHead();
                }
                break;
            case RIGHT:
                for(RowLinkedList rl : this.rowsRowLinkedLists){
                    rl.shiftToTail();
                }
                break;
        }
        return true;
    }

    @Override
    public ArrayList<Cell> getNonEmptyTiles() {
        ArrayList<Cell> ar = new ArrayList<>();
        for(ColumnLinkedList cl : this.columnLinkedLists){
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
}
