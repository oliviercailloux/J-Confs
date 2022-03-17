package io.github.oliviercailloux.jconfs.location;

import com.google.common.base.Preconditions;

/**
 * This class store a step define as a departureAddress and a arrivalAddress (see Address class).
 * It's also possible to add the distance and duration informations of a step.
 * 
 * @author sbourg & ZOUARI Anis
 *
 */
public class Step {
  private Address departureAddress;
  private Address arrivalAddress;
  private int distance;
  private int duration;

  /**
   * 
   * Static factory method to create a simple step.
   * 
   * @param dep : departure address
   * @param arriv : arrival address
   * 
   */
  public static Step newStep(Address dep, Address arriv) {
    Preconditions.checkNotNull(dep);
    Preconditions.checkNotNull(arriv);
    return new Step(dep, arriv);
  }

  private Step(Address dep, Address arriv) {
    this.departureAddress = dep;
    this.arrivalAddress = arriv;
    this.distance = -1;
    this.duration = -1;
  }

  /**
   * Static factory method to create a step with distance and duration informations.
   * 
   * @param dep : departure address
   * @param arriv : arrival address
   * @param dist : distance
   * @param dur : duration
   */
  public static Step newStep(Address dep, Address arriv, int dist, int dur) {
    Preconditions.checkNotNull(dep);
    Preconditions.checkNotNull(arriv);
    Preconditions.checkNotNull(dist);
    Preconditions.checkNotNull(dur);
    return new Step(dep, arriv, dist, dur);
  }

  private Step(Address dep, Address arriv, int dist, int dur) {
    this.departureAddress = dep;
    this.arrivalAddress = arriv;
    this.distance = dist;
    this.duration = dur;
  }

  /**
   * Return the departure address of the step
   * 
   * @return departureAddress
   */
  public Address getDepartureAddress() {
    return departureAddress;
  }

  /**
   * Return the arrival address of the step
   * 
   * @return arrivalAddress
   */
  public Address getArrivalAddress() {
    return arrivalAddress;
  }

  /**
   * Return the distance of the step.
   * 
   * @return distance (or -1 if it hasn't been specified for the step)
   */
  public int getDistance() {
    return distance;
  }

  /**
   * Return the duration of the step.
   * 
   * @return duration (or -1 if it hasn't been specified for the step)
   */
  public int getDuration() {
    return duration;
  }
}
