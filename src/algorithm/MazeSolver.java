/*
 * Michael Pu
 * RobotPathFinder - MazeSolver
 * November 2017
 */

package algorithm;

import java.awt.*;
import java.util.ArrayDeque;

public class MazeSolver {

    public static Path solve(Maze maze) {

        ArrayDeque<Point> path = new ArrayDeque<>();
        Point startPoint = maze.getStartingPos();
        path.push(startPoint);

        Move[][] parentArray = new Move[maze.getWidth()][maze.getHeight()];
        parentArray[startPoint.x][startPoint.y] = new Move(null, null);

        Point foundPosition = null;

        while(!path.isEmpty()) {

            Point curPoint = path.removeFirst();

            if(maze.getCell(curPoint.x, curPoint.y) == Cell.GOAL) {
                foundPosition = curPoint;
                break;
            }

            for(Direction direction : Direction.values()) {
                int newX = curPoint.x + direction.p.x;
                int newY = curPoint.y + direction.p.y;
                if(newX < 0 || newY < 0 || newX >= maze.getWidth() || newY >= maze.getHeight())
                    continue;
                if(parentArray[newX][newY] != null)
                    continue;
                if(maze.getCell(newX, newY) == Cell.WALL)
                    continue;
                parentArray[newX][newY] = new Move(direction, curPoint);
                path.addLast(new Point(newX, newY));
            }
        }

        if (foundPosition == null) {
            return null;
        }

        Path foundPath = new Path();

        Move currentLocation = parentArray[foundPosition.x][foundPosition.y];

        while(currentLocation.getmPosition() != null) {
            foundPath.add(currentLocation.getmDirection());
            Point pos = currentLocation.getmPosition();
            currentLocation = parentArray[pos.x][pos.y];
        }

        foundPath.reverseAndFinalize();

        return foundPath;
    }

}
