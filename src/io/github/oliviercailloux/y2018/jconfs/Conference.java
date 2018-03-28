package io.github.oliviercailloux.y2018.jconfs;
import java.net.*;
import java.time.Instant;
import java.util.Objects;

/**
 * @author huong,camille
 */
public class Conference {
	private URL url;
	private String title;
	private Instant startDate;
	private Instant endDate;
	private Double registrationFee;
	private String city;
	private String country;

	/**
	 * This is a getter which return the URL 	 
	 * @return url
	 */
	public URL getUrl() {
		return url;
	}
    
    /**
     * This is a getter which return the title 	 
     * @return title
     */
    public String getTitle() {
    	return title;
    }
    
    /**
     * This is a setter which set the title     
     * @param title
     * not <code>null</code>.
     */
    public void setTitle(String title) {
    	this.title = Objects.requireNonNull(title);
    }
    
    /**
     * This is a getter which return the date start 	 
     * @return startDate
     */
    public Instant getStartDate() {
    	return startDate;
    }
    
    /**
     * This is a setter which set the the date start 	 
     * @param startDate
     * not <code>null</code>.
     */
    public void setStartDate(Instant startDate) {
    	this.startDate = Objects.requireNonNull(startDate);
    }
    
    /**
     * This is a getter which return the date end     
     * @return endDate
     */
    public Instant getEndDate() {
    	return endDate;
    }
    
    /**
     * This is a setter which set the the date end 	 
     * @param endDate
     * not <code>null</code>.
     */
    public void setEndDate(Instant endDate) {
    	this.endDate = Objects.requireNonNull(endDate);
    }
    
    /**
     * This is a getter which return the fee of registration    
     * @return registrationFee
     */
    public Double getFeeRegistration() {
    	return registrationFee;
    }
    
    /**
     * This is a setter which set the fee of registration in euro
     * @param registrationFee
     * not <code>null</code>.
     */
    public void setFeeRegistration(Double registrationFee) {
    	this.registrationFee = Objects.requireNonNull(registrationFee);
    }
    
    /**
     * This is a getter which return the city    
     * @return city
     */
    public String getCity() {
    	return city;
    }
    
    /**
     * This is a setter which set the city
     * @param city
     * not <code>null</code>.
     */
    public void setCity(String city) {
    	this.city = Objects.requireNonNull(city);
    }
    
    /**
     * This is a getter which return the country    
     * @return country
     */
    public String getCountry() {
    	return country;
    }
    
    /**
     * This is a setter which set the country
     * @param country
     * not <code>null</code>.
     */
    public void setCountry(String country) {
    	this.country = Objects.requireNonNull(country);
    }
    
    /**
     * This is a constructor which initializes the conference object
     * @param url primary key
    */
    public Conference(URL url) {
   	 this.url = url;
   	 this.title = "";
   	 this.startDate = Instant.EPOCH;
   	 this.endDate = Instant.EPOCH;
   	 this.registrationFee = 0.0;
   	 this.city = "";
   	 this.country = "";
    }
    
    @Override
    public String toString() {
    	return "Conference [url=" + url + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
   			 + ", registrationFee=" + registrationFee + ", city=" + city + ", country=" + country + "]";
    }
   	 
}


