/*
 * Michael Pu
 * RobotPathFinder - Robot
 * November 2017
 */

package backend;

import algorithm.Direction;
import algorithm.Path;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class Robot extends Sprite {

    private static final String IMAGE_ROBOT = Utilities.IMAGE_DIRECTORY + "robot.png";

    private static final double MOVE_DURATION = 1;

    private ImageView mImageView;
    private Path mPath;
    private double mSquareSideLength;
    private Direction mCurrentDirection;
    private Coordinate mPositionBeforeMove;
    private Point mPositionInGraphAfterMove;
    private double mMoveCountdown = MOVE_DURATION;
    private boolean mDoneMoving = false;
    private int mFacingDirection = 0;

    public Robot(ImageView image, Path path, Point startingPosition, double squareSideLength) {

        super(image);

        double imageMaxDimension = squareSideLength * 0.8;

        mImageView = (ImageView) mNode;
        mImageView.setImage(new Image(Utilities.getResourceAsStream(IMAGE_ROBOT)));
        mImageView.setPreserveRatio(true);
        if (mImageView.getFitWidth() > mImageView.getFitHeight()) {
            mImageView.setFitWidth(imageMaxDimension);
        } else {
            mImageView.setFitHeight(imageMaxDimension);
        }

        mPath = path;
        mSquareSideLength = squareSideLength;
        mPositionInGraphAfterMove = startingPosition;
        setCentreOfImage(new Coordinate(startingPosition.x * mSquareSideLength + mSquareSideLength / 2.0, startingPosition.y * mSquareSideLength + mSquareSideLength / 2.0));

        updateImageViewPosition();
    }

    private void updateImageViewPosition() {
        mImageView.setX(mPosition.getX());
        mImageView.setY(mPosition.getY());
    }

    private void setCentreOfImage(Coordinate centrePosition) {
        double width = mImageView.getBoundsInParent().getWidth();
        double height = mImageView.getBoundsInParent().getHeight();
        mPosition.setX(centrePosition.getX() - (width / 2));
        mPosition.setY(centrePosition.getY() - (height / 2));
        updateImageViewPosition();
    }

    @Override
    public void update(double deltaTime) {
        if (!mDoneMoving) {
            mMoveCountdown += deltaTime;
            if (mMoveCountdown >= MOVE_DURATION) {

                mMoveCountdown = 0;

                double beforeMoveCentreX = mPositionInGraphAfterMove.x * mSquareSideLength + mSquareSideLength / 2.0;
                double beforeMoveCentreY = mPositionInGraphAfterMove.y * mSquareSideLength + mSquareSideLength / 2.0;
                mPositionBeforeMove = new Coordinate(beforeMoveCentreX, beforeMoveCentreY);

                if (mPath.hasNext()) {
                    mCurrentDirection = mPath.getNext();
                    mPositionInGraphAfterMove.x += mCurrentDirection.change.x;
                    mPositionInGraphAfterMove.y += mCurrentDirection.change.y;
                    int imageRotateAmount = mFacingDirection - mCurrentDirection.direction;
                    mImageView.setRotate(imageRotateAmount);

                } else {
                    mPosition = mPositionBeforeMove.copy();
                    mDoneMoving = true;
                    return;
                }
            }

            double offsetX = mCurrentDirection.change.x * mSquareSideLength * (mMoveCountdown / MOVE_DURATION);
            double offsetY = mCurrentDirection.change.y * mSquareSideLength * (mMoveCountdown / MOVE_DURATION);

            Coordinate newCentre = new Coordinate(mPositionBeforeMove.getX() + offsetX, mPositionBeforeMove.getY() + offsetY);

            setCentreOfImage(newCentre);

            updateImageViewPosition();
        }
    }
}
