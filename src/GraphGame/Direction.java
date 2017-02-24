package GraphGame;

/**
 * Created by preston on 2/17/17.
 */
public enum Direction {
    TOP_LEFT, BTM_RIGHT, BTM_LEFT, ABOVE, BELOW, LEFT, RIGHT, TOP_RIGHT;

    public static final Direction[] values = {TOP_LEFT, BTM_RIGHT,
             ABOVE, BELOW, LEFT, RIGHT, TOP_RIGHT, BTM_LEFT};

    private Cell cell;

    /**
     * @param parent the original cell
     * @param other  the other cell
     * @return the Direction that other is from parent
     */
    public static Direction isTo(Cell parent, Cell other) {
        if (other.row > parent.row && other.column > parent.column) {
            return BTM_RIGHT;
        } else if (other.row > parent.row && other.column < parent.column) {
            return BTM_LEFT;
        } else if (other.row < parent.row && other.column < parent.column) {
            return TOP_LEFT;
        } else if (other.row < parent.row && other.column > parent.column) {
            return TOP_RIGHT;
        } else if (other.row > parent.row) {
            return BELOW;
        } else if (other.row < parent.row) {
            return ABOVE;
        } else if (other.column < parent.column) {
            return LEFT;
        } else if (other.column > parent.column){
            return RIGHT;
        } else {
            throw new IllegalArgumentException(parent + " ==//==" + other);
        }
    }

    public Direction opposite(){
        switch (this)
        {
            case ABOVE:
                return BELOW;
            case BELOW:
                return ABOVE;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case TOP_LEFT:
                return BTM_RIGHT;
            case TOP_RIGHT:
                return BTM_LEFT;
            case BTM_LEFT:
                return TOP_RIGHT;
            case BTM_RIGHT:
                return TOP_LEFT;
        }
        throw new NullPointerException("can't add directions...");
    }

    public int nextValue(int current){
        if(this == LEFT || this == ABOVE){
            return current - 1;
        } else if (this == RIGHT || this == BELOW){
            return current + 1;
        } else {
            throw new IllegalArgumentException("Cannot walk diagonally!");
        }
    }
}
