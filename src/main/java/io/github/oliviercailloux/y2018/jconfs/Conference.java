package io.github.oliviercailloux.y2018.jconfs;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import com.google.common.base.MoreObjects;



/**
 * @author huong,camille
 */
public class Conference {
	private URL url;
	private String uid;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double registrationFee;
	private String country;
	private String city;

	/**
	 * This is a getter which return the URL  	
	 * @return not <code>null</code>
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * This is a getter which return the title  	
	 * @return not <code>null</code>
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title 	
	 * @param title
	 * 			not <code>null</code>
	 */
	public void setTitle(String title) {
		this.title = Objects.requireNonNull(title);
	}

	/**
	 * This is a getter which return the date start  	
	 * @return not <code>null</code>
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date  	
	 * @param startDate
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Sets the start date  	
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.startDate = LocalDate.parse(startDate, formatter);
	}

	/**
	 * This is a getter which return the date end 	
	 * @return endDate
	 */
	public  LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * Sets the the end date   	
	 * @param end_date
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	/**
	 * Sets the the end date   	
	 * @param end_date
	 */
	public void setEndDate(String endDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.endDate = LocalDate.parse(endDate, formatter);
	}

	/**
	 * This is a getter which return the fee of registration	
	 * @return registrationFee
	 */
	public Double getFeeRegistration() {
		return registrationFee;
	}

	/**
	 * Sets the fee of registration
	 * @param registrationFee
	 */
	public void setFeeRegistration(double registrationFee) {
		this.registrationFee = Objects.requireNonNull(registrationFee);
	}


	/**
	 * This is a getter which return the country	
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country
	 * @param country
	 * 			not <code>null</code>
	 */
	public void setCountry(String country) {
		this.country = Objects.requireNonNull(country);
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
	 * 			not <code>null</code>
	 */
	public void setCity(String city) {
		this.city = Objects.requireNonNull(city);
	}
	
	public String getUid() {
		return this.uid;
	}
	
	public void setUid(String uid) {
		this.uid=uid;
	}

	/**
	 * This is a constructor which initializes the conference object 
	 * @param url primary key
	 * 			not <code>null</code>
	 */
	public Conference(URL url) {
		this.url =Objects.requireNonNull(url);
		this.title ="";
		this.startDate = LocalDate.now();
		this.endDate = LocalDate.now();
		this.registrationFee = 0.0;
		this.country = "";
		this.city="";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Conference) {
			Conference conference2 = (Conference) obj;
			
			if (title.equals(conference2.title) && url.equals(conference2.url)
					&& startDate.equals(conference2.startDate) && endDate.equals(conference2.endDate)
					&& registrationFee.equals(conference2.registrationFee) && city.equals(conference2.city)
					&& country.equals(conference2.country)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode(){
		return Objects.hash(url,title,registrationFee,startDate,endDate,country,city);
	}


	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("UID", uid)
				.add("url", url)
				.add("title", title)
				.add("startDate", startDate)
				.add("endDate", endDate)
				.add("registrationFee", registrationFee)
				.add("country", country)
				.add("city", city)
				.toString();
	}

}
