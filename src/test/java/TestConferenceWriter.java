import java.net.URL;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceWriter;
public class TestConferenceWriter {
	public static void main (String [] args) throws Exception {
		Conference conferenceToWrite = new Conference(new URL("http://www.test.com"));
		ConferenceWriter.writeCalendarFiles(conferenceToWrite.getTitle(),conferenceToWrite);
		
		System.out.println("done");
	}

}
