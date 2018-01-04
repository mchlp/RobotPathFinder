/*
 * Michael Pu
 * RobotPathFinder - MazeTest
 * ICS3U1 - Mr. Radulovic
 * December 22, 2017
 */

package tests;


import algorithm.*;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class AlgorithmTest {

    @Test
    public void testParser() throws InvalidMazeException {

        String testFile = null;
        try {
            testFile = new String(Files.readAllBytes(new File("src/tests/test.txt").toPath()), "UTF-8");
            assertEquals(testFile,
                    "0 0 0 0 0 0 0 0 0 0\n" + "0 0 1 1 1 1 1 1 1 R\n" + "0 0 1 1 0 0 1 0 1 0\n"
                            + "0 0 0 1 0 0 1 0 1 0\n" + "0 0 1 1 1 1 1 0 1 0\n" + "0 1 1 0 0 1 0 0 1 0\n"
                            + "0 0 1 0 0 1 1 1 1 0\n" + "0 G 0 0 0 1 0 0 1 0\n" + "0 1 0 0 1 1 1 1 1 0\n"
                            + "0 1 1 1 1 1 1 0 0 0\n" + "0 1 1 1 1 1 1 0 0 0");
            Maze maze = new Maze(testFile);
            assertEquals(maze.getHeight(), 11);
            assertEquals(maze.getWidth(), 10);
            assertEquals(maze.getStartingPos(), new Point(9, 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSolver() throws InvalidMazeException {
        Direction[] answer = {Direction.LEFT, Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.DOWN,
                Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.DOWN,
                Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.UP,
                Direction.UP,};

        Maze maze = new Maze("0 0 0 0 0 0 0 0 0 0\n" + "0 0 1 1 1 1 1 1 1 R\n" + "0 0 1 1 0 0 1 0 1 0\n"
                + "0 0 0 1 0 0 1 0 1 0\n" + "0 0 1 1 1 1 1 0 1 0\n" + "0 1 1 0 0 1 0 0 1 0\n"
                + "0 0 1 0 0 1 1 1 1 0\n" + "0 G 0 0 0 1 0 0 1 0\n" + "0 1 0 0 1 1 1 1 1 0\n"
                + "0 1 1 1 1 1 1 0 0 0\n" + "0 1 1 1 1 1 1 0 0 0\n");
        Path path = maze.solveMaze();

        int i = 0;
        while (path.hasNext()) {
            assertEquals(path.getNext(), answer[i]);
            i++;
        }
    }

    @Test
    public void testFull() throws InvalidMazeException {

        Direction[] answer = {Direction.LEFT, Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.DOWN,
                Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.DOWN,
                Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.UP,
                Direction.UP,};

        String testFile = null;
        Path path = null;
        try {
            testFile = new String(Files.readAllBytes(new File("src/tests/test.txt").toPath()), "UTF-8");
            Maze maze = new Maze(testFile);
            path = maze.solveMaze();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (path.hasNext()) {
            assertEquals(path.getNext(), answer[i]);
            i++;
        }

    }

}