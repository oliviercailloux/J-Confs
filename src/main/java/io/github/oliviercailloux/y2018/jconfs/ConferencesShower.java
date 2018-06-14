package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;

import com.hp.hpl.jena.rdf.model.EmptyListException;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;

/**
 * this class show Conferences data
 * 
 * @author stanislas
 *
 */
public class ConferencesShower {
	ConferencesRetriever retriever;

	/**
	 * this is a constructor to initialize a ConferencesShower
	 * 
	 * @param retriever
	 */
	public ConferencesShower(ConferencesRetriever retriever) {
		this.retriever = retriever;

	}

	/**
	 * 
	 * take a filename and search every conference with the title given in arg
	 * 
	 * @param title
	 *            not <code>null<code>
	 * @return Set<Conference> Not <code>null<code>
	 * 
	 * @throws Exception
	 */
	public Set<Conference> searchConferenceByTitle(String title) throws Exception {
		if (Objects.isNull(title))
			throw new IllegalArgumentException("title is null");
		Conference conf = null;
		Set<Conference> setOfConfFiltred = new LinkedHashSet<>();
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("please enter a file name");
			String fileName = sc.nextLine();
			Set<Conference> set = retriever.retrive(Objects.requireNonNull(fileName));

			while (set.iterator().hasNext()) {
				conf = set.iterator().next();
				if (conf.getTitle() == title) {
					setOfConfFiltred.add(conf);
				}
			}

		}
		if (setOfConfFiltred.isEmpty())
			throw new EmptyListException("no conferences found");
		return setOfConfFiltred;
	}

	/**
	 * 
	 * read a conference file with nameFile name
	 * 
	 * @return null if conference is not find
	 * 
	 * @throws ParseException
	 * @throws ParserException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public Set<Conference> searchConferenceInFile(String fileName)
			throws NumberFormatException, IOException, ParserException, ParseException {
		Set<Conference> set = retriever.retrive(Objects.requireNonNull(fileName));
		return set;

	}

	/**
	 * this method return a set of Conference with every conferences data in
	 * icaldata directory
	 * 
	 * @return Set<Conference> not <code>null</code>
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ParserException
	 * @throws ParseException
	 */
	public Set<Conference> allConferences() throws NumberFormatException, IOException, ParserException, ParseException {
		return retriever.retrive();
	}

	public Set<Conference> conferencesFiltredByDate()
			throws NumberFormatException, IOException, ParserException, ParseException {
		LocalDate minDate;
		LocalDate maxDate;
		try (Scanner sc = new Scanner(System.in)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			System.out.println("Please enter the minimale date dd/MM/yyyy format");
			minDate = LocalDate.parse(sc.nextLine(), formatter);
			System.out.println("Please enter the maximale date dd/MM/yyyy format ");
			maxDate = LocalDate.parse(sc.nextLine(), formatter);

			if (!minDate.isBefore(maxDate))
				throw new IllegalArgumentException("minDate must be before maxDate");

		}
		return retriever.retrive(minDate, maxDate);

	}
}
