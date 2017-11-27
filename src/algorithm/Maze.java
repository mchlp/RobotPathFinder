/*
 * Michael Pu
 * RobotPathFinder - Maze
 * November 2017
 */

package algorithm;

import java.awt.*;
import java.util.ArrayDeque;

public class Maze {

    private Cell[][] mMaze;

    private Point startingPos;
    private Point endingPos;

    public Maze(int width, int height) {
        mMaze = new Cell[width][height];
    }

    public void setCell(int cellX, int cellY, Cell type) {
        switch (type) {
            case START:
                startingPos = new Point(cellX, cellY);
                break;
            case GOAL:
                endingPos = new Point(cellX, cellY);
                break;
        }

        mMaze[cellX][cellY] = type;
    }

    public Point getStartingPos() {
        return startingPos;
    }

    public Point getEndingPos() {
        return endingPos;
    }

    public int getWidth() {
        return mMaze.length;
    }

    public int getHeight() {
        return mMaze[0].length;
    }

    public Cell getCell(int cellX, int cellY) {
        return mMaze[cellX][cellY];
    }

    public Path solveMaze() {
        ArrayDeque<Point> path = new ArrayDeque<>();
        Point startPoint = getStartingPos();
        path.push(startPoint);

        Move[][] parentArray = new Move[getWidth()][getHeight()];
        parentArray[startPoint.x][startPoint.y] = new Move(null, null);

        Point foundPosition = null;

        while(!path.isEmpty()) {

            Point curPoint = path.removeFirst();

            if(getCell(curPoint.x, curPoint.y) == Cell.GOAL) {
                foundPosition = curPoint;
                break;
            }

            for(Direction direction : Direction.values()) {
                int newX = curPoint.x + direction.change.x;
                int newY = curPoint.y + direction.change.y;
                if(newX < 0 || newY < 0 || newX >= getWidth() || newY >= getHeight())
                    continue;
                if(parentArray[newX][newY] != null)
                    continue;
                if(getCell(newX, newY) == Cell.WALL)
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
