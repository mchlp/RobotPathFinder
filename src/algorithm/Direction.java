/*
 * Michael Pu
 * RobotPathFinder - Direction
 * ICS3U1 - Mr. Radulovic
 * January 04, 2018
 */

package algorithm;

import javafx.geometry.Point2D;

/**
 * Represents the four possible directions of movement with their respective changes in the x and y position and their angle.
 */
public enum Direction {

    UP(new Point2D(0, -1), 0),
    DOWN(new Point2D(0, 1), 180),
    LEFT(new Point2D(-1, 0), 90),
    RIGHT(new Point2D(1, 0), 270);

    // change in the x and y direction
    public final Point2D change;
    // the angle at which the direction points
    public final int direction;

    Direction(Point2D change, int d) {
        this.change = change;
        this.direction = d;
    }
}
