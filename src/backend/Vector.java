
/*
 * Michael Pu
 * SpaceshipGame - Vector
 * November 2017
 */


package backend;

public class Vector {

	private static final int MAX_DEGREES = 360;
	private double magnitude;
	private double direction; // 0-359 degrees

	public Vector() {
		this(0, 0);
	}

	/**
	 * Creates a Vector object using magnitude and direction
	 * 
	 * @param magnitude
	 *            Magnitude of vector
	 * @param direction
	 *            Direction of vector
	 */
	public Vector(double magnitude, double direction) {
		setVector(magnitude, direction);
	}

	/**
	 * Creates a Vector object using X and Y components
	 * 
	 * @param xComponent
	 *            X Component of vector
	 * @param yComponent
	 *            Y Component of vector
	 * @param xyComponent
	 *            To differentiate between magnitude and direction constructor
	 */
	public Vector(double xComponent, double yComponent, boolean xyComponent) {
		setXY(xComponent, yComponent);
	}

	private double[] componentToMagnitudeAndDirection(double xComponent, double yComponent) {
		double[] result = new double[2];

		result[0] = Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponent, 2));

		if (xComponent == 0) { // prevent divide by zero error
			result[1] = yComponent == 0 ? 180 : yComponent > 0 ? 90 : 270;
		} else {
			result[1] = Math.toDegrees(Math.atan(yComponent / xComponent));
			if (xComponent < 0 && yComponent > 0) {
				// quadrant 2
				result[1] += 180;
			} else if (xComponent < 0 && yComponent <= 0) {
				// quadrant 3
				result[1] += 180;
			} else if (xComponent > 0 && yComponent < 0) {
				// quadrant 4
				result[1] += 360;
			}
		}

		return result;

	}

	protected void setXY(double xComponent, double yComponent) {
		double[] magnitudeAndDirection = componentToMagnitudeAndDirection(xComponent, yComponent);
		setVector(magnitudeAndDirection[0], magnitudeAndDirection[1]);
	}

	protected void setVector(double magnitude, double direction) {
		setMagnitude(magnitude);
		setDirection(direction);
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public void setDirection(double direction) {
		this.direction = direction % MAX_DEGREES;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public double getDirection() {
		return direction;
	}

	/**
	 * Adds two Vector objects together and returns the sum
	 * 
	 * @param vector2
	 *            Vector object to add
	 * @return Vector object representing the sum
	 */
	public Vector add(Vector vector2) {
		double newXComponent = this.getXComponent() + vector2.getXComponent();
		double newYComponent = this.getYComponent() + vector2.getYComponent();
		newXComponent = Math.abs(newXComponent) > Utilities.EPSILON ? newXComponent : 0;
		newYComponent = Math.abs(newYComponent) > Utilities.EPSILON ? newYComponent : 0;
		return new Vector(newXComponent, newYComponent, true);
	}

	/**
	 * @return magnitude in the X direction, 0 if it is less than
	 *         {@value backend.Utilities#EPSILON}
	 */
	public double getXComponent() {
		return magnitude < Utilities.EPSILON ? 0 : magnitude * Math.cos(Math.toRadians(direction));
	}

	/**
	 * @return magnitude in the Y direction, 0 if it is less than
	 *         {@value backend.Utilities#EPSILON}
	 */
	public double getYComponent() {
		return magnitude < Utilities.EPSILON ? 0 : magnitude * Math.sin(Math.toRadians(direction));
	}

}
