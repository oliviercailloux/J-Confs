package io.github.oliviercailloux.jconfs.calendar;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Sequence;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;

public class CalDavCalendarGeneric implements CalendarOnCloudInterface{
	String url;
	String userName;
	String password;
	String calendarID;
	CredentialsProvider credsProvider = new BasicCredentialsProvider();
	CloseableHttpClient httpclient;
	HttpHost hostTarget;	
	CalDAVCollection collectionCalendarsOnline;
	int port;
	
	
	
	
	
	public CalDavCalendarGeneric(String url, String userName, String password, String calendarID,int port) {
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.calendarID = calendarID;
		this.port=port;
		this.credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(this.url, 443),
				new UsernamePasswordCredentials(this.userName, this.password));
		httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		hostTarget = new HttpHost(this.url, 443, "https");

		collectionCalendarsOnline = new CalDAVCollection("/remote.php/caldav/calendars/" + this.userName + "/" + this.calendarID,
				hostTarget, new CalDAV4JMethodFactory(), null);
		
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setCredentials() {
		credsProvider.setCredentials(new AuthScope(getUrl(), this.port), new UsernamePasswordCredentials(getUserName(), getPassword()));
	}
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
		Property uid = new Uid(conferenceEdited.getUid().toLowerCase());
		PropertyList<Property> propertyListVevent = new PropertyList<>();
		Property sequence = new Sequence(1);
		Property created = new Created();
		Property dtstamp = new DtStamp();
		Property lastModified = new LastModified();
		//propertyListVevent.add(url);
		//propertyListVevent.add(description);
		//propertyListVevent.add(location);
		propertyListVevent.add(created);
		propertyListVevent.add(dtstamp);
		propertyListVevent.add(lastModified);
		propertyListVevent.add(sequence);
		propertyListVevent.add(uid);
		propertyListVevent.add(startDate);
		propertyListVevent.add(endDate);
		propertyListVevent.add(name);
		vEventConference = new VEvent(propertyListVevent);
		System.out.println(vEventConference);
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
	public Optional<Conference> getConferenceFromUid(String uid) throws CalDAV4JException, InvalidConferenceFormatException {
		VEvent vEventConferenceFound = null;
		GenerateQuery searchQuery = new GenerateQuery();
		searchQuery.setFilter("VEVENT : UID==" + uid);
		CalendarQuery calendarQuery = searchQuery.generate();
		List<Calendar> calendarsResult = collectionCalendarsOnline.queryCalendars(httpclient, calendarQuery);
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
	 * @throws ParseException
	 * @throws URISyntaxException
	 */
	public void addOnlineConference(Conference conferenceToPush)
			throws CalDAV4JException, URISyntaxException, ParseException {
		VEvent ve;
		ve = conferenceToVEvent(conferenceToPush);
		Objects.requireNonNull(ve);
		Objects.requireNonNull(ve.getUid());
		collectionCalendarsOnline.add(httpclient, ve, new VTimeZone());
	}

	/**
	 * @param uid uid of the event to delete
	 * @throws CalDAV4JException
	 */
	public void deleteOnlineConference(String uid) throws CalDAV4JException {
		Objects.requireNonNull(uid);
		collectionCalendarsOnline.delete(httpclient,
				collectionCalendarsOnline.getCalendarCollectionRoot() + uid + ".ics");
	}
	public static void main(String [] args) throws CalDAV4JException, URISyntaxException, ParseException, MalformedURLException, InvalidConferenceFormatException {
		//CalDavCalendarGeneric instanceCalendarOnline = new CalDavCalendarGeneric("dav.fruux.com", "b3297431258", "jizbr5fuj9gi", "6e8c6372-eba5-43da-9eed-8e5413559c99", 443);
		//CalDavCalendarGeneric instanceCalendarOnline = new CalDavCalendarGeneric("us.cloudamo.com", "sebastien.bourg@dauphine.eu", "600bec84476fb1", "a", 443);
		CalDavCalendarGeneric instanceCalendarOnline = new CalDavCalendarGeneric("ppp.woelkli.com", "93@yopmail.com", "Loscincos9378", "a", 443);
		instanceCalendarOnline.setCredentials();
		//instanceCalendarOnline.deleteOnlineConference("17C3F97A-B3A4-4B2E-8BC1-2751E62C7716");
		//instanceCalendarOnline.getOnlineConferences();
		
		
		  LocalDate start_ = null; LocalDate end_ = null; String uid =
		  "685F1D53-BECE-4070-8456-7A1431224252"; try { DateTimeFormatter formatter =
		  DateTimeFormatter.ofPattern("dd/MM/yyyy"); start_ =
		  LocalDate.parse("16/04/2020", formatter); end_ =
		  LocalDate.parse("16/04/2020", formatter); } catch (Exception e) { throw new
		  IllegalArgumentException("Date impossible to put in the conference", e); }
		  Conference conference = new Conference(uid, new
		  URL("http://ppp.woelkli.com"), "Java", start_, end_, 1.36,
		  "France", "Paris");
		  //instanceCalendarOnline.getOnlineConferences();
		  instanceCalendarOnline.addOnlineConference(conference);
		  //uid="685F1D53-BECE-4070-8456-7A1431224252";
		  //Optional<Conference> confTest =
		  //instanceCalendarOnline.getConferenceFromUid(uid); 
		  
		  //instanceCalendarOnline.deleteOnlineConference(uid);
		
	}
}
