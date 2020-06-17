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
	private List<Address> addressFound;
	
	/**
	 * Factory Method
	 * @param address
	 * @return
	 * @throws ApiException
	 */
	public static AddressQuerier given(String address) throws ApiException {
		return new AddressQuerier(address);
	}

	/**
	 * Private constructor
	 * @throws ApiException 
	 */

	private AddressQuerier(String address) throws ApiException {
		this.addressInformations = this.recoveryAddressInformations(address);
		this.addressFound = this.recoveryAddressFound();
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

	public List<Address> getAddressFound() {
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

	public List<String> recoveryAddressInformations(String address) throws ApiException {
		this.addressInformations = new ArrayList<>();
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
		for (int a = 0; a < this.addressInformations.size(); a++) {
			String contenu = this.addressInformations.get(a);
			String hash = contenu.substring(1, contenu.length() - 2);
			this.addressInformations.set(a, hash);
		}
		this.addressInformations = ImmutableList.copyOf(this.addressInformations);
		return this.addressInformations;
	}

	/**
	 * This method modifies the contents of the ArrayList addressInformations to
	 * make it more readable and to be able to apply different methods more easily.
	 * It also makes it possible to retrieve all the addresses with latitude and
	 * longitude found by autocomplete, to store them in addressFound.
	 */

	public List<Address> recoveryAddressFound() {
		this.addressFound = new ArrayList<>();
		ArrayList<Address> selection = new ArrayList<>();
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
			Address adr = Address.given(address, lat, lon);
			selection.add(adr);

		}
		this.addressFound = selection;
		this.addressFound = ImmutableList.copyOf(this.addressFound);
		return this.addressFound;
	}
}
