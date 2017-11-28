/*
 * Michael Pu
 * RobotPathFinder - MazeParser
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package algorithm;

public class MazeParser {

    //TODO put into Maze class

    public static Maze parseMaze(String rawData) throws InvalidMapException {

        Maze maze = null;

        boolean foundStart = false;
        boolean foundGoal = false;

        String[] allData = rawData.trim().split("\n");

        for (int row = 0; row < allData.length; row ++ ) {
            String[] rowData = allData[row].split("[ \t]+");

            if (row == 0) {
                maze = new Maze(rowData.length, allData.length);
            }

            for (int col = 0; col < rowData.length; col++) {

                if (rowData.length != maze.getWidth()) {
                    throw new InvalidMapException("Different number of characters in row " + (row + 1) + ".");
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
                            throw new InvalidMapException("More than one starting position found.");
                        }
                        curCell = Cell.START;
                        foundStart = true;
                        break;
                    case "G":
                        if (foundGoal) {
                            throw new InvalidMapException("More than one goal found.");
                        }
                        curCell = Cell.GOAL;
                        foundGoal = true;
                        break;
                    default:
                        throw new InvalidMapException("Invalid character in map.");
                }

                maze.setCell(col, row, curCell);
            }

        }

        if (!foundGoal) {
            throw new InvalidMapException("Goal not found in map.");
        }

        if (!foundStart) {
            throw new InvalidMapException("Start position not found in map.");
        }

        return maze;

    }

}
