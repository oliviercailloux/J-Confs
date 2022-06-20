package io.github.oliviercailloux.jconfs.conference;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.caldav4j.exceptions.CalDAV4JException;
import io.github.oliviercailloux.jaris.collections.ImmutableCompleteMap;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jconfs.calendar.CalDavCalendarGeneric;
import io.github.oliviercailloux.jconfs.calendar.CalendarOnline;
import io.github.oliviercailloux.jconfs.calendar.JARiS.FruuxKeysCredential;
import io.github.oliviercailloux.jconfs.conference.Conference.ConferenceBuilder;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Instant;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.RandomUidGenerator;
import org.junit.jupiter.api.Test;

/**
 * Unit tests in order to test that a participant can attend a conference
 * 
 * @author Sarah & Mikhal
 */

public class CheckParticipantsTests {

  static CredentialsReader<FruuxKeysCredential> reader =
      CredentialsReader.using(FruuxKeysCredential.class, Path.of("API_Credentials_Fruux.txt"));
  static ImmutableCompleteMap<FruuxKeysCredential, String> Auth = reader.getCredentials();

  static String uidpr = new RandomUidGenerator().generateUid().getValue();

  /**
   * The test add one participant (Nathan) to the conference "Python". It respects the contract of
   * the Conference class.
   * 
   */

  @Test
  public void testOneParticipant() {
    ConferenceBuilder conf1 = new ConferenceBuilder();
    conf1.setTitle("Python");
    conf1.setCountry("England");
    Instant deb1 = Instant.parse("2022-06-09T10:15:30.00Z");
    conf1.setStartDate(deb1);
    Instant deb2 = Instant.parse("2022-06-10T16:22:52.966Z");
    conf1.setEndDate(deb2);
    conf1.setCity("London");
    conf1.setRegistrationFee(10);
    conf1.setParticipant("Nathan");
    Conference c1 = conf1.build();
    assertEquals(c1.getParticipants(), "[Nathan]");

  }

  /**
   * This test is used to check if the participant has been correctly added to the online Calendar.
   * 
   * @author Julien and Saidaniyaa
   * 
   */

  @Test
  public void testPaticipantOnlineCalendar()
      throws MalformedURLException, CalDAV4JException, URISyntaxException {

    VEvent confVEvent;
    CalendarOnline instanceCalendarOnline = new CalendarOnline(new CalDavCalendarGeneric(Auth, ""));
    URL url = new URL("http://fruux.com");
    ConferenceBuilder conf1 = new ConferenceBuilder();
    conf1.setUid(uidpr);
    conf1.setUrl(url);
    conf1.setTitle("Python");
    conf1.setCountry("England");
    conf1.setCity("London");
    Instant deb1 = Instant.parse("2022-06-09T10:15:30.00Z");
    conf1.setStartDate(deb1);
    Instant deb2 = Instant.parse("2022-06-10T16:22:52.966Z");
    conf1.setEndDate(deb2);
    conf1.setRegistrationFee(10);
    conf1.setParticipant("Nathan");
    Conference c1 = conf1.build();
    instanceCalendarOnline.addOnlineConference(c1);

    confVEvent = instanceCalendarOnline.conferenceToVEvent(c1);

    assertEquals(confVEvent.getProperty(Property.SUMMARY).getValue(), c1.getTitle());
    assertEquals(confVEvent.getProperty(Property.ATTENDEE).getValue(),
        c1.getParticipants().substring(1, c1.getParticipants().length() - 1));
    // c1.getParticipants() returns a hashlist in the form of a string, so it is "[Nathan]". We want
    // to delete the brackets.

  }
}
