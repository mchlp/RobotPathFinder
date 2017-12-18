/*
 * Michael Pu
 * RobotPathFinder - InvalidMazeException
 * ICS3U1 - Mr. Radulovic
 * December 17, 2017
 */

package algorithm;

/**
 * Exception thrown when an invalid map is loaded
 */
public class InvalidMazeException extends Exception {
    public InvalidMazeException(String message) {
        super(message);
    }
}
