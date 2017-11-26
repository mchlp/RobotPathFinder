/*
 * Michael Pu
 * RobotPathFinder - OutputConsole
 * November 2017
 */

package frontend;

import javafx.scene.control.TextArea;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class OutputConsole extends TextArea {

    private static final SimpleDateFormat TIME_STAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS a\n");

    public OutputConsole() {
        super();
    }

    @Override
    public void appendText(String text) {
        String timeStamp = TIME_STAMP.format(new Timestamp(System.currentTimeMillis()));
        super.appendText("\n-\n" + timeStamp + text);
    }

    public void setInitalText(String text) {
        String timeStamp = TIME_STAMP.format(new Timestamp(System.currentTimeMillis()));
        super.appendText(timeStamp + text);
    }
}
