package io.github.oliviercailloux.jconfs.conference;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.caldav4j.exceptions.CalDAV4JException;
import io.github.oliviercailloux.jconfs.calendar.CalDavCalendarGeneric;
import io.github.oliviercailloux.jconfs.calendar.CalendarOnline;
import io.github.oliviercailloux.jconfs.conference.Conference.ConferenceBuilder;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;
import net.fortuna.ical4j.util.RandomUidGenerator;
import org.junit.jupiter.api.Test;

/**
 * Unit tests in order to test that a participant can attend a conference
 * 
 * @author Sarah & Mikhal
 */

public class CheckParticipantsTests {

  private final String lv_url = "dav.fruux.com";
  private final String lv_username = "b3297394371";
  private final String lv_password = "g8tokd3q0hc2";
  private final String lv_calendarID = "548d1281-4843-4582-8d68-aee8fe0c45da";

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

    CalendarOnline instanceCalendarOnline = new CalendarOnline(
        new CalDavCalendarGeneric(lv_url, lv_username, lv_password, lv_calendarID, ""));
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
    Optional<Conference> confTest = instanceCalendarOnline.getConferenceFromUid(uidpr);
    Conference confCheck = confTest.get();
    System.out.println(confCheck.toString());
    assertEquals("[Nathan]", confCheck.getParticipants());

  }
}
