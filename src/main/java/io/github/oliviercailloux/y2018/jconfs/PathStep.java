package io.github.oliviercailloux.y2018.jconfs;

import java.util.Objects;

import com.google.common.base.MoreObjects;

import io.github.oliviercailloux.y2018.geocode.ReverseGeoCode;


/*
 * An object of PathStep represents a path step of a conference with parameters
 * (type of transport, departure and arrival place) that describe this path
 * 
 */
public class PathStep {


	private TransportType type = TransportType.NOTRANSPORT;
	private String startingPoint = "";
	private String arrivalPoint = "";
	private double latitudeStartingPoint;
	private double longitudeStartingPoint;
	private double latitudeArrivalPoint;
	private double longitudeArrivalPoint;

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
	 * this is a constructor use with the MapGui in GuiConference. By default, Paris is the
	 * starting point
	 * 
	 */
	public PathStep() {
		this.startingPoint = "Paris";
		this.latitudeStartingPoint = 48.866667;
		this.longitudeStartingPoint = 2.333333;
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
	 * This is a getter which return the latitude of starting point
	 * 
	 * @return not <code>null</code>.
	 * 
	 */
	public double getLatitudeStartingPoint() {
		return latitudeStartingPoint;
	}

	/**
	 * This is a getter which return the longitude of starting point
	 * 
	 * @return not <code>null</code>.
	 * 
	 */
	public double getLongitudeStartingPoint() {
		return longitudeStartingPoint;
	}

	/**
	 * This is a getter which return the latitude of arrival point
	 * 
	 * @return not <code>null</code>.
	 * 
	 */
	public double getLatitudeArrivalPoint() {
		return latitudeArrivalPoint;
	}

	/**
	 * This is a getter which return the longitude of arrival point
	 * 
	 * @return not <code>null</code>.
	 * 
	 */
	public double getLongitudeArrivalPoint() {
		return longitudeArrivalPoint;
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

	
	/**
	 * a setter to modify the latitude of the Starting Point
	 * 
	 * @param latitudeStartingPoint
	 *            not <code>null</code>.
	 */
	public void setLatitudeStartingPoint(double latitudeStartingPoint) {
		this.latitudeStartingPoint = Objects.requireNonNull(latitudeStartingPoint);
	}

	/**
	 * a setter to modify the longitude of the Starting Point
	 * 
	 * @param longitudeStartingPoint
	 *            not <code>null</code>.
	 */
	public void setLongitudeStartingPoint(double longitudeStartingPoint) {
		this.longitudeStartingPoint = Objects.requireNonNull(longitudeStartingPoint);
	}

	/**
	 * a setter to modify the latitude of the Arrival Point
	 * 
	 * @param latitudArrivalPoint
	 *            not <code>null</code>.
	 */
	public void setLatitudeArrivalPoint(double latitudArrivalPoint) {
		this.latitudeArrivalPoint = latitudArrivalPoint;
	}

	/**
	 * a setter to modify the longitude of the Arrival Point
	 * 
	 * @param longitudeArrivalPoint
	 *            not <code>null</code>.
	 */
	public void setLongitudeArrivalPoint(double longitudeArrivalPoint) {
		this.longitudeArrivalPoint = Objects.requireNonNull(longitudeArrivalPoint);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("StartingPoint", startingPoint)
				.add("LatitudeStartingPoint", latitudeStartingPoint)
				.add("LongitudeStartingPoint", longitudeStartingPoint)
				.add("ArrivalPoint", arrivalPoint)
				.add("LatitudeArrivalPoint", latitudeArrivalPoint)
				.add("LongitudeArrivalPoint", longitudeArrivalPoint)
				.add("Type", type)
				.toString();
	}

}
// end of class PathStep
