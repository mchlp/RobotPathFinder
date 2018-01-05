/*
 * Michael Pu
 * RobotPathFinder - Maze
 * ICS3U1 - Mr. Radulovic
 * January 05, 2018
 */

package algorithm;

import java.awt.*;

/**
 * Represents a maze, including the content of each cell, the starting point, and the ending point.
 */
public class Maze {

    // characters representing each cell in the maze
    private static final String WALL_CHAR = "0";
    private static final String EMPTY_CHAR = "1";
    private static final String START_CHAR = "R";
    private static final String GOAL_CHAR = "G";

    // regex expression for the separator character between each cell in the maze
    private static final String CELL_SEPARATOR_CHAR = "\\s";

    // regex expression for the separator character between each row in the maze
    private static final String ROW_SEPARATOR_CHAR = "\n";

    // 2D array representing cells in maze
    private Cell[][] mMaze;
    // starting position
    private Point startingPos;
    // goal position
    private Point endingPos;

    /**
     * Creates a map object from a string of map data.
     *
     * @param mapData The string of data representing the map.
     * @throws InvalidMazeException If there is an error parsing the map.
     */
    public Maze(String mapData) throws InvalidMazeException {

        // splits the map data into rows
        String[] allData = mapData.trim().split(ROW_SEPARATOR_CHAR);

        // flags to ensure that only one start and goal cell is in the map
        boolean foundStart = false;
        boolean foundGoal = false;

        // loops through all the rows
        for (int row = 0; row < allData.length; row++) {

            // splits the row data into columns
            String[] rowData = allData[row].split(CELL_SEPARATOR_CHAR);

            // if this is the first row, initialize the array to store the maze
            if (row == 0) {
                mMaze = new Cell[rowData.length][allData.length];
            }

            // loops through all columns in the row
            for (int col = 0; col < rowData.length; col++) {

                // if the width of this row is different
                if (rowData.length != getWidth()) {
                    throw new InvalidMazeException("Different number of characters in row " + (row + 1) + ".");
                }

                // create cell that represents the current column
                Cell curCell;

                switch (rowData[col]) {
                    case WALL_CHAR:
                        curCell = Cell.WALL;
                        break;
                    case EMPTY_CHAR:
                        curCell = Cell.EMPTY;
                        break;
                    case START_CHAR:
                        if (foundStart) {
                            // if found start already, there is more than one starting position
                            throw new InvalidMazeException("More than one starting position found.");
                        }
                        curCell = Cell.START;
                        foundStart = true;
                        break;
                    case GOAL_CHAR:
                        if (foundGoal) {
                            // if found goal already, there is more than one goal
                            throw new InvalidMazeException("More than one goal found.");
                        }
                        curCell = Cell.GOAL;
                        foundGoal = true;
                        break;
                    default:
                        // if the character does not match any of the predefined ones, it is invalid
                        throw new InvalidMazeException(
                                "Invalid character in map: row " + (row + 1) + " column " + (col + 1));
                }

                // applies the cell to the current position in the array
                setCell(col, row, curCell);
            }

        }

        if (!foundGoal) {
            // if no goal was found
            throw new InvalidMazeException("Goal not found in map.");
        }

        if (!foundStart) {
            // if no start was found
            throw new InvalidMazeException("Start position not found in map.");
        }
    }

    /**
     * Sets the content of a cell in the maze.
     *
     * @param cellX X position of the cell.
     * @param cellY Y position of the cell.
     * @param type  The content of the cell.
     */
    public void setCell(int cellX, int cellY, Cell type) {

        if (type == Cell.START) {
            startingPos = new Point(cellX, cellY);
        }

        if (type == Cell.GOAL) {
            endingPos = new Point(cellX, cellY);
        }

        mMaze[cellX][cellY] = type;
    }

    /**
     * @return The starting point of the maze.
     */
    public Point getStartingPos() {
        return startingPos;
    }

    /**
     * @return The width of the maze (number of columns).
     */
    public int getWidth() {
        return mMaze.length;
    }

    /**
     * @return The height of the maze (number of rows).
     */
    public int getHeight() {
        return mMaze[0].length;
    }

    /**
     * @param cellX X position of the cell.
     * @param cellY Y position of the cell.
     * @return the contents of the cell.
     */
    public Cell getCell(int cellX, int cellY) {
        return mMaze[cellX][cellY];
    }

    /**
     * Computes the shortest path from the starting point to the ending point in the maze using a modified version of
     * the Breath-First Search algorithm. Using this algorithm, the first path that reaches the goal will always be the
     * shortest path. This algorithm has been tested and verified to accurately compute the shortest path in maps up to
     * a size of 400 by 400 cells.
     * <p>
     * This algorithm first visits the starting position by pushing it in to the queue. Every loop, the first cell in
     * the queue will be popped out and visited. For every cell that it visits, it will first check if it is the ending
     * point. If the ending point has been found, the loop is stopped. This is the shortest path because the points are
     * visited in order of distance away of the starting point. If there was a shorter path to the ending point, it
     * would have already been discovered before the current one. If the current point is not the ending point, it will
     * check all four of its neighbours (up, down, left, right). It will add all neighbouring cells that are within the
     * bounds of the map, have not been visited, and are not walls to the end of the queue. For each of these visitable
     * neighbours, it will set itself as the parent cell of its neighbour and record the direction from which it reached
     * the neighbouring cell in the parentArray. This will continue until either the queue is empty or a goal has been
     * found. If the queue empties before the goal has been found, it means that the algorithm was unable to find a path
     * from the starting point to the ending point. If a goal has been found, it will start with the ending point and
     * trace backwards through the map to find the route it took using the information stored in the parent array. Once
     * the starting point is reached (the parent of this point in the parentArray would be null), the entire path is
     * reversed, an iterator for the path is created, and the path object returned.
     *
     * @return the shortest path from the starting point to the ending point, null if no path found
     */
    public Path solveMaze() {

        // queue to keep track of which points to visit next
        PointQueue queue = new PointQueue();

        // push starting point into queue
        Point startPoint = getStartingPos();
        queue.push(startPoint);

        // keep track of which points have been visited, and if visited, its parent and the direction that led to the point
        Move[][] parentArray = new Move[getWidth()][getHeight()];
        parentArray[startPoint.x][startPoint.y] = new Move(null, null);

        // stores the position of the goal if found, null otherwise
        Point goalPoint = null;

        // while the queue is not empty
        while (!queue.isEmpty()) {

            // pop point from queue to visit
            Point curPoint = queue.pop();

            // if the current point is the goal
            if (getCell(curPoint.x, curPoint.y) == Cell.GOAL) {
                goalPoint = curPoint;
                break;
            }

            // explore all four adjacent cells around the current point
            for (Direction direction : Direction.values()) {

                // coordinates of new point
                int newX = curPoint.x + (int) direction.change.getX();
                int newY = curPoint.y + (int) direction.change.getY();

                // if point is outside the array, skip this point
                if (newX < 0 || newY < 0 || newX >= getWidth() || newY >= getHeight())
                    continue;

                // if point has already been visited, skip the point
                if (parentArray[newX][newY] != null)
                    continue;

                // if point is a wall, skip the point
                if (getCell(newX, newY) == Cell.WALL)
                    continue;

                // set the originating point and direction moved in the parent array
                parentArray[newX][newY] = new Move(direction, curPoint);

                // add the new point to end of queue
                queue.push(new Point(newX, newY));
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
