package game;

import org.jetbrains.annotations.Nullable;

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

    public void setHead(Cell cell) {
        if(this.head != null && cell != null) {
            if(head.column < cell.column){
                //means we're deleting the current one
                cell.setcRight(head.getcRight());
                head.setcRight(null);
                head.setcLeft(null);
                cell.setcLeft(null);
            } else {
                //meanse we're inserting before head
                this.head.setcLeft(cell);
                cell.setcRight(head);
                cell.setcLeft(null);
                if(this.tail == null){
                    tail = head;
                    head.setcRight(null);
                }
            }
        }
        this.head = cell;
    }

    public void setTail(Cell cell) {
        if(this.head == null)
            throw new IllegalArgumentException("Head doesn't exist yet, can't set tail");
        if(this.tail != null){
            if(tail.column < cell.column){
                tail.setcRight(cell);
                cell.setcLeft(tail);
            } else {
                if(tail.equals(this.head)){
                    tail.setcRight(null);
                    tail.setcLeft(null);
                    this.tail = null;
                } else {
                    tail.getcLeft().setcRight(cell);
                    tail.setcLeft(null);
                    tail.setcRight(null);
                }
            }
        } else if(cell != null){
            this.head.setcRight(cell);
            cell.setcLeft(this.head);
        }
        this.tail = cell;
        if(this.tail != null){this.tail.setcRight(null);}
    }

    public Cell insert(Cell cell){
        if(this.head == null || cell.column < head.column){
            this.setHead(cell);
        } else {
            /**otherwise, go down the Column until the correct spot is found*/
            Cell placeHolder = head;
            cell.setcLeft(head);
            // FIXME: 2/15/17 bad logic
            while(placeHolder.column < cell.column){
                placeHolder = placeHolder.getcRight();
                if(placeHolder != null && placeHolder.column <= cell.column){
                    cell.setcLeft(placeHolder);
                } else {break;}
            }
            cell.getcLeft().setcRight(cell);
            //if the placeholder is null, the cell is the new tail
            if(placeHolder == null){
                this.setTail(cell);
            } else if(placeHolder.column == cell.column) { //not allowed to insert overlapping cells
                    throw new IllegalArgumentException("Not allowed to overlap cells!");
            }else { //if not null, cell is inserted between placeHolder and placeHolder's next
                cell.setcRight(cell.getcLeft().getcRight());
                cell.getcRight().setcLeft(cell);
                cell.getcLeft().setcRight(cell);
            }
        }
        return cell;
    }

    public void remove(Cell cell){
        if(cell.getcLeft() != null && cell.getcRight() != null){
            cell.getcRight().setcLeft(cell.getcLeft());
            cell.getcLeft().setcRight(cell.getcRight());
        } else if(cell.getcRight() == null){
            //it's a tail
            this.tail = cell.getcLeft();
            if(this.tail != null){
                this.tail.setcRight(null);
            }
        } else if(cell.getcLeft() == null){
            //its a head
            this.head = cell.getcRight();
            if(this.head != null){
                this.head.setcLeft(null);
            }
        }
        cell.setcLeft(null);
        cell.setcRight(null);
        /*if(!cell.equals(head) && !cell.equals(tail)) {
            cell.getcRight().setcLeft(cell.getcLeft());
            cell.getcLeft().setcRight(cell.getcRight());
        } else if(cell.equals(getHead())){
            setHead(cell.getcRight());
        } else if(cell.equals(getTail())){
            setTail(cell.getcLeft());
        }*/
    }

    public void prettyPrint(){
        System.out.print("\n");
        if(head == null){
            System.out.println("<-- empty row -->");
        } else {
            Cell c = head;
            System.out.print("[ ");
            while (c != null){
                System.out.print("-" + c.value + "-");
                c = c.getcRight();
            }
            System.out.print(" ]");
        }
    }
}
