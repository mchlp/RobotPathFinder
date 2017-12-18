/*
 * Michael Pu
 * RobotPathFinder - Maze
 * ICS3U1 - Mr. Radulovic
 * November 28, 2017
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

    public Maze(String mapData) throws InvalidMazeException {

        boolean foundStart = false;
        boolean foundGoal = false;

        String[] allData = mapData.trim().split("\n");

        for (int row = 0; row < allData.length; row ++ ) {
            String[] rowData = allData[row].split("[ \t]+");

            if (row == 0) {
                mMaze = new Cell[rowData.length][allData.length];
            }

            for (int col = 0; col < rowData.length; col++) {

                if (rowData.length != getWidth()) {
                    throw new InvalidMazeException("Different number of characters in row " + (row + 1) + ".");
                }

                Cell curCell;

                switch (rowData[col]) {
                    case "0":
                        curCell = Cell.WALL;
                        break;
                    case "1":
                        curCell = Cell.EMPTY;
                        break;
                    case "R":
                        if (foundStart) {
                            throw new InvalidMazeException("More than one starting position found.");
                        }
                        curCell = Cell.START;
                        foundStart = true;
                        break;
                    case "G":
                        if (foundGoal) {
                            throw new InvalidMazeException("More than one goal found.");
                        }
                        curCell = Cell.GOAL;
                        foundGoal = true;
                        break;
                    default:
                        throw new InvalidMazeException("Invalid character in map.");
                }

                setCell(col, row, curCell);
            }

        }

        if (!foundGoal) {
            throw new InvalidMazeException("Goal not found in map.");
        }

        if (!foundStart) {
            throw new InvalidMazeException("Start position not found in map.");
        }
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

        /*
        Uses BFS algorithm to find the goal point while keeping track of the parent of each point
        (the point visited before that point)
         */

        // queue to keep track of path
        ArrayDeque<Point> queue = new ArrayDeque<>();

        // push starting point into queue
        Point startPoint = getStartingPos();
        queue.push(startPoint);

        // keep track of which points have been visited, and if visited, its parent
        Move[][] parentArray = new Move[getWidth()][getHeight()];
        parentArray[startPoint.x][startPoint.y] = new Move(null, null);

        // stores the position of the goal if found, null otherwise
        Point goalPoint = null;

        // while the queue is not empty
        while (!queue.isEmpty()) {

            // pop point from queue to explore next
            Point curPoint = queue.removeFirst();

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

                parentArray[newX][newY] = new Move(direction, curPoint);

                // add point to end of queue
                queue.addLast(new Point(newX, newY));
            }
        }

        // if no path from start to goal was found
        if (goalPoint == null) {
            return null;
        }

        // initialize path to store path from starting point to goal
        Path foundPath = new Path();

        // set the current location to the parent of the goal point
        Move currentLocation = parentArray[goalPoint.x][goalPoint.y];

        /*
        Starts with the goal point, follows the parent of each point using the parent array until the starting point
        is reached and adds the direction of each point to the path
        */

        // while the current location is not the starting position
        while (currentLocation.getmPosition() != null) {
            // add the direction of the current location to the path
            foundPath.add(currentLocation.getmDirection());
            // get the coordinates of the current point
            Point pos = currentLocation.getmPosition();
            // set the current location to the parent of the current point
            currentLocation = parentArray[pos.x][pos.y];
        }

        // reverse the path and set iterator for reading path elements
        foundPath.reverseAndFinalize();

        return foundPath;
    }
}
