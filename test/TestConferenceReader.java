import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ReadCalendarFiles;
import net.fortuna.ical4j.data.ParserException;

/*
 * This class tests the methods of conferenceReader
 */
public class TestConferenceReader {
	

	public static void main(String[] args) throws IOException, ParserException, ParseException {
		Conference conf = new Conference(new URL("http://www.test.com/"));

		conf = ReadCalendarFiles.createConference("resources1/test2.ics");
		System.out.println(" On va Lire l'objet conference créé à partir du fichier ics ");
		System.out.println(conf);
		System.out.println(conf.toString());
		System.out.println(" \n \n  On va Lire le fichier ics");
		ReadCalendarFiles.ReadCalendarFile("resources1/test2.ics");
	}

}
