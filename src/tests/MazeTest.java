/*
 * Michael Pu
 * RobotPathFinder - MazeParserTest
 * November 2017
 */

package tests;


import algorithm.*;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class MazeTest {

    @Test
    public void testParser() throws InvalidMapException {

        String testFile = null;
        try {
            testFile = new String(Files.readAllBytes(new File("test.txt").toPath()), "UTF-8");
            assertEquals(testFile,
                    "0 0 0 0 0 0 0 0 0 0\n" + "0 0 1 1 1 1 1 1 1 R\n" + "0 0 1 1 0 0 1 0 1 0\n"
                            + "0 0 0 1 0 0 1 0 1 0\n" + "0 0 1 1 1 1 1 0 1 0\n" + "0 1 1 0 0 1 0 0 1 0\n"
                            + "0 0 1 0 0 1 1 1 1 0\n" + "0 G 0 0 0 1 0 0 1 0\n" + "0 1 0 0 1 1 1 1 1 0\n"
                            + "0 1 1 1 1 1 1 0 0 0\n" + "0 1 1 1 1 1 1 0 0 0\n");
            Maze maze = MazeParser.parseMaze(testFile);
            assertEquals(maze.getHeight(), 11);
            assertEquals(maze.getWidth(), 10);
            assertEquals(maze.getStartingPos(), new Point(9, 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSolver() throws InvalidMapException {
        Direction[] answer = {Direction.LEFT, Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.DOWN,
                Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.DOWN,
                Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.UP,
                Direction.UP,};

        Maze maze = MazeParser.parseMaze("0 0 0 0 0 0 0 0 0 0\n" + "0 0 1 1 1 1 1 1 1 R\n" + "0 0 1 1 0 0 1 0 1 0\n"
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
    public void testFull() throws InvalidMapException {

        Direction[] answer = {Direction.LEFT, Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.DOWN,
                Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.LEFT, Direction.LEFT, Direction.DOWN,
                Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.UP,
                Direction.UP,};

        String testFile = null;
        Path path = null;
        try {
            testFile = new String(Files.readAllBytes(new File("test.txt").toPath()), "UTF-8");
            Maze maze = MazeParser.parseMaze(testFile);
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