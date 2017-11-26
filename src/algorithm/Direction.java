/*
 * Michael Pu
 * RobotPathFinder - Direction
 * November 2017
 */

package algorithm;

import java.awt.*;

public enum Direction {
    UP(new Point(0, -1), 0),
    DOWN(new Point(0, 1), 180),
    LEFT(new Point(-1, 0), 90),
    RIGHT(new Point(1, 0), 270);

    public final Point change;
    public final int direction;

    Direction(Point change, int d) {
        this.change = change;
        this.direction = d;
    }
}
