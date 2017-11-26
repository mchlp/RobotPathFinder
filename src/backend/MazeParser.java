/*
 * Michael Pu
 * RobotPathFinder - MazeParser
 * November 2017
 */

package backend;

public class MazeParser {

    public static Maze parseMaze(String rawData) {

        Maze maze = null;

        String[] allData = rawData.split("\n");

        for (int row = 0; row < allData.length; row ++ ) {
            String[] rowData = allData[row].split(" +");

            if (row == 0) {
                maze = new Maze(rowData.length, allData.length);
            }

            for (int col = 0; col < rowData.length; col++) {

                Cell curCell;

                switch (rowData[col]) {
                    case "0":
                        curCell = Cell.WALL;
                        break;
                    case "1":
                        curCell = Cell.EMPTY;
                        break;
                    case "R":
                        curCell = Cell.START;
                        break;
                    case "G":
                        curCell = Cell.GOAL;
                        break;
                    default:
                        throw new RuntimeException("Invalid cell type.");
                }

                maze.setCell(col, row, curCell);
            }

        }

        return  maze;

    }

}
