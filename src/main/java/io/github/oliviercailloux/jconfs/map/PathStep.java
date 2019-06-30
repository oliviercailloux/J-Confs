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
	private GeoPoint arrival;
	private GeoPoint starting;
	
	public GeoPoint getArrival() {
		return arrival;
	}

	public void setArrival(GeoPoint arrival) {
		this.arrival = arrival;
	}

	public GeoPoint getStarting() {
		return starting;
	}

	public void setStarting(GeoPoint starting) {
		this.starting = starting;
	}

	/**
	 * this is a constructor which initializes the PathStep object The TransporType
	 * is noTranport by default
	 * 
	 * @param startingGeoPoint not <code>null</code>.
	 * @param arrivalGeoPoint  not <code>null</code>.
	 */
	public PathStep(GeoPoint startingGeoPoint, GeoPoint arrivalGeoPoint) {
		this.starting = Objects.requireNonNull(startingGeoPoint);
		this.arrival = Objects.requireNonNull(arrivalGeoPoint);
	}
	
	/**
	 * this is a constructor which initializes the PathStep object The TransporType
	 * is noTranport by default
	 * 
	 * @param startingGeoPoint not <code>null</code>.
	 * @param arrivalGeoPoint  not <code>null</code>.
	 */
	public PathStep(GeoPoint arrivalGeoPoint) {
		this.starting = new GeoPoint();
		this.arrival = Objects.requireNonNull(arrivalGeoPoint);
	}

	/**
	 * this is a constructor which initializes the PathStep object
	 * 
	 * @param startingGeoPoint not <code>null</code>.
	 * @param arrivalGeoPoint  not <code>null</code>.
	 * @param type          not <code>null</code>.
	 */
	public PathStep(GeoPoint startingGeoPoint, GeoPoint arrivalGeoPoint, TransportType type) {
		this(startingGeoPoint, arrivalGeoPoint);
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
	 * This is a setter to modify the type
	 * 
	 * @param type not <code>null</code>
	 */
	public void setType(TransportType type) {
		this.type = Objects.requireNonNull(type);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("StartingPoint", starting)
				.add("ArrivalPoint", arrival)
				.add("Type", type)
				.toString();
	}

}
// end of class PathStep
