public class PathStep {
	private String type;// of transport (taxis,plane...)
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
}
