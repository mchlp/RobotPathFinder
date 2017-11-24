
/*
 * Michael Pu
 * SpaceshipGame - Velocity
 * November 2017
 */


package backend;

public class Velocity {

	private Vector mVector = new Vector();

	public Velocity() {
		mVector.setVector(0, 0);
	}

	/**
	 * Creates a Velocity object using a vector object
	 * 
	 * @param vector
	 *            a Vector object representing the speed and direction
	 */
	public Velocity(Vector vector) {
		mVector = vector;
	}

	/**
	 * Creates a Velocity object using X and Y speeds
	 * 
	 * @param xSpeed
	 *            X Speed of velocity in m/s
	 * @param ySpeed
	 *            Y Speed of velocity in m/s
	 * @param xySpeed
	 *            To differentiate between speed and direction constructor
	 */
	public Velocity(double xSpeed, double ySpeed, boolean xySpeed) {
		mVector.setXY(xSpeed, ySpeed);
	}

	/**
	 * Creates a Velocity object from speed and direction
	 * 
	 * @param speed
	 *            Speed of velocity in m/s
	 * @param direction
	 *            Direction of velocity in degrees (0-365 inclusive)
	 */
	public Velocity(double speed, double direction) {
		setVelocity(speed, direction);
	}

	public void setVelocity(double speed, double direction) {
		setSpeed(speed);
		setDirection(direction);
	}

	public void setSpeed(double speed) {
		mVector.setMagnitude(speed);
	}

	public void setDirection(double direction) {
		mVector.setDirection(direction);
	}

	public double getSpeed() {
		return mVector.getMagnitude();
	}

	public double getDirection() {
		return mVector.getDirection();
	}

	/**
	 * Adds two Velocity objects together and returns the sum
	 * 
	 * @param velocity2
	 *            Velocity object to add
	 * @return Velocity object representing the sum
	 */
	public Velocity add(Velocity velocity2) {
		return new Velocity(mVector.add(velocity2.mVector));
	}

	/**
	 * @return Speed in the X direction
	 */
	public double getXSpeed() {
		return mVector.getXComponent();
	}

	/**
	 * @return Speed in the Y direction
	 */
	public double getYSpeed() {
		return mVector.getYComponent();
	}

	/**
	 * 
	 * @param acceleration
	 *            An acceleration object representing how much to accelerate by
	 * @return the new velocity
	 */
	public Velocity accelerate(Acceleration acceleration) {
		return this.add(new Velocity(acceleration.getRate(), acceleration.getDirection()));
	}

	public Velocity copy() {
		return new Velocity(getSpeed(), getDirection());
	}
}
