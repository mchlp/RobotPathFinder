/*
 * Michael Pu
 * RobotPathFinder - Robot
 * November 2017
 */

package frontend;

import algorithm.Direction;
import algorithm.Path;
import backend.Coordinate;
import backend.Sprite;
import backend.Utilities;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Robot extends Sprite {

    private static final String IMAGE_ROBOT = Utilities.IMAGE_DIRECTORY + "robot.png";

    private static final double MOVE_DURATION = 0.5;
    private static final Color VISITED_CELL_COLOUR = Color.YELLOW;

    private ImageView mImageView;
    private Path mPath;
    private double mSquareSideLength;
    private Direction mCurrentDirection;
    private Coordinate mPositionBeforeMove;
    private Point mPositionInGraphAfterMove;
    private double mMoveCountdown = MOVE_DURATION;
    private boolean mDoneMoving = false;
    private int mFacingDirection = 0;
    private Rectangle[][] mMazeRect;
    private int mNumMoves = 0;

    public Robot(ImageView image, Path path, Point startingPosition, double squareSideLength, Rectangle[][] mazeRect) {

        super(image);

        double imageMaxDimension = squareSideLength * 0.8;

        System.out.println(startingPosition.x + " " + startingPosition.y);

        mImageView = (ImageView) mNode;
        mImageView.setImage(new Image(Utilities.getResourceAsStream(IMAGE_ROBOT)));
        mImageView.setPreserveRatio(true);
        if (mImageView.getFitWidth() > mImageView.getFitHeight()) {
            mImageView.setFitWidth(imageMaxDimension);
        } else {
            mImageView.setFitHeight(imageMaxDimension);
        }

        mPath = path;
        mMazeRect = mazeRect;
        mSquareSideLength = squareSideLength;
        mPositionInGraphAfterMove = (Point) startingPosition.clone();
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
                mNumMoves++;

                double beforeMoveCentreX = mPositionInGraphAfterMove.x * mSquareSideLength + mSquareSideLength / 2.0;
                double beforeMoveCentreY = mPositionInGraphAfterMove.y * mSquareSideLength + mSquareSideLength / 2.0;
                mPositionBeforeMove = new Coordinate(beforeMoveCentreX, beforeMoveCentreY);

                mMazeRect[mPositionInGraphAfterMove.x][mPositionInGraphAfterMove.y].setFill(VISITED_CELL_COLOUR);

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

    public int getmNumMoves() {
        return mNumMoves;
    }
}
