/*
 * Michael Pu
 * RobotPathFinder - InvalidMapException
 * November 2017
 */

package algorithm;

/**
 * Exception thrown when an invalid map is loaded
 */
public class InvalidMapException extends Exception {
    public InvalidMapException(String message) {
        super(message);
    }
}
