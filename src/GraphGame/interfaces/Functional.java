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

        cell.setCoord(direction, direction.nextValue(startValue));

        Cell previous = cell;
        cell = cell.get(direction);

        Cell c1;
        Cell c2;

        while (cell != null) {

            if(cell.value == previous.value){

                cell.value = 0;
                previous.value += previous.value;

                cell.row = 2147483647;
                cell.column = 2147483647;

                c1 = cell.get(direction);
                c2 = cell.get(direction.opposite());

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

                previous = cell;
                cell = cell.get(direction);
                previous.EDGES.clear();

            } else {
                cell.setCoord(direction, direction.nextValue(startValue));
                previous = cell;
                cell = cell.get(direction);
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

    public static final GraphAction UPDATE = (cell) -> {
            Cell current = cell;

            while(current != null){
                current.update();
                current = current.get(LEFT);
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
