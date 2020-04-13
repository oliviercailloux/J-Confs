package io.github.oliviercailloux.jconfs.calendar;

import static org.junit.jupiter.api.Assertions.fail;

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
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;


public class CalendarOnNextcloud implements CalendarOnCloudInterface {
	String url;
	String userName;
	String password;
	String calendarID;
	CredentialsProvider credsProvider = new BasicCredentialsProvider();
	CloseableHttpClient httpclient;
	HttpHost hostTarget;	
	CalDAVCollection collectionCalendarsOnline;

	public static CalendarOnNextcloud newInstance() {
		return new CalendarOnNextcloud();
	}
	
	private CalendarOnNextcloud() {
		this.url = "us.cloudamo.com";
		this.userName = "sebastien.bourg@dauphine.eu";
		this.password = "600bec84476fb1";
		setCredentials();
		this.httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		this.hostTarget = new  HttpHost(url, 443, "https");
		this.collectionCalendarsOnline = new CalDAVCollection("/remote.php/caldav/calendars/" + this.userName + "/" + "a",
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
		credsProvider.setCredentials(new AuthScope(getUrl(), 443), new UsernamePasswordCredentials(getUserName(), getPassword()));
	}
	
	/*public Set<Conference> getOnlineConferences() throws CalDAV4JException, InvalidConferenceFormatException {
		GenerateQuery searchQuery = new GenerateQuery();
		CalendarQuery calendarQuery = searchQuery.generate();
		List<Calendar> calendarsResult = collectionCalendarsOnline.queryCalendars(httpclient, calendarQuery);	
		/*List<Calendar> calendarsResult = collectionCalendarsOnline.queryCalendars(httpclient, calendarQuery);
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
	}*/

	public List<Calendar> getOnlineConferences() throws CalDAV4JException, InvalidConferenceFormatException {
		GenerateQuery searchQuery = new GenerateQuery();
		CalendarQuery calendarQuery = searchQuery.generate();
		List<Calendar> calendarsResult = collectionCalendarsOnline.queryCalendars(httpclient, calendarQuery);	
		 
		return calendarsResult;
	}

}
