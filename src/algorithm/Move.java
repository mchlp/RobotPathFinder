/*
 * Michael Pu
 * RobotPathFinder - Move
 * ICS3U1 - Mr. Radulovic
 * January 05, 2018
 */

package algorithm;

import java.awt.*;

/**
 * Represents a move with a direction and the originating position
 */
public class Move {

    private Direction mDirection;
    private Point mPosition;

    public Move(Direction direction, Point position) {
        mDirection = direction;
        mPosition = position;
    }


    public Direction getmDirection() {
        return mDirection;
    }

    public Point getmPosition() {
        return mPosition;
    }

}
