package game;

/**
 * Created by preston on 2/14/17.
 */
public class ColumnLinkedList {

    /**Reference to all of the Rows, responsibility of the
     * game board to make sure this is updated/correct
     * Here it's assumed this is always right*/
    public static RowLinkedList[] rowsRef;

    private Cell head;
    private Cell tail;

    public ColumnLinkedList()
    {
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
            head.setColumnPrevious(tail);
            return cell;
        }
 /*       *//**If there's only one cell in the linked list*//*
        if(tail == null){
            if(head.column == cell.column && head.row == cell.row){
                throw new IllegalArgumentException("Not allowed to overlap cells!");
            }
            if(cell.column < head.column){
                this.tail = cell;
            } else {
                head.setColumnNext(cell);
                cell.setColumnPrevious(head);
                cell.setColumnNext(null);
                this.tail = cell;
                return cell;
            }
        }
*/        /**If the cell (param) is the furthest left
         * set the head to that cell*/
        if(cell.row < head.row){
            head.setColumnPrevious(cell);
            cell.setColumnNext(head);
            cell.setColumnPrevious(null);
            head = cell;
        } else {
            /**otherwise, go down the Column until the correct spot is found*/
            Cell placeHolder = head.getColumnNext();
            while(placeHolder != null && placeHolder.row <= cell.row){
                if(placeHolder.column == cell.column && placeHolder.row == cell.row){
                    throw new IllegalArgumentException("Not allowed to overlap cells!");
                }
                cell.setColumnPrevious(placeHolder);
                placeHolder = placeHolder.getColumnNext();
            }
            //if the placeholder is null, the cell is the new tail
            if(placeHolder == null){
                this.tail = cell;
            } else { //if not null, cell is inserted between placeHolder and placeHolder's next
                cell.setColumnNext(cell.getColumnPrevious().getColumnNext());
                placeHolder.setColumnNext(cell);
            }

        }
        if(head.equals(tail)){
            Cell c = cell;
            Cell cNext = c.getColumnNext();
            while(cNext != null){
                cNext = c.getColumnNext();
                if(cNext != null){
                    c = cNext;
                }
            }
            tail = c;
        }
        return cell;
    }

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
        // TODO: 2/14/17 REMOVE THE OTHER COMBINED VALUE FROM IT'S OTHER DIMENSIONAL ROW
        if(current.value == current.getColumnPrevious().value){
            current.value = current.value * 2;
            if(current.getColumnPrevious().getColumnPrevious() != null){
                current.getColumnPrevious().getColumnPrevious().setColumnNext(current);
            } else { //if previous one's previous is null, current is the new head
                current.setColumnPrevious(null);
                head = current;
            }
            return;
        }

        //set the current cell's row to the correct value and call this method on the previous cell
        int oldRow = current.row;
        if(current.getColumnNext() != null){
            current.row = current.getColumnNext().row - 1;
        } else {
            current.row = rowsRef.length;
        }


        if(current.row != oldRow){
            this.removeCellFromRow(current, oldRow);
            rowsRef[current.column].insert(current);
        }
        shiftToTail(current.getColumnPrevious());
    }

    private void shiftToHead(Cell current)
    {
        if(current.equals(tail)){
            return;
        }

        // TODO: 2/14/17 REMOVE THE OTHER COMBINED VALUE FROM IT'S OTHER DIMENSIONAL ROW
        if(current.value == current.getColumnNext().value){
            current.value = current.value * 2;
            if(current.getColumnNext().getColumnNext() != null){
                current.getColumnNext().getColumnNext().setColumnPrevious(current);
            } else {
                current.setColumnNext(null);
                tail = current;
            }
        }

        int oldRow = current.row;
        if(current.getColumnPrevious() != null){
            current.row = current.getColumnPrevious().row + 1;
        } else {
            current.row = 0;
        }

        if(oldRow != current.row){
            this.removeCellFromRow(current, oldRow);
            rowsRef[current.column].insert(current);
        }
        shiftToHead(current.getColumnNext());
    }

    private void removeCellFromRow(Cell current, int oldRow){
        if(current.getRowPrevious() != null && current.getRowNext() != null)
        { //if it's old row had values on either side
            current.getRowPrevious().setRowNext(current.getRowNext());
            current.getRowNext().setRowPrevious(current.getRowPrevious());
        } else if(current == rowsRef[oldRow].getHead()){
            //if current cell was the head of the old row
            rowsRef[oldRow].setHead(current.getRowNext());
        }else if(current.getRowPrevious() != null){
            //if the current cell was the tail of the old row
            current.getRowPrevious().setRowNext(null);
            rowsRef[oldRow].setTail(current.getRowPrevious());
        }
    }
}
