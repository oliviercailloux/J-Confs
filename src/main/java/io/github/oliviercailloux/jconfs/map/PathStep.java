package io.github.oliviercailloux.jconfs.map;

import java.util.Objects;

import com.google.common.base.MoreObjects;

/*
 * An object of PathStep represents a path step of a conference with parameters
 * (type of transport, departure and arrival place) that describe this path
 * 
 */
public class PathStep {

	public class point{
		private String pointName;
		private double latitude;
		private double longitude;

		public String getPointName() {
			return pointName;
		}
		public void setPointName(String pointName) {
			this.pointName = Objects.requireNonNull(pointName);
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}	

		public point (String name, double latitude, double longitude) {
			this.pointName = Objects.requireNonNull(name);
			this.latitude = latitude;
			this.longitude = longitude;
		}

		/**
		 * By default with mapGui, Paris is the default point
		 */
		public point() {
			this.pointName = "Paris";
			this.latitude = 48.866667;
			this.longitude = 2.333333;
		}

	}

	private TransportType type = TransportType.NOTRANSPORT;
	private point arrival;
	private point starting;

	/**
	 * this is a constructor which initializes the PathStep object The TransporType
	 * is noTranport by default
	 * 
	 * @param startingPoint not <code>null</code>.
	 * @param arrivalPoint  not <code>null</code>.
	 */
	public PathStep(point startingPoint, point arrivalPoint) {
		this.starting = Objects.requireNonNull(startingPoint);
		this.arrival = Objects.requireNonNull(arrivalPoint);
	}

	/**
	 * this is a constructor which initializes the PathStep object
	 * 
	 * @param startingPoint not <code>null</code>.
	 * @param arrivalPoint  not <code>null</code>.
	 * @param type          not <code>null</code>.
	 */
	public PathStep(point startingPoint, point arrivalPoint, TransportType type) {
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
