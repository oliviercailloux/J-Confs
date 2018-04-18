import java.net.URL;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceWriter;
public class TestConferenceWriter {
	public static void main (String [] args) throws Exception {
		Conference conferenceToWrite = new Conference(new URL("http://www.test.com"));
		conferenceToWrite.setTitle("coucou");
		conferenceToWrite.setCountry("America");
		conferenceToWrite.setCity("New_YorkS");
		conferenceToWrite.setEndDate("2018-01-04");
		conferenceToWrite.setStartDate("2017-06-06");
		conferenceToWrite.setFeeRegistration(12.5);
		ConferenceWriter.writeCalendarFiles(conferenceToWrite);
		
		System.out.println("done");
	}

}
