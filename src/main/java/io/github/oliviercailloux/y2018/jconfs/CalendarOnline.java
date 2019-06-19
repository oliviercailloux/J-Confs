package io.github.oliviercailloux.y2018.jconfs;

import java.util.List;
import java.util.Set;
import java.net.URISyntaxException;
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

public class CalendarOnline {
	final private static String userNameFruux = "b3297431374";
	final private static String passwordFruux = "4j9z8c7no08f";
	final private static String calendarIDFruux = "d1010138-3ae5-4ba5-b0fd-dcee1e5fcdf2";
	private static HttpClient httpClient;

	/**
	 * This method has been partially taken from :
	 * https://github.com/dedeibel/list-events-caldav4j-example/blob/master/src/test/java/benjaminpeter/name/ListCalendarTest.java
	 * It allows you to retrieve events from a calendar hosted on a fruux account
	 * @return
	 * @throws CalDAV4JException
	 * @throws InvalidConferenceFormatException
	 */
	public static Set<Conference> getOnlineConferences() throws CalDAV4JException, InvalidConferenceFormatException {
		httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost("dav.fruux.com", 443, "https");
		UsernamePasswordCredentials httpCredentials = new UsernamePasswordCredentials(userNameFruux, passwordFruux);
		httpClient.getState().setCredentials(AuthScope.ANY, httpCredentials);
		httpClient.getParams().setAuthenticationPreemptive(true);

		CalDAVCollection collectionCalendarsOnline = new CalDAVCollection(
				"/calendars/" + userNameFruux + "/" + calendarIDFruux,
				(HostConfiguration) httpClient.getHostConfiguration().clone(), new CalDAV4JMethodFactory(),
				CalDAVConstants.PROC_ID_DEFAULT);

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
	 * @param c : the conference you want to convert
	 * @return the VEvent corresponding to your Conference
	 * @throws URISyntaxException
	 * @throws ParseException
	 */
	public static VEvent conferenceToVEvent(Conference c) throws URISyntaxException, ParseException  {
		  VEvent composant;
		  Property url,location,startDate,endDate,description,name;
		  url=new Url(c.getUrl().toURI());
		  location=new Location(c.getCity()+","+c.getCountry());
		  description=new Description("Fee:"+c.getFeeRegistration());
		  name=new Name(c.getTitle());
		  startDate=new DtStart(c.getEndDate().toString());
		  endDate=new DtEnd(c.getStartDate().toString());
		  
		  PropertyList<Property> p=new PropertyList<>();
		  p.add(url);
		  p.add(name);
		  p.add(description);
		  p.add(location);	  
		  p.add(startDate);
		  p.add(endDate);
		  
		  composant=new VEvent(p);
		  return composant;
	  }
}