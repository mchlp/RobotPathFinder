/*
 * Michael Pu
 * RobotPathFinder - Maze
 * November 2017
 */

package algorithm;

import java.awt.*;

public class Maze {

    private Cell[][] mMaze;

    private Point startingPos;

    public Maze(int width, int height) {
        mMaze = new Cell[width][height];
    }

    public void setCell(int cellX, int cellY, Cell type) {
        if (type == Cell.START) {
            startingPos = new Point(cellX, cellY);
        }
        mMaze[cellX][cellY] = type;
    }

    public Point getStartingPos() {
        return startingPos;
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
}
