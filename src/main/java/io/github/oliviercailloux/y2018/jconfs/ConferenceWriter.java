package io.github.oliviercailloux.y2018.jconfs;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Country;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Region;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.model.Calendar;
public class ConferenceWriter {
	/**
	 * Write the conference in form calendar 
	 * @param conference
	 * @throws ParseException
	 * @throws IOException
	 * @throws ParserException
	 * @throws ValidationException
	 * @throws URISyntaxException
	 */
	public static void writeCalendarFiles(Conference conference) throws ParseException, IOException, ParserException, ValidationException, URISyntaxException{
		  
		  String calFile = "mycalendar.ics";
		 
		  //Creating a new calendar
		  Calendar calendar = new Calendar();
		  calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		  calendar.getProperties().add(Version.VERSION_2_0);
		  calendar.getProperties().add(CalScale.GREGORIAN);
		  
		  //Creating an event
		  PropertyList propertyList = new PropertyList();
		  propertyList.add(new DtStart(conference.getStartDate()));
		  propertyList.add(new DtEnd(conference.getEndDate()));
		  propertyList.add(new Summary(conference.getTitle()));
		  propertyList.add(new Country (conference.getCountry()));
		  propertyList.add(new Region(conference.getCity()));
		  propertyList.add(new Url(conference.getUrl().toURI()));
		  propertyList.add(new Description("A conference with a fee of :" + conference.getFeeRegistration() + "euros"));
		
		  VEvent meeting = new VEvent(propertyList);
		  //add event to the calendar
		  calendar.getComponents().add(meeting);
		   
		  //Saving an iCalendar file
		  try(FileOutputStream fout = new FileOutputStream(calFile)){
			  CalendarOutputter outputter = new CalendarOutputter();
			  outputter.setValidating(false);
			  outputter.output(calendar, fout);
			  fout.close();	 
		  }catch (IOException e) {
				e.printStackTrace();
		  }
		  
		  //Now Parsing an iCalendar file
		  FileInputStream fin = new FileInputStream(calFile);
		  CalendarBuilder builder = new CalendarBuilder();
		  calendar = builder.build(fin);
		  
		  //Iterating over a Calendar
		  for (Iterator<?> i = calendar.getComponents().iterator(); i.hasNext();) {
		      Component component = (Component) i.next();
		      System.out.println("Component [" + component.getName() + "]");
	
		      for (Iterator<?> j = component.getProperties().iterator(); j.hasNext();) {
		          Property property = (Property) j.next();
		          System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
		      }
		  }
	}

}
