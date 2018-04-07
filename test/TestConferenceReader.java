import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceReader;
import net.fortuna.ical4j.data.ParserException;

public class TestConferenceReader {

	/*
	 * a test for ConferenceReader Methods which print success or fail
	 */
	public static void main(String[] args) throws IOException, ParserException, ParseException {

		
		/*
		 * create a conference object by reading the file test2.ics in resources1 folder
		 * 
		 */
		Conference conf = ConferenceReader.createConference("resources1/test2.ics");
		
		/*
		 * create a conference object to compare with conf 
		 * 
		 */
		
		Conference conf2 =new Conference (new URL ("http://www.test.com/"));
		conf2.setCity("Paris");
		conf2.setCountry("France");
		conf2.setEndDate("19970714T170000Z");
		conf2.setFeeRegistration(22.60);
		conf2.setStartDate("19970714T170000Z");
		conf2.setTitle("java");
		
		if(conf.equals(conf2)) {
			System.out.println("success");
		}else {
			System.out.println("fail");
		}
		
		System.out.println("done");
	}
}
