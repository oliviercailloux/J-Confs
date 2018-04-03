package io.github.oliviercailloux.y2018.jconfs;
import java.io.IOException;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Iterator;
import javax.xml.bind.ValidationException;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;


/**
 * @author berkani-mustapha
 *
 */
public class ReadCalendarFiles {


	/** Parse an iCalendar object with ical4j API
	 * function took from source : https://www.programcreek.com/java-api-examples/?api=net.fortuna.ical4j.model.property.Method 
	 * @param filePath
	 * @throws IOException
	 * @throws ParserException
	 */
	public static  void ReadCalendarFile(String filePath) throws IOException, ParserException, ValidationException {
		FileInputStream fin = new FileInputStream(filePath);

		CalendarBuilder builder = new CalendarBuilder();

		net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);

		// Iterating over the calendar 
		for (Iterator<Component> i = calendar.getComponents().iterator(); i.hasNext();) {
			Component component = i.next();
			System.out.println("Component [" + component.getName() + "]");

			for (Iterator<Property> j = component.getProperties().iterator(); j.hasNext();) {
				Property property = (Property) j.next();
				System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
			}
		}
	}

	

	/**
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
	public static  Conference createConference(String filepath) throws IOException, ParserException, ValidationException {
		Conference conf = null;
		FileInputStream fin = new FileInputStream(filepath);
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

		fin.close();
		return conf; 
	}

}
