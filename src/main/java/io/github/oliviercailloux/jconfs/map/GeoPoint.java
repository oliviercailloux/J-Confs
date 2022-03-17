package io.github.oliviercailloux.jconfs.map;

import java.util.Objects;

/**
 * Immuable classe that represent a point on a map
 * 
 * @author felii
 *
 */
public final class GeoPoint {

  private final String pointName;
  private final double latitude;
  private final double longitude;

  public String getPointName() {
    return pointName;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public GeoPoint(String name, double latitude, double longitude) {
    this.pointName = Objects.requireNonNull(name);
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * By default with mapGui, Paris is the default point
   */
  public GeoPoint() {
    this.pointName = "Paris";
    this.latitude = 48.866667;
    this.longitude = 2.333333;
  }
}
