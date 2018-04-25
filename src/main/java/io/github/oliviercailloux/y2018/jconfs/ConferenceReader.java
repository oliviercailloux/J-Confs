package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Iterator;
import javax.xml.bind.ValidationException;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;

/**
 * This class allows to read and iCalelndar file and creates a conference object
 * from a parsed iCalendar file
 * 
 *
 */
public class ConferenceReader {

	/**
	 * Parse an iCalendar object with ical4j API function took from source :
	 * https://www.programcreek.com/java-api-examples/?api=net.fortuna.ical4j.model.property.Method
	 * 
	 * @param filePath
	 * @throws IOException
	 * @throws ParserException
	 */
	public static void ReadCalendarFile(String filePath) throws IOException, ParserException {

		try (InputStream calendarFile = ConferenceReader.class.getClassLoader().getResourceAsStream(filePath)) {

			CalendarBuilder builder = new CalendarBuilder();

			Calendar calendar = builder.build(calendarFile);

			// Iterating over the calendar component

			for (Component component : calendar.getComponents()) {
				System.out.println("Component [" + component.getName() + "]");
				// Iterating over the component property
				for (Property property : component.getProperties()) {
					System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
				}
			}
		}
	}

	/**
	 * Creates conference from ics file, function inspired by function
	 * readCalendarFile
	 * 
	 * @param filepath
	 * 		not <code> null</code>
	 * @return Conference
	 * @throws IOException
	 * @throws ParserException
	 * @throws ParseException
	 * @throws ValidationException
	 */

	public static Conference createConference(String filePath)
			throws IOException, ParserException, ParseException, NumberFormatException {

		if(filePath==null) {
			throw new IllegalArgumentException("Argument is null");
		}
		Conference conf = null;
		try (InputStream calendarFile = ConferenceReader.class.getClassLoader().getResourceAsStream(filePath)) {

			CalendarBuilder builder = new CalendarBuilder();
			Calendar calendar = builder.build(calendarFile);
			Component confCompo = calendar.getComponent("X-CONFERENCE");

			// the url is the primary key of a conference
			URL confURL = new URL(confCompo.getProperty("URL").getValue());
			conf = new Conference(confURL);

			// add the others attributes
			conf.setTitle(confCompo.getProperty("SUMMARY").getValue());
			conf.setCountry(confCompo.getProperty("X-COUNTRY").getValue());
			conf.setFeeRegistration(Double.parseDouble(confCompo.getProperty("X-FEE").getValue()));
			conf.setStartDate(confCompo.getProperty("DTSTART").getValue());
			conf.setEndDate(confCompo.getProperty("DTEND").getValue());
			conf.setCity(confCompo.getProperty("X-CITY").getValue());
		}
		return conf;

	}
}
