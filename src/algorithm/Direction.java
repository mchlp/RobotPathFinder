/*
 * Michael Pu
 * RobotPathFinder - Direction
 * November 2017
 */

package algorithm;

import java.awt.*;

public enum Direction {
    UP(new Point(0, -1)),
    DOWN(new Point(0, 1)),
    LEFT(new Point(-1, 0)),
    RIGHT(new Point(1, 0));

    public final Point p;

    Direction(Point p) {
        this.p = p;
    }
}
