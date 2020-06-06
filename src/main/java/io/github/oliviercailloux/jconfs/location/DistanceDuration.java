package io.github.oliviercailloux.jconfs.location;

import com.locationiq.client.ApiClient;
import com.locationiq.client.ApiException;
import java.math.BigDecimal;
import com.locationiq.client.api.DirectionsApi;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.locationiq.client.model.*;

/**
 * This class allows to calculate the distance and the duration between two
 * places defined by address (convert to lattitude, longitude). It define the
 * steps to go to the destination. The units are meter and second, and the steps
 * are a string containing all the routes indication.
 * 
 * @author Anis HAMOUNI & Sébastien BOURG
 */
public class DistanceDuration {
	private int duration;
	private int distance;
	private String steps;
	private Address addressDeparture;
	private Address addressArrival;

	/**
	 * 
	 * Static factory method to build a direction instance. Important : A sleep of 1
	 * second as been added due to the request limit per second of the API
	 * 
	 * @param dep   string address, example : "13 Rue Cloche Percé, 75004 Paris"
	 * @param arriv string address, example : "Avenue du général de gaulle, 92800
	 *              puteaux"
	 * 
	 * @throws ApiException
	 * @throws InterruptedException
	 * 
	 */
	public static DistanceDuration newDistanceDuration(Address dep, Address arriv)
			throws ApiException, InterruptedException {
		return new DistanceDuration(dep, arriv);
	}

	private DistanceDuration(Address dep, Address arriv) throws ApiException, InterruptedException {
		this.duration = 0;
		this.distance = 0;
		this.steps = "";
		this.addressDeparture = dep;
		this.addressArrival = arriv;
	}

	/**
	 * Return the duration in second
	 * 
	 * @return duration
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * Return the distance in meters
	 * 
	 * @return distance
	 */
	public int getDistance() {
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
	 */
	public String getSteps() {
		return this.steps;
	}

	/**
	 * This function calculates the time, the distance and the steps to move between
	 * two address (converted to latitude and longitude using TranslationAddress
	 * class)
	 * 
	 * @throws ApiException
	 */
	public void calculateDistanceDuration() throws ApiException {

		ApiClient defaultClient = AddressQuerier.connexion();

		String latLonAddressDeparture = this.addressDeparture.getLongitude() + ","
				+ this.addressDeparture.getLatitude();
		String latLonAddressArrival = this.addressArrival.getLongitude() + "," + this.addressArrival.getLatitude();

		DirectionsApi api = new DirectionsApi(defaultClient);

		DirectionsDirections response = api.directions(latLonAddressDeparture + ";" + latLonAddressArrival, null, null,
				null, null, null, null, "true", null, null, "simplified", null);
		List<DirectionsDirectionsRoutes> routes = response.getRoutes();
		Iterator<DirectionsDirectionsRoutes> ite = routes.iterator();
		while (ite.hasNext()) {
			DirectionsDirectionsRoutes oneDirection = ite.next();
			this.distance = this.distance + oneDirection.getDistance().intValue();
			this.duration = this.duration + oneDirection.getDuration().intValue();
			this.steps = this.steps + oneDirection.toString();

		}

	}

}
