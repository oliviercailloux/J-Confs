package fr.msch.Jconfs;

public enum TransportType {
	PublicTransporation ("Public transportation"),
	taxi ("Taxi"),
	train ("Train"),
	plane("Plane"),
	noTransport("walking");
	
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
