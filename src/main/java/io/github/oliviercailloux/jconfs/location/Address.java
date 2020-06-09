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
	 * 
	 * Factory method which creates a Address instance
	 *
	 */

	public static Address newInstanceAddressEmpty() {
		return new Address();
	}

	/**
	 * Factory method which creates a Address instance
	 *
	 * @param address
	 * @param latitude
	 * @param longitude
	 */
	public static Address given(String address, String latitude, String longitude) {
		return new Address(address, latitude, longitude);
	}

	/**
	 * Private constructor
	 */

	private Address() {
		this.address = "";
		this.longitude = "";
		this.latitude = "";
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
	 * This method set the address name
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * This method set the address latitude
	 * 
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * This method return the longitude of the address object
	 * 
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * This method set the address longitude
	 * 
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
