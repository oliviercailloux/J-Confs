package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;

/**
 * This class allows to read and iCalelndar file and creates a conference object
 * from a parsed iCalendar file
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

	public static void readCalendarFile(Reader read) throws IOException, ParserException, NumberFormatException {
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(read);
		for (Component component : calendar.getComponents()) {
			System.out.println("Component [" + component.getName() + "]");
			for (Property property : component.getProperties()) {
				System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
			}
		}
	}
	
	/**
	 * We parse a calendar component to create a conference
	 * @param confCompo it's a calendar component that contains the data of one conference
	 * @return a conference
	 * @throws InvalidConferenceFormatException 
	 * @throws IOException
	 * @throws ParserException
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public static Conference createConference(Component confCompo) throws InvalidConferenceFormatException {
		Conference conf = null;
		URL confURL;
		
		try {
			confURL = new URL(confCompo.getProperty("URL").getValue());
			conf = new Conference(confURL);
		} catch (MalformedURLException e1) {
			throw new InvalidConferenceFormatException("URL malformated, impossible to put in a conference",e1);
		}
		
		conf.setTitle(confCompo.getProperty("SUMMARY").getValue());
		conf.setCountry(confCompo.getProperty("X-COUNTRY").getValue());
		String stringDTSTART=convertDate(confCompo.getProperty("X-DTSTART").getValue());
		String stringDTEND=convertDate(confCompo.getProperty("X-DTEND").getValue());
		
		conf.setStartDate(stringDTSTART);
		
		conf.setEndDate(stringDTEND);
		
		conf.setFeeRegistration(Double.parseDouble(confCompo.getProperty("X-FEE").getValue()));
		conf.setCity(confCompo.getProperty("X-CITY").getValue());	
		return conf;

	}
	
	/**
	 * We will import a set of conferences contain in a ical
	 * @param read contain data of the user's calendar
	 * @return a list of the conferences of the user
	 * @throws IOException
	 * @throws ParserException
	 * @throws NumberFormatException
	 * @throws ParseException
	 * @throws InvalidConferenceFormatException 
	 */
	public static Set<Conference> readConferences(Reader reader) throws InvalidConferenceFormatException, IOException, ParserException {
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(reader);
		Set<Conference> listeconfuser=new LinkedHashSet<>();
		ComponentList<CalendarComponent> conflist=calendar.getComponents("X-CONFERENCE");
		for (int i=0;i<conflist.size();i++) {
			listeconfuser.add(createConference(conflist.get(i)));
		}
		return listeconfuser;

	}
	
	/**this function transform a date to an another pattern of date
	 * @param date Not <code>null</code>
	 * 	 it's the date that we want to change its pattern.
	 * @return dateformated it's the date with the good pattern
	 */
	public static String convertDate(String date) {
		DateTimeFormatter formatBefore=DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter formatAfter=DateTimeFormatter.ofPattern("dd/MM/yyy");
		String dateformated=LocalDate.parse(date,formatBefore).format(formatAfter);
		return dateformated;
	}

}
