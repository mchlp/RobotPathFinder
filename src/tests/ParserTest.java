/*
 * Michael Pu
 * RobotPathFinder - ParserTest
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
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
            Path path = maze.solveMaze();

            while (path.hasNext()) {
                System.out.println(path.getNext());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
