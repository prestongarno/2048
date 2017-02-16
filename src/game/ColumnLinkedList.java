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

    public void setHead(Cell cell) {
        if(this.head != null && cell != null) {
            if(head.row < cell.row){
                cell.setcBelow(head.getcBelow());
                head.setcBelow(null);
                head.setcAbove(null);
                cell.setcAbove(null);
            } else {
                //meanse we're inserting before head
                this.head.setcAbove(cell);
                cell.setcBelow(head);
                cell.setcAbove(null);
                if(this.tail == null){
                    tail = head;
                    head.setcBelow(null);
                }
            }
        } else if(cell == null && this.head != null){
            head.setcAbove(null);
            head = null;
        }
        this.head = cell;
    }

    public void setTail(Cell cell) {
        if(this.head == null)
            throw new IllegalArgumentException("Head doesn't exist yet, can't set tail");
        if(this.tail != null){
            if(tail.row < cell.row){
                tail.setcBelow(cell);
                cell.setcAbove(tail);
            } else {
                if(tail.equals(this.head)){
                    tail.setcBelow(null);
                    tail.setcAbove(null);
                    this.tail = null;
                } else {
                    tail.getcAbove().setcBelow(cell);

                }
            }
        } else if(cell != null){
            this.head.setcBelow(cell);
            cell.setcAbove(this.head);
        }
        this.tail = cell;
        if(this.tail != null){this.tail.setcBelow(null);}
    }

    public Cell insert(Cell cell){
        if(this.head == null || cell.row < head.row){
            this.setHead(cell);
        } else {
            /**otherwise, go down the Column until the correct spot is found*/
            Cell placeHolder = head;
            cell.setcAbove(head);
            // FIXME: 2/15/17 not stylish enough
            while(placeHolder.row < cell.row){
                placeHolder = placeHolder.getcBelow();
                if (placeHolder != null && placeHolder.row < cell.row) {
                    cell.setcAbove(placeHolder);
                } else {break;}
            }
            //if the placeholder is null, the cell is the new tail
            if(placeHolder == null){
                this.setTail(cell);
            } else if(placeHolder.row == cell.row) { //not allowed to insert overlapping cells
                throw new IllegalArgumentException("Not allowed to overlap cells!");
            }else { //if not null, cell is inserted between placeHolder and placeHolder's next
                cell.setcBelow(cell.getcAbove().getcBelow());;
                cell.getcBelow().setcAbove(cell);
                cell.getcAbove().setcBelow(cell);
            }
        }
        return cell;
    }

    public void remove(Cell cell){
        if(cell.getcAbove() != null && cell.getcBelow() != null){
            cell.getcBelow().setcAbove(cell.getcAbove());
            cell.getcAbove().setcBelow(cell.getcBelow());
        } else if(cell.getcBelow() == null){
            //it's a tail
            this.tail = cell.getcAbove();
            if(this.tail != null){
                this.tail.setcBelow(null);
            }
        } else if(cell.getcAbove() == null){
            //its a head
            this.head = cell.getcBelow();
            if(this.head != null){
                this.head.setcAbove(null);
            }
        }
        cell.setcAbove(null);
        cell.setcBelow(null);
/*        if(!cell.equals(head) && !cell.equals(tail)) {
        cell.getcBelow().setcAbove(cell.getcAbove());
        cell.getcAbove().setcBelow(cell.getcBelow());
    } else if(cell.equals(getHead())){
        setHead(cell.getcBelow());
    } else if(cell.equals(getTail())){
        setTail(cell.getcAbove());
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
                c = c.getcBelow();
            }
            System.out.print(" ]");
        }
    }
}
