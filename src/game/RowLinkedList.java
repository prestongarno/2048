package game;

/**
 * Created by preston on 2/14/17.
 */
public class RowLinkedList {

    private Cell head;
    private Cell tail;
    
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
            /*tail = head;*/
            head.setRowNext(null);
            head.setRowPrevious(null);
            return cell;
        }

        /**If the cell (param) is the furthest left
         * set the head to that cell*/
        if(cell.column < head.column){
            head.setRowPrevious(cell);
            if(head.getRowNext() == null){
                this.tail = head;
            }
            cell.setRowNext(head);
            cell.setRowPrevious(null);
            head = cell;
        } else {
            /**otherwise, go down the Column until the correct spot is found*/
            Cell placeHolder = head;
            cell.setRowPrevious(head);
            while(placeHolder.column < cell.column){
                placeHolder = placeHolder.getRowNext();
                if(placeHolder != null){
                    cell.setRowPrevious(placeHolder);
                } else {break;}
            }
            cell.getRowPrevious().setRowNext(cell);
            //if the placeholder is null, the cell is the new tail
            if(placeHolder == null){
                this.tail = cell;
                this.tail.setRowNext(null);
            } else if(placeHolder.column == cell.column) { //not allowed to insert overlapping cells
                    throw new IllegalArgumentException("Not allowed to overlap cells!");
            }else { //if not null, cell is inserted between placeHolder and placeHolder's next
                cell.setRowNext(cell.getRowPrevious().getRowNext());
                cell.getRowPrevious().setRowNext(cell);
            }
        }
        return cell;
    }

    public void shiftToTail(ColumnLinkedList[] columnsRef){
        if(head != null){
            this.shiftToTail(this.tail, columnsRef);
        }
    }

    public void shiftToHead(ColumnLinkedList[] columnsRef){
        if(head != null){
            this.shiftToHead(this.head, columnsRef);
        }
    }

    /**
     * Call this to shift the row towards the tail
     */
    private void shiftToTail(Cell current, ColumnLinkedList[] columnsRef){
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
            this.removeCellFromColumn(current, oldRow, columnsRef);
            columnsRef[current.column].insert(current);
        }
        shiftToTail(current.getRowPrevious(), columnsRef);
    }

    private void shiftToHead(Cell current, ColumnLinkedList[] columnsRef)
    {
        /********************* BASE CASE*****************/
        if(current == null){
            return;
        }
        /**************** CHECK IF THE CELL HAS A NEXT TO COMBINE*********/
        //if it has a next, it's not a head or a tail
        if(current.getRowNext() != null && current.value == current.getRowNext().value){
            current.value = current.value * 2;
            Cell nextNext = current.getRowNext().getRowNext();
            this.removeCellFromColumn(current.getRowNext(), current.getRowNext().column, columnsRef);
            if(nextNext != null){
                current.getRowNext().getRowNext().setRowPrevious(current);
                current.setRowNext(nextNext);
            } else {
                current.setRowNext(null);
                this.tail = current;
            }
        } else if(current.getRowNext() == null){ /******* if no next, it must be a tail ********/
            this.tail = current;
        }

        /*******************************************/
        //shift the column it's in here
        int oldColumn = current.column;
        if(current.getRowPrevious() != null){
            current.column = current.getRowPrevious().column + 1;
        } else {
            current.column = 0;
        }
        //update the appropriate column if it moved
        if(oldColumn != current.column){
            this.removeCellFromColumn(current, oldColumn, columnsRef);
            columnsRef[current.column].insert(current);
        }

        shiftToHead(current.getRowNext(), columnsRef);
    }

    private void removeCellFromColumn(Cell current, int oldRow, ColumnLinkedList[] columnsRef){
        if(current.equals(columnsRef[oldRow].getHead())){
            columnsRef[oldRow].setHead(current.getColumnNext());
        } else if(current.equals(columnsRef[oldRow].getTail())){
            columnsRef[oldRow].setTail(current.getColumnPrevious());
        } else {
            current.getColumnNext().setColumnPrevious(current.getColumnPrevious());
            current.getColumnPrevious().setColumnNext(current.getColumnNext());
        }
    }
}
