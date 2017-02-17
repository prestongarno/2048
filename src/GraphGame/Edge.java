package GraphGame;

/**
 * Created by preston on 2/16/17.
 * Important --> Each edge can only be referenced by 1 and only one cell, the other cell stored in this
 * "Edge" object has an entirely different edge object even though it contains ref's to the same cells
 * No sharing of edges, they are easily nulled, destroyed and recreated
 * i.e. immutable I guess
 */
public class Edge {
    public enum Direction{
        TOP_LEFT, BTM_RIGHT, BTM_LEFT, ABOVE, BELOW, LEFT, RIGHT, TOP_RIGHT;
    }

    public final Direction dir;
    public final Cell cell;
    public final Cell parent;
    public final int distanceToParent;

    public Edge(Cell edge, Cell parent){
        this.distanceToParent = edge.distanceTo(parent.row, parent.column);
        this.cell = edge;
        this.parent = parent;
        this.calcRelationshipToParent();
        this.dir = calcRelationshipToParent();
    }

    /** Calculates where this edge stands on the parent
     * @return where this edge stands on the parent
     */
    private Direction calcRelationshipToParent(){
        if(cell.row > parent.row && cell.column > parent.column){return Direction.BTM_RIGHT;}
        else if(cell.row > parent.row && cell.column < parent.column){return Direction.BTM_LEFT;}
        else if(cell.row < parent.row && cell.column < parent.column){return Direction.TOP_LEFT;}
        else if(cell.row < parent.row && cell.column > parent.column){return Direction.TOP_RIGHT;}
        else if(cell.row > parent.row){return Direction.BELOW;}
        else if(cell.row < parent.row){return Direction.ABOVE;}
        else if(cell.column < parent.column){return Direction.LEFT;}
        else {return Direction.RIGHT;}
    }

    public static Direction calcRelationshipToParent(Cell edgeCell, Cell parent){
        if(edgeCell.row > parent.row && edgeCell.column > parent.column){return Direction.BTM_RIGHT;}
        else if(edgeCell.row > parent.row && edgeCell.column < parent.column){return Direction.BTM_LEFT;}
        else if(edgeCell.row < parent.row && edgeCell.column < parent.column){return Direction.TOP_LEFT;}
        else if(edgeCell.row < parent.row && edgeCell.column > parent.column){return Direction.TOP_RIGHT;}
        else if(edgeCell.row > parent.row){return Direction.BELOW;}
        else if(edgeCell.row < parent.row){return Direction.ABOVE;}
        else if(edgeCell.column < parent.column){return Direction.LEFT;}
        else {return Direction.RIGHT;}
    }
}
