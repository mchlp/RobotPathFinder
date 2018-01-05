/*
 * Michael Pu
 * RobotPathFinder - InvalidMazeException
 * ICS3U1 - Mr. Radulovic
 * January 04, 2018
 */

package algorithm;

/**
 * Exception thrown when an invalid map is loaded.
 */
public class InvalidMazeException extends Exception {
    public InvalidMazeException(String message) {
        super(message);
    }
}
