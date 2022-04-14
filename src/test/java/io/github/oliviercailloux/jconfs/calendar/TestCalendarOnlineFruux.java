package io.github.oliviercailloux.jconfs.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.oliviercailloux.jconfs.conference.Conference;
import io.github.oliviercailloux.jconfs.conference.Conference.ConferenceBuilder;
import java.net.URL;
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
 * Unit tests for connect to a calendar on Fruux cloud platform
 * 
 * @author machria & sbourg
 */

public class TestCalendarOnlineFruux {

	static String uidpr = new RandomUidGenerator().generateUid().getValue();

	// Introduce constant values for url, username,password and calendarId
	private final String lv_url = "dav.fruux.com";
	private final String lv_username = "b3297393754";
	private final String lv_password = "4pq8nzbhzugs";
	private final String lv_calendarID = "8b3ff300-b8ce-4d85-a255-76ea3dff1338";

	@Test
	public void testGetOnlineConferenceFromUid() throws Exception {

		CalendarOnline instanceCalendarOnline = new CalendarOnline(
				new CalDavCalendarGeneric(lv_url, lv_username, lv_password, lv_calendarID, ""));
		String uidSearch = "0cf024cb-9dfd-4956-8076-a7c24a0ff8b6";
		Optional<Conference> potentialConference;
		potentialConference = instanceCalendarOnline.getConferenceFromUid(uidSearch);
		if (potentialConference.isPresent()) {
			Conference conferenceFound = potentialConference.get();
			assertEquals("Java formation", conferenceFound.getTitle());
			assertEquals(uidSearch, conferenceFound.getUid());
			assertEquals("Paris", conferenceFound.getCity());
			assertEquals("France", conferenceFound.getCountry());
			assertEquals(Instant.parse("2019-08-06T00:00:00Z"), conferenceFound.getStartDate());
			assertEquals(136, conferenceFound.getFeeRegistration().get());
		} else {
			fail(new NullPointerException());
		}
	}

	@Test
	public void testGetAllOnlineConferences() throws Exception {

		CalendarOnline instanceCalendarOnline = new CalendarOnline(
				new CalDavCalendarGeneric(lv_url, lv_username, lv_password, lv_calendarID, ""));
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
		CalendarOnline instanceCalendarOnline = new CalendarOnline(
				new CalDavCalendarGeneric(lv_url, lv_username, lv_password, lv_calendarID, ""));
		URL url = new URL("http://fruux.com");
		String city = "Paris";
		String country = "France";
		String endDate = "08/08/2019";
		Double feeRegistration = 1.36;
		String startDate = "06/08/2019";
		String title = "Java formation";
		String uid = "4e14d618-1d93-29a3-adb3-2c21dca5ee67";
		LocalDate start_ = null;
		LocalDate end_ = null;

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			start_ = LocalDate.parse(startDate, formatter);
			end_ = LocalDate.parse(endDate, formatter);
		} catch (Exception e) {
			throw new IllegalArgumentException("Date impossible to put in the conference", e);
		}

		ConferenceBuilder theBuild = new ConferenceBuilder();
		Conference conference = theBuild.setUid(uid).setUrl(url).setTitle(title)
				.setStartDate(start_.atStartOfDay(ZoneOffset.UTC).toInstant())
				.setEndDate(end_.atStartOfDay(ZoneOffset.UTC).toInstant())
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
		CalendarOnline instanceCalendarOnline = new CalendarOnline(
				new CalDavCalendarGeneric(lv_url, lv_username, lv_password, lv_calendarID, ""));
		LocalDate start_ = null;
		LocalDate end_ = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			start_ = LocalDate.parse("06/08/2019", formatter);
			end_ = LocalDate.parse("08/08/2019", formatter);
		} catch (Exception e) {
			throw new IllegalArgumentException("Date impossible to put in the conference", e);
		}

		ConferenceBuilder theBuild = new ConferenceBuilder();
		Conference conference = theBuild.setUid(uidpr).setUrl(new URL("http://fruux.com")).setTitle("Java formation")
				.setStartDate(start_.atStartOfDay(ZoneOffset.UTC).toInstant())
				.setEndDate(end_.atStartOfDay(ZoneOffset.UTC).toInstant()).setRegistrationFee(136).setCity("Paris")
				.setCountry("France").build();

		instanceCalendarOnline.addOnlineConference(conference);
		Optional<Conference> confTest = instanceCalendarOnline.getConferenceFromUid(uidpr);
		if (!confTest.isPresent()) {
			fail();
		}
	}

	@Test
	public void testDelete() throws Exception {
		CalendarOnline instanceCalendarOnline = new CalendarOnline(
				new CalDavCalendarGeneric(lv_url, lv_username, lv_password, lv_calendarID, ""));
		instanceCalendarOnline.deleteOnlineConference(uidpr);
		System.out.println(instanceCalendarOnline.getOnlineConferences());
		if (instanceCalendarOnline.getConferenceFromUid(uidpr).isPresent()) {
			fail();
		}
	}
}
