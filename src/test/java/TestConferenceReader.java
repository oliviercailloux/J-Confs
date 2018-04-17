import java.io.IOException;
import java.text.ParseException;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceReader;
import net.fortuna.ical4j.data.ParserException;

public class TestConferenceReader {

	public static void main(String[] args) throws IOException, ParserException, ParseException {
		
		Conference conf = ConferenceReader.createConference("test2.ics");
		System.out.println(" On va Lire l'objet conference créé à partir du fichier ics ");
		System.out.println(conf);
		System.out.println(conf.toString());
		System.out.println(" \n \n  On va Lire le fichier ics");

		ConferenceReader.ReadCalendarFile("test2.ics");
		
		ConferenceReader.ReadCalendarFile("test2.ics");
		System.out.println("donee");
	}
}
