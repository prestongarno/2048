package GraphGame.interfaces;

import GraphGame.Direction;

import java.util.concurrent.CountDownLatch;

/**
 * ========================================================================
 * $(PROJECT_NAME) - by Preston Garno on 2/23/17
 * =========================================================================
 */
public interface WalkListener {
    void onComplete(Direction from, Walk which);
    public CountDownLatch getLatch();
}
