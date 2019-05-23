package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Set;

import net.fortuna.ical4j.data.ParserException;

/**
 * this interface read conference data and return a set of conference
 * 
 * @author stanislas
 *
 */
public interface ConferencesRetriever {

	/**
	 * this method read conference data and return a set of conference
	 * 
	 * @return Set<Conference>
	 */

	public Set<Conference> retrieve() throws NumberFormatException, IOException, ParserException, ParseException;

	/**
	 * this method take a date interval and return a restricted set of conference
	 * 
	 * @param maxDate
	 *            not <code> null</code>
	 * @param minDate
	 *            Not <code> null</code>
	 * @return Set<Conference>,Not <code> null</code>, return empty set if no data
	 *         found
	 */
	public Set<Conference> retrieve(LocalDate minDate, LocalDate maxDate)
			throws NumberFormatException, IOException, ParserException, ParseException;

	/**
	 * this method search an .ics file located in classpath 
	 * and return a set of conferences contain in this file.
	 * 
	 * @param fileName
	 *            Not <code> null</code>
	 * @return
	 */

	public Set<Conference> retrieve(String fileName)
			throws NumberFormatException, IOException, ParserException, ParseException;

}
