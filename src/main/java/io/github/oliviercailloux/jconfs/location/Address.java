package io.github.oliviercailloux.jconfs.location;

import com.google.common.base.Preconditions;

/**
 * This class allows you to store an address define as a latitude and a longitude and an (optional)
 * string address associate with.
 * 
 * @author Floryan Kieffer & ZOUARI Anis
 * 
 */
public class Address {

  private String address;
  private String latitude;
  private String longitude;

  /**
   * Static factory method which creates a Address instance
   *
   * @param address
   * @param latitude
   * @param longitude
   */
  public static Address given(String address, String latitude, String longitude) {
    Preconditions.checkNotNull(latitude);
    Preconditions.checkNotNull(longitude);

    if (latitude.isEmpty() || longitude.isEmpty())
      throw new NullPointerException("latitude or longitude can't be empty or null ");

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
   * @return address
   */
  public String getAddressName() {
    return address;
  }
  
  /**
   * This method return the address name if it has been set
   */
  public void setAddressName(String location) {
    address = location;
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
