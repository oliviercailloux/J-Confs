package io.github.oliviercailloux.y2018.jconfs;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;



/**
 * @author huong,camille
 */
public class Conference {
	private URL url;
	private String title;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double registrationFee;
	private String country;
	private String city;
	
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
	 * @return 
	 */
	public void setTitle(String title) {
		this.title = Objects.requireNonNull(title);
	}

	/**
	 * This is a getter which return the date start  	
	 * @return startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * This is a setter which set the the date start  	
	 * @param startDate
	 * @throws ParseException 
	 */
	public void setStartDate(String startDate) throws ParseException {
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
	 * This is a setter which set the the date end  	
	 * @param end_date
	 * @throws ParseException 
	 */
	public void setEndDate( String endDate) throws ParseException {
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
	 * This is a setter which set the fee of registration
	 * @param registrationFee
	 */
	public void setFeeRegistration(Double registrationFee) {
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
	 * This is a setter which set the country
	 * @param country
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
	 */
	public void setCity(String city) {
		this.city = Objects.requireNonNull(city);
	}
	
	/**
	 * This is a constructor which initializes the conference object 
	 * @param url primary key
	 */
	public Conference(URL url) {
		this.url =Objects.requireNonNull(url);
		this.title =new String("");
		this.startDate = LocalDate.now();
		this.endDate = LocalDate.now();
		this.registrationFee = 0.0;
		this.country = "";
		this.city="";
	}
	
	@Override
	/**
	 * Compare the conference to object by comparing all attributes 
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Conference) {
			Conference conference2 = (Conference) obj;
			System.out.println(toString());
			System.out.println(conference2.toString());
			if (title.equals(conference2.title) && url.equals(conference2.url)
					&& startDate.equals(conference2.startDate) && endDate.equals(conference2.endDate)
					&& registrationFee == conference2.registrationFee && city.equals(conference2.city)
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
		return "Conference [url=" + url + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", registrationFee=" + registrationFee + ", country=" + country+ "city = " + city +"]";
	}
	
	
	

}
