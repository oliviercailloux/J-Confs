package io.github.oliviercailloux.y2018.jconfs;

import java.util.Objects;

import ezvcard.property.Title;
import ezvcard.property.Url;

public class MemberVcard {
	private Url url;
	private Title profession;
	private Double registrationFee;
	private String address;
	private String city;
	private String country;

	/**
	 * This is a getter which return the object Url	 
	 * @return url
	 */
	public Url getUrl() {
		return url;
	}
    
    /**
     * This is a getter which return the object title 	 
     * @return title
     */
    public Title getTitle() {
    	return profession;
    }

    /**
     * This is a setter which set the title     
     * @param title
     * not <code>null</code>.
     */
    public void setTitle(Title title) {
    	this.profession = Objects.requireNonNull(title);
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
     * This is a getter which return the address    
     * @return address
     */
    public String getAddress() {
    	return address;
    }
    
    /**
     * This is a setter which set the address
     * @param address
     * not <code>null</code>.
     */
    public void setAddress(String address) {
    	this.address = Objects.requireNonNull(address);
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
    public MemberVcard(Url url) {
	   	 this.url = url;
	   	 this.profession = new Title("");
	   	 this.registrationFee = 0.0;
	   	 this.city = "";
	   	 this.country = "";
	   	 this.address = ""; 
   	 }
}
