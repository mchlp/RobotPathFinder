/*
 * Michael Pu
 * RobotPathFinder - Robot
 * ICS3U1 - Mr. Radulovic
 * December 22, 2017
 */

package frontend;

import java.awt.Point;

import algorithm.Direction;
import algorithm.Path;
import backend.Coordinate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Represents a robot moving around the maze.
 */
public class Robot {

    // location of image of robot
	private static final String IMAGE_ROBOT = "/images/robot.png";
    // amount of time to take for each move (seconds)
	private static final double MOVE_DURATION = 0.5;
	// colour of visited cell
	private static final Color VISITED_CELL_COLOUR = Color.YELLOW;

	// position of robot
	private Coordinate mPosition;
	// imageView of robot
	private ImageView mImageView;
	// path for robot to follow
	private Path mPath;
	// width of each cell (pixels)
	private double mSquareSideLength;
	// direction robot is current proceeding in
	private Direction mCurrentDirection;
	// position of robot before the move (pixels, pixels)
	private Coordinate mPositionBeforeMove;
	// position of robot in the maze after the move (col, row)
	private Point mPositionInGraphAfterMove;
	// time before move should be complete (seconds)
	private double mMoveCountdown = MOVE_DURATION;
	// if the robot has finished the path
	private boolean mDoneMoving = false;
	// the direction the robot is currently facing (angle, up = 0 degrees, right = 90 degrees, down = 180 degrees, left = 270 degrees)
	private int mFacingDirection = 0;
	// the graphicsContext for the canvas that the squares are drawn on
	private GraphicsContext mGraphicsContext;
	// the number of moves the robot has made
	private int mNumMoves = 0;

	public Robot(ImageView image, Path path, Point startingPosition, double squareSideLength, GraphicsContext graphicsContext) {

		// set dimensions for robot image
		double imageMaxDimension = squareSideLength * 0.8;

		// set up imageView of robot
		mImageView = image;
		mImageView.setImage(new Image(IMAGE_ROBOT));
		mImageView.setPreserveRatio(true);

		// resize image depending if the width or length of image is larger
		if (mImageView.getFitWidth() > mImageView.getFitHeight()) {
			mImageView.setFitWidth(imageMaxDimension);
		} else {
			mImageView.setFitHeight(imageMaxDimension);
		}

		// set up member variables
		mPath = path;
        mGraphicsContext = graphicsContext;
		mPosition = new Coordinate();
		mSquareSideLength = squareSideLength;
		mPositionInGraphAfterMove = (Point) startingPosition.clone();

		// sets initial position of the robot
		setCentreOfImage(new Coordinate(startingPosition.x * mSquareSideLength + mSquareSideLength / 2.0,
				startingPosition.y * mSquareSideLength + mSquareSideLength / 2.0));

		// update the imageView of the robot
		updateImageViewPosition();
	}

	private void updateImageViewPosition() {
		mImageView.setX(mPosition.getX());
		mImageView.setY(mPosition.getY());
	}

    /**
     * Sets the position of the imageView so that the centre of the imageView is the provided coordinate
     *
     * @param centrePosition the coordinate representing where the centre of the imageView should be
     */
    private void setCentreOfImage(Coordinate centrePosition) {

        // get the current width and height of the image
		double imageWidth = mImageView.getBoundsInParent().getWidth();
		double imageHeight = mImageView.getBoundsInParent().getHeight();

		// depending on the direction the robot is facing, the centre point of the robot
        // will be different as the dimensions retrieved earlier are of the original unrotated image
		if (mCurrentDirection == Direction.UP || mCurrentDirection == Direction.DOWN) {
            mPosition.setX(centrePosition.getX() - (imageWidth / 2));
            mPosition.setY(centrePosition.getY() - (imageHeight / 2));
        } else {
            mPosition.setX(centrePosition.getX() - (imageHeight / 2));
            mPosition.setY(centrePosition.getY() - (imageWidth / 2));
        }
        updateImageViewPosition();
	}

	public void update(double deltaTime) {
        // update only if the robot has not finished the path
		if (!mDoneMoving) {

		    // add to the amount of time spend on the current move
			mMoveCountdown += deltaTime;

			// if the time spent on the current move has reached the time for each move
			if (mMoveCountdown >= MOVE_DURATION) {

			    // reset move timer
				mMoveCountdown = 0;
				// add number of moves
				mNumMoves++;

				// set new before move position (used to calculate the position of the robot in the middle of a move)
				double beforeMoveCentreX = mPositionInGraphAfterMove.x * mSquareSideLength + mSquareSideLength / 2.0;
				double beforeMoveCentreY = mPositionInGraphAfterMove.y * mSquareSideLength + mSquareSideLength / 2.0;
				mPositionBeforeMove = new Coordinate(beforeMoveCentreX, beforeMoveCentreY);

				// set the colour of the previous cell to the visited colour
                double movedRectXPos = mPositionInGraphAfterMove.x*mSquareSideLength;
                double movedRectYPos = mPositionInGraphAfterMove.y*mSquareSideLength;
                mGraphicsContext.setFill(VISITED_CELL_COLOUR);
                mGraphicsContext.fillRect(movedRectXPos, movedRectYPos, mSquareSideLength, mSquareSideLength);
                mGraphicsContext.strokeRect(movedRectXPos, movedRectYPos, mSquareSideLength, mSquareSideLength);

				// if there are still moves left in the path
				if (mPath.hasNext()) {
				    // get the next move from the path
					mCurrentDirection = mPath.getNext();
					// set the position of of the robot in the graph after this move
					mPositionInGraphAfterMove.x += mCurrentDirection.change.getX();
					mPositionInGraphAfterMove.y += mCurrentDirection.change.getY();
					// rotate the robot to face the direction it is moving
					int imageRotateAmount = mFacingDirection - mCurrentDirection.direction;
					mImageView.setRotate(imageRotateAmount);

				} else {
				    // when the robot has finished the path
					mPosition = mPositionBeforeMove.copy();
					mDoneMoving = true;
					return;
				}
			}

			// calculate the distance the robot is away from its last cell according to the amount of time that has elapsed since the beginning of the last move
			double offsetX = mCurrentDirection.change.getX() * mSquareSideLength * (mMoveCountdown / MOVE_DURATION);
			double offsetY = mCurrentDirection.change.getY() * mSquareSideLength * (mMoveCountdown / MOVE_DURATION);

			// calculate and set the new centre position of the image using the offset calculated earlier
			Coordinate newCentre = new Coordinate(mPositionBeforeMove.getX() + offsetX,
					mPositionBeforeMove.getY() + offsetY);
			setCentreOfImage(newCentre);

			// update the imageView of the robot
			updateImageViewPosition();
		}
	}

    /**
     * @return the number of moves the robot has made
     */
    public int getmNumMoves() {
		return mNumMoves;
	}
}
