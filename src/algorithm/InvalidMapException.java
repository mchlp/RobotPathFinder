/*
 * Michael Pu
 * RobotPathFinder - InvalidMapException
 * November 2017
 */

package algorithm;

public class InvalidMapException extends Exception {

    public InvalidMapException() {
        super();
    }

    public InvalidMapException(String message) {
        super(message);
    }

    public InvalidMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMapException(Throwable cause) {
        super(cause);
    }

}
