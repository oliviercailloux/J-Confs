package io.github.oliviercailloux.jconfs.conference;

import java.net.URL;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

import com.google.common.base.MoreObjects;

import net.fortuna.ical4j.model.property.Url;

/**
 * @author huong,camille This class is immutable.
 *
 */
public class Conference {
	private Optional<URL> url;
	private String uid;
	private String title;
	private Instant startDate;
	private Instant endDate;
	private Optional<Integer> registrationFee;
	private String country;
	private String city;

	/**
	 * This is a constructor which initializes the conference object
	 * 
	 * @param uid	not <code>null</code>
	 * @param url
	 * @param title not <code>null</code>
	 * @param startDate       not <code>null</code>
	 * @param endDate         not <code>null</code>
	 * @param registrationFee
	 * @param country
	 * @param city
	 */
	private Conference() {
		this.url=Optional.empty();
		this.registrationFee=Optional.empty();
		this.country="";
		this.city="";
	}

	/**
	 * This is a getter which return the URL
	 * 
	 * @return url
	 */
	public Optional<URL> getUrl() {
		return url;
	}

	/**
	 * This is a getter which return the title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This is a getter which return the date start
	 * 
	 * @return not <code>null</code>
	 */
	public Instant getStartDate() {
		return startDate;
	}

	/**
	 * This is a getter which return the date end
	 * 
	 * @return not <code>null</code>
	 */
	public Instant getEndDate() {
		return endDate;
	}

	/**
	 * This is a getter which return the fee of registration
	 * 
	 * @return registrationFee
	 */
	public Optional<Integer> getFeeRegistration() {
		return registrationFee;
	}

	/**
	 * This is a getter which return the country
	 * 
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * This is a getter which return the city
	 * 
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * This is a getter which return the iud
	 * 
	 * @return not <code>null</code>
	 */
	public String getUid() {
		return this.uid;
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
	public int hashCode() {
		return Objects.hash(url, title, registrationFee, startDate, endDate, country, city);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("UID", uid).add("url", url).add("title", title)
				.add("startDate", startDate).add("endDate", endDate).add("registrationFee", registrationFee)
				.add("country", country).add("city", city).toString();
	}
	
	
	
	
	
	public static class ConferenceBuilder {
        private Conference conferenceToBuild;

        public ConferenceBuilder() {
            conferenceToBuild = new Conference();
        }

        public Conference build() {
            Conference builtConference = conferenceToBuild;
            conferenceToBuild = new Conference();
    		Preconditions.checkNotNull(builtConference.uid);
    		Preconditions.checkNotNull(builtConference.title);
    		Preconditions.checkNotNull(builtConference.startDate);
    		Preconditions.checkNotNull(builtConference.endDate);
            return builtConference;
        }

        public ConferenceBuilder setUid(String uid) {
            this.conferenceToBuild.uid = uid;
            return this;
        }

        public ConferenceBuilder setTitle(String title) {
            this.conferenceToBuild.title = title;
            return this;
        }
        
        public ConferenceBuilder setStartDate(Instant startDate) {
            this.conferenceToBuild.startDate = startDate;
            return this;
        }
        
        public ConferenceBuilder setEndDate(Instant endDate) {
            this.conferenceToBuild.endDate = endDate;
            return this;
        }
        
        public ConferenceBuilder setRegistrationFee(Integer registrationFee) {
            this.conferenceToBuild.registrationFee = Optional.ofNullable(registrationFee);
            return this;
        }
        
        public ConferenceBuilder setCountry(String country) {
            this.conferenceToBuild.country = Strings.emptyToNull(country);
            return this;
        }
        
        public ConferenceBuilder setCity(String city) {
            this.conferenceToBuild.city = Strings.emptyToNull(city);
            return this;
        }
        
        public ConferenceBuilder setUrl(URL url) {
            this.conferenceToBuild.url = Optional.ofNullable(url);
            return this;
        }


    }

	

}