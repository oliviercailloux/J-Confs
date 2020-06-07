package io.github.oliviercailloux.jconfs.location;

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

	public static Address newInstanceAddressComplete(String address, String latitude, String longitude) {
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

	private Address(String address, String latitude, String longitude) {
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
