/*
 * Michael Pu
 * RobotPathFinder - Path
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Represents a path from the starting point to the goal point using a list of turns
 */
public class Path {

    // list of directions (turns) to reach goal point
    private ArrayList<Direction> mPath;

    // iterator for following the path
    private Iterator<Direction> mIterator;

    public Path() {
        mPath = new ArrayList<>();
    }

    /**
     * Adds a direction to the end of the path
     *
     * @param direction {@link Direction} to add
     */
    public void add(Direction direction) {
        mPath.add(direction);
    }

    /**
     * Reverses the path and creates an iterator for the finalized path
     */
    public void reverseAndFinalize() {
        Collections.reverse(mPath);
        mIterator = mPath.iterator();
    }

    /**
     * Gets the next direction to proceed in
     *
     * @return Next direction in the path
     */
    public Direction getNext() {
        return mIterator.next();
    }

    /**
     * @return If there are any moves left in the path
     */
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    /**
     * Resets the iterator of the path to the beginning (starting point)
     */
    public void resetIterator() {
        mIterator = mPath.iterator();
    }

}
