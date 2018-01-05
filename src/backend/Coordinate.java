
/*
 * Michael Pu
 * RobotPathFinder - Coordinate
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */


package backend;

/**
 * Represents a point in JavaFX by representing x and y position with double precision.
 */
public class Coordinate {

	private double x;
	private double y;

    /**
     * Creates a {@link Coordinate} object representing the point (0, 0).
     */
	public Coordinate() {
		this(0, 0);
	}

    /**
     * Creates a {@link Coordinate} object representing the specified X and Y coordinate.
     *
     * @param x X coordinate of the point.
     * @param y Y coordinate of the point.
     */
    public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

    /**
     * @return The X coordinate of the point.
     */
    public double getX() {
		return x;
	}

    /**
     * @return The Y coordinate of the point.
     */
    public double getY() {
		return y;
	}

	/**
	 * Sets the X coordinate of the point.
	 *
	 * @param x The new X coordinate.
	 */
	public void setX(double x) {
		this.x = x;
	}

    /**
     * Sets the Y coordinate of the point.
     *
     * @param y The new Y coordinate.
     */
	public void setY(double y) {
		this.y = y;
	}

    /**
     * @return A deep copy of the {@link Coordinate} object
     */
    public Coordinate copy() {
        return new Coordinate(getX(), getY());
    }
}
