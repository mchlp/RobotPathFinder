/*
 * Michael Pu
 * RobotPathFinder - ParserTest
 * November 2017
 */

package tests;

import backend.Maze;
import backend.MazeParser;
import backend.MazeSolver;
import backend.Path;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ParserTest {
    public static void main(String[] args) {
        try {
            String file = new String(Files.readAllBytes(new File("test.txt").toPath()), "UTF-8");
            System.out.println(file);
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
