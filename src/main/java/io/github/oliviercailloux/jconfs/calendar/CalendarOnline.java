package io.github.oliviercailloux.jconfs.calendar;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.net.URISyntaxException;
import java.rmi.server.UID;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
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

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.github.caldav4j.CalDAVCollection;
import com.github.caldav4j.CalDAVConstants;
import com.github.caldav4j.exceptions.CalDAV4JException;
import com.github.caldav4j.methods.CalDAV4JMethodFactory;
import com.github.caldav4j.model.request.CalendarQuery;
import com.github.caldav4j.util.GenerateQuery;
import com.github.caldav4j.util.ICalendarUtils;

import io.github.oliviercailloux.jconfs.conference.Conference;
import io.github.oliviercailloux.jconfs.conference.ConferenceReader;
import io.github.oliviercailloux.jconfs.conference.InvalidConferenceFormatException;

/**
 * @author nikola This class permits to access to online conferences from
 *         https://fruux.com
 */
public class CalendarOnline {
	final private static String userNameFruux = "b3297431258";
	final private static String passwordFruux = "jizbr5fuj9gi";
	final private static String calendarIDFruux = "6e8c6372-eba5-43da-9eed-8e5413559c99";
	private static CalendarOnline instanceCalendarOnline;
	private CalDAVCollection collectionCalendarsOnline;
	private static CloseableHttpClient httpclient;

	private CalendarOnline() {
		HttpHost hostTarget;
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("dav.fruux.com", 443),
				new UsernamePasswordCredentials(userNameFruux, passwordFruux));
		httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		hostTarget = new HttpHost("dav.fruux.com", 443, "https");

		collectionCalendarsOnline = new CalDAVCollection("/calendars/" + userNameFruux + "/" + calendarIDFruux,
				hostTarget, new CalDAV4JMethodFactory(), CalDAVConstants.PROC_ID_DEFAULT);
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
		List<Calendar> calendarsResult = collectionCalendarsOnline.queryCalendars(httpclient, calendarQuery);
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
		collectionCalendarsOnline.updateMasterEvent(httpclient, vEventConferenceModified, null);
	}

	/**
	 * @param conferenceEdited : the conference you want to convert
	 * @return the VEvent corresponding to your Conference
	 * @throws URISyntaxException
	 * @throws ParseException
	 */
	public VEvent conferenceToVEvent(Conference conferenceEdited) throws URISyntaxException, ParseException {
		VEvent vEventConference;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		Property url = new Url(conferenceEdited.getUrl().toURI());
		Property location = new Location(conferenceEdited.getCity() + "," + conferenceEdited.getCountry());
		Property description = new Description("Fee:" + conferenceEdited.getFeeRegistration());
		Property name = new Summary(conferenceEdited.getTitle());
		Property startDate = new DtStart(new Date(conferenceEdited.getStartDate().format(formatter)));
		Property endDate = new DtEnd(new Date(conferenceEdited.getEndDate().format(formatter)));
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
		List<Calendar> calendarsResult = collectionCalendarsOnline.queryCalendars(httpclient, calendarQuery);
		for (Calendar calendar : calendarsResult) {
			vEventConferenceFound = ICalendarUtils.getFirstEvent(calendar);
		}
		if (vEventConferenceFound == null) {
			return null;
		}
		return ConferenceReader.createConference(vEventConferenceFound);
	}

	/**
	 * @param ve event to add
	 * @throws CalDAV4JException
	 * @throws ParseException
	 * @throws URISyntaxException
	 */
	public void addOnlineConference(Conference conferenceToPush)
			throws CalDAV4JException, URISyntaxException, ParseException {
		VEvent ve;
		ve = conferenceToVEvent(conferenceToPush);
		Objects.requireNonNull(ve);
		Objects.requireNonNull(ve.getUid());
		collectionCalendarsOnline.add(httpclient, ve, null);
	}

	/**
	 * @param uid uid of the event to delete
	 * @throws CalDAV4JException
	 */
	public void deleteOnlineConference(String uid) throws CalDAV4JException {
		Objects.requireNonNull(uid);
		collectionCalendarsOnline.delete(httpclient,
				collectionCalendarsOnline.getCalendarCollectionRoot() + "/" + uid + ".ics");
	}

}