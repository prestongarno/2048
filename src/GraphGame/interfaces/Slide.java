package GraphGame.interfaces;

import GraphGame.Cell;
import GraphGame.Direction;

/**
 * ========================================================================
 * $(PROJECT_NAME) - by Preston Garno on 2/23/17
 * =========================================================================
 */
public interface Slide {
    void slide(Cell c, Direction direction, int startValue, UpdateListener queue);
}
