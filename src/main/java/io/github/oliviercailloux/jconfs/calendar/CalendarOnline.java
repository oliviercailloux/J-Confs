package io.github.oliviercailloux.jconfs.calendar;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.server.UID;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedHashSet;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Name;
import net.fortuna.ical4j.model.property.Sequence;
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
import io.github.oliviercailloux.jconfs.conference.ConferenceWriter;
import io.github.oliviercailloux.jconfs.conference.InvalidConferenceFormatException;

/**
 * This class is the management of calendar online object. It makes you able to
 * add, edit and delete conference.
 * 
 * @author nikola, machria & sbourg
 */
public class CalendarOnline {

	private CalDavCalendarGeneric connector;

	public CalendarOnline(CalDavCalendarGeneric connector) {
		this.connector = connector;
	}

	/**
	 * This method has been partially taken from :
	 * https://github.com/dedeibel/list-events-caldav4j-example/blob/master/src/test/java/benjaminpeter/name/ListCalendarTest.java
	 * It allows you to retrieve events from calendars hosted on a fruux account
	 * Maybe add a try catch in line 97 because if an event don't have an url in
	 * vEvent it block the program.
	 * 
	 * @return
	 * @throws CalDAV4JException
	 * @throws MalformedURLException
	 */
	public Set<Conference> getOnlineConferences() throws CalDAV4JException, MalformedURLException {
		GenerateQuery searchQuery = new GenerateQuery();
		CalendarQuery calendarQuery = searchQuery.generate();
		Set<Conference> listConferencesUser = new LinkedHashSet<>();
		List<Calendar> calendarsResult = connector.collectionCalendarsOnline.queryCalendars(this.connector.httpclient,
				calendarQuery);
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
	 * @throws CalDAV4JException
	 * @throws URISyntaxException
	 */
	public void editConferenceOnline(Conference conferenceEdited) throws CalDAV4JException, URISyntaxException {
		VEvent vEventConferenceModified = conferenceToVEvent(conferenceEdited);
		this.connector.collectionCalendarsOnline.updateMasterEvent(connector.httpclient, vEventConferenceModified,
				null);
	}

	/**
	 * Different behavior depending on the calendar online
	 * 
	 * @param conferenceEdited : the conference you want to convert
	 * @return the VEvent corresponding to your Conference
	 * @throws URISyntaxException
	 */
	public VEvent conferenceToVEvent(Conference conferenceEdited) throws URISyntaxException {
		VEvent vEventConference;
		PropertyList<Property> propertyListVevent = new PropertyList<>();
		if (this.connector.url.contains("fruux")) {
			propertyListVevent = ConferenceWriter.conferenceToPropertyFruux(conferenceEdited);
		} else {
			propertyListVevent = ConferenceWriter.conferenceToProperty(conferenceEdited);
		}
		vEventConference = new VEvent(propertyListVevent);

		return vEventConference;

	}

	/**
	 * Recovery of an VEvent by his UID
	 * 
	 * @param uid
	 * @return The VEvent that have this uid
	 * @throws CalDAV4JException
	 * @throws MalformedURLException
	 */
	public Optional<Conference> getConferenceFromUid(String uid) throws CalDAV4JException, MalformedURLException {
		VEvent vEventConferenceFound = null;
		GenerateQuery searchQuery = new GenerateQuery();
		searchQuery.setFilter("VEVENT : UID==" + uid);
		CalendarQuery calendarQuery = searchQuery.generate();
		List<Calendar> calendarsResult = connector.collectionCalendarsOnline.queryCalendars(this.connector.httpclient,
				calendarQuery);
		for (Calendar calendar : calendarsResult) {
			vEventConferenceFound = ICalendarUtils.getFirstEvent(calendar);
		}
		if (vEventConferenceFound == null) {
			return Optional.empty();
		}
		return Optional.of(ConferenceReader.createConference(vEventConferenceFound));
	}

	/**
	 * @param ve event to add
	 * @throws CalDAV4JException
	 * @throws URISyntaxException
	 */
	public void addOnlineConference(Conference conferenceToPush) throws CalDAV4JException, URISyntaxException {
		VEvent ve;
		ve = conferenceToVEvent(conferenceToPush);
		System.out.println(ve);
		Objects.requireNonNull(ve);
		Objects.requireNonNull(ve.getUid());

		this.connector.collectionCalendarsOnline.add(connector.httpclient, ve, null);
	}

	/**
	 * @param uid of the event to delete
	 * @throws CalDAV4JException
	 */
	public void deleteOnlineConference(String uid) throws CalDAV4JException {
		Objects.requireNonNull(uid);
		connector.collectionCalendarsOnline.delete(connector.httpclient,
				connector.collectionCalendarsOnline.getCalendarCollectionRoot() + uid + ".ics");
	}
}