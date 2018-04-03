package io.github.oliviercailloux.y2018.jconfs;
import java.time.LocalDate;


/**
 * @author huong,camille
 */
public class Conference {
	private String url;
	private String title;
	private LocalDate start_date;
	private LocalDate end_date;
	private Double registration_fee;
	private String country;
	private String region;
	
	/**
	 * This is a getter which return the URL  	
	 * @return url
	 */
	public String getUrl() {
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
	 * @param title2
	 */
	public void setTitle(String title2) {
		this.title = title2;
	}

	/**
	 * This is a getter which return the date start  	
	 * @return start_date
	 */
	public LocalDate getStartDate() {
		return start_date;
	}

	/**
	 * This is a setter which set the the date start  	
	 * @param start_date
	 */
	public void setStartDate(String start_date) {
		this.end_date = LocalDate.parse(start_date);

	}

	/**
	 * This is a getter which return the date end 	
	 * @return end_date
	 */
	public  LocalDate getEndDate() {
		return end_date;
	}

	/**
	 * This is a setter which set the the date end  	
	 * @param end_date
	 */
	public void setEndDate( String end_date) {
		this.end_date = LocalDate.parse(end_date);
	}

	/**
	 * This is a getter which return the fee of registration	
	 * @return registration_fee
	 */
	public Double getFeeRegistration() {
		return registration_fee;
	}

	/**
	 * This is a setter which set the fee of registration
	 * @param registration_fee
	 */
	public void setFeeRegistration(Double registration_fee) {
		this.registration_fee = registration_fee;
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
	 */
	public void setCountry(String Country) {
		this.country = Country;
	}
	
	/**
	 * @return
	 */
	public String getRegion() {
		return region;
	}

	public void setRegion(String Region) {
		this.region = Region;
	}
	
	/**
	 * This is a constructor which initializes the conference object 
	 * @param url primary key
	 */
	public Conference(String url) {
		this.url = url;
		this.title =new String("");
		this.start_date = LocalDate.now() ;
		this.end_date =  LocalDate.now() ;
		this.registration_fee = 0.0;
		this.country = "";
		this.region="";
	}

	@Override
	public String toString() {
		return "Conference [url=" + url + ", title=" + title + ", start_date=" + start_date + ", end_date=" + end_date
				+ ", registration_fee=" + registration_fee + ", country=" + country+ "region = " + region +"]";
	}
	
	
	

}
