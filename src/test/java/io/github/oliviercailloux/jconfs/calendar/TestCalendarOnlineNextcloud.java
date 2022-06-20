package io.github.oliviercailloux.jconfs.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.oliviercailloux.jaris.collections.ImmutableCompleteMap;
import io.github.oliviercailloux.jaris.credentials.CredentialsReader;
import io.github.oliviercailloux.jconfs.calendar.JARiS.FruuxKeysCredential;
import io.github.oliviercailloux.jconfs.conference.Conference;
import io.github.oliviercailloux.jconfs.conference.Conference.ConferenceBuilder;
import java.net.URL;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.RandomUidGenerator;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for connect to a calendar on Nextcloud platform
 * 
 * @author machria & sbourg
 */

public class TestCalendarOnlineNextcloud {
  static String uidpr = new RandomUidGenerator().generateUid().getValue();
  static CredentialsReader<FruuxKeysCredential> reader = CredentialsReader.using(FruuxKeysCredential.class, Path.of("API_Credentials_Nextcloud.txt"));
  static ImmutableCompleteMap<FruuxKeysCredential, String> Auth = reader.getCredentials();

  private final String lv_url = "nch.pl";
  private final String lv_username = "JAVA_PROJECT";
  private final String lv_password = "Azert2022&&";
  private final String lv_calendarID = "projet_j-conf";
  private final String lv_postUrl = "/remote.php/dav";

  @Test
  public void testGetOnlineConferenceFromUid() throws Exception {

    CalendarOnline instanceCalendarOnline =
        new CalendarOnline(new CalDavCalendarGeneric(Auth, "/remote.php/dav"));
    String uidSearch = "b0672b91-af89-491f-92be-9e0032828413";
    Optional<Conference> potentialConference;
    potentialConference = instanceCalendarOnline.getConferenceFromUid(uidSearch);
    if (potentialConference.isPresent()) {
      Conference conferenceFound = potentialConference.get();
      assertEquals("Java formation", conferenceFound.getTitle());
      assertEquals(uidSearch, conferenceFound.getUid());
      assertEquals("Paris", conferenceFound.getCity());
      assertEquals("France", conferenceFound.getCountry());
      assertEquals(Instant.parse("2022-05-31T00:00:00Z"), conferenceFound.getStartDate());
    } else {
      fail(new NullPointerException());
    }
  }

  @Test
  public void testGetAllOnlineConferences() throws Exception {

    CalendarOnline instanceCalendarOnline =
        new CalendarOnline(new CalDavCalendarGeneric(Auth, "/remote.php/dav"));
    Set<Conference> collectionConferences = instanceCalendarOnline.getOnlineConferences();
    Iterator<Conference> iteratorConf = collectionConferences.iterator();
    while (iteratorConf.hasNext()) {
      Conference conferenceOnline = iteratorConf.next();
      System.out.println(conferenceOnline.toString());
    }
  }

  @Test
  public void testConferenceToVEvent() throws Exception {
    VEvent conferenceVEvent;
    CalendarOnline instanceCalendarOnline =
        new CalendarOnline(new CalDavCalendarGeneric(Auth, "/remote.php/dav"));
    URL url = new URL("http://fruux.com");
    String city = "Paris";
    String country = "France";
    String endDate = "08/08/2019";
    Double feeRegistration = 1.36;
    String startDate = "06/08/2019";
    String title = "Java formation";
    String uid = "4e14d618-1d93-29a3-adb3-2c21dca5ee67";
    LocalDate start = null;
    LocalDate end = null;

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      start = LocalDate.parse(startDate, formatter);
      end = LocalDate.parse(endDate, formatter);
    } catch (Exception e) {
      throw new IllegalArgumentException("Date impossible to put in the conference", e);
    }

    ConferenceBuilder theBuild = new ConferenceBuilder();
    Conference conference = theBuild.setUid(uid).setUrl(url).setTitle(title)
        .setStartDate(start.atStartOfDay(ZoneOffset.UTC).toInstant())
        .setEndDate(end.atStartOfDay(ZoneOffset.UTC).toInstant())
        .setRegistrationFee(feeRegistration.intValue()).setCity(city).setCountry(country).build();

    conferenceVEvent = instanceCalendarOnline.conferenceToVEvent(conference);

    assertEquals(conferenceVEvent.getProperty(Property.SUMMARY).getValue(), conference.getTitle());
    assertEquals(conferenceVEvent.getProperty(Property.LOCATION).getValue(),
        conference.getCity() + "," + conference.getCountry());
    assertEquals(conferenceVEvent.getProperty(Property.UID).getValue(), conference.getUid());
    assertEquals(conferenceVEvent.getProperty(Property.DESCRIPTION).getValue(),
        "Fee:" + conference.getFeeRegistration().get());
  }

  @Test
  public void testAddOnlineConference() throws Exception {
    CalendarOnline instanceCalendarOnline =
        new CalendarOnline(new CalDavCalendarGeneric(Auth, "/remote.php/dav"));
    LocalDate start = null;
    LocalDate end = null;
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      start = LocalDate.parse("06/08/2019", formatter);
      end = LocalDate.parse("08/08/2019", formatter);
    } catch (Exception e) {
      throw new IllegalArgumentException("Date impossible to put in the conference", e);
    }

    ConferenceBuilder theBuild = new ConferenceBuilder();
    Conference conference = theBuild.setUid(uidpr).setUrl(new URL("http://fruux.com"))
        .setTitle("Java formation").setStartDate(start.atStartOfDay(ZoneOffset.UTC).toInstant())
        .setEndDate(end.atStartOfDay(ZoneOffset.UTC).toInstant()).setRegistrationFee(136)
        .setCity("Paris").setCountry("France").build();

    instanceCalendarOnline.addOnlineConference(conference);
    Optional<Conference> confTest = instanceCalendarOnline.getConferenceFromUid(uidpr);
    assertTrue(confTest.isPresent());
  }

  @Test
  public void testDelete() throws Exception {

    CalendarOnline instanceCalendarOnline =
        new CalendarOnline(new CalDavCalendarGeneric(Auth, "/remote.php/dav"));
    instanceCalendarOnline.deleteOnlineConference(uidpr);
    System.out.println(instanceCalendarOnline.getOnlineConferences());
    if (instanceCalendarOnline.getConferenceFromUid(uidpr).isPresent()) {
      fail();
    }
  }
}
