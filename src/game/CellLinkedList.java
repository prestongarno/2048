package game;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by preston on 2/14/17.
 */
public class CellLinkedList {
    private Cell head;
    private Cell tail;

    public CellLinkedList(Collection<Cell> initialCells){
        if(initialCells != null && initialCells.size() != 0){
            Iterator<Cell> it = initialCells.iterator();
            while(it.hasNext()){
                Cell c = it.next();

            }
        }
    }
}
