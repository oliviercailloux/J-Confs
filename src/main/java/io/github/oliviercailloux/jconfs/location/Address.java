package io.github.oliviercailloux.jconfs.location;

/**
 * This class allows you to store an address define as a latitude and a
 * longitude and an (optional) string address associate with.
 * 
 * @author Floryan Kieffer
 * 
 */
public class Address {

	private String address;
	private String latitude;
	private String longitude;

	/**
	 * Static factory method which creates a Address instance
	 *
	 * @param address   (can be null)
	 * @param latitude
	 * @param longitude
	 */
	public static Address given(String address, String latitude, String longitude) {
		if (latitude == null || latitude.isEmpty() || longitude == null || longitude.isEmpty()) {
			throw new IllegalArgumentException("latitude or longitude can't be empty or null ");
		}
		return new Address(address, latitude, longitude);
	}

	private Address(String address, String latitude, String longitude) {
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * This method return the address name if it has been set
	 * 
	 * @return address or null
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method return the latitude
	 * 
	 * @return latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * This method return the longitude
	 * 
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}

}
