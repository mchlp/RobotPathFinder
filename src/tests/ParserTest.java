/*
 * Michael Pu
 * RobotPathFinder - ParserTest
 * ICS3U1 - Mr. Radulovic
 * December 17, 2017
 */

package tests;

import algorithm.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ParserTest {
    public static void main(String[] args) throws InvalidMazeException {
        try {
            String file = new String(Files.readAllBytes(new File("/home/mpu/programming/intellij-eclipse/RobotPathFinder/src/tests/test.txt").toPath()), "UTF-8");
            Maze maze = new Maze(file);
            Path path = maze.solveMaze();

            while (path.hasNext()) {
                System.out.println(path.getNext());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
