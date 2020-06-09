package io.github.oliviercailloux.jconfs.location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.locationiq.client.api.*;
import com.google.common.collect.ImmutableList;
import com.locationiq.client.ApiClient;
import com.locationiq.client.ApiException;
import com.locationiq.client.Configuration;
import com.locationiq.client.auth.ApiKeyAuth;

/**
 * This class allows you to translate an address into several pieces of
 * informations. It therefore allows, from imprecise information, to propose
 * several addresses or a single address if the information entered is precise.
 * 
 * @author Floryan Kieffer
 * 
 * 
 */

public class AddressQuerier {

	private List<String> addressInformations;
	private List<String> addressFound;

	/**
	 * Private constructor
	 */

	public AddressQuerier() {
		this.addressFound = new ArrayList<>();
		this.addressInformations = new ArrayList<>();
	}

	/**
	 * This class is a builder, it's allows to make the object AddressQuerier
	 * immutable. It builds all the attributes of the class using a AddressQuerier
	 * object.
	 * 
	 * @author floryan
	 *
	 */

	public static class AddressQuerierBuilder {

		private AddressQuerier addressQuerier;

		/**
		 * This method initialize state
		 * 
		 * @param addressQuerier
		 */

		private AddressQuerierBuilder(final AddressQuerier addressQuerier) {
			this.addressQuerier = addressQuerier;
		}

		/**
		 * This method start building a addressQuerier
		 * 
		 * @return addressQuerierBuilder
		 */

		public static AddressQuerierBuilder build() {
			return new AddressQuerierBuilder(new AddressQuerier());
		}

		/**
		 * Instantiate attribute addressInformations
		 * 
		 * @param address
		 * @return this (current object)
		 * @throws ApiException
		 */

		public AddressQuerierBuilder addressInformations(final String address) throws ApiException {
			this.addressQuerier.recoveryAddressInformations(address);
			return this;
		}

		/**
		 * Instantiate attribute addressFound
		 * 
		 * @return this (current object)
		 */

		public AddressQuerierBuilder addressFound() {
			this.addressQuerier.recoveryAddressFound();
			return this;
		}

		/**
		 * Return the unique object
		 * 
		 * @return ret
		 */

		public AddressQuerier get() {
			final AddressQuerier ret = new AddressQuerier();
			ret.addressFound = addressQuerier.addressFound;
			ret.addressInformations = addressQuerier.addressInformations;
			return ret;
		}
	}

	/**
	 * This method return a list with a lot of informations about an address
	 * research
	 * 
	 * @return adressInformations
	 */

	public List<String> getAddressInformations() {
		return addressInformations;
	}

	/**
	 * This method return all address found after a researcher address
	 * 
	 * @return adressFound
	 */

	public List<String> getAddressFound() {
		return addressFound;
	}

	/**
	 * This method allows connection to LocationIQ and its database.
	 * 
	 * @return ApiClient defaultClient
	 */

	public static ApiClient connexion() {
		ApiClient defaultClient = Configuration.getDefaultApiClient();
		defaultClient.setBasePath("https://eu1.locationiq.com/v1");
		ApiKeyAuth key = (ApiKeyAuth) defaultClient.getAuthentication("key");
		key.setApiKey("d4b9a23eaef07d");
		return defaultClient;
	}

	/**
	 * 
	 * This method allows to create the ArrayList adressInformations making a
	 * precise or not address search (precise is better). The ArrayList is filled
	 * according to the different elements to find, that is to say that if for an
	 * imprecise search it is possible to assign several addresses to it, all these
	 * addresses are entered in the ArrayList.
	 * 
	 * @param adresse
	 * @throws ApiException
	 */

	public void recoveryAddressInformations(String address) throws ApiException {
		if (address == "" || address == null || address.isEmpty()) {
			throw new IllegalArgumentException("Address error");
		}
		ApiClient defaultClient = AddressQuerier.connexion();
		AutocompleteApi api = new AutocompleteApi(defaultClient);
		List<Object> tmp = api.autocomplete(address, 1, null, null, null, null, null, null);
		Iterator<Object> i = tmp.iterator();
		while (i.hasNext()) {
			this.addressInformations.add(i.next().toString());
		}
		this.addressInformations=ImmutableList.copyOf(this.addressInformations);
	}

	/**
	 * This method modifies the contents of the ArrayList addressInformations to
	 * make it more readable and to be able to apply different methods more easily.
	 * It also makes it possible to retrieve all the addresses with latitude and
	 * longitude found by autocomplete, to store them in addressFound.
	 */

	public void recoveryAddressFound() {
		for (int i = 0; i < this.addressInformations.size(); i++) {
			String contenu = this.addressInformations.get(i);
			String hash = contenu.substring(1, contenu.length() - 2);
			this.addressInformations.set(i, hash);
		}
		ArrayList<String> selection = new ArrayList<>();
		for (int i = 0; i < this.addressInformations.size(); i++) {
			String search1 = "display_name=";
			String search2 = "lat=";
			String search3 = "lon=";
			int posDep = this.addressInformations.get(i).indexOf(search1);
			int posArr = this.addressInformations.get(i).indexOf(", display_place=");
			String address = this.addressInformations.get(i).substring(posDep + search1.length(), posArr);
			int posDepLat = this.addressInformations.get(i).indexOf(search2);
			int posArrLat = this.addressInformations.get(i).indexOf(", lon=");
			String lat = this.addressInformations.get(i).substring(posDepLat + search2.length(), posArrLat);
			int posDepLon = this.addressInformations.get(i).indexOf(search3);
			int posArrLon = this.addressInformations.get(i).indexOf(", boundingbox=");
			String lon = this.addressInformations.get(i).substring(posDepLon + search3.length(), posArrLon);
			String all = address + ", lat=" + lat + ", lon=" + lon;
			selection.add(all);

		}
		this.addressFound = selection;
		this.addressFound = ImmutableList.copyOf(this.addressFound);
	}
}
