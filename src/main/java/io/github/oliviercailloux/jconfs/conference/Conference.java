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
import net.fortuna.ical4j.util.RandomUidGenerator;

/**
 * This class is immutable. Please note that the registrationFee is in euro
 * cent.
 * 
 * @author huong,camille
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
	 * @param uid             not <code>null</code>
	 * @param url
	 * @param title           not <code>null</code>
	 * @param startDate       not <code>null</code>
	 * @param endDate         not <code>null</code>
	 * @param registrationFee
	 * @param country
	 * @param city
	 */
	private Conference() {
		this.uid = "";
		this.url = Optional.empty();
		this.registrationFee = Optional.empty();
		this.country = "";
		this.city = "";
	}

	public Optional<URL> getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public Optional<Integer> getFeeRegistration() {
		return registrationFee;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getUid() {
		return this.uid;
	}

	public boolean isConf() {
		Preconditions.checkNotNull(this.title);
		Preconditions.checkNotNull(this.startDate);
		Preconditions.checkNotNull(this.endDate);
		return true;
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
			if (builtConference.uid.isEmpty())
				builtConference.uid = new RandomUidGenerator().generateUid().getValue();
			Preconditions.checkNotNull(builtConference.title);
			Preconditions.checkNotNull(builtConference.startDate);
			Preconditions.checkNotNull(builtConference.endDate);
			return builtConference;
		}

		public ConferenceBuilder setUid(String uid) {
			Preconditions.checkNotNull(uid);
			this.conferenceToBuild.uid = uid;
			return this;
		}

		public ConferenceBuilder setTitle(String title) {
			Preconditions.checkNotNull(title);
			this.conferenceToBuild.title = title;
			return this;
		}

		public ConferenceBuilder setStartDate(Instant startDate) {
			Preconditions.checkNotNull(startDate);
			if ((this.conferenceToBuild.endDate == null) || (startDate.isBefore(this.conferenceToBuild.endDate)))
				this.conferenceToBuild.startDate = startDate;
			else
				throw new IllegalArgumentException();
			return this;
		}

		/**
		 * This method makes you able to change the end date.
		 * 
		 * @throws NullPointerException - if the parameter is null or if the conference
		 *                              start date has not been set up yet.
		 */
		public ConferenceBuilder setEndDate(Instant endDate) {
			Preconditions.checkNotNull(endDate);
			if ((this.conferenceToBuild.startDate == null) || (this.conferenceToBuild.startDate.isBefore(endDate)))
				this.conferenceToBuild.endDate = endDate;
			else
				throw new IllegalArgumentException();
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
