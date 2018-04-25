package io.github.oliviercailloux.y2018.jconfs;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;

public class ConferencesFromICal implements ConferencesRetriever {

	/**
	 * A constructor to initialize a ConferencesFromICal
	 * 
	 * @throws URISyntaxException
	 */
	public ConferencesFromICal() {
	}

	@Override
	public Set<Conference> retrive() throws IOException, NumberFormatException, ParserException, ParseException {
		String filePath = ConferencesFromICal.class.getClassLoader().getResource("icaldata").getFile();

		File ressourcesDirectory = new File(filePath);

		File[] fileList = ressourcesDirectory.listFiles();

		Set<Conference> setOfConf = new HashSet<>();

		for (File file : fileList) {
			if (file.getName().endsWith(".ics")) {
				setOfConf.add(ConferenceReader.createConference(file.getName()));
			}
		}

		return setOfConf;
	}

	@Override
	public Set<Conference> retrive(DateTime minDate, DateTime maxDate)
			throws IOException, NumberFormatException, ParserException, ParseException {
		Set<Conference> setOfAllConf = retrive();
		Set<Conference> setOfConfFiltred = new HashSet<>();
		for (Conference conf : setOfAllConf) {
			if (conf.getStartDate().after(minDate) && conf.getEndDate().before(maxDate)) {
				setOfConfFiltred.add(conf);

			}

		}
		return setOfConfFiltred;
	}

	@Override
	public Set<Conference> retrive(String fileName)
			throws NumberFormatException, IOException, ParserException, ParseException {
		Set<Conference> setOfConf = new HashSet<>();
		Conference conf = ConferenceReader.createConference(fileName);
		setOfConf.add(conf);
		return setOfConf;
	}

}
