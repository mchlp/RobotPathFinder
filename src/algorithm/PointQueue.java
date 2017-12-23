/*
 * Michael Pu
 * RobotPathFinder - Queue
 * ICS3U1 - Mr. Radulovic
 * December 22, 2017
 */

package algorithm;

import java.awt.*;

/**
 * Represents a queue or FIFO (First In First Out) data structure
 */
public class PointQueue {

    // initial size of the queue
    private static int INITIAL_SIZE = 10;
    // times to increase the size of the queue by when it runs out of space
    private static int UPDATE_SIZE_TIMES = 2;

    // array of the points in the queue
    private Point[] arr;
    // the index of the last Point in the queue
    private int lastIndex;
    // the index of the first Point in the queue
    private int startIndex;

    public PointQueue() {
        arr = new Point[INITIAL_SIZE];
        startIndex = 0;
        lastIndex = 0;
    }

    /**
     * Adds a point to the end of the queue
     *
     * @param point {@link Point} to add
     */
    public void push(Point point) {
        if (lastIndex+1 >= arr.length) {
            updateSize();
        }
        arr[lastIndex] = point;
        lastIndex++;
    }

    /**
     * @return The {@link Point} at the beginning of the queue
     */
    public Point pop() {
        return arr[startIndex++];
    }

    /**
     * @return If the queue is empty
     */
    public boolean isEmpty() {
        return lastIndex == startIndex;
    }

    // resizes the array when it is out of space
    private void updateSize() {
        // get size of the new array
        Point[] newArr = new Point[(lastIndex-startIndex)*UPDATE_SIZE_TIMES];
        // copy old array to new array
        for (int i=0; i<lastIndex-startIndex; i++) {
            newArr[i] = arr[startIndex+i];
        }
        arr = newArr;
        lastIndex=lastIndex-startIndex;
        startIndex=0;
    }
}
