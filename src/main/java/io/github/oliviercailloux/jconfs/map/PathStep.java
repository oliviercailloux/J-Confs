package io.github.oliviercailloux.jconfs.map;

import java.util.Objects;

import com.google.common.base.MoreObjects;


/*
 * An object of PathStep represents a path step of a conference with parameters
 * (type of transport, departure and arrival place) that describe this path
 * 
 */
public class PathStep {


	private TransportType type = TransportType.NOTRANSPORT;
	private String startingPoint = "";
	private String arrivalPoint = "";

	/**
	 * this is a constructor which initializes the PathStep object The TransporType
	 * is noTranport by default
	 * 
	 * @param startingPoint
	 *            not <code>null</code>.
	 * @param arrivalPoint
	 *            not <code>null</code>.
	 */
	public PathStep(String startingPoint, String arrivalPoint) {
		this.startingPoint = Objects.requireNonNull(startingPoint);
		this.arrivalPoint = Objects.requireNonNull(arrivalPoint);
	}

	/**
	 * this is a constructor which initializes the PathStep object
	 * 
	 * @param startingPoint
	 *            not <code>null</code>.
	 * @param arrivalPoint
	 *            not <code>null</code>.
	 * @param type
	 *            not <code>null</code>.
	 */
	public PathStep(String startingPoint, String arrivalPoint, TransportType type) {
		this(startingPoint, arrivalPoint);
		this.type = Objects.requireNonNull(type);
	}

	/**
	 * this is a getter which return the type
	 * 
	 * @return not <code>null</code>.
	 */
	public TransportType getType() {
		return type;
	}

	/**
	 * this is a getter which return the startingPoint
	 * 
	 * @return not <code>null</code>.
	 */
	public String getStartingPoint() {
		return startingPoint;
	}

	/**
	 * This is a getter which return the arrivalPoint
	 * 
	 * @return not <code>null</code>.
	 */
	public String getArrivalPoint() {
		return arrivalPoint;
	}

	/**
	 * This is a setter to modify the type
	 * 
	 * @param type
	 *            not <code>null</code>
	 */
	public void setType(TransportType type) {
		this.type = Objects.requireNonNull(type);
	}

	/**
	 * a setter to modify the startingPoint
	 * 
	 * @param startingPoint
	 *            not <code>null</code>
	 */
	public void setStartingPoint(String startingPoint) {
		this.startingPoint = Objects.requireNonNull(startingPoint);
	}

	/**
	 * a setter to modify the arrivalPoint
	 * 
	 * @param arrivalPoint
	 *            not <code>null</code>.
	 */
	public void setArrivalPoint(String arrivalPoint) {
		this.arrivalPoint = Objects.requireNonNull(arrivalPoint);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("StartingPoint", startingPoint)
				.add("ArrivalPoint", arrivalPoint)
				.add("Type", type)
				.toString();
	}

}
// end of class PathStep
