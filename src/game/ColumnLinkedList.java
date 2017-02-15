package game;

/**
 * Created by preston on 2/14/17.
 */
public class ColumnLinkedList {

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
            /*tail = head;*/
            head.setColumnNext(null);
            head.setColumnPrevious(null);
            return cell;
        }

        /**If the cell (param) is the furthest left
         * set the head to that cell*/
        if(cell.row < head.row){
            head.setColumnPrevious(cell);
            if(head.getColumnNext() == null){
                this.tail = head;
                this.tail.setColumnNext(null);
            }
            cell.setColumnNext(head);
            cell.setColumnPrevious(null);
            head = cell;
        } else {
            /**otherwise, go down the Column until the correct spot is found*/
            Cell placeHolder = head;
            cell.setColumnPrevious(head);
            while(placeHolder != null && placeHolder.column < cell.column){
                cell.setColumnPrevious(placeHolder);
                placeHolder = placeHolder.getColumnNext();
            }
            cell.getColumnPrevious().setColumnNext(cell);
            //if the placeholder is null, the cell is the new tail
            if(placeHolder == null){
                this.tail = cell;
                this.tail.setColumnNext(null);
            } else if(placeHolder.row == cell.row) { //not allowed to insert overlapping cells
                throw new IllegalArgumentException("Not allowed to overlap cells!");
            }else { //if not null, cell is inserted between placeHolder and placeHolder's next
                cell.setColumnNext(cell.getColumnPrevious().getColumnNext());
                cell.getColumnPrevious().setColumnNext(cell);
            }
        }
        return cell;
    }

    public void shiftToTail(RowLinkedList[] rowsRef){
        if(head != null){
            this.shiftToTail(this.tail, rowsRef);
        }
    }

    public void shiftToHead(RowLinkedList[] rowsRef){
        if(head != null){
            this.shiftToHead(this.head, rowsRef);
        }
    }

    /**
     * Call this to shift the row towards the tail
     */
    private void shiftToTail(Cell current, RowLinkedList[] rowsRef){
        if(current.equals(head)){
            return;
        }

        //if the cells equal and need to be combined
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
            this.removeCellFromRow(current, oldRow, rowsRef);
            rowsRef[current.column].insert(current);
        }
        shiftToTail(current.getColumnPrevious(), rowsRef);
    }

    private void shiftToHead(Cell current, RowLinkedList[] rowsRef)
    {
        /********************* BASE CASE*****************/
        if(current == null){
            return;
        }
        /**************** CHECK IF THE CELL HAS A NEXT TO COMBINE*********/
        //if it has a next, it's not a head or a tail
        if(current.getColumnNext() != null && current.value == current.getColumnNext().value){
            current.value = current.value * 2;
            Cell nextNext = current.getColumnNext().getColumnNext();
            this.removeCellFromRow(current.getColumnNext(), current.getColumnNext().row, rowsRef);
            if(nextNext != null){
                current.getColumnNext().getColumnNext().setColumnPrevious(current);
                current.setRowNext(nextNext);
            } else {
                current.setColumnNext(null);
                this.tail = current;
            }
        } else if(current.getRowNext() == null){ /******* if no next, it must be a tail ********/
            current.setRowNext(null);
            this.tail = current;
        }

        /*******************************************/
        //shift the column it's in here
        int oldColumn = current.row;
        if(current.getColumnPrevious() != null){
            current.row = current.getColumnPrevious().row + 1;
        } else {
            current.row = 0;
        }
        //update the appropriate column if it moved
        if(oldColumn != current.row){
            this.removeCellFromRow(current, oldColumn, rowsRef);
            rowsRef[current.row].insert(current);
        }

        shiftToHead(current.getColumnNext(), rowsRef);
    }

    private void removeCellFromRow(Cell current, int oldRow, RowLinkedList[] rowsRef){
        if(current.equals(rowsRef[oldRow].getHead())){
            rowsRef[oldRow].setHead(current.getRowNext());
        } else if(current.equals(rowsRef[oldRow].getTail())){
            rowsRef[oldRow].setTail(current.getRowPrevious());
        } else {
            current.getRowNext().setRowPrevious(current.getRowPrevious());
            current.getRowPrevious().setRowNext(current.getRowNext());
        }
    }
}
