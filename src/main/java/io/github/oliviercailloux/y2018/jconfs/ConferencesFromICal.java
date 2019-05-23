package io.github.oliviercailloux.y2018.jconfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import com.google.common.base.Preconditions;

import net.fortuna.ical4j.data.ParserException;

public class ConferencesFromICal implements ConferencesRetriever {

	@Override
	public Set<Conference> retrieve() throws IOException, NumberFormatException, ParserException, ParseException {
		String filePath = ConferencesFromICal.class.getClassLoader().getResource("icaldata").getFile();
		File ressourcesDirectory = new File(filePath);
		File[] fileList = ressourcesDirectory.listFiles();
		Set<Conference> setOfConf = new LinkedHashSet<>();
		for (File file : fileList) {
			if (file.getName().endsWith(".ics")) {
				try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {
					setOfConf.addAll(ConferenceReader.readConferences(reader));
				}
			}
		}
		return setOfConf;
	}

	@Override
	public Set<Conference> retrieve(LocalDate minDate, LocalDate maxDate)
			throws IOException, NumberFormatException, ParserException, ParseException {
		Set<Conference> setOfAllConf = retrieve();
		Set<Conference> setOfConfFiltred = new LinkedHashSet<>();
		for (Conference conf : setOfAllConf) {
			if (conf.getStartDate().isAfter(minDate) && conf.getEndDate().isBefore(maxDate)) {
				setOfConfFiltred.add(conf);
			}
		}
		return setOfConfFiltred;
	}

	@Override
	public Set<Conference> retrieve(String fileName)
			throws NumberFormatException, IOException, ParserException, ParseException {
		Preconditions.checkNotNull(fileName);
		URL urlcalendar = ConferenceReader.class.getResource(fileName+".ics");		
		FileReader reader=new FileReader(new File(urlcalendar.getFile()));
		return ConferenceReader.readConferences(reader);
	}

}
