package io.github.oliviercailloux.y2018;

import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import io.github.oliviercailloux.y2018.jconfs.Conference;
import io.github.oliviercailloux.y2018.jconfs.ConferencesFromICal;
import io.github.oliviercailloux.y2018.jconfs.ConferencesShower;
import io.github.oliviercailloux.y2018.jconfs.InvalidConferenceFormatException;
import net.fortuna.ical4j.data.ParserException;

/**
 * it's a main to  test the method ConferencesFiltredByDate
 * @author stanislas
 *
 */
public class ConferencesShowerTest {
	public static void main(String[]args) throws NumberFormatException, IOException, ParserException, InvalidConferenceFormatException {
		ConferencesShower testShower=new ConferencesShower(new ConferencesFromICal());
		Set<Conference> testConf=testShower.conferencesFiltredByDate();
		for (Conference conf :testConf) {
			System.out.println(conf.getTitle());
			System.out.println(conf.getStartDate());
		}
	}
}

