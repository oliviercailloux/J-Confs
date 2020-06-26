package io.github.oliviercailloux.jconfs.location;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.locationiq.client.ApiClient;
import com.locationiq.client.ApiException;

import java.io.IOException;
import com.locationiq.client.api.DirectionsApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.simple.parser.ParseException;

import com.locationiq.client.model.*;

/**
 * This class allows to get a trajet and informations about it. A trajet is
 * define beetween two addresses (see Address class). Using LocationIQ
 * "DirectionsAPI" you can get the total distance (in meters) and duration (in
 * seconds) for the trajet. The global informations about the trajet (stored in
 * a Step object) can be get using getGeneralStep method. You can also get all
 * the steps of the trajet (stored as a List<Step>) using getAllSteps method.
 * 
 * @author Anis HAMOUNI & sbourg
 */
public class DistanceDuration {
	private int duration;
	private int distance;
	private List<Step> allSteps;
	private Step generalStep;
	private Address addressDeparture;
	private Address addressArrival;
	private DirectionsDirectionsRoutes trip;

	/**
	 * 
	 * Static factory method to build a DistanceDuration instance.
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
		this.allSteps = new ArrayList<>();
		this.trip = null;
		this.generalStep = null;
	}

	/**
	 * Return the duration in seconds
	 * 
	 * @return duration
	 * @throws ApiException
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 */
	public int getDuration() throws ApiException, JsonIOException, JsonSyntaxException, IOException, ParseException {
		if (trip == null)
			calculateDistanceDuration();
		return this.duration;
	}

	/**
	 * Return the distance in meters
	 * 
	 * @return distance
	 * @throws ApiException
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 */
	public int getDistance() throws ApiException, JsonIOException, JsonSyntaxException, IOException, ParseException {
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
	 * Return all the detailed steps of the traject
	 * 
	 * @return allSteps
	 * @throws ApiException
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 */
	public List<Step> getAllSteps()
			throws ApiException, JsonIOException, JsonSyntaxException, IOException, ParseException {
		if (trip == null)
			calculateDistanceDuration();

		String[] tabToParse = this.trip.getLegs().get(0).toString().split("location=\\[");
		List<String> listToParse = new ArrayList<>(Arrays.asList(tabToParse));
		listToParse.remove(0);
		listToParse.remove(0);
		listToParse.remove(listToParse.size() - 1);
		for (int i = 0; i < listToParse.size() - 1; i++) {
			String departureLatitude = listToParse.get(i).split("]")[0].split(",")[1];
			String departureLongitude = listToParse.get(i).split("]")[0].split(",")[0];
			String arrivalLatitude = listToParse.get(i + 1).split("]")[0].split(",")[1];
			String arrivalLongitude = listToParse.get(i + 1).split("]")[0].split(",")[0];
			Address departureStep = Address.given(null, departureLatitude, departureLongitude);
			Address arrivalStep = Address.given(null, arrivalLatitude, arrivalLongitude);
			Step oneNewStep = Step.newStep(departureStep, arrivalStep);
			this.allSteps.add(oneNewStep);
		}
		return this.allSteps;
	}

	/**
	 * Return the generalStep composed of the departure and arrival address and the
	 * global distance and duration.
	 * 
	 * @return generalStep
	 * @throws ApiException
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 */
	public Step getGeneralStep()
			throws ApiException, JsonIOException, JsonSyntaxException, IOException, ParseException {
		if (trip == null)
			calculateDistanceDuration();

		this.generalStep = Step.newStep(this.addressDeparture, this.addressArrival, this.distance, this.duration);
		return this.generalStep;
	}

	/**
	 * This function use the LocataionIQ "DirectionsAPI" to calculates the duration,
	 * the distance and all the informations about the traject bewteen the two
	 * addresses of the class.
	 * 
	 * @throws ApiException
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 */
	private void calculateDistanceDuration()
			throws ApiException, JsonIOException, JsonSyntaxException, IOException, ParseException {
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
