/*
 * Michael Pu
 * RobotPathFinder - ScoreIndicator
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package frontend;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreIndicator {

    private static final int FONT_SIZE = 20;

    private Robot mRobot;
    private Text mText;

    public ScoreIndicator(Text text) {
        mText = text;
        mText.setFont(new Font(FONT_SIZE));
    }

    public void setmRobot(Robot mRobot) {
        this.mRobot = mRobot;
    }

    public void update(double deltaTime) {
        mText.setText("Moves: " + mRobot.getmNumMoves());
    }

}
