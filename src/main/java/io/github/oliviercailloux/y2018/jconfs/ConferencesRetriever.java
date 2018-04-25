package io.github.oliviercailloux.y2018.jconfs;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.DateTime;

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

	public Set<Conference> retrive() throws NumberFormatException, IOException, ParserException, ParseException;

	/**
	 * this method take a date interval and return a restricted set of conference
	 * 
	 * @param  maxDate
	 *   not <code> null</code>
	 * @param minDate 
	 * 		Not  <code> null</code>
	 * @return Set<Conference>,Not <code> null</code>, return empty set if no data
	 *         found
	 */
	public Set<Conference> retrive(DateTime minDate, DateTime maxDate)
			throws NumberFormatException, IOException, ParserException, ParseException;

	/**
	 * this method search a conference file and return a set of conference with the
	 * conference in this file
	 * 
	 * @param title
	 *            Not <code> null</code>
	 * @return
	 */

	public Set<Conference> retrive(String fileName)
			throws NumberFormatException, IOException, ParserException, ParseException;

}
