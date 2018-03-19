/* An object of PathStep represents a path step of a conference
 with parametres (type of transport, departure and arrival place) that describe this path

*/

public class PathStep {
	

	private String type;// type of transport tu use (taxis,plane, bus...)
	private String startingPoint;// place of departure
	private String arrivalPoint;//  place of arrival
	
	/**
	 * constructor for PathStep 
	 * @param type String type 
	 * @param startingPoint String type 
	 * @param arrivalPoint String type
	 */
	public PathStep(String type,String startingPoint,String arrivalPoint) {
		this.type=type;
		this.startingPoint=startingPoint;
		this.arrivalPoint=arrivalPoint;
	}

  // begin of getter
  	public static String getType () {
		return type;
	}

	public static String getStartingPoint(){
		return startingPoint;
	}

	public static String getStartingPoint(){
		return startingPoint;
	}
  // end of getter	
}