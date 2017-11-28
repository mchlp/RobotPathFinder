/*
 * Michael Pu
 * RobotPathFinder - Move
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package algorithm;

import java.awt.*;

/**
 * Represents a move with a direction and the ending position
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
