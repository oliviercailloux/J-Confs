package io.github.oliviercailloux.jconfs.calendar;


import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.checkerframework.common.reflection.qual.NewInstance;
import org.junit.jupiter.api.Test;

import com.github.caldav4j.exceptions.CalDAV4JException;

import io.github.oliviercailloux.jconfs.calendar.CalendarOnCloudInterface;
import io.github.oliviercailloux.jconfs.calendar.CalendarOnFruux;
import io.github.oliviercailloux.jconfs.calendar.CalendarOnNextcloud;

import io.github.oliviercailloux.jconfs.conference.Conference;
import io.github.oliviercailloux.jconfs.conference.InvalidConferenceFormatException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;


public class TestCalendarOnCloud {

	@Test
	public void testGetAllOnlineConferencesFromFruux()
			throws InvalidConferenceFormatException, com.github.caldav4j.exceptions.CalDAV4JException {
		CalendarOnFruux instanceCalendarOnline = CalendarOnFruux.newInstance(); 
		Set<Conference> collectionConferences = instanceCalendarOnline.getOnlineConferences();
		Iterator<Conference> iteratorConf = collectionConferences.iterator();
		while (iteratorConf.hasNext()) {
			Conference conferenceOnline = iteratorConf.next();
			System.out.println(conferenceOnline.toString());
		}
	}
	
 
	@Test
	public void testGetAllOnlineConferencesFromNextCloud()
			throws InvalidConferenceFormatException, com.github.caldav4j.exceptions.CalDAV4JException {
		CalendarOnNextcloud instanceCalendarOnline = CalendarOnNextcloud.newInstance(); 
		List<Calendar> collectionConferences = instanceCalendarOnline.getOnlineConferences();
		Iterator<Calendar> iteratorConf = collectionConferences.iterator();
		while (iteratorConf.hasNext()) {
			Calendar conferenceOnline = iteratorConf.next();
			System.out.println(conferenceOnline.toString());
		}
	}
}