package io.github.oliviercailloux.y2018.jconfs;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.Date;
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

  public static Set<Conference> getonlineConferences() throws CalDAV4JException, InvalidConferenceFormatException
  {
    HttpClient httpClient = new HttpClient();
    httpClient.getHostConfiguration().setHost("dav.fruux.com", 443, "https");
    String username = "b3297431374";
    String calendarID="d1010138-3ae5-4ba5-b0fd-dcee1e5fcdf2";
    UsernamePasswordCredentials httpCredentials = new UsernamePasswordCredentials(username, "4j9z8c7no08f");
    httpClient.getState().setCredentials(AuthScope.ANY, httpCredentials);
    httpClient.getParams().setAuthenticationPreemptive(true);


    CalDAVCollection collection = new CalDAVCollection(
        "/calendars/"+ username +"/"+calendarID,
        (HostConfiguration) httpClient.getHostConfiguration().clone(),
        new CalDAV4JMethodFactory(),
        CalDAVConstants.PROC_ID_DEFAULT
        );

    GenerateQuery gq=new GenerateQuery();
    CalendarQuery calendarQuery = gq.generate();
    List<Calendar>calendars = collection.queryCalendars(httpClient, calendarQuery);
	Set<Conference> listeconfuser=new LinkedHashSet<>();

    for (Calendar calendar : calendars) {
      ComponentList<VEvent> componentList = calendar.getComponents(Component.VEVENT);
      Iterator<VEvent> eventIterator = componentList.iterator();
      while (eventIterator.hasNext()) {
        VEvent ve = eventIterator.next();
        listeconfuser.add(ConferenceReader.createConference(ve));
        System.out.println("Event: "+ ve.toString());
       }
    }
	return listeconfuser;

  }
}