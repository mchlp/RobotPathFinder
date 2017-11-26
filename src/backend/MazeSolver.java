/*
 * Michael Pu
 * RobotPathFinder - MazeSolver
 * November 2017
 */

package backend;

import java.awt.*;
import java.util.ArrayDeque;

public class MazeSolver {

    public static Path solve(Maze maze) {

        ArrayDeque<Point> path = new ArrayDeque<>();
        Point starting = maze.getStartingPos();
        path.push(starting);
        Move[][] parent = new Move[maze.getWidth()][maze.getHeight()];
        parent[starting.x][starting.y] = new Move(null, null);

        Point foundPosition = null;

        while(!path.isEmpty()) {
            Point p = path.removeFirst();

            if(maze.getCell(p.x, p.y) == Cell.GOAL) {
                foundPosition = p;
                break;
            }

            for(Direction direction : Direction.values()) {
                int newX = p.x + direction.p.x;
                int newY = p.y + direction.p.y;
                if(newX < 0 || newY < 0 || newX >= maze.getWidth() || newY >= maze.getHeight())
                    continue;
                if(parent[newX][newY] != null)
                    continue;
                if(maze.getCell(newX, newY) == Cell.WALL)
                    continue;
                parent[newX][newY] = new Move(direction, p);
                path.addLast(new Point(newX, newY));
            }
        }

        if(foundPosition == null) {
            return null;
        }

        Path foundPath = new Path();

        Move currentLocation = parent[foundPosition.x][foundPosition.y];
        while(currentLocation.getmPosition() != null) {
            foundPath.add(currentLocation.getmDirection());
            Point pos = currentLocation.getmPosition();
            currentLocation = parent[pos.x][pos.y];
        }
        foundPath.reverseAndFinalize();

        return foundPath;
    }

}
