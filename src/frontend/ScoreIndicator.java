/*
 * Michael Pu
 * RobotPathFinder - ScoreIndicator
 * November 2017
 */

package frontend;

import backend.Sprite;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreIndicator extends Sprite {

    private static final int FONT_SIZE = 20;

    private Robot mRobot;
    private Text mText;

    public ScoreIndicator(Text text) {
        super(new Text());
        mText = (Text) mNode;
        mText.setFont(new Font(FONT_SIZE));
    }

    public void setmRobot(Robot mRobot) {
        this.mRobot = mRobot;
    }

    @Override
    public void update(double deltaTime) {
        mText.setText("Moves: " + mRobot.getmNumMoves());
    }

}
