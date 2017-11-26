/*
 * Michael Pu
 * RobotPathFinder - Path
 * November 2017
 */

package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Path {

    private ArrayList<Direction> mPath;
    private Iterator<Direction> mIterator;

    public Path() {
        mPath = new ArrayList<>();
    }

    public void add(Direction direction) {
        mPath.add(direction);
    }

    public void reverseAndFinalize() {
        Collections.reverse(mPath);
        mIterator = mPath.iterator();
    }

    public Direction getNext() {
        return mIterator.next();
    }

    public boolean hasNext() {
        return mIterator.hasNext();
    }

}
