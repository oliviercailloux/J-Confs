package io.github.oliviercailloux.y2018.jconfs;

public enum TransportType {
	PUBLICTRANSPORTATION ("Public transportation"),
	TAXI ("Taxi"),
	TRAIN ("Train"),
	PLANE("Plane"),
	NOTRANSPORT("walking");
	
	private String name="";//the name of a TransportType
	
	/**
	 * this is a constructor which initializes the TransporType object
	 * @param name
	 */
	private TransportType(String name) {
		this.name=name;
	}
	
	
	/**
	 * return the name of the object
	 */
	@Override 
	public String toString() {
		return name;
	}
}
