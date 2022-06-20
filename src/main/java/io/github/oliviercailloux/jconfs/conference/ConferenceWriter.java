package io.github.oliviercailloux.jconfs.conference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.Objects;
import java.util.Set;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.XComponent;
import net.fortuna.ical4j.model.property.Attendee;
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
import net.fortuna.ical4j.validate.ValidationException;

public class ConferenceWriter {

  /**
   * Open the specified ics file as an Calendar object
   * 
   * @param calFile not {@code null}.
   * @return not {@code null}.
   * @throws IOException
   * @throws ParserException
   */

  private static Calendar openCalendar(String calFile) throws IOException, ParserException {
    Objects.requireNonNull(calFile);
    URL urlcalendar = ConferenceWriter.class.getResource(calFile + ".ics");
    try (FileReader reader = new FileReader(new File(urlcalendar.getFile()))) {
      CalendarBuilder builder = new CalendarBuilder();
      Calendar calendar = builder.build(reader);
      return calendar;
    }
  }

  /**
   * Delete the conference of the ics file (if it exists)
   * 
   * @param calFile not {@code null}.
   * @param conference not {@code null}.
   * @throws IOException
   * @throws ParserException
   * @throws URISyntaxException
   */
  public static void deleteConference(String calFile, Conference conference)
      throws IOException, ParserException, URISyntaxException {
    Objects.requireNonNull(calFile);
    Objects.requireNonNull(conference);

    Calendar calendar = new Calendar();
    Calendar newCalendar;

    Conference tempConf;
    calendar = openCalendar(calFile);

    ComponentList<CalendarComponent> confList = calendar.getComponents("X-CONFERENCE");
    for (int i = 0; i < confList.size(); i++) {
      tempConf = ConferenceReader.createConference(confList.get(i));
      if (tempConf.equals(conference)) {
        confList.remove(i);
      }
    }

    newCalendar = new Calendar(confList);
    saveIcsFile(newCalendar, calFile);
  }

  /**
   * Transform a Conference into a Property List to be used after in VEvent(in the case of
   * communication with server ) or Event (In the case of IcsFile) this is the specific one for
   * fruux. To communicate with fruux we need to specificate order
   * 
   * @param conference not {@code null}.
   * 
   * @throws URISyntaxException
   */

  public static PropertyList<Property> conferenceToPropertyFruux(Conference conference)
      throws URISyntaxException {
    Property urlz, description, location, startDate, endDate, uid, name;
    PropertyList<Property> propertyList = new PropertyList<>();
    startDate = new DtStart(new Date(java.util.Date.from(conference.getStartDate())));
    endDate = new DtEnd(new Date(java.util.Date.from(conference.getEndDate())));
    uid = new Uid(conference.getUid().toLowerCase());
    name = new Summary(conference.getTitle());

    if (conference.getUrl().isPresent()) {
      urlz = new Url(conference.getUrl().get().toURI());
      propertyList.add(urlz);
    }
    propertyList.add(name);
    if (conference.getFeeRegistration().isPresent()) {
      description = new Description("Fee:" + conference.getFeeRegistration().get());
      propertyList.add(description);
    }
    if (!((conference.getCity().isEmpty()) && (conference.getCountry().isEmpty()))) {
      location = new Location(conference.getCity() + "," + conference.getCountry());
      propertyList.add(location);
    }
    if (!(conference.getParticipants().isEmpty())) {

      Set<String> it2 = conference.getParticipantsSet();
      for (String parti : it2) {
        String attendee = parti;
        Attendee participant = new Attendee(URI.create(attendee.substring(0, (attendee.length()))));
        propertyList.add(participant);
      }
    }
    propertyList.add(startDate);
    propertyList.add(endDate);
    propertyList.add(uid);
    return propertyList;
  }

  /**
   * Transform a Conference into a Property List to be used after in VEvent(in the case of
   * communication with server ) or Event (In the case of IcsFile)
   * 
   * @param conference not {@code null}.
   * 
   * @throws URISyntaxException
   */

  public static PropertyList<Property> conferenceToProperty(Conference conference)
      throws URISyntaxException {
    Property urlz, description, location, startDate, endDate, uid, sequence, created, dtstamp,
        lastModified, name;
    PropertyList<Property> propertyList = new PropertyList<>();
    startDate = new DtStart(new Date(java.util.Date.from(conference.getStartDate())));
    endDate = new DtEnd(new Date(java.util.Date.from(conference.getEndDate())));
    uid = new Uid(conference.getUid().toLowerCase());
    sequence = new Sequence(2);
    created = new Created();
    dtstamp = new DtStamp();
    lastModified = new LastModified();
    name = new Summary(conference.getTitle());

    propertyList.add(created);
    propertyList.add(dtstamp);
    propertyList.add(lastModified);
    propertyList.add(sequence);
    propertyList.add(uid);
    propertyList.add(startDate);
    propertyList.add(endDate);

    propertyList.add(name);
    if (!((conference.getCity().isEmpty()) && (conference.getCountry().isEmpty()))) {
      location = new Location(conference.getCity() + "," + conference.getCountry());
      propertyList.add(location);
    }
    if (conference.getFeeRegistration().isPresent()) {
      description = new Description("Fee:" + conference.getFeeRegistration().get());
      propertyList.add(description);
    }
    if (conference.getUrl().isPresent()) {
      urlz = new Url(conference.getUrl().get().toURI());
      propertyList.add(urlz);
    }
    if (!(conference.getParticipants().isEmpty())) {
      String attendee = conference.getParticipants();
      Attendee participant =
          new Attendee(URI.create(attendee.substring(1, (attendee.length() - 1))));
      propertyList.add(participant);
    }

    return propertyList;
  }

  /**
   * Add the conference in the ics file file (if it exists)
   * 
   * @param calFile not {@code null}.
   * @param conference not {@code null}.
   * @throws IOException
   * @throws ParserException
   * @throws ValidationException
   * @throws URISyntaxException
   * @throws ParseException
   */

  public static void addConference(String calFile, Conference conference)
      throws IOException, ParserException, ValidationException, URISyntaxException {
    Objects.requireNonNull(calFile);
    Objects.requireNonNull(conference);

    Calendar calendar = new Calendar();
    calendar = openCalendar(calFile);

    // Creating an event
    PropertyList<Property> propertyList = new PropertyList<>();
    propertyList = conferenceToProperty(conference);
    XComponent meeting = new XComponent("CONFERENCE", propertyList);
    // add event to the calendar
    calendar.getComponents().add(meeting);

    // Saving an iCalendar file
    saveIcsFile(calendar, calFile);
  }

  /**
   * Save the given conference in the ics File
   * 
   * @param cal not {@code null}.
   * @param calFile not {@code null}.
   * @throws URISyntaxException
   * @throws ValidationException
   * @throws IOException
   */
  private static void saveIcsFile(Calendar cal, String calFile)
      throws URISyntaxException, ValidationException, IOException {
    Objects.requireNonNull(calFile);
    Objects.requireNonNull(cal);

    URL resourceUrl = ConferenceWriter.class.getResource(calFile + ".ics");
    File file = new File(resourceUrl.toURI());
    try (FileOutputStream fout = new FileOutputStream(file)) {
      CalendarOutputter outputter = new CalendarOutputter();
      outputter.setValidating(false);
      outputter.output(cal, fout);
    }
  }
}
