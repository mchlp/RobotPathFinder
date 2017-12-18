/*
 * Michael Pu
 * RobotPathFinder - ScoreIndicator
 * ICS3U1 - Mr. Radulovic
 * December 01, 2017
 */

package frontend;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Displays the number of moves made by the robot
 */
public class ScoreIndicator {

    // size of the font to display the score
    private static final int FONT_SIZE = 20;

    private Robot mRobot;
    private Text mText;

    public ScoreIndicator(Text text) {
        mText = text;
        mText.setFont(new Font(FONT_SIZE));
    }

    /**
     * Set the robot to display the score of
     *
     * @param mRobot the score of the {@link Robot} to display
     */
    public void setmRobot(Robot mRobot) {
        this.mRobot = mRobot;
    }

    /**
     * Updates the score (the number of moves made) of the robot
     *
     * @param deltaTime the amount of time passed since the last update
     */
    public void update(double deltaTime) {
        mText.setText("Moves: " + mRobot.getmNumMoves());
    }

}
