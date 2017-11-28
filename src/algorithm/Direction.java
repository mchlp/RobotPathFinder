/*
 * Michael Pu
 * RobotPathFinder - Direction
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package algorithm;

import java.awt.*;

/**
 * Represents the four possible directions of movement with their respective changes in the x and y position and their angle
 */
public enum Direction {

    UP(new Point(0, -1), 0),
    DOWN(new Point(0, 1), 180),
    LEFT(new Point(-1, 0), 90),
    RIGHT(new Point(1, 0), 270);

    // change in the x and y direction
    public final Point change;
    // the angle at which the direction points
    public final int direction;

    Direction(Point change, int d) {
        this.change = change;
        this.direction = d;
    }
}
