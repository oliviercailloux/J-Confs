package io.github.oliviercailloux.jconfs.location;

/**
 * This class allows you to store an address define as a string address and the
 * latitude and longitude associate wth.
 * 
 * @author Floryan Kieffer
 * 
 * 
 */
public class Address {

	private String address;
	private String latitude;
	private String longitude;

	
	/**
	 * Factory method which creates a Address instance
	 *
	 * @param address
	 * @param latitude
	 * @param longitude
	 */
	public static Address given(String address, String latitude, String longitude) {
		if (latitude.isEmpty() | longitude.isEmpty()) {
			throw new IllegalArgumentException("latitude or longitude can't be empty");
		}
		return new Address(address, latitude, longitude);
	}

	/**
	 * Private constructor
	 *
	 * @param address
	 * @param latitude
	 * @param longitude
	 */
	private Address(String address, String latitude, String longitude) {
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * This method return the address name
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method return the latitude of the address object
	 * 
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}


	/**
	 * This method return the longitude of the address object
	 * 
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}


}
