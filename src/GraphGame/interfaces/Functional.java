package GraphGame.interfaces;

import GraphGame.Cell;

import static GraphGame.Direction.*;

/**
 * ========================================================================
 * 2048 - by Preston Garno on 2/22/17
 * =========================================================================
 */
public class Functional {

    public static final Slide SLIDE = (cell, direction, startValue, updateQueue) -> {

        Cell current = cell;
        current.setCoord(direction, startValue);

        Cell previous = current;
        current = current.get(direction);

        Cell c1;
        Cell c2;

        while (current != null) {

            if(previous != null && current.value == previous.value){

                current.value = 0;
                previous.value += previous.value;

                current.row = 2147483647;
                current.column = 2147483647;

                c1 = current.get(direction);
                c2 = current.get(direction.opposite());

                if(c1 != null){
                    c1.EDGES.remove(direction.opposite());
                    if(c2!=null)
                        c1.EDGES.put(direction.opposite(), c2);
                }

                if(c2 != null){
                    c2.EDGES.remove(direction);
                    if(c1!=null)
                        c2.EDGES.put(direction, c1);
                }

                previous = current;
                current = current.get(direction);
                previous.EDGES.clear();
                previous = null;

            } else {
                current.setCoord(direction, direction.nextValue(startValue));
                previous = current;
                current = current.get(direction);
            }

            startValue = direction.nextValue(startValue);
        }

        switch (direction){
            case LEFT:
                if(previous.get(TOP_LEFT) == null && previous.get(BTM_LEFT) == null) {
                    updateQueue.notifyStart();
                    System.out.println("NOTIFYING DONE AT : " + previous);
                }
                break;
        }
    };

    public static final Slide UPDATE = (cell, direction, startValue, updateQueue) -> {
        while(cell != null){
            cell.update();
            cell = cell.get(direction);
        }
    };

    public static final GraphAction DELETE = (cell -> cell.EDGES.clear());
    /**************************
     * used for printing the board
     *************************/
    public static final GraphAction printGraphicalBoard = (Cell cell) -> {
        if(!cell.hasEdge(LEFT)){
            System.out.format("ROW%5d =%3s[", cell.row, "");
            for (int i = 0; i < cell.column; i++) {
                System.out.print("-  ");
            }
        }

        System.out.format("%-3d", cell.value);
        Cell next = cell.get(RIGHT);

        if(next != null){
            for (int i = cell.column+1; i < next.column; i++) {
                System.out.print("-  ");
            }
        } else {
            System.out.print("]\n");
        }
    };
    /**************************
     * Print a cell
     *************************/
    public static final GraphAction printCell = System.out::println;
    /**************************
     * Print a cell only Row,Column
     *************************/
    public static final GraphAction printCellShort = (Cell cell) -> {
        System.out.println(cell.toShortString());
    };
}
