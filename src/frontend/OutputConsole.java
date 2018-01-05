/*
 * Michael Pu
 * RobotPathFinder - OutputConsole
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package frontend;

import javafx.scene.control.TextArea;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * A {@link TextArea} with timestamps to display output of a program.
 */
public class OutputConsole extends TextArea {

    // format to display timestamp
    private static final SimpleDateFormat TIME_STAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS a\n");

    public OutputConsole() {
        super();
    }

    /**
     * Adds text to bottom of the existing text with timestamp.
     *
     * @param text Text to add.
     */
    @Override
    public void appendText(String text) {
        String timeStamp = TIME_STAMP.format(new Timestamp(System.currentTimeMillis()));
        super.appendText("\n-\n" + timeStamp + text);
    }

    /**
     * Sets the first line of text with timestamp.
     *
     * @param text First line of text to add.
     */
    public void setInitalText(String text) {
        String timeStamp = TIME_STAMP.format(new Timestamp(System.currentTimeMillis()));
        super.appendText(timeStamp + text);
    }
}
