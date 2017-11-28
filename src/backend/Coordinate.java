
/*
 * Michael Pu
 * RobotPathFinder - Coordinate
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */


package backend;

/**
 * Represents a point in JavaFX by representing x and y position with double precision
 */
public class Coordinate {

	private double x;
	private double y;

	public Coordinate() {
		this(0, 0);
	}

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

    /**
     * @return A copy of the {@link Coordinate} object
     */
    public Coordinate copy() {
        return new Coordinate(getX(), getY());
    }
}
