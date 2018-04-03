package io.github.oliviercailloux.y2018.jconfs;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Iterator;
import javax.xml.bind.ValidationException;

import org.apache.commons.io.IOUtils;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;


/**
 * This class allows to read and iCalelndar file and creates a conference object from a parsed iCalendar file
 * @author berkani-mustapha
 *
 */
public class ReadCalendarFiles {


	private static InputStream fin;


	/** Parse an iCalendar object with ical4j API
	 * function took from source : https://www.programcreek.com/java-api-examples/?api=net.fortuna.ical4j.model.property.Method 
	 * @param filePath
	 * @throws IOException
	 * @throws ParserException
	 */
	public static  void ReadCalendarFile(String filePath) throws IOException, ParserException, ValidationException {
		FileInputStream fin1 = new FileInputStream(filePath);

		CalendarBuilder builder = new CalendarBuilder();

		net.fortuna.ical4j.model.Calendar calendar = builder.build(fin1);

		// Iterating over the calendar component
		for (Iterator<Component> i = calendar.getComponents().iterator(); i.hasNext();) {
			Component component = i.next();
			System.out.println("Component [" + component.getName() + "]");
			// Iterating over the component property
			for (Iterator<Property> j = component.getProperties().iterator(); j.hasNext();) {
				Property property = j.next();
				System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
			}
		}
	}



	/**
	 * generate a localDate from string parametre
	 * This function is needed since the ical4j property DTSTART is a string 
	 * so we have to convert it into a localDate format  
	 * @param date
	 * @return
	 */
	public static LocalDate stringToLocalDate (String date){
		return LocalDate.parse(date);
	}


	/**
	 * Creates conference from ics file, function inspired by function readCalendarFile
	 * @param filepath
	 * @return
	 * @throws IOException
	 * @throws ParserException
	 * @throws ValidationException
	 */

	public static  Conference createConference(String filepath) throws IOException, ParserException {

		Conference conf = null;
		fin = new java.io.FileInputStream(filepath);
		try {
			fin = new java.io.BufferedInputStream(fin);
			CalendarBuilder builder = new CalendarBuilder();
			net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);

			for (Iterator j = calendar.getProperties().iterator(); j.hasNext();) {
				Property property = (Property) j.next();

				conf = new Conference(Property.URL );

				if(property.getName()==Property.DTSTART)
					conf.setStartDate(property.getValue());

				if(property.getName()==Property.DTEND)
					conf.setEndDate(property.getValue());

				if(property.getName()==Property.REGION)
					conf.setRegion(property.getValue());

				if(property.getName()==Property.COUNTRY)
					conf.setCountry(property.getValue());

				//add field for Title
				//add field for free-registration
			}

		} finally {
			IOUtils.closeQuietly(fin);
		}
		return conf; 
	}
}
