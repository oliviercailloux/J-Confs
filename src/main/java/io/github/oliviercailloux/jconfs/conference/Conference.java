package io.github.oliviercailloux.jconfs.conference;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.net.URL;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.fortuna.ical4j.util.RandomUidGenerator;

/**
 * This class is immutable. Please note that the registrationFee is in euro cent.
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
  private Set<String> participants;

  /**
   * This is a constructor which initializes the conference object
   * 
   * @param uid not {@code null}
   * @param url
   * @param title not {@code null}
   * @param startDate not {@code null}
   * @param endDate not {@code null}
   * @param registrationFee
   * @param country
   * @param city
   * @param participants
   */
  private Conference() {
    this.uid = "";
    this.url = Optional.empty();
    this.registrationFee = Optional.empty();
    this.country = "";
    this.city = "";
    this.participants = new HashSet<String>();
  }

  public Optional<URL> getUrl() {
    return url;
  }

  /**
   * This method is used to get the url without the "Optional" in front.
   * 
   * @return url as a string
   */
  public String getUrlAsShortString() {
    if (url.equals(Optional.empty())) {
      return "";
    }
    String url = this.url.toString();
    if (url.length() < 9) { // case where there is no "Optional"
      return url;
    }
    return url.substring(9, url.length() - 1);
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

  /**
   * this method return the price of registration in a conference in euro cent
   * 
   * @return Optional<Integer>
   */
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

  public String getParticipants() {
    return participants.toString();
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
          && country.equals(conference2.country) && participants.equals(conference2.participants)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, title, registrationFee, startDate, endDate, country, city,
        participants);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("UID", uid).add("url", url).add("title", title)
        .add("startDate", startDate).add("endDate", endDate).add("registrationFee", registrationFee)
        .add("country", country).add("city", city).add("participants", participants).toString();
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
      if ((this.conferenceToBuild.endDate == null)
          || (startDate.isBefore(this.conferenceToBuild.endDate)))
        this.conferenceToBuild.startDate = startDate;
      else
        throw new IllegalArgumentException();
      return this;
    }

    /**
     * This method makes you able to change the end date.
     * 
     * @throws NullPointerException - if the parameter is null or if the conference start date has
     *         not been set up yet.
     */
    public ConferenceBuilder setEndDate(Instant endDate) {
      Preconditions.checkNotNull(endDate);
      if ((this.conferenceToBuild.startDate == null)
          || (this.conferenceToBuild.startDate.isBefore(endDate)))
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

    public ConferenceBuilder setParticipant(String oneParticipant) {
      if (conferenceToBuild.participants.size() > 0) {
        throw new IllegalStateException("Only one participant can attend a conference");
      }
      this.conferenceToBuild.participants.add(Strings.nullToEmpty(oneParticipant));
      return this;
    }
    
    public ConferenceBuilder setAdresse(URL ad) {
      this.conferenceToBuild.url = Optional.ofNullable(url);
      return this;
    }
    
  }
}
