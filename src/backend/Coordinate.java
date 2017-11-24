
/*
 * Michael Pu
 * SpaceshipGame - Coordinate
 * November 2017
 */


package backend;

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

    public void move(Velocity velocity) {
        this.x += velocity.getXSpeed();
        this.y -= velocity.getYSpeed();
    }

    /**
     * @return A copy of the {@link Coordinate} object
     */
    public Coordinate copy() {
        return new Coordinate(getX(), getY());
    }
}
