package io.github.oliviercailloux.jconfs.location;

import com.hp.hpl.jena.graph.GetTriple;
import com.locationiq.client.ApiClient;
import com.locationiq.client.ApiException;
import java.math.BigDecimal;
import com.locationiq.client.api.DirectionsApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.locationiq.client.model.*;

/**
 * This class allows to calculate the distance and the duration between two
 * places defined by address (of type Address). It define the steps to go to the
 * destination. The units are meter and second, and the steps are a string
 * containing all the routes indication.
 * 
 * @author Anis HAMOUNI & Sébastien BOURG
 */
public class DistanceDuration {
	private int duration;
	private int distance;
	private List<Step> steps;
	private Address addressDeparture;
	private Address addressArrival;
	private DirectionsDirectionsRoutes trip;

	/**
	 * 
	 * Static factory method to build a direction instance.
	 * 
	 * @param dep   Address
	 * @param arriv Address
	 * 
	 */
	public static DistanceDuration newDistanceDuration(Address dep, Address arriv) {
		return new DistanceDuration(dep, arriv);
	}

	private DistanceDuration(Address dep, Address arriv) {
		this.duration = 0;
		this.distance = 0;
		this.addressDeparture = dep;
		this.addressArrival = arriv;
		this.steps = new ArrayList<>();
		this.trip = null;
	}

	/**
	 * Return the duration in second
	 * 
	 * @return duration
	 * @throws ApiException
	 */
	public int getDuration() throws ApiException {
		if (trip == null)
			calculateDistanceDuration();
		return this.duration;
	}

	/**
	 * Return the distance in meters
	 * 
	 * @return distance
	 * @throws ApiException
	 */
	public int getDistance() throws ApiException {
		if (trip == null)
			calculateDistanceDuration();
		return this.distance;
	}

	/**
	 * Return the departure address
	 * 
	 * @return addressDeparture
	 */
	public Address getDeparture() {
		return this.addressDeparture;
	}

	/**
	 * Return the arrival address
	 * 
	 * @return addressArrival
	 */
	public Address getArrival() {
		return this.addressArrival;
	}

	/**
	 * Return the steps
	 * 
	 * @return steps
	 * @throws ApiException
	 */
	public List<Step> getSteps() throws ApiException {
		if (trip == null)
			calculateDistanceDuration();
		
		String[] tabToParse = this.trip.getLegs().get(0).toString().split("location=\\[");
		List<String> listToParse = new ArrayList<>(Arrays.asList(tabToParse));
		listToParse.remove(0);
		listToParse.remove(0);
		listToParse.remove(listToParse.size()-1);
		for(int i = 0; i<listToParse.size()-1;i++) {
			String departureLatitude = listToParse.get(i).split("]")[0].split(",")[1];
			String departureLongitude = listToParse.get(i).split("]")[0].split(",")[0];
			String arrivalLatitude = listToParse.get(i+1).split("]")[0].split(",")[1];
			String arrivalLongitude = listToParse.get(i+1).split("]")[0].split(",")[0];
			Address departureStep = Address.given(null, departureLatitude, departureLongitude);
			Address arrivalStep = Address.given(null, arrivalLatitude, arrivalLongitude);
			Step oneNewStep = Step.newStep(departureStep, arrivalStep);
			this.steps.add(oneNewStep);
		}
		return this.steps;
	}

	/**
	 * This function calculates the time, the distance and the steps to move between
	 * two address. The API also allow to calculate this informations beetween more
	 * than 2 address but it hasn't been implement here
	 * 
	 * @throws ApiException
	 */
	private void calculateDistanceDuration() throws ApiException {
		String latLonAddressDeparture = this.addressDeparture.getLongitude() + ","
				+ this.addressDeparture.getLatitude();
		String latLonAddressArrival = this.addressArrival.getLongitude() + "," + this.addressArrival.getLatitude();
		
		ApiClient defaultClient = AddressQuerier.connexion();
		DirectionsApi api = new DirectionsApi(defaultClient);
		DirectionsDirections response = api.directions(latLonAddressDeparture + ";" + latLonAddressArrival, null, null,
				null, null, null, null, "true", null, null, "simplified", null);
		
		this.trip = response.getRoutes().get(0);
		this.distance = trip.getDistance().intValue();
		this.duration = trip.getDuration().intValue();
		}
}
