package game;

/**
 * Created by preston on 2/14/17.
 */
public class RowLinkedList {
    private Cell head;
    private Cell tail;

    /**Reference to all of the Columns, responsibility of the
     * game board to make sure this is updated/correct
     * Here it's assumed this is always right*/
    public static ColumnLinkedList[] columnsRef;
    
    public RowLinkedList(){
        this.head = null;
        this.tail = null;
    }

    public Cell getHead() {
        return head;
    }

    public Cell getTail() {
        return tail;
    }

    public void setHead(Cell head) {
        this.head = head;
    }

    public void setTail(Cell tail) {
        this.tail = tail;
    }

    public Cell insert(Cell cell){
        /**If there's no value in the row*/
        if(head == null){
            head = cell;
            tail = head;
            head.setColumnNext(tail);
            head.setRowPrevious(tail);
            return cell;
        }

/*
        if(tail == null){
            if(cell.row < head.row){
                this.tail = cell;
            } else {
                if(head.column == cell.column && head.row == cell.row) {
                    throw new IllegalArgumentException("Not allowed to overlap cells!");
                }
                head.setRowNext(cell);
                cell.setRowPrevious(head);
                cell.setRowNext(null);
                this.tail = cell;
                return cell;
            }
        }
*/
        /**If the cell (param) is the furthest left
         * set the head to that cell*/
        if(cell.row < head.row){
            head.setRowPrevious(cell);
            cell.setRowNext(head);
            cell.setRowPrevious(null);
            head = cell;
            return cell;
        } else {
            /**otherwise, go down the Column until the correct spot is found*/
            Cell placeHolder = head.getRowNext();
            while(placeHolder != null && placeHolder.column <= cell.column){
                if(placeHolder.column == cell.column && placeHolder.row == cell.row){
                    throw new IllegalArgumentException("Not allowed to overlap cells!");
                }
                cell.setRowPrevious(placeHolder);
                placeHolder = placeHolder.getRowNext();
            }
            //if the placeholder is null, the cell is the new tail
            if(placeHolder == null){
                this.tail = cell;
            } else { //if not null, cell is inserted between placeHolder and placeHolder's next
                cell.setRowNext(cell.getRowPrevious().getRowNext());
                placeHolder.setRowNext(cell);
            }
        }

        if(head.equals(tail)){
            Cell c = cell;
            Cell cNext = c.getRowNext();
            while(cNext != null){
                cNext = c.getRowNext();
                if(cNext != null){
                    c = cNext;
                }
            }
            tail = c;
        }
        return cell;    }

    public void shiftToTail(){
        if(head != null){
            this.shiftToTail(this.tail);
        }
    }

    public void shiftToHead(){
        if(head != null){
            this.shiftToHead(this.head);
        }
    }

    /**
     * Call this to shift the row towards the tail
     */
    private void shiftToTail(Cell current){
        if(current.equals(head)){
            return;
        }

        //if the cells equal and need to be combined
        // TODO: 2/14/17 REMOVE THE OTHER COMBINED VALUE FROM IT'S OTHER DIMENSIONAL ROW?
        if(current.value == current.getRowPrevious().value){
            current.value = current.value * 2;
            if(current.getRowPrevious().getRowPrevious() != null){
                current.getRowPrevious().getRowPrevious().setColumnNext(current);
            } else { //if previous one's previous is null, current is the new head
                current.setColumnPrevious(null);
                head = current;
            }
            return;
        }

        //set the current cell's row to the correct value and call this method on the previous cell
        int oldRow = current.row;
        if(current.getRowNext() != null){
            current.row = current.getRowNext().row - 1;
        } else {
            current.row = columnsRef.length;
        }


        if(current.row != oldRow){
            this.removeCellFromColumn(current, oldRow);
            columnsRef[current.column].insert(current);
        }
        shiftToTail(current.getRowPrevious());
    }

    private void shiftToHead(Cell current)
    {
        if(current.equals(tail)){
            current.column = current.getRowPrevious().column + 1;
            return;
        }

        //if only one tile in the Row
        if(current.equals(this.head) && current.getRowNext() == null){
            current.column = 0;
            return;
        }

        // TODO: 2/14/17 REMOVE THE OTHER COMBINED VALUE FROM IT'S OTHER DIMENSIONAL ROW
        if(current.value == current.getRowNext().value){
            current.value = current.value * 2;
            if(current.getRowNext().getRowNext() != null){
                current.getRowNext().getRowNext().setColumnPrevious(current);
            } else {
                current.setColumnNext(null);
                tail = current;
            }
        }

        int oldColumn = current.column;
        if(current.getRowPrevious() != null){
            current.column = current.getRowPrevious().column + 1;
        } else {
            current.column= 0;
        }

        if(oldColumn != current.column){
            this.removeCellFromColumn(current, oldColumn);
            columnsRef[current.row].insert(current);
        }
        shiftToHead(current.getRowNext());
    }

    private void removeCellFromColumn(Cell current, int oldRow){
        if(current.getColumnPrevious() != null && current.getColumnNext() != null)
        { //if it's old row had values on either side
            current.getColumnPrevious().setRowNext(current.getColumnNext());
            current.getColumnNext().setRowPrevious(current.getColumnPrevious());
        } else if(current == columnsRef[oldRow].getHead()){
            //if current cell was the head of the old row
            columnsRef[oldRow].setHead(current.getColumnNext());
        }else if(current.getColumnPrevious() != null){
            //if the current cell was the tail of the old row
            current.getColumnPrevious().setRowNext(null);
            columnsRef[oldRow].setTail(current.getColumnPrevious());
        }
    }
}
