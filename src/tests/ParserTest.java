/*
 * Michael Pu
 * RobotPathFinder - ParserTest
 * November 2017
 */

package tests;

import algorithm.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ParserTest {
    public static void main(String[] args) throws InvalidMapException{
        try {
            String file = new String(Files.readAllBytes(new File("test.txt").toPath()), "UTF-8");
            Maze maze = MazeParser.parseMaze(file);
            Path path = MazeSolver.solve(maze);

            while (path.hasNext()) {
                System.out.println(path.getNext());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
