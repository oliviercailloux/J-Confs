package io.github.oliviercailloux;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.osaf.caldav4j.exceptions.CalDAV4JException;

import io.github.oliviercailloux.jconfs.calendar.CalendarOnline;
import io.github.oliviercailloux.jconfs.conference.Conference;
import io.github.oliviercailloux.jconfs.conference.InvalidConferenceFormatException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;

public class TestCalendarOnline {

	public void testGetOnlineConferenceFromUid() throws CalDAV4JException, InvalidConferenceFormatException {
		CalendarOnline instanceCalendarOnline = CalendarOnline.getInstance();
		Conference conferenceFound;
		String uidSearch = "4e14d618-1d93-40a3-adb9-3c01dca8ee67";
		conferenceFound = instanceCalendarOnline.getConferenceFromUid(uidSearch);
		assertEquals(conferenceFound.getTitle(), "Third iteration JAVA");
		assertEquals(conferenceFound.getUid(), uidSearch);
		assertEquals(conferenceFound.getCity(), "Paris");
		assertEquals(conferenceFound.getCountry(), "France");
		assertEquals(conferenceFound.getStartDate().toString(), "2019-06-21");
		assertEquals(conferenceFound.getFeeRegistration().toString(), "1.46");
	}

	@Test
	public void testGetAllOnlineConferences() throws CalDAV4JException, InvalidConferenceFormatException {
		CalendarOnline instanceCalendarOnline = CalendarOnline.getInstance();
		Set<Conference> collectionConferences = instanceCalendarOnline.getOnlineConferences();
		Iterator<Conference> iteratorConf = collectionConferences.iterator();
		while (iteratorConf.hasNext()) {
			Conference conferenceOnline = iteratorConf.next();
			System.out.println(conferenceOnline.toString());
		}
	}

	@Test
	public void testConferenceToVEvent() throws URISyntaxException, ParseException, MalformedURLException {
		VEvent conferenceVEvent;
		CalendarOnline instanceCalendarOnline = CalendarOnline.getInstance();
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

		Conference conference = new Conference(uid, url, title, start_, end_, feeRegistration, country, city);

		conferenceVEvent = instanceCalendarOnline.conferenceToVEvent(conference);

		assertEquals(conferenceVEvent.getProperty(Property.SUMMARY).getValue(), conference.getTitle());
		assertEquals(conferenceVEvent.getProperty(Property.LOCATION).getValue(),
				conference.getCity() + "," + conference.getCountry());
		assertEquals(conferenceVEvent.getProperty(Property.UID).getValue(), conference.getUid());
		assertEquals(conferenceVEvent.getProperty(Property.DESCRIPTION).getValue(),
				"Fee:" + conference.getFeeRegistration());
		Property startDate_ = new DtStart(conference.getEndDate().toString());
		Property endDate_ = new DtEnd(conference.getStartDate().toString());
		assertTrue(conferenceVEvent.getProperty(Property.DTSTART).equals(startDate_));
		assertTrue(conferenceVEvent.getProperty(Property.DTEND).equals(endDate_));
	}

}
