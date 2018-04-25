package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

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
	 * ask to the user to write a filename and return a conference created by
	 * reading a file
	 * 
	 * @return null if conference is not find
	 * 
	 * @throws ParseException
	 * @throws ParserException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public Conference searchConference() throws NumberFormatException, IOException, ParserException, ParseException {

		Conference conf = null;
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("please enter a file name");
			String fileName = sc.nextLine();
			Set<Conference> set = retriever.retrive(Objects.requireNonNull(fileName));

			if (set.iterator().hasNext()) {
				conf = set.iterator().next();
			}

		}
		return conf;
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
	public Conference searchConference(String fileName)
			throws NumberFormatException, IOException, ParserException, ParseException {
		Conference conf = null;
		Set<Conference> set = retriever.retrive(Objects.requireNonNull(fileName));

		if (set.iterator().hasNext()) {
			conf = set.iterator().next();
		}

		return conf;

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

	public Set<Conference> filtredConferences()
			throws NumberFormatException, IOException, ParserException, ParseException {
		DateTime minDate;
		DateTime maxDate;
		try (Scanner sc = new Scanner(System.in)) {

			System.out.println("Please enter the minimale date");
			minDate = new DateTime(sc.nextLine());
			System.out.println("Please enter the maximale date");
			maxDate = new DateTime(sc.nextLine());

		}
		return retriever.retrive(minDate, maxDate);

	}
}
