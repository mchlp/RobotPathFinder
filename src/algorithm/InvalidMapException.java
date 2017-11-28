/*
 * Michael Pu
 * RobotPathFinder - InvalidMapException
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
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
