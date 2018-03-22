package fr.msch.Jconfs;

public class PathStep {
	/*
	 * An object of PathStep represents a path step of a conference with parametres
	 * (type of transport, departure and arrival place) that describe this path
	 * 
	 */

	private TransportType type = TransportType.noTransport;// type of transport,( not null,noTransport by default)
	private String startingPoint = "";// place of departure,(not null, "" by defaut)
	private String arrivalPoint = "";// place of arrival, (not null, "" by defaut)

	/**
	 * this is a constructor which initializes the PathStep object The TransporType
	 * is noTranport by default
	 * 
	 * @param startingPoint
	 *            , can't be null
	 * @param arrivalPoint
	 *            , can't be null
	 */

	public PathStep(String startingPoint, String arrivalPoint) {
		if (startingPoint == null || arrivalPoint == null) {
			throw new IllegalArgumentException("startingPoint and arrivalPoint can't be null");
		}
		this.startingPoint = startingPoint;
		this.arrivalPoint = arrivalPoint;
	}

	/**
	 * this is a constructor which initializes the PathStep object
	 * 
	 * @param startingPoint
	 *            , can't be null
	 * @param arrivalPoint
	 *            , can't be null
	 * @param type
	 *            , can't be null
	 */
	public PathStep(String startingPoint, String arrivalPoint, TransportType type) {
		this(startingPoint, arrivalPoint);
		if (type == null) {
			throw new IllegalArgumentException("type can't be null");
		}
		this.type = TransportType.noTransport;
	}

	/**
	 * this is a getter which return the type
	 * 
	 * @return type
	 */
	public TransportType getType() {
		return type;
	}

	/**
	 * this is a getter which return the startingPoint
	 * 
	 * @return startingPoint
	 */
	public String getStartingPoint() {
		return startingPoint;
	}

	/**
	 * This is a getter which return the arrivalPoint
	 * 
	 * @return arrivalPoint
	 */

	public String getArrivalPoint() {
		return arrivalPoint;
	}

	/**
	 * This is a setter to modify the type
	 * 
	 * @param type
	 *            can't be null
	 */
	public void setType(TransportType type) {
		if (type == null) {
			throw new IllegalArgumentException("type can't be null");
		}
		this.type = type;
	}

	/**
	 * a setter to modify the startingPoint
	 * 
	 * @param startingPoint
	 */
	public void setStartingPoint(String startingPoint) {
		if (startingPoint == null) {
			throw new IllegalArgumentException("startingPoint can't be null");
		}
		this.startingPoint = startingPoint;
	}

	/**
	 * a setter to modify the arrivalPoint
	 * 
	 * @param arrivalPoint,
	 *            can't be null
	 */
	public void setArrivalPoint(String arrivalPoint) {
		if (arrivalPoint == null) {
			throw new IllegalArgumentException("startingPoint can't be null");
		}
		this.arrivalPoint = arrivalPoint;
	}

	@Override
	public String toString() {
		return "StartingPoint: " + startingPoint + " ArrivalPoint: " + arrivalPoint + " type: " + type.toString();
	}

}
// end of class PathStep
