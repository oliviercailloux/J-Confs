package io.github.oliviercailloux.y2018;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferenceReader;
import net.fortuna.ical4j.data.ParserException;

public class TestConferenceReader {

	public static void main(String[] args) throws IOException, ParserException, ParseException {
		File test=new File (TestConferenceReader.class.getClassLoader().getResource("test2.ics").getFile());
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream(test))) {
			Conference conf = ConferenceReader.createConference(reader);
			System.out.println(" On va Lire l'objet conference créé à partir du fichier ics ");
			System.out.println(conf);
			System.out.println(conf.toString());
			System.out.println(" \n \n  On va Lire le fichier ics");

			ConferenceReader.ReadCalendarFile(reader);

			ConferenceReader.ReadCalendarFile(reader);
			System.out.println("donee");
		}
	}
}
