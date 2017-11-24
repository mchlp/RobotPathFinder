
/*
 * Michael Pu
 * SpaceshipGame - Acceleration
 * November 2017
 */


package backend;

public class Acceleration {

	private Vector mVector = new Vector();

	public Acceleration() {
		mVector.setVector(0, 0);
	}

	/**
	 * Creates a Acceleration object using a vector object
	 * 
	 * @param vector
	 *            a Vector object representing the speed and direction
	 */
	public Acceleration(Vector vector) {
		mVector = vector;
	}

	/**
	 * Creates an Acceleration object using X and Y speeds
	 * 
	 * @param xRate
	 *            X Rate of acceleration in m/s/s
	 * @param yRate
	 *            Y Rate of acceleration in m/s/s
	 * @param xyRate
	 *            To differentiate between rate and direction constructor
	 */
	public Acceleration(double xRate, double yRate, boolean xyRate) {
		mVector.setXY(xRate, yRate);
	}

	/**
	 * Creates an Acceleration object from speed and direction
	 * 
	 * @param rate
	 *            Rate of acceleration in m/s
	 * @param direction
	 *            Direction of acceleration in degrees (0-365 inclusive)
	 */
	public Acceleration(double rate, double direction) {
		setAcceleration(rate, direction);
	}

	public void setAcceleration(double rate, double direction) {
		setRate(rate);
		setDirection(direction);
	}

	public void setRate(double rate) {
		mVector.setMagnitude(rate);
	}

	public void setDirection(double direction) {
		mVector.setDirection(direction);
	}

	public double getRate() {
		return mVector.getMagnitude();
	}

	public double getDirection() {
		return mVector.getDirection();
	}

	/**
	 * Adds two Acceleration objects together and returns the sum
	 *
	 * @param acceleration2
	 *            Acceleration object to add
	 * @return Acceleration object representing the sum
	 */
	public Acceleration add(Acceleration acceleration2) {
		return new Acceleration(mVector.add(acceleration2.mVector));
	}

	/**
	 * @return Rate in the X direction
	 */
	public double getXRate() {
		return mVector.getXComponent();
	}

	/**
	 * @return Rate in the Y direction
	 */
	public double getYRate() {
		return mVector.getYComponent();
	}

	public Acceleration getAccelerationByTime(double time) {
		return new Acceleration(mVector.getMagnitude() * time, mVector.getDirection());
	}
}
