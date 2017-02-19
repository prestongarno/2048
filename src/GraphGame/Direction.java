package GraphGame;

/**
 * Created by preston on 2/17/17.
 */
public enum Direction {
    TOP_LEFT, BTM_RIGHT, BTM_LEFT, ABOVE, BELOW, LEFT, RIGHT, TOP_RIGHT;

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
}
