package io.github.oliviercailloux.jconfs.calendar;

import java.util.List;
import java.util.Set;
import java.net.URISyntaxException;
import java.rmi.server.UID;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Name;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.osaf.caldav4j.CalDAVCollection;
import org.osaf.caldav4j.CalDAVConstants;
import org.osaf.caldav4j.exceptions.CalDAV4JException;
import org.osaf.caldav4j.methods.CalDAV4JMethodFactory;
import org.osaf.caldav4j.methods.HttpClient;
import org.osaf.caldav4j.model.request.CalendarQuery;
import org.osaf.caldav4j.util.GenerateQuery;
import org.osaf.caldav4j.util.ICalendarUtils;

import io.github.oliviercailloux.jconfs.conference.Conference;
import io.github.oliviercailloux.jconfs.conference.ConferenceReader;
import io.github.oliviercailloux.jconfs.conference.InvalidConferenceFormatException;

/**
 * @author nikola 
 * This class permits to access to online conferences from https://fruux.com
 */
public class CalendarOnline {
	final private static String userNameFruux = "b3297431258";
	final private static String passwordFruux = "jizbr5fuj9gi";
	final private static String calendarIDFruux = "6e8c6372-eba5-43da-9eed-8e5413559c99";
	private static HttpClient httpClient;
	private static CalendarOnline instanceCalendarOnline;
	private CalDAVCollection collectionCalendarsOnline;

	private CalendarOnline() {
		httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost("dav.fruux.com", 443, "https");
		UsernamePasswordCredentials httpCredentials = new UsernamePasswordCredentials(userNameFruux, passwordFruux);
		httpClient.getState().setCredentials(AuthScope.ANY, httpCredentials);
		httpClient.getParams().setAuthenticationPreemptive(true);

		collectionCalendarsOnline = new CalDAVCollection("/calendars/" + userNameFruux + "/" + calendarIDFruux,
				(HostConfiguration) httpClient.getHostConfiguration().clone(), new CalDAV4JMethodFactory(),
				CalDAVConstants.PROC_ID_DEFAULT);
	}

	public static CalendarOnline getInstance() {
		if (instanceCalendarOnline == null) {
			instanceCalendarOnline = new CalendarOnline();
		}
		return instanceCalendarOnline;
	}

	/**
	 * This method has been partially taken from :
	 * https://github.com/dedeibel/list-events-caldav4j-example/blob/master/src/test/java/benjaminpeter/name/ListCalendarTest.java
	 * It allows you to retrieve events from calendars hosted on a fruux account
	 * 
	 * @return
	 * @throws CalDAV4JException
	 * @throws InvalidConferenceFormatException
	 */
	public Set<Conference> getOnlineConferences() throws CalDAV4JException, InvalidConferenceFormatException {
		GenerateQuery searchQuery = new GenerateQuery();
		CalendarQuery calendarQuery = searchQuery.generate();
		List<Calendar> calendarsResult = collectionCalendarsOnline.queryCalendars(httpClient, calendarQuery);
		Set<Conference> listConferencesUser = new LinkedHashSet<>();
		for (Calendar calendar : calendarsResult) {
			ComponentList<VEvent> componentList = calendar.getComponents(Component.VEVENT);
			Iterator<VEvent> eventIterator = componentList.iterator();
			while (eventIterator.hasNext()) {
				VEvent vEventFound = eventIterator.next();
				listConferencesUser.add(ConferenceReader.createConference(vEventFound));
			}
		}
		return listConferencesUser;
	}

	/**
	 * Method that upload online the conference that has been modified by a user
	 * 
	 * @param conferenceEdited
	 * @throws ParseException
	 * @throws CalDAV4JException
	 * @throws URISyntaxException
	 */
	public void editConferenceOnline(Conference conferenceEdited)
			throws ParseException, CalDAV4JException, URISyntaxException {
		VEvent vEventConferenceModified = conferenceToVEvent(conferenceEdited);
		collectionCalendarsOnline.updateMasterEvent(httpClient, vEventConferenceModified, null);
	}

	/**
	 * @param conferenceEdited : the conference you want to convert
	 * @return the VEvent corresponding to your Conference
	 * @throws URISyntaxException
	 * @throws ParseException
	 */
	public VEvent conferenceToVEvent(Conference conferenceEdited) throws URISyntaxException, ParseException {
		VEvent vEventConference;
		Property url = new Url(conferenceEdited.getUrl().toURI());
		Property location = new Location(conferenceEdited.getCity() + "," + conferenceEdited.getCountry());
		Property description = new Description("Fee:" + conferenceEdited.getFeeRegistration());
		Property name = new Summary(conferenceEdited.getTitle());
		Property startDate = new DtStart(conferenceEdited.getEndDate().toString());
		Property endDate = new DtEnd(conferenceEdited.getStartDate().toString());
		Property uid = new Uid(conferenceEdited.getUid());
		PropertyList<Property> propertyListVevent = new PropertyList<>();
		propertyListVevent.add(url);
		propertyListVevent.add(name);
		propertyListVevent.add(description);
		propertyListVevent.add(location);
		propertyListVevent.add(startDate);
		propertyListVevent.add(endDate);
		propertyListVevent.add(uid);
		vEventConference = new VEvent(propertyListVevent);
		return vEventConference;
	}

	/**
	 * Recovery of an VEvent by his UID
	 * 
	 * @param uid
	 * @return The VEvent that have this uid
	 * @throws CalDAV4JException
	 * @throws InvalidConferenceFormatException
	 */
	public Conference getConferenceFromUid(String uid) throws CalDAV4JException, InvalidConferenceFormatException {
		VEvent vEventConferenceFound = null;
		GenerateQuery searchQuery = new GenerateQuery();
		searchQuery.setFilter("VEVENT : UID==" + uid);
		CalendarQuery calendarQuery = searchQuery.generate();
		List<Calendar> calendarsResult = collectionCalendarsOnline.queryCalendars(httpClient, calendarQuery);
		for (Calendar calendar : calendarsResult) {
			vEventConferenceFound = ICalendarUtils.getFirstEvent(calendar);
		}
		return ConferenceReader.createConference(vEventConferenceFound);
	}

}