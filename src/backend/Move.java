/*
 * Michael Pu
 * RobotPathFinder - Move
 * November 2017
 */

package backend;

import java.awt.*;

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
