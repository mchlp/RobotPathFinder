/*
 * Michael Pu
 * RobotPathFinder - Maze
 * November 2017
 */

package algorithm;

import java.awt.*;
import java.util.ArrayDeque;

/**
 * Represents a maze, including the content of each cell, the starting point, and the ending point
 */
public class Maze {

    // 2D array representing cells in maze
    private Cell[][] mMaze;
    // starting position
    private Point startingPos;
    // goal position
    private Point endingPos;

    /**
     * @param width  width of maze
     * @param height height of maze
     */
    public Maze(int width, int height) {
        mMaze = new Cell[width][height];
    }

    /**
     * Sets the content of a cell in the maze
     *
     * @param cellX X position of the cell
     * @param cellY Y position of the cell
     * @param type  the content of the cell
     */
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

    /**
     * @return the starting point of the maze
     */
    public Point getStartingPos() {
        return startingPos;
    }

    /**
     * @return the ending point of the maze
     */
    public Point getEndingPos() {
        return endingPos;
    }

    /**
     * @return the width of the maze (number of columns)
     */
    public int getWidth() {
        return mMaze.length;
    }

    /**
     * @return the height of the maze (number of rows)
     */
    public int getHeight() {
        return mMaze[0].length;
    }

    /**
     * @param cellX X position of the cell
     * @param cellY Y position of the cell
     * @return the contents of the cell
     */
    public Cell getCell(int cellX, int cellY) {
        return mMaze[cellX][cellY];
    }

    /**
     * Computes the shortest path from the starting point to the ending point in the maze using the BFS algorithm.
     *
     * @return the shortest path from the starting point to the ending point, null if no path found
     */
    public Path solveMaze() {

        // queue to keep track of path
        ArrayDeque<Point> path = new ArrayDeque<>();

        // push starting point into queue
        Point startPoint = getStartingPos();
        path.push(startPoint);

        // keep track of which points have been visited, and if visited, its parent
        Move[][] parentArray = new Move[getWidth()][getHeight()];
        parentArray[startPoint.x][startPoint.y] = new Move(null, null);

        // stores the position of the goal if found, null otherwise
        Point goalPoint = null;

        // while the queue is not empty
        while (!path.isEmpty()) {

            // pop point from queue to explore next
            Point curPoint = path.removeFirst();

            // if the current point is the goal
            if (getCell(curPoint.x, curPoint.y) == Cell.GOAL) {
                goalPoint = curPoint;
                break;
            }

            // explore all four adjacent around the current point
            for (Direction direction : Direction.values()) {

                // coordinates of new point
                int newX = curPoint.x + direction.change.x;
                int newY = curPoint.y + direction.change.y;

                // if point is outside the array, skip this point
                if (newX < 0 || newY < 0 || newX >= getWidth() || newY >= getHeight())
                    continue;

                // if point has already been visited, skip the point
                if (parentArray[newX][newY] != null)
                    continue;

                // if point is a wall, skip the point
                if (getCell(newX, newY) == Cell.WALL)
                    continue;

                // 
                parentArray[newX][newY] = new Move(direction, curPoint);
                path.addLast(new Point(newX, newY));
            }
        }

        if (goalPoint == null) {
            return null;
        }

        Path foundPath = new Path();

        Move currentLocation = parentArray[goalPoint.x][goalPoint.y];

        while (currentLocation.getmPosition() != null) {
            foundPath.add(currentLocation.getmDirection());
            Point pos = currentLocation.getmPosition();
            currentLocation = parentArray[pos.x][pos.y];
        }

        foundPath.reverseAndFinalize();

        return foundPath;
    }
}
