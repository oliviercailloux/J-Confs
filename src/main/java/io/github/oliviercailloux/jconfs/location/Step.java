package io.github.oliviercailloux.jconfs.location;

public class Step {
	private Address departureAddress;
	private Address arrivalAddress;
	
	public static Step newStep(Address dep, Address arriv) {
		return new Step(dep, arriv);
	}
	
	private Step(Address dep, Address arriv) {
		this.departureAddress = dep;
		this.arrivalAddress = arriv;
	}

	public Address getDepartureAddress() {
		return departureAddress;
	}

	public Address getArrivalAddress() {
		return arrivalAddress;
	}
}
